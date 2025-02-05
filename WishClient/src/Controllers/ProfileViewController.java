/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Controllers;

import BDO.WishList;
import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.ListView;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.text.Text;
import org.json.JSONException;
import org.json.JSONObject;
import wishclient.SetSocket;

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
    private ListView<?> notificationslist;
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
    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        
        try {
            SetSocket socket = new SetSocket();
            JSONObject getWishReq = new JSONObject();
            getWishReq.put("Command", "getWishList");

            socket.getDOS().println(getWishReq);
//            ArrayList<WishList> WL = (ArrayList<WishList>)socket.getDIS().readLine();
            String data = socket.getDIS().readLine();

            updateTableFromString(data);

            System.out.println("this is the string array list from DIS "+data);
        } catch (IOException ex) {
            Logger.getLogger(ProfileViewController.class.getName()).log(Level.SEVERE, null, ex);
        } catch (JSONException ex) {
                Logger.getLogger(ProfileViewController.class.getName()).log(Level.SEVERE, null, ex);
        }

        
            
        
    }    
    
    
    // Function to parse input string and update TableView
    public void updateTableFromString(String input) {
        
        ItemNameCol.setCellValueFactory(new PropertyValueFactory<>("itemName"));
        DescriptionCol.setCellValueFactory(new PropertyValueFactory<>("itemDescription"));
        ItemPriceCol.setCellValueFactory(new PropertyValueFactory<>("price"));
        RemainingCol.setCellValueFactory(new PropertyValueFactory<>("remaining"));

        WishListTable.setItems(wishListData);
        wishListData.clear();  // Clear existing data

        // Regex to extract wish list items
        Pattern pattern = Pattern.compile(
                "WishList\\{itemName=(.*?), itemDescription=(.*?), price=(.*?), remaining=(.*?)\\}"
        );
        Matcher matcher = pattern.matcher(input);

        while (matcher.find()) {
            String itemName = matcher.group(1).trim();
            String itemDescription = matcher.group(2).trim();
            double price = Double.parseDouble(matcher.group(3).trim());
            double remaining = Double.parseDouble(matcher.group(4).trim());

            wishListData.add(new WishList(itemName, itemDescription, price, remaining));
        }
    }

}
