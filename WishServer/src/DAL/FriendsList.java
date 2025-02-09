package DAL;

import DBO.Friends;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

public class FriendsList {
    private final Connection DBCon;
    private final String query = "SELECT FRIENDUSERNAME FROM PERSONFRIENDS WHERE PERSONUSERNAME = ? AND STATUS = 'Accepted'";
    private int executeResult;
    private String userName;
    private ArrayList<Friends> userFriendsListArr = new ArrayList<>();

    public FriendsList(String userName) throws SQLException {
        DBCon = establishConnection();
        this.userName = userName;
        System.out.println("Fetching friends list for user: " + userName);
        getFriends();
        DBCon.close();
    }

    private Connection establishConnection() throws SQLException {
        return new DBConnection().getConection();
    }

    public int getExecuteResult() {
        return executeResult;
    }

    public ArrayList<Friends> getUserFriendsListArr() {
        return userFriendsListArr;
    }

    private void getFriends() throws SQLException {
        PreparedStatement statement = DBCon.prepareStatement(query);
        statement.setString(1, userName);
        ResultSet rs = statement.executeQuery();

        while (rs.next()) {
            Friends tempFriend = new Friends(userName, rs.getString("FRIENDUSERNAME"));
            System.out.println("Friend found: " + tempFriend.getFriendUserName());
            userFriendsListArr.add(tempFriend);
            executeResult = 1;
        }

        if (userFriendsListArr.isEmpty()) {
            executeResult = 0;
        }
    }
}
