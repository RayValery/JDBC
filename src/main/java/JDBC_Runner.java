import java.io.IOException;
import java.sql.*;

public class JDBC_Runner {

    static final String userName = "root";
    static final String password = "lerona-makarona18239";
    static final String URL = "jdbc:mysql://127.0.0.1:3306/my_project?useUnicode=true&characterEncoding=UTF-8&zeroDateTimeBehavior=CONVERT_TO_NULL&serverTimezone=GMT";

    public static void main(String[] args) throws IOException {
        try (Connection connection = DriverManager.getConnection(URL, userName, password)) {
            System.out.println("Connected to DataBase");
            String sql;
            sql = "create table Books2 (id int not null auto_increment, name varchar(30) not null, dt date, img longblob, primary key (id));";
            Statement statement = connection.createStatement();
            statement.executeUpdate(sql);

            sql = "insert into Books2 (name) values ('Angels_and_Daemons');";
            statement.executeUpdate(sql);


            sql = "insert into Books2 (name) values ('Solomon_key');";
            statement.executeUpdate(sql);



            statement.executeUpdate("drop table Books2;");

        } catch (SQLSyntaxErrorException e) {
            System.out.println("You have an error in your SQL syntax");
            e.printStackTrace();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}

