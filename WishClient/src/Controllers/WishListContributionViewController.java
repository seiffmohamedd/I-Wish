/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Controllers;

import BDO.User;
import java.net.URL;
import java.util.ResourceBundle;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;

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
    
    
    public void setFriendUserName(String friendUserName) {
        userNameTxtField.setText(friendUserName);
    }
    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        pointsTxtFields.setText(String.valueOf(User.getPoints()));
    }    
    
}
