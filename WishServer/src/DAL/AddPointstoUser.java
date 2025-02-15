/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package DAL;

import DBO.ChargePoints;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

/**
 *
 * @author Windo
 */
public class AddPointstoUser {
    private ChargePoints RequestData;
    private Connection DBCon = DBConnection.getConnection();
    private final String query1 = "Select count(*) as count from CREDITCARD where userName = ? and CREDITCARDNUMBER = ? and CVV = ? ";
    private final String query2 = "update PERSON set Points = Points + ? where userName = ? ";
    private int executeResult;
    
    public AddPointstoUser(ChargePoints RequestData) throws SQLException{
        this.RequestData = RequestData;
        if(ValidateCreditCard(query1) == 1){
            addPoints(query2);
           
        }else{
            executeResult = -5;
           
        }
    }

    public int getExecuteResult() {
        return executeResult;
    }
    
    private int ValidateCreditCard(String query) throws SQLException {
        PreparedStatement statement = DBCon.prepareStatement(query);
        statement.setString(1, RequestData.getUserName());
        statement.setString(2, RequestData.getCreditCardNumber() );
        statement.setInt(3, RequestData.getCvv());
        ResultSet rs = statement.executeQuery();
        rs.next();
        if(rs.getInt(1)> 0)
            return 1;
        else
            return -5;
     }
    
    private void addPoints(String query) throws SQLException {
        PreparedStatement statement = DBCon.prepareStatement(query);
        statement.setInt(1, RequestData.getNumberOfPoints());
        statement.setString(2, RequestData.getUserName());
        executeResult = statement.executeUpdate();
         System.out.println("the result for the excecution for remove item is : "+executeResult);
     }
}
