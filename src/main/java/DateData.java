import java.sql.*;

public class DateData {
    static final String userName = "root";
    static final String password = "lerona-makarona18239";
    static final String URL = "jdbc:mysql://127.0.0.1:3306/my_project?useUnicode=true&characterEncoding=UTF-8&zeroDateTimeBehavior=CONVERT_TO_NULL&serverTimezone=GMT";

    public static void main(String[] args) throws SQLException {
        try (Connection connection = DriverManager.getConnection(URL, userName, password)) {
            System.out.println("Connected to DataBase");
            Statement statement = connection.createStatement();
            statement.executeUpdate("create table Books2 (id int not null auto_increment, name varchar(30) not null, dt date, img longblob, primary key (id));");

            PreparedStatement dateStatement =        //protection against Injection using PreparedStatement
                    connection.prepareStatement("insert into Books2 (name, dt) values ('Inferno', ?);");
            dateStatement.setDate(1, new Date(1564743895136L));
            dateStatement.execute();

            ResultSet dateResultSet = dateStatement.executeQuery("select * from Books2;");
            while (dateResultSet.next()){
                System.out.println(dateResultSet.getDate("dt"));
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