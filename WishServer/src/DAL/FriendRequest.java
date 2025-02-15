package DAL;

import DBO.Friends;
import DBO.PersonFriends;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

/**
 *
 * @author Lenovo
 */
public class FriendRequest {
   private Connection DBCon = DBConnection.getConnection();
    private final String query = "select personUserName from personFriends where friendUserName = ? and status = 'Pending'";
    private int executeResult;
    private String userName;
    private ArrayList<PersonFriends> personFriends = new ArrayList<>();

    public FriendRequest(String userName) throws SQLException {
        this.userName = userName;
        System.out.println("Fetching friends list for user: " + userName);
        getPersonFriends();       
    }

    public int getExecuteResult() {
        return executeResult;
    }
    public ArrayList<PersonFriends> getPersonFriendsArr() {
        return personFriends;
    }
    
    private void getPersonFriends() throws SQLException {
        PreparedStatement statement = DBCon.prepareStatement(query);
        statement.setString(1, userName);
        ResultSet rs = statement.executeQuery();
        while(rs.next()){
            String requesterUsername = rs.getString("personUserName");
            personFriends.add(new PersonFriends(requesterUsername, userName, "Pending"));
        }
        

    }
   public boolean acceptFriendRequest(String personUserName, String friendUserName) {
    String updateQuery = "UPDATE personFriends SET status = 'Accepted' WHERE personUserName = ? AND friendUserName = ? AND status = 'Pending'";
    String insertNotificationQuery = "INSERT INTO Notification (notificationText, timeStamp, userName) VALUES (?, SYSTIMESTAMP, ?)";
    
    boolean updateSuccess = executeUpdate(updateQuery, personUserName, friendUserName);
    
    if (updateSuccess) {
        String notificationText = "Your friend request to " + friendUserName + " has been accepted!";
        executeUpdate(insertNotificationQuery, notificationText, personUserName);
    }
    
    return updateSuccess;
}

public boolean declineFriendRequest(String personUserName, String friendUserName) {
    String deleteQuery = "DELETE FROM personFriends WHERE personUserName = ? AND friendUserName = ? AND status = 'Pending'";
    return executeUpdate(deleteQuery, personUserName, friendUserName);
}

private boolean executeUpdate(String query, String param1, String param2) {
    try (PreparedStatement statement = DBCon.prepareStatement(query)) {
        statement.setString(1, param1);
        statement.setString(2, param2);
        int rowsAffected = statement.executeUpdate();
        return rowsAffected > 0;
    } catch (SQLException ex) {
        ex.printStackTrace();
        return false;
    }
}

    
}
