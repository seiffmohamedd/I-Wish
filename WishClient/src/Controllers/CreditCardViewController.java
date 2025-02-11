/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Controllers;

import BDO.ChargePoints;
import BDO.User;
import java.io.IOException;
import java.net.URL;
import java.time.LocalDate;
import java.util.Date;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.DatePicker;
import javafx.scene.control.TextField;
import org.json.JSONException;
import org.json.JSONObject;
import wishclient.SetSocket;

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
       points = Integer.parseInt(currentPointsTxfield.getText());
       dg = new Dialog();
       
    }    
    Dialog dg ; 
    SetSocket socket ;
    int points;
    @FXML
    private void backBtnAction(ActionEvent event) {
        new LoadView(event, "ProfileView");
    }

    @FXML
    private void ConfirmbtnAction(ActionEvent event) {
        if (validateInputs(CreditCardNumTxtfield, CVVTxtField, ValidtoDataField, HolderNameTxtField, numberOfPointsToChargeTxtField)) {
            try {
                String creditCardNumber = CreditCardNumTxtfield.getText();
                int cvv = Integer.parseInt(CVVTxtField.getText());
                LocalDate validTo = ValidtoDataField.getValue();
                String holderName = HolderNameTxtField.getText();
                int numberOfPoints = Integer.parseInt(numberOfPointsToChargeTxtField.getText());
                ChargePoints cc = new ChargePoints(creditCardNumber,cvv,validTo,holderName,User.getUserName(),numberOfPoints);
                JSONObject userRequest = new JSONObject(cc);
                userRequest.put("Command", "ChargePoints");
                socket = new SetSocket();
                socket.getDOS().println(userRequest);
                String Respond = socket.getDIS().readLine();
                socket.closeStreams();
                System.out.println("the respond is " + Respond);
                if (null == Respond) {
                    dg.showDialog("Point Add", "Failed to Add Points to the Account", "ERROR");
                    
                }else switch (Respond) {
                    case "Success":
                        dg.showDialog("Point Add", "Points Added Successfully", "CONFIRMATION");
                        User.setPoints(points + numberOfPoints);
                        currentPointsTxfield.setText(String.valueOf(User.getPoints()));
                        break;
                    case "inValidCreditCardData":
                        dg.showDialog("Point Add", "Can't Validate the Credit Card Data", "ERROR");
                        break;
                    default:
                        dg.showDialog("Point Add", "Failed to Add Points to the Account", "ERROR");
                        break;
                }
                
            } catch (IOException | JSONException ex) {
                Logger.getLogger(CreditCardViewController.class.getName()).log(Level.SEVERE, null, ex);
            }
            
        }

    }
    
    
    public boolean validateInputs(TextField creditCardNumTxtField, TextField cvvTxtField, 
                              DatePicker validToDataField, TextField holderNameTxtField, 
                              TextField numberOfPointsToChargeTxtField) {
    
    // Validate Credit Card Number (Only Digits, 13-19 characters)
    String creditCardNumber = creditCardNumTxtField.getText().trim();
    if (!creditCardNumber.matches("\\d{13,19}")) {
 
        dg.showDialog("Data Validation", "Invalid Credit Card Number", "Error");
        return false;
    }

    // Validate CVV (Only Digits, 3-4 characters)
    String cvvText = cvvTxtField.getText().trim();
    if (!cvvText.matches("\\d{3,4}")) {
        dg.showDialog("Data Validation", "Invalid CVV", "Error");
        return false;
    }
    int cvv = Integer.parseInt(cvvText);

    // Validate Expiry Date (Cannot be null & Must be in the future)
    LocalDate validTo = validToDataField.getValue();
    if (validTo == null || validTo.isBefore(LocalDate.now())) {
        dg.showDialog("Data Validation", "Invalid Expiry Date", "Error");
        return false;
    }

    // Validate Card Holder Name (Only Letters & Spaces)
    String holderName = holderNameTxtField.getText().trim();
    if (!holderName.matches("[a-zA-Z ]+")) {
        dg.showDialog("Data Validation", "Invalid Card Holder Name", "Error");
        return false;
    }

    // Validate Number of Points (Only Digits, Positive Integer)
    String numberOfPointsText = numberOfPointsToChargeTxtField.getText().trim();
    if (!numberOfPointsText.matches("\\d+")) {
        dg.showDialog("Data Validation", "Invalid Number of Points", "Error");
        return false;
    }
    int numberOfPoints = Integer.parseInt(numberOfPointsText);

    System.out.println("Validation Successful!");
    return true;
}
    
}
