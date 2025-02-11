/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Controllers;

import BDO.User;
import java.net.URL;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.DatePicker;
import javafx.scene.control.TextField;

/**
 * FXML Controller class
 *
 * @author Windo
 */
public class CreditCardViewController implements Initializable {

    @FXML
    private TextField currentPointsTxfield;
    @FXML
    private TextField CreditCardNumTxtfield;
    @FXML
    private DatePicker ValidtoDataField;
    @FXML
    private TextField HolderNameTxtField;
    @FXML
    private TextField CVVTxtField;
    @FXML
    private TextField numberOfPointsToChargeTxtField;

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
       currentPointsTxfield.setText(String.valueOf(User.getPoints()));
       
    }    

    @FXML
    private void backBtnAction(ActionEvent event) {
        new LoadView(event, "ProfileView");
    }

    @FXML
    private void ConfirmbtnAction(ActionEvent event) {
    }
    
}
