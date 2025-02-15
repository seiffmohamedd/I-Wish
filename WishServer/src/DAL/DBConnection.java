package DAL;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.logging.Level;
import java.util.logging.Logger;
import oracle.jdbc.OracleDriver;

/**
 * Singleton Database Connection Class
 * Ensures only one connection instance is created and reused
 */
public class DBConnection {
    private static final String IP = "localhost";
    private static final String PORT = "1521";
    private static final String SERVICE_ID = "XE";
    private static final String DB_SCHEMA = "iwish";
    private static final String SCHEMA_PASSWORD = "iwish";
    
    private static Connection con = null;

    // Private constructor to prevent instantiation
    private DBConnection() {}

    // Static method to get the single instance of the connection
    public static Connection getConnection() {
        if (con == null) {
            synchronized (DBConnection.class) {
                if (con == null) { // Double-check locking for thread safety
                    try {
                        DriverManager.registerDriver(new OracleDriver());
                        con = DriverManager.getConnection(
                                "jdbc:oracle:thin:@" + IP + ":" + PORT + ":" + SERVICE_ID,
                                DB_SCHEMA,
                                SCHEMA_PASSWORD
                        );
                    } catch (SQLException ex) {
                        Logger.getLogger(DBConnection.class.getName()).log(Level.SEVERE, "Database Connection Failed", ex);
                    }
                }
            }
        }
        return con;
    }

    // Close the connection (optional)
    public static void closeConnection() {
        if (con != null) {
            try {
                con.close();
                con = null; // Reset connection after closing
            } catch (SQLException ex) {
                Logger.getLogger(DBConnection.class.getName()).log(Level.SEVERE, "Failed to close connection", ex);
            }
        }
    }
}