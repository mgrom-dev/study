import java.sql.*;

public class DBConnection
{
    private static Connection connection;

    private static final String DB_NAME = "learn";
    private static final String DB_USER = "newuser";
    private static final String DB_PASS = "ya78yrc8n4w3984";

    public static Connection getConnection()
    {
        if(connection == null)
        {
            try {
                connection = DriverManager.getConnection(
                    "jdbc:mysql://localhost:3306/" + DB_NAME +
                    "?user=" + DB_USER + "&password=" + DB_PASS);
                connection.createStatement().execute("DROP TABLE IF EXISTS voter_count");
                connection.createStatement().execute("CREATE TABLE voter_count(" +
                        "id INT NOT NULL AUTO_INCREMENT, " +
                        "name TINYTEXT NOT NULL, " +
                        "birthDate DATE NOT NULL, " +
                        "`count` INT NOT NULL, " +
                        "PRIMARY KEY(id), " +
                        "UNIQUE KEY name_date(name(50), birthDate))");
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
        return connection;
    }

    public static void executeMultiInsert(StringBuilder values) {
        try {
            values.insert(0, "INSERT INTO voter_count(name, birthDate, `count`) VALUES ");
            values.append(" ON DUPLICATE KEY UPDATE `count` = `count` + 1");
            DBConnection.getConnection().createStatement().execute(values.toString());
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public static void printVoterCounts() {
        try {
            String sql = "SELECT name, birthDate, `count` FROM voter_count WHERE `count` > 1";
            ResultSet rs = DBConnection.getConnection().createStatement().executeQuery(sql);
            while (rs.next()) {
                System.out.println("\t" + rs.getString("name") + " (" +
                        rs.getString("birthDate") + ") - " + rs.getInt("count"));
            }
            rs.close();
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }
}
