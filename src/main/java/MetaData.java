import java.sql.*;

public class MetaData {
    static final String userName = "root";
    static final String password = "lerona-makarona18239";
    static final String URL = "jdbc:mysql://127.0.0.1:3306/my_project?useUnicode=true&characterEncoding=UTF-8&zeroDateTimeBehavior=CONVERT_TO_NULL&serverTimezone=GMT";

    public static void main(String[] args) throws SQLException {
        try (Connection connection = DriverManager.getConnection(URL, userName, password)) {
            System.out.println("Connected to DataBase");
            Statement statement = connection.createStatement();
            statement.executeUpdate("create table Books2 (id int not null auto_increment, name varchar(30) not null, dt date, img longblob, primary key (id));");
            statement.executeUpdate("insert into Books2 (name) values ('Angels_and_Daemons');");
            statement.executeUpdate("insert into Books2 (name) values ('Inferno');");
            statement.executeUpdate("insert into Books2 (name) values ('Solomon_key');");
            statement.executeUpdate("insert into Books2 (name) values ('DaVinci_code');");

            DatabaseMetaData databaseMetaData = connection.getMetaData();
            ResultSet metaDataResultSet = databaseMetaData.getTables(null,null,null, new String[] {"Table"});
            while (metaDataResultSet.next()){
                metaDataResultSet.getString(3); // metaData about dataBase
            }

            System.out.println("==============================");

            ResultSet resultSet = connection.createStatement().executeQuery("select * from Books2;");
            ResultSetMetaData resultSetMetaData = resultSet.getMetaData();    // metaData about table
            for (int i = 1; i <= resultSetMetaData.getColumnCount(); i++) {
                System.out.println(resultSetMetaData.getColumnLabel(i));
            }


            statement.executeUpdate("drop table Books2;");

        } catch (SQLSyntaxErrorException e) {
            System.out.println("You have an error in your SQL syntax");
            e.printStackTrace();
        } catch (SQLException e) {
            e.printStackTrace();

        }
    }
}
