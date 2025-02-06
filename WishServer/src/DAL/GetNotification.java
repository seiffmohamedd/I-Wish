/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package DAL;

import DBO.Notification;
import DBO.WishList;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

/**
 *
 * @author Windo
 */
public class GetNotification {
    private final Connection DBCon;
    private final String query = "select NotificationText, TIMESTAMP, USERNAME from Notification where UserName = ?  order by TIMESTAMP DESC";
    private int executeResult;
    private String UserName;
    private ArrayList<Notification> userNotificationtsArr = new ArrayList<>();
    
    public GetNotification(String UserName) throws SQLException{
        DBCon = establishConnection();
        this.UserName = UserName;
        System.out.println("the username for Notifications is : " + UserName);
        getNotification();
    
    }

    public int getExecuteResult() {
        return executeResult;
    }

    public ArrayList<Notification> getUserNotificationtsArr() {
        return userNotificationtsArr;
    }
    
    
    private Connection establishConnection() throws SQLException{
        return new DBConnection().getConection();
    }
    
    private void getNotification() throws SQLException {
        PreparedStatement statement = DBCon.prepareStatement(query);
        statement.setString(1, UserName);
        ResultSet rs = statement.executeQuery();
        while(rs.next()){
            Notification tempNotification = new Notification(rs.getString("NotificationText"),rs.getString("TIMESTAMP"), rs.getString("USERNAME") );
            System.out.println("tempNotification from db is : "+ tempNotification.getNotificationText());
            userNotificationtsArr.add(tempNotification);
            executeResult = 1;
        }
    
    }
    
}
