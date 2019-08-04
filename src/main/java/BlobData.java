import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.io.OutputStream;
import java.sql.*;

public class BlobData {
    static final String userName = "root";
    static final String password = "lerona-makarona18239";
    static final String URL = "jdbc:mysql://127.0.0.1:3306/my_project?useUnicode=true&characterEncoding=UTF-8&zeroDateTimeBehavior=CONVERT_TO_NULL&serverTimezone=GMT";

    public static void main(String[] args) throws SQLException, IOException {
        try (Connection connection = DriverManager.getConnection(URL, userName, password)) {
            System.out.println("Connected to DataBase");
            Statement statement = connection.createStatement();
            statement.executeUpdate("create table Books2 (id int not null auto_increment, name varchar(30) not null, dt date, img longblob, primary key (id));");

            BufferedImage image = ImageIO.read(new File("smile.jpg"));
            Blob blob = connection.createBlob();
            try (OutputStream outputStream = blob.setBinaryStream(1)) {
                ImageIO.write(image, "jpg", outputStream);
            }

            PreparedStatement statement1 =
                    connection.prepareStatement("insert into Books2 (name, img) values (?,?);");
            statement1.setString(1, "DaVinci_Code");
            statement1.setBlob(2, blob);
            statement1.execute();

            ResultSet resultSet = statement1.executeQuery("select * from Books2");
            if (resultSet.next()){
                Blob blob2 = resultSet.getBlob("img");
                BufferedImage image2 = ImageIO.read(blob.getBinaryStream());
                File outputFIle = new File("saved.png");
                ImageIO.write(image,"png", outputFIle);
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
