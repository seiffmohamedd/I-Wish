/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Requests;

import DAL.Login;
import DAL.SignUp;
import DAL.WishListItem;
import org.json.JSONException;
import org.json.JSONObject;
import DBO.User;
import DBO.WishList;
import java.sql.SQLException;
import java.text.ParseException;
import java.util.ArrayList;

/**
 *
 * @author hekal
 */
public class HandleRequests {
    private JSONObject userRequest;
    private String Command;
    private String HandlingResult;
    private static String UserName;
    private ArrayList<WishList> WLI;
    public HandleRequests(JSONObject userRequest ) throws JSONException, SQLException, ParseException{
        this.userRequest = userRequest;
        getCommand();
//        System.out.println("get the command and it was : "+ Command);
        executeRequest();
    }
    
    public String getCommand() throws JSONException{
        Command = userRequest.getString("Command");    
        return Command;
    }

    public String getHandlingResult() {
        return HandlingResult;
    }

    public ArrayList<WishList> getUserWishListItems() {
//        JSONObject jsonobject = new JSONObject(WLI);
        return WLI;
    }
    
    
    
    
    
    public void executeRequest() throws JSONException, SQLException, ParseException{
        User user;
        
        switch (Command) {
            case "Signup":
                user = new User(userRequest);
//                       System.out.println("Store the data in user Object");
                SignUp SU = new SignUp(user);
//                       System.out.println("data is Stored in database");
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
                UserName= user.getUserName();
//                       System.out.println("Store the data in user Object");
                Login LI = new Login(user);
//                       System.out.println("data is Stored in database");
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
//                       System.out.println("data is Stored in database");
                WLI =  WL.getUserWishListItemsArr();
                switch (WL.getExecuteResult()) {
                    case 1:
                        HandlingResult = "Success";
                        break;
                    case 0:
                        HandlingResult = "Fail";
                        break;
                }
                break;

      

                
        }
    
}

}
