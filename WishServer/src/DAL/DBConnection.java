/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package DAL;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.logging.Level;
import java.util.logging.Logger;
import oracle.jdbc.OracleDriver;

/**
 *
 * @author hekal
 */
public class DBConnection {
    private final String ip = "localhost";
    private final String port = "1521";
    private final String serviceID = "XE";
    private final String dbSchema = "iwish";
    private final String schemaPassword = "iwish";
    private Connection con;
    
    
    public Connection getConection() throws SQLException{
        DriverManager.registerDriver(new OracleDriver());
        con = DriverManager.getConnection("jdbc:oracle:thin:@"+ip+ ":" + port + ":"  + serviceID , dbSchema , schemaPassword);
        return con;
    }
}
