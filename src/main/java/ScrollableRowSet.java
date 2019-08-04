import java.sql.*;

public class ScrollableRowSet {
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

            Statement statement2 =
                    connection.createStatement(ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_READ_ONLY);
                    // INSENSITIVE - scrolling(forward and backward) without changes
                    // SENSITIVE - scrolling(forward and backward) with changes
            ResultSet scrollResultSet = statement2.executeQuery("select * from Books2;");

            System.out.println("\nForward:\n");
            while (scrollResultSet.next()){
                System.out.println(scrollResultSet.getString("name"));
            }

            System.out.println("\nBackward:\n");
            while (scrollResultSet.previous()){
                System.out.println(scrollResultSet.getString("name"));
            }

            if(scrollResultSet.relative(3)){                        // сдвиг на 3 вправо
                System.out.println("\nrelative(3): "+scrollResultSet.getString("name"));
            }
            if(scrollResultSet.relative(-2)){                        // сдвиг на 2 влево
                System.out.println("relative(-2): "+scrollResultSet.getString("name"));
            }
            if (scrollResultSet.absolute(2)){                         // 2-ой от начала
                System.out.println("\nabsolute(2): "+scrollResultSet.getString("name"));
            }
            if (scrollResultSet.first()){                                  // first row
                System.out.println("\nfirst: "+scrollResultSet.getString("name"));
            }
            if (scrollResultSet.last()){                                    // last row
                System.out.println("last: "+scrollResultSet.getString("name"));
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
