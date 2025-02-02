/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package iwishserver;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import oracle.jdbc.OracleDriver;
import org.json.JSONObject;

/**
 *
 * @author mohamedhekal
 */
public class dbconnection {
    public Connection con;
    public dbconnection() throws SQLException{
        DriverManager.registerDriver(new OracleDriver());
        con = DriverManager.getConnection("jdbc:oracle:thin:@//localhost:1521/freepdb1","iwish","iwish");
        //System.out.print(con);
    }
    
    
    
   
    
}
