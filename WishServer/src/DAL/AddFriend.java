package DAL;

import DBO.Friends;
import DBO.User;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

public class AddFriend {
    private Connection DBCon = DBConnection.getConnection();
//    private final String query = "SELECT USERNAME FROM PERSON WHERE USERNAME LIKE ? AND USERNAME != ?";
 private final String query = "SELECT USERNAME FROM PERSON " +
                             "WHERE USERNAME LIKE ? " +
                             "AND USERNAME != ? " +
                             "AND USERNAME NOT IN (" +
                             "    SELECT PERSONUSERNAME FROM PERSONFRIENDS WHERE FRIENDUSERNAME = ? AND STATUS IN ('Accepted', 'Pending') " +
                             "    UNION " +
                             "    SELECT FRIENDUSERNAME FROM PERSONFRIENDS WHERE PERSONUSERNAME = ? AND STATUS IN ('Accepted', 'Pending')" +
                             ")";

    private int executeResult;
    private String userName;
    private ArrayList<User> users = new ArrayList<>();

    public AddFriend(String userName, String searchQuery) throws SQLException { 
        this.userName = userName;  
        System.out.println("Fetching users based on search query: " + searchQuery);
        getUsers(searchQuery); 
    }
    public int getExecuteResult() {
        return executeResult;
    }

    public ArrayList<User> getUsersListArr() {
        return users;
    }

    private void getUsers(String searchQuery) throws SQLException {
        PreparedStatement statement = DBCon.prepareStatement(query);
        statement.setString(1, "%" + searchQuery + "%"); 
        statement.setString(2, userName);  
        statement.setString(3, userName);  
        statement.setString(4, userName);  
        ResultSet rs = statement.executeQuery();

        while (rs.next()) {
            User tempUser = new User(rs.getString("USERNAME"));
            System.out.println("User found: " + tempUser.getUserName());
            users.add(tempUser);
            executeResult = 1;
        }

        if (users.isEmpty()) {
            executeResult = 0;
        }
    }


}

//FINAALLLLLL  REPOS
///DAHAHAHAHA LLL LREPPPOOOO yseiff
