import java.sql.*;

public class Transactions {
    static final String userName = "root";
    static final String password = "lerona-makarona18239";
    static final String URL = "jdbc:mysql://127.0.0.1:3306/my_project?useUnicode=true&characterEncoding=UTF-8&zeroDateTimeBehavior=CONVERT_TO_NULL&serverTimezone=GMT";

    public static void main(String[] args) throws SQLException {
        Connection connection = DriverManager.getConnection(URL, userName, password);
        try  {
            System.out.println("Connected to DataBase");
            connection.setAutoCommit(false);
            Statement statement = connection.createStatement();
            statement.executeUpdate("drop table Books2;");
            statement.executeUpdate("create table Books2 (id int not null auto_increment, name varchar(30) not null, dt date, img longblob, primary key (id));");
            statement.executeUpdate("insert into Books2 (name) values ('Angels_and_Daemons');");

            Savepoint savepoint = connection.setSavepoint();

            statement.executeUpdate("insert into Books2 (name) values ('Inferno');");
            statement.executeUpdate("insert into Books2 (name) values ('Solomon_key');");
            statement.executeUpdate("insert into Books2 (name) values ('DaVinci_code');");

            //connection.rollback(savepoint);
            connection.commit();

        } catch (SQLSyntaxErrorException e) {
            System.out.println("You have an error in your SQL syntax");
            e.printStackTrace();
        } catch (SQLException e) {
            connection.rollback();
            e.printStackTrace();

        }
    }
}
