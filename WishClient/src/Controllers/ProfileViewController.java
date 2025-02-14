/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Controllers;

import BDO.Notification;
import BDO.User;
import BDO.WishList;
import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ListView;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import wishclient.SetSocket;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import javafx.event.ActionEvent;
/**
 * FXML Controller class
 *
 * @author Windo
 */
public class ProfileViewController implements Initializable {

    @FXML
    private TextField username;
    @FXML
    private Button friendbtn;
    @FXML
    private TextField points;
    @FXML
    private Button addbtn;
    @FXML
    private Button requestbtn;
    @FXML
    private Text myWishListtxt;
    @FXML
    private Text notificationtxt;
    @FXML
    private TableColumn<WishList, String> ItemNameCol;
    @FXML
    private TableColumn <WishList, Double> ItemPriceCol;
    @FXML
    private TableColumn<WishList, Double> RemainingCol;
    @FXML
    private TableColumn <WishList, String> DescriptionCol;
    @FXML
    private TableView<WishList> WishListTable;
    private ObservableList<WishList> wishListData = FXCollections.observableArrayList();
    @FXML
    private TableView<Notification> NotificationTable;
    private ObservableList<Notification> notificationData = FXCollections.observableArrayList();
    public static String loggedInUser;
    @FXML
    private TableColumn<Notification, String> NotificationTextCol;
    @FXML
    private Button updateWishList;
    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
         dg = new Dialog();
        try {
            SetSocket socket = new SetSocket();
            JSONObject GetProfile = new JSONObject();
            GetProfile.put("Command", "GetProfileData");
            GetProfile.put("userName" , User.getUserName());
            socket.getDOS().println(GetProfile);
            // get wish list content and view it
            JSONArray WishList = new JSONArray (socket.getDIS().readLine());
            User.setWishList(WishList);
            System.out.println("this is wishList Json array"+WishList);
            updateTableFrom(WishList);
            // get Notification content and view it
            JSONArray Notification = new JSONArray(socket.getDIS().readLine());
            System.out.println("this is notification Json Array "+Notification);
            updateNotificationTable(Notification);
            loggedInUser=User.getUserName();
            // fill userName in username text field
            username.setText(User.getUserName());
            System.out.println("the points for the user " + User.getUserName() + " is " + User.getPoints());
            points.setText(String.valueOf(User.getPoints()));
            
        } catch (IOException ex) {
            Logger.getLogger(ProfileViewController.class.getName()).log(Level.SEVERE, null, ex);
        } catch (JSONException ex) {
                Logger.getLogger(ProfileViewController.class.getName()).log(Level.SEVERE, null, ex);
        }

        
            
        
    }
    
    Dialog dg;


    public void updateTableFrom(JSONArray jsonArray) {
        ItemNameCol.setCellValueFactory(new PropertyValueFactory<>("itemName"));
        DescriptionCol.setCellValueFactory(new PropertyValueFactory<>("itemDescription"));
        ItemPriceCol.setCellValueFactory(new PropertyValueFactory<>("price"));
        RemainingCol.setCellValueFactory(new PropertyValueFactory<>("remaining"));

        WishListTable.setItems(wishListData);
        wishListData.clear();  // Clear existing data

        // Iterate through JSONArray and extract objects
        for (int i = 0; i < jsonArray.length(); i++) {
            try {
                JSONObject jsonObject = jsonArray.getJSONObject(i);
                int itemid = jsonObject.getInt("ItemID");
                String itemName = jsonObject.getString("itemName");
                String itemDescription = jsonObject.getString("itemDescription");
                double price = jsonObject.getDouble("price");
                double remaining = jsonObject.getDouble("remaining");
                
                wishListData.add(new WishList(itemid, itemName, itemDescription, price, remaining));
            } catch (JSONException ex) {
                System.err.println(ex);
                dg.showDialog("ERROR", "ERROR in data retrival", "ERROR");
            }
        }
    }
    
    
    

    public void updateNotificationTable(JSONArray jsonArray) {
        // Set the column property
        NotificationTextCol.setCellValueFactory(new PropertyValueFactory<>("notificationText"));

        // Clear existing data
        NotificationTable.setItems(notificationData);
        notificationData.clear();

        // Define date format to extract only the date
        DateTimeFormatter dateOnlyFormatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");

        // Iterate through the JSONArray and extract relevant fields
        for (int i = 0; i < jsonArray.length(); i++) {
            try {
                JSONObject jsonObject = jsonArray.getJSONObject(i);

                String notificationText = jsonObject.getString("NotificationText");
                String timestamp = jsonObject.getString("TimeStamp");

                // Extract only the date part (YYYY-MM-DD)
                String datePart = timestamp.split(" ")[0];

                // Concatenate NotificationText with Date
                String formattedText = notificationText + " - " + datePart;

                // Add new Notification object to the table
                notificationData.add(new Notification(formattedText));
            } catch (JSONException ex) {
                Logger.getLogger(ProfileViewController.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
}
    
    
   @FXML
    private void handleFriendsButton(ActionEvent event) {
       
        new LoadView(event, "MyFriends");
    }
    @FXML
    private void handleAddButton(ActionEvent event) {
       
        new LoadView(event, "AddFriend");
    }

    @FXML
    private void updateWishListAction(ActionEvent event) {
       
        new LoadView(event, "UpdateWishListView");
        
    }
    


    @FXML
    private void addPointsAction(ActionEvent event) {
        new LoadView(event, "CreditCardView");
    }

    @FXML
    private void rqstBtnAction(ActionEvent event) {
        new LoadView(event, "FriendRequest");
    }
}