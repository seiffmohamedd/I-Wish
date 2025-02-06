/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package DBO;

import java.io.Serializable;

/**
 *
 * @author Windo
 */
public class Notification implements Serializable{
    private String NotificationText;
    private String TimeStamp;
    private String UserName;

    public Notification(String NotificationText) {
        this.NotificationText = NotificationText;
    }
    
    
    
    public Notification(String NotificationText, String TimeStamp, String UserName) {
        this.NotificationText = NotificationText;
        this.TimeStamp = TimeStamp;
        this.UserName = UserName;
    }

    public String getNotificationText() {
        return NotificationText;
    }

    public void setNotificationText(String NotificationText) {
        this.NotificationText = NotificationText;
    }

    public String getTimeStamp() {
        return TimeStamp;
    }

    public void setTimeStamp(String TimeStamp) {
        this.TimeStamp = TimeStamp;
    }

    public String getUserName() {
        return UserName;
    }

    public void setUserName(String UserName) {
        this.UserName = UserName;
    }

    @Override
    public String toString() {
        return "{"
            + "\"NotificationText\": \"" + NotificationText + "\", "
            + "\"TimeStamp\": \"" + TimeStamp + "\", "
            + "\"UserName\": \"" + UserName + "\""
            + "}";
        }
    
    
}
