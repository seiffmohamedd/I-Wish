/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Controllers;

import BDO.ContributeData;
import BDO.User;
import BDO.WishList;
import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.TextField;
import org.json.JSONException;
import org.json.JSONObject;
import wishclient.SetSocket;

/**
 * FXML Controller class
 *
 * @author Windo
 */
public class WishDetailContributeViewController implements Initializable {

    @FXML
    private TextField NameTxtxField;
    @FXML
    private TextField priceTextField;
    @FXML
    private TextField RemainTxtField;
    @FXML
    private TextField ContributeTxtField;
    @FXML
    private TextField AvailablePointsTxtField;

    /**
     * Initializes the controller class.
     */
    
    public void setWishDetails(WishList wish) {
        AvailablePointsTxtField.setText(String.valueOf(User.getPoints()));
        NameTxtxField.setText(wish.getItemName());
        priceTextField.setText(String.valueOf(wish.getPrice()));
        RemainTxtField.setText(String.valueOf(wish.getRemaining()));
        System.out.println("the data from setwish is "+ wish.toString());
        this.wish = wish;
    }
    public void setFriendUserName(String FUserName) {
        this.FUserName = FUserName;
        System.out.println("the firend user name is "+ FUserName);
    }
    
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        dg = new Dialog();
    }    
    WishList wish;
    Dialog dg;
    String FUserName;
    SetSocket socket;
    boolean fullContributeClicked = false;
    @FXML
    private void ContributeAction(ActionEvent event) {
        if(validateContribution()){
            System.out.println("ContributeAction");
            sendRequest(Double.parseDouble(ContributeTxtField.getText()));
        }
    }

    @FXML
    private void FullContributeAction(ActionEvent event) {
        fullContributeClicked = true;
        if(validateContribution()){
            System.out.println("FullContributeAction");
            sendRequest(Double.parseDouble(RemainTxtField.getText()));            
        }
        fullContributeClicked = false;
    }
    
    private void sendRequest(Double contributePoints){
        try {
            ContributeData CD = new ContributeData(User.getUserName(), wish.getItemid(), FUserName,wish.getRemaining(),contributePoints);
            System.out.println("the ContributeData is " + CD.toString());
            JSONObject request = new JSONObject(CD);
            request.put("Command", "Contribute");
            socket = new SetSocket();
            socket.getDOS().println(request);
            String Respond = socket.getDIS().readLine();
            if ("Success".equals(Respond)) {
                dg.showDialog("Item Remove", "Contribute done", "CONFIRMATION");
                User.setPoints((int) (User.getPoints()- contributePoints));
                wish.setRemaining(wish.getRemaining() - contributePoints);
                RemainTxtField.setText(String.valueOf(wish.getRemaining()));
                AvailablePointsTxtField.setText(String.valueOf(User.getPoints()));
                
            } else {
                 dg.showDialog("Item Remove", "Failed to Remove Item ", "ERROR");
            }
            socket.closeStreams();

        } catch (java.net.ConnectException ex) {
        new Dialog().showDialog("Connection Error", "Failed to connect to the server. Please try again later.", "ERROR");
    }catch (IOException | JSONException ex) {
            Logger.getLogger(WishDetailContributeViewController.class.getName()).log(Level.SEVERE, null, ex);
        }
       
     
    }
    private boolean validateContribution(){
        // Validate Number of Points (Only Digits, Positive Integer)
        String numberOfPointsText = ContributeTxtField.getText().trim();
       
        if ((!numberOfPointsText.matches("\\d+"))&&(fullContributeClicked == false)) {
            dg.showDialog("Data Validation", "Invalid Number of Points", "Error");
            return false;
        }
        try{
            if (Integer.parseInt(AvailablePointsTxtField.getText()) < Integer.parseInt(ContributeTxtField.getText())) {
                dg.showDialog("Data Validation", "Invalid number of Points", "Error");
                return false;
            }
        }catch (NumberFormatException e){
             if (Double.parseDouble(AvailablePointsTxtField.getText()) < Double.parseDouble(RemainTxtField.getText())) {
                dg.showDialog("Data Validation", "Invalid number of Points", "Error");
                return false;
            }
        }
        if (Double.parseDouble(RemainTxtField.getText()) == 0.0) {
            dg.showDialog("Data Validation", "Item already has 0 remaining points !!", "Error");
            return false;
        }
        return true;
    }
}
