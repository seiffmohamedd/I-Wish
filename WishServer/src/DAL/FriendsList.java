package DAL;

import DBO.Friends;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

public class FriendsList {
    private Connection DBCon = DBConnection.getConnection();
    private final String query = 
    "SELECT PERSONUSERNAME AS Friend FROM PERSONFRIENDS WHERE FRIENDUSERNAME = ? AND STATUS = 'Accepted' " +
    "UNION " +
    "SELECT FRIENDUSERNAME AS Friend FROM PERSONFRIENDS WHERE PERSONUSERNAME = ? AND STATUS = 'Accepted'";
    private int executeResult;
    private String userName;
    private ArrayList<Friends> userFriendsListArr = new ArrayList<>();

    public FriendsList(String userName) throws SQLException {
       
        this.userName = userName;
        System.out.println("Fetching friends list for user: " + userName);
        getFriends();
    }
     public FriendsList(Friends friendRequest) throws SQLException {
        sendFriendRequest(friendRequest);
       
    }


    public int getExecuteResult() {
        return executeResult;
    }

    public ArrayList<Friends> getUserFriendsListArr() {
        return userFriendsListArr;
    }
    
    private void sendFriendRequest(Friends friendRequest) throws SQLException {
        String friendRequestQuery = "INSERT INTO PERSONFRIENDS (PERSONUSERNAME, FRIENDUSERNAME, STATUS, TIMESTAMP) VALUES (?, ?, ?, ?)";
        String notificationQuery = "INSERT INTO Notification (notificationTEXT, timeStamp, userName) VALUES (?, ?, ?)";

        try (
            PreparedStatement stmt = DBCon.prepareStatement(friendRequestQuery);
            PreparedStatement notifStmt = DBCon.prepareStatement(notificationQuery)
        ) {
            stmt.setString(1, friendRequest.getPersonUserName());
            stmt.setString(2, friendRequest.getFriendUserName());
            stmt.setString(3, "Pending");
            stmt.setTimestamp(4, new java.sql.Timestamp(System.currentTimeMillis()));
            executeResult = stmt.executeUpdate() > 0 ? 1 : 0;

            if (executeResult > 0) {
                String notificationMessage = "Friend request from " + friendRequest.getPersonUserName();
                notifStmt.setString(1, notificationMessage);
                notifStmt.setTimestamp(2, new java.sql.Timestamp(System.currentTimeMillis()));
                notifStmt.setString(3, friendRequest.getFriendUserName());
                notifStmt.executeUpdate();
            }
        }
    }


    private void getFriends() throws SQLException {
        PreparedStatement statement = DBCon.prepareStatement(query);
        statement.setString(1, userName);
        statement.setString(2, userName); 
        ResultSet rs = statement.executeQuery();

        while (rs.next()) {
            Friends tempFriend = new Friends(userName, rs.getString("Friend"));
            System.out.println("Friend found: " + tempFriend.getFriendUserName());
            userFriendsListArr.add(tempFriend);
            executeResult = 1;
        }

        if (userFriendsListArr.isEmpty()) {
            executeResult = 0;
        }
    }
}
