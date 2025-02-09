package Requests;

import DAL.AddFriend;
import DAL.FriendsList;
import DAL.GetNotification;
import DAL.Login;
import DAL.SignUp;
import DAL.WishListItem;
import DBO.Friends;
import DBO.Notification;
import org.json.JSONException;
import org.json.JSONObject;
import DBO.User;
import DBO.WishList;
import java.sql.SQLException;
import java.text.ParseException;
import java.util.ArrayList;
import org.json.JSONArray;

public class HandleRequests {
    private JSONObject userRequest;
    private String Command;
    private String HandlingResult;
    private User UserData;
    private ArrayList<WishList> WLI;
    private ArrayList<Notification> userNotificationArr;
    private ArrayList<Friends> friendsList; 
    private ArrayList<User> userList; 

    public HandleRequests(JSONObject userRequest) throws JSONException, SQLException, ParseException {
        this.userRequest = userRequest;
        getCommand();
        executeRequest();
    }

    public User getUserData() {
        return UserData;
    }
    
    public String getCommand() throws JSONException {
        Command = userRequest.getString("Command");    
        return Command;
    }

    public String getHandlingResult() {
        return HandlingResult;
    }

    public ArrayList<WishList> getUserWishListItems() {
        return WLI;
    }

    public ArrayList<Notification> getUserNotificationArr() {
        return userNotificationArr;
    }
    

    public ArrayList<Friends> getUserFriendsList() { 
        return friendsList;
    }
    
    public ArrayList<User> getUserList() { 
        return userList;
    }

    public void executeRequest() throws JSONException, SQLException, ParseException {
        User user;
        switch (Command) {
            case "Signup":
                user = new User(userRequest);
                SignUp SU = new SignUp(user);
                switch (SU.getExecuteResult()) {
                    case 1:
                        HandlingResult = "Success";
                        break;
                    case -5:
                        HandlingResult = "PK_Violation";
                        break;
                    case 0:
                        HandlingResult = "Fail";
                        break;
                }
                break;

            case "Login":
                user = new User(userRequest);
//                UserName = user.getUserName();
                Login LI = new Login(user);
                switch (LI.getExecuteResult()) {
                    case 1:
                        HandlingResult = "Success";
                        UserData = LI.getUser();
                        break;
                    case 0:
                        HandlingResult = "Fail";
                        break;
                }
                break;

            case "GetProfileData":
                WishListItem WL = new WishListItem(userRequest.getString("userName"));
                WLI = WL.getUserWishListItemsArr();
                GetNotification userNotification = new GetNotification(userRequest.getString("userName"));
                userNotificationArr = userNotification.getUserNotificationtsArr();
                switch (WL.getExecuteResult()) {
                    case 1:
                        HandlingResult = WLI.toString(); // Return the actual wish list
                        break;
                    case 0:
                        HandlingResult = "Fail";
                        break;
                }
                break;

            case "getFriendsList":
                FriendsList FL = new FriendsList(userRequest.getString("userName"));
                friendsList = FL.getUserFriendsListArr();

                if (friendsList == null || friendsList.isEmpty()) {
                    HandlingResult = "No friends found";
                } else {
                    HandlingResult = friendsList.toString();
                }
                break;

                case "searchUsers":
               String searchQuery = userRequest.getString("query");  
               AddFriend AF = new AddFriend(userRequest.getString("userName"), searchQuery);  
               userList = AF.getUsersListArr();

               if (userList == null || userList.isEmpty()) {
                   HandlingResult = "No Users found";
               } else {
                   JSONArray userArray = new JSONArray();
                   for (User users : userList) {
                       userArray.put(userRequest.getString("userName"));  
                   }
                   HandlingResult = userArray.toString();  
               }
               break;

        }
    }
}
