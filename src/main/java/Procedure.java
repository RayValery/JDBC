import java.sql.*;

public class Procedure {
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

            /*
              create procedure BooksCount (out n int)
              begin
              select count(*) into n from Books;
              end
            */

            CallableStatement callableStatement = connection.prepareCall("{call BooksCount(?)}");
            callableStatement.registerOutParameter(1, Types.INTEGER); // type of the OutParameter
            callableStatement.execute();
            System.out.println(callableStatement.getInt(1));

            /*
             create procedure getBooks (bookId int)
             begin
             select * from books where id = bookId;
             end
            */

            CallableStatement callableStatement2 = connection.prepareCall("{call getBooks(?)}");
            callableStatement2.setInt(1,1); // value of InParameter
            if (callableStatement2.execute()){
                ResultSet procedureResultSet = callableStatement2.getResultSet();
                while (procedureResultSet.next()){
                    System.out.println(procedureResultSet.getInt("id"));
                    System.out.println(procedureResultSet.getString("name"));
                }
            }

            /*
              create procedure getCount ()
              begin
              select count(*) from Books;
              select count(*) from person;
              end
            */

            CallableStatement callableStatement3 = connection.prepareCall("{call getCount(?)}"); // without "?" ???
            boolean hasResults = callableStatement3.execute();
            while (hasResults){
                ResultSet resultSet = callableStatement3.getResultSet();
                while (resultSet.next()){
                System.out.println(resultSet.getInt(1));}
                hasResults = callableStatement3.getMoreResults();
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

