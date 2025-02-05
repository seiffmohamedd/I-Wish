/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Controllers;

import BDO.WishList;
import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.ListView;
import javafx.scene.control.TextField;
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
    private ListView<?> wishlistList;
    @FXML
    private Text notificationtxt;
    @FXML
    private ListView<?> notificationslist;

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
//              ArrayList<WishList> WL = (ArrayList<WishList>)socket.getDIS().readLine();
            String data = socket.getDIS().readLine();
            System.out.println("this is the string array list from DIS "+data);
        } catch (IOException ex) {
            Logger.getLogger(ProfileViewController.class.getName()).log(Level.SEVERE, null, ex);
        } catch (JSONException ex) {
                Logger.getLogger(ProfileViewController.class.getName()).log(Level.SEVERE, null, ex);
        }

        
    }    
    
}
