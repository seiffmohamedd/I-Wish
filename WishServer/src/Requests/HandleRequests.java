package Requests;
//sssssssssssss
import DAL.AddFriend;
import DAL.AddItemToUserWish;
import DAL.AddPointstoUser;
import DAL.FriendsList;
import DAL.GetItemLike;
import DAL.GetNotification;
import DAL.Login;
import DAL.MakeContribute;
import DAL.RemoveWishItem;
import DAL.SignUp;
import DAL.WishListItem;
import DBO.ChargePoints;
import DBO.ContributeData;
import DBO.Friends;
import DBO.Items;
import DBO.Notification;
import org.json.JSONException;
import org.json.JSONObject;
import DBO.User;
import DBO.WishList;
import DBO.WishitemRemove;
import java.sql.SQLException;
import java.text.ParseException;
import java.util.ArrayList;
import org.json.JSONArray;
import java.util.HashSet;
public class HandleRequests {
    private JSONObject userRequest;
    private String Command;
    private String HandlingResult;
    private User UserData;
    private ArrayList<WishList> WLI;
    private ArrayList<Notification> userNotificationArr;
    private ArrayList<Friends> friendsList; 
    private ArrayList<User> userList; 
    private ArrayList<Items> searchItems;
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
    
    public ArrayList<Items> getSearchItems() {
        return searchItems;
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
                userList = AF.getUsersListArr();  // Fetch users from DB

                if (userList == null || userList.isEmpty()) {
                    HandlingResult = "No Users found";
                } else {
                    JSONArray userArray = new JSONArray();
                    for (User users : userList) {
//                        userArray.put(userRequest.getString("userName")); 
                        userArray.put(users.getUserName()); 
                    }
                    HandlingResult = userArray.toString();  
                }
                break;
            case "RemoveWishListItem":
              
                WishitemRemove wishitem = new WishitemRemove(userRequest);
                RemoveWishItem remWishItem = new RemoveWishItem(wishitem);
                switch (remWishItem.getExecuteResult()) {
                    case 1:
                        HandlingResult = "Success";
                        break;
                    case 0:
                        HandlingResult = "Fail";
                        break;
                }
                break;
            case "getItemsLike":  
                GetItemLike itemLike = new GetItemLike(userRequest);
                switch (itemLike.getExecuteResult()) {
                    case 1:
                        searchItems = itemLike.getItemsList();
                        break;
                    case 0:
                        HandlingResult = "Fail";
                        break;
                }
                break;
               case "sendFriendRequest":
                String fromUser = userRequest.getString("fromUser");
                String toUser = userRequest.getString("toUser");
                String status = userRequest.getString("status");

                Friends friendRequest = new Friends(fromUser, toUser, status);
                FriendsList friendsListHandler = new FriendsList(friendRequest);

                switch (friendsListHandler.getExecuteResult()) {
                    case 1:
                        HandlingResult = "Friend request sent!";
                        break;
                    case 0:
                        HandlingResult = "Error sending friend request.";
                        break;
                }
                break;
            case "addItemToWish":
                AddItemToUserWish addWishItem = new AddItemToUserWish(userRequest);
                switch (addWishItem.getExecuteResult()) {
                    case 1:
                        HandlingResult = "Success";
                        break;
                    case 0:
                        HandlingResult = "Fail";
                        break;
                    
                }
                break;
                
            case "ChargePoints":
                ChargePoints chargePointsdata = new ChargePoints(userRequest);
                AddPointstoUser addPoints = new AddPointstoUser(chargePointsdata);
                switch (addPoints.getExecuteResult()) {
                    case 1:
                        HandlingResult = "Success";
                        break;
                    case 0:
                        HandlingResult = "Fail";
                        break;
                    case -5:
                        HandlingResult = "inValidCreditCardData";
                        break;
                    
                }
                break;
            case "Contribute":
                ContributeData CD = new ContributeData(userRequest);
                MakeContribute MC = new MakeContribute(CD);
                switch (MC.getExecuteResult()) {
                    case 1:
                        HandlingResult = "Success";
                        break;
                    case 0:
                        HandlingResult = "Fail";
                        break; 
                }
                break;
            case "GETWISHLIST":
           
                WishListItem FWL = new WishListItem(userRequest.getString("userName"));
                switch (FWL.getExecuteResult()) {
                    case 1:
//                      HandlingResult = "Success";
                        HandlingResult = FWL.getUserWishListItemsArr().toString();
                        break;
                    case 0:
                        HandlingResult = "Fail";
                        break; 
                }
                break;
        }
    }
}
