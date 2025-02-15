/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package DAL;
import DBO.User;

import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.ParseException;
import java.sql.*;
import java.text.SimpleDateFormat;
/**
 *
 * @author Windo
 */
public class Login {
    private User user;
    private String ifUserExistsQuery = "SELECT * FROM PERSON WHERE USERNAME = ? and PASSWORD = ?";
    private Connection DBCon = DBConnection.getConnection();
    private int executeResult;
    public Login(User user) throws SQLException, ParseException{
        this.user = user;
       
//        System.out.println("db connection established");
        executeResult = getLogInResponse();
       
    }
    
    public int getExecuteResult() {
        return executeResult;
    }

    public User getUser() {
        return user;
    }
    
    
    
    private boolean authenticateUser() throws SQLException{
        PreparedStatement statement = DBCon.prepareStatement(ifUserExistsQuery);
        statement.setString(1, user.getUserName());
        statement.setString(2, user.getPassword());
        ResultSet rs = statement.executeQuery();
        if(!rs.next())
            return false;
        Date birthDate = null;
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        if (rs.getString("USERNAME").equals(user.getUserName())){
            birthDate = rs.getDate("BIRTHDATE"); // Get the SQL Date
            String birthDateString = (birthDate != null) ? sdf.format(birthDate) : "N/A"; // Convert to String
            user= new User(rs.getString("USERNAME"),rs.getString("FIRSTNAME"), rs.getString("LASTNAME"), "password", rs.getString("GENDER"), birthDateString, rs.getString("PHONE"),rs.getInt("POINTS"),rs.getInt("WISHLISTID"));
            return true;
        }else
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
