/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package DAL;
import DBO.User;
import Requests.SendRespond;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
/**
 *
 * @author Windo
 */
public class Login {
    private User user;
    private Connection DBCon;
    private String ifUserExistsQuery = "SELECT COUNT(*) as isexist FROM PERSON WHERE USERNAME = ? and PASSWORD = ?";
    private SendRespond respond;
    private int executeResult;
    public Login(User user) throws SQLException, ParseException{
        this.user = user;
        DBCon = establishConnection();
//        System.out.println("db connection established");
        executeResult = getLogInResponse();
    }
    
    private Connection establishConnection() throws SQLException{
        return new DBConnection().getConection();
    }

    public int getExecuteResult() {
        return executeResult;
    }
    
    
    private boolean authenticateUser() throws SQLException{
        PreparedStatement statement = DBCon.prepareStatement(ifUserExistsQuery);
        statement.setString(1, user.getUserName());
        statement.setString(2, user.getPassword());
        ResultSet rs = statement.executeQuery();
        rs.next();
        if (rs.getInt(1) > 0 )
            return true;
        else
            return false;
            
    }
    
    private int getLogInResponse() throws SQLException{
        if (authenticateUser()) {
            return 1;
        } else {
            return 0;
        }
    }
    
     
}
