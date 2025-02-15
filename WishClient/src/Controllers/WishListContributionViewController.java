/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Controllers;

import BDO.User;
import BDO.WishList;
import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Stage;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import wishclient.SetSocket;

/**
 * FXML Controller class
 *
 * @author Windo
 */
public class WishListContributionViewController implements Initializable {
    @FXML
    private TextField userNameTxtField;
    @FXML
    private TextField pointsTxtFields;
    @FXML
    private TableView<WishList> WishListTable;
    @FXML
    private TableColumn<WishList, String> ItemNameCol;
    @FXML
    private TableColumn<WishList, Double> priceCol;
    @FXML
    private TableColumn<WishList, Double> RemainCol;
    @FXML
    private TableColumn<WishList, String> DescriptionCol;
    private ObservableList<WishList> wishListData = FXCollections.observableArrayList();

    
    public void setFriendUserName(String friendUserName) {
        userNameTxtField.setText(friendUserName);
        this.FriendUserName = friendUserName;
        friendWishList();
    }
    /**
     * Initializes the controller class.
     */
    public void initialize(URL location, ResourceBundle resources) {
        dg = new Dialog();
        pointsTxtFields.setText(String.valueOf(User.getPoints()));
        
//      updateTableFrom(User.getWishList());
//      System.out.println("the user wishlist is "+ User.getWishList().toString());
        
        
        
        
        // Add double-click event listener
        WishListTable.setOnMouseClicked(event -> {
            if (event.getClickCount() == 2) { // Detect double click
                WishList selectedWish = WishListTable.getSelectionModel().getSelectedItem();
                if (selectedWish != null) {
                    openWishDetailsScene(selectedWish,FriendUserName);
                    
                }
            }
        });
        
    }
    
    Dialog dg;
    String FriendUserName;
    
    private void friendWishList(){
        try {
           
            JSONObject request = new JSONObject();
            request.put("Command", "GETWISHLIST");
            request.put("userName", FriendUserName);
            SetSocket socket = new SetSocket();
            socket.getDOS().println(request);
            String respond = socket.getDIS().readLine();
            updateTableFrom(new JSONArray(respond));
        } catch (JSONException | IOException ex) {
            Logger.getLogger(WishListContributionViewController.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
    public void updateTableFrom(JSONArray jsonArray) {
        ItemNameCol.setCellValueFactory(new PropertyValueFactory<>("itemName"));
        DescriptionCol.setCellValueFactory(new PropertyValueFactory<>("itemDescription"));
        priceCol.setCellValueFactory(new PropertyValueFactory<>("price"));
        RemainCol.setCellValueFactory(new PropertyValueFactory<>("remaining"));

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
    
    private void openWishDetailsScene(WishList wish,String UserName) {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/Views/WishDetailContributeView.fxml"));
            Parent root = loader.load();

            // Get controller of the new scene
            WishDetailContributeViewController controller = loader.getController();
            controller.setWishDetails(wish); // Pass data
            controller.setFriendUserName(FriendUserName);
            Stage stage = new Stage();
            stage.setScene(new Scene(root));
            stage.setTitle("Wish Details");
            stage.show();
        } catch (IOException e) {
            e.printStackTrace();
        }
}

   
}
