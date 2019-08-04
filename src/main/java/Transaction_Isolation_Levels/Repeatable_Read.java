package Transaction_Isolation_Levels;

import java.sql.*;

public class Repeatable_Read {
    static final String userName = "root";
    static final String password = "lerona-makarona18239";
    static final String URL = "jdbc:mysql://127.0.0.1:3306/my_project?useUnicode=true&characterEncoding=UTF-8&zeroDateTimeBehavior=CONVERT_TO_NULL&serverTimezone=GMT";

    public static void main(String[] args) throws SQLException, InterruptedException {
        try (Connection connection = DriverManager.getConnection(URL, userName, password)) {
            System.out.println("Connected to DataBase");
            Statement statement = connection.createStatement();
            connection.setAutoCommit(false);
            connection.setTransactionIsolation(Connection.TRANSACTION_REPEATABLE_READ);  // protection against NON-REPEATABLE READ

            statement.executeUpdate("drop table Books2;");
            statement.executeUpdate("create table Books2 (id int not null auto_increment, name varchar(30) not null, dt date, img longblob, primary key (id));");
            statement.executeUpdate("insert into Books2 (name) values ('Inferno');");

            ResultSet resultSet = statement.executeQuery("select * from Books2;");
            while (resultSet.next()) {
                System.out.println(resultSet.getString("name"));
            }

            new OtherTransaction().start();
            //Thread.sleep(2000);

            ResultSet resultSet2 = statement.executeQuery("select * from Books2;");
            while (resultSet2.next()) {
                System.out.println(resultSet2.getString("name"));
            }

            connection.commit();
        }
    }

    static class OtherTransaction extends Thread {
        @Override
        public void run() {
            try (Connection connection = DriverManager.getConnection(URL, userName, password)) {
                System.out.println("Connected to DataBase");
                Statement statement = connection.createStatement();
                connection.setAutoCommit(false);
                connection.setTransactionIsolation(Connection.TRANSACTION_REPEATABLE_READ);  // protection against NON-REPEATABLE READ

                statement.execute("update Books2 set name = 'new value' where id = 1;");
                connection.commit();

            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
    }
}
