import javax.sql.rowset.CachedRowSet;
import javax.sql.rowset.RowSetFactory;
import javax.sql.rowset.RowSetProvider;
import java.sql.*;

public class UpdateResultSet {
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

            Statement statement3 = connection.createStatement(ResultSet.TYPE_SCROLL_SENSITIVE, ResultSet.CONCUR_UPDATABLE);
            ResultSet resultSet3 = statement3.executeQuery("select * from Books2");

            resultSet3.last();
            resultSet3.updateString("name", "new Value");
            resultSet3.rowUpdated();

            resultSet3.moveToInsertRow();
            resultSet3.insertRow();
            resultSet3.updateString("name", "inserted row");
            resultSet3.rowUpdated();

            resultSet3.absolute(2);
            resultSet3.deleteRow();

            resultSet3.beforeFirst();
            while (resultSet3.next()){
                System.out.println();
            }


            // Cached RowSet
            RowSetFactory factory = RowSetProvider.newFactory();
            CachedRowSet cachedRowSet = factory.createCachedRowSet();
            cachedRowSet.populate(resultSet3);
            cachedRowSet.setCommand("select * from Books2 where id = ? ;");
            cachedRowSet.setInt(1,1);
            cachedRowSet.setPageSize(20);
            cachedRowSet.execute();
            do{
                while (cachedRowSet.next()){
                    System.out.println(cachedRowSet.getInt("id"));
                    System.out.println(cachedRowSet.getString("name"));
                }
            } while (cachedRowSet.nextPage());

            statement.executeUpdate("drop table Books2;");

        } catch (SQLSyntaxErrorException e) {
            System.out.println("You have an error in your SQL syntax");
            e.printStackTrace();
        } catch (SQLException e) {
            e.printStackTrace();

        }
    }
}

