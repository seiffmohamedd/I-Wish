/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package DAL;

import DBO.ChargePoints;
import DBO.ContributeData;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

/**
 *
 * @author Windo
 */
public class MakeContribute {
    private ContributeData RequestData;
    private Connection DBCon = DBConnection.getConnection();
    private final String query1 = "UPDATE WISHLISTITEM SET REMAINING = ? WHERE UserName = ? AND ITEMID = ?";
    private final String query2 = "insert into WISHLISTITEMCONTRIBUTE(USERNAME,ITEMID,FRIENDUSERNAME,CONTRIBUTEAMOUNT) values(?,?,?,?)";
    private final String query3 = "UPDATE PERSON SET POINTS = POINTS - ? WHERE USERNAME = ?";
    private final String query4 = "INSERT INTO NOTIFICATION (USERNAME, notificationtext) VALUES (?, ?)";
    
    private int executeResult;
    public MakeContribute(ContributeData RequestData) throws SQLException{
        this.RequestData = RequestData;
        System.out.println("from MakeContribute constructors the data is :" + RequestData.toString());
        updateRemaining();
        insertContribution();
        subtractPoints();
        insertNotification();
    }

    
    public int getExecuteResult() {
        return executeResult;
    }
    
    private void updateRemaining() throws SQLException{
        PreparedStatement statement = DBCon.prepareStatement(query1);
        Double newRemaining = RequestData.getRemaining() - Double.parseDouble(String.valueOf(RequestData.getContributionAmount()));
        statement.setDouble(1, newRemaining);
        statement.setString(2, RequestData.getFirendUserName());
        statement.setInt(3, RequestData.getItemID());
        executeResult = statement.executeUpdate();
        System.out.println("the result for the excecution for update Remaining is : "+executeResult);
    }
    private void insertContribution() throws SQLException{
        if(executeResult == 1){
            PreparedStatement statement = DBCon.prepareStatement(query2);
            statement.setString(1,RequestData.getUserName());
            statement.setInt(2,RequestData.getItemID());
            statement.setString(3, RequestData.getFirendUserName());
            statement.setDouble(4,Double.parseDouble(String.valueOf(RequestData.getContributionAmount())));
            executeResult = statement.executeUpdate();
            System.out.println("the result for the excecution for insert Contribution : "+executeResult);
        }
        
    }
    
     private void subtractPoints() throws SQLException{
       if(executeResult == 1){
            PreparedStatement statement = DBCon.prepareStatement(query3);
            statement.setDouble(1,Double.parseDouble(String.valueOf(RequestData.getContributionAmount())));
            statement.setString(2, RequestData.getUserName());
            System.out.println("the user data is "+ RequestData.toString());
            executeResult = statement.executeUpdate();
            System.out.println("the result for the excecution for subtractPoints Contribution : "+executeResult);
        }
         
     }
     private void insertNotification() throws SQLException {
        if (executeResult == 1) {  
            PreparedStatement statement = DBCon.prepareStatement(query4);
            String message = RequestData.getUserName() + " contributed " + RequestData.getContributionAmount() + " for your item (ID: " + RequestData.getItemID() + ")";
            statement.setString(1, RequestData.getFirendUserName());
            statement.setString(2, message);
            int notificationResult = statement.executeUpdate();
            System.out.println("Execution result for insertNotification: " + notificationResult);
        }
    }
    
     
}
