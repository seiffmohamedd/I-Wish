package DAL;

import DBO.Friends;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

public class FriendsList {
    private final Connection DBCon;
    private final String query = "SELECT FRIENDUSERNAME FROM FRIENDS WHERE PERSONUSERNAME = ? AND STATUS = 'Accepted'";
    private int executeResult;
    private String userName;
    private ArrayList<Friends> userFriendsList;

    public FriendsList(String userName) throws SQLException {
        DBCon = establishConnection();  
        this.userName = userName;
        this.userFriendsList = new ArrayList<>();  

        System.out.println("Fetching friends list for user: " + userName);
        getFriends(); 
    }
    
    private Connection establishConnection() throws SQLException {
        System.out.println("Establishing connection to the database...");
        return new DBConnection().getConection();
    }
    
    public int getExecuteResult() {
        return executeResult;
    }

    public ArrayList<Friends> getUserFriendsList() {
        return userFriendsList;
    }

   private void getFriends() throws SQLException {
    System.out.println("Executing query to fetch friends list: " + query);

    PreparedStatement statement = DBCon.prepareStatement(query);
    statement.setString(1, userName);

    System.out.println("Query parameter (userName): " + userName);

    ResultSet rs = statement.executeQuery();

    int resultCount = 0;
    
    userFriendsList.clear();
    
    while (rs.next()) {  
        resultCount++;
        Friends friend = new Friends(userName, rs.getString("FRIENDUSERNAME"));
        userFriendsList.add(friend);
        System.out.println("Friend added: " + friend.getFriendUserName());
    }

    if (resultCount == 0) {
        System.out.println("No friends found for user: " + userName);
        executeResult = 0;
    } else {
        executeResult = 1;
        System.out.println("Total friends found: " + resultCount);
    }
}

}
