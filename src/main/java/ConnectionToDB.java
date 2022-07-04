
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class ConnectionToDB {
    private static final String MYSQL_DRIVER = "com.mysql.cj.jdbc.Driver";
    private static final String MYSQL_DB_URL = "jdbc:mysql://localhost:3306/jdbc_learning";
    private static final String MYSQL_DB_USER = "";
    private static final String MYSQL_DB_PASSWORD = "";

    public static Connection connectToDB() throws ClassNotFoundException, SQLException {
        Class.forName(MYSQL_DRIVER);
        Connection con = DriverManager.getConnection(MYSQL_DB_URL, MYSQL_DB_USER, MYSQL_DB_PASSWORD);
        return con;
    }
}
