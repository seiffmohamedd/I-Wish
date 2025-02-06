package Requests;

import DAL.FriendsList;
import DAL.Login;
import DAL.SignUp;
import DAL.WishListItem;
import DBO.Friends;
import org.json.JSONException;
import org.json.JSONObject;
import DBO.User;
import DBO.WishList;
import java.sql.SQLException;
import java.text.ParseException;
import java.util.ArrayList;

public class HandleRequests {
    private JSONObject userRequest;
    private String Command;
    private String HandlingResult;
    private static String UserName;
    private ArrayList<WishList> WLI;
    private ArrayList<Friends> friendsList; 

    public HandleRequests(JSONObject userRequest) throws JSONException, SQLException, ParseException {
        this.userRequest = userRequest;
        getCommand();
        executeRequest();
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

    public ArrayList<Friends> getUserFriendsList() { 
        return friendsList;
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
                UserName = user.getUserName();
                Login LI = new Login(user);
                switch (LI.getExecuteResult()) {
                    case 1:
                        HandlingResult = "Success";
                        break;
                    case 0:
                        HandlingResult = "Fail";
                        break;
                }
                break;

            case "getWishList":
                WishListItem WL = new WishListItem(UserName);
                WLI = WL.getUserWishListItemsArr();
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
                FriendsList FL = new FriendsList(UserName);
                friendsList = FL.getUserFriendsListArr();
                switch (FL.getExecuteResult()) {
                    case 1:
                        HandlingResult = friendsList.toString(); 
                        break;
                    case 0:
                        HandlingResult = "Fail";
                        break;
                }
                break;
        }
    }
}
