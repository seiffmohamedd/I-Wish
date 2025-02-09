/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Controllers;

import BDO.User;
import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import org.json.JSONException;
import org.json.JSONObject;
import wishclient.SetSocket;

/**
 * FXML Controller class
 *
 * @author Windo
 */
public class SignUpViewController implements Initializable {

    @FXML
    private TextField Fname;
    @FXML
    private TextField Lname;
    @FXML
    private TextField password;
    @FXML
    private TextField gender;
    @FXML
    private TextField phone;
    @FXML
    private TextField birthDate;
    @FXML
    private TextField ID;

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
       dg = new Dialog();
    }    
    Dialog dg ;
    @FXML
    private void SignupAction(ActionEvent event) {
        if (ID.getText().isEmpty() || Fname.getText().isEmpty() || Lname.getText().isEmpty() || 
            password.getText().isEmpty() || gender.getText().isEmpty() || birthDate.getText().isEmpty() || phone.getText().isEmpty()) {
            dg.showDialog("Data Insertion","Signup Failed, All fields are required!","ERROR");
        }
            
        User user = new User(ID.getText(), Fname.getText(), Lname.getText(), password.getText(), gender.getText(), birthDate.getText(), phone.getText());
        JSONObject userJSONData = new JSONObject(user);
        try {
            userJSONData.put("Command", "Signup");
        } catch (JSONException ex) {
            Logger.getLogger(SignUpViewController.class.getName()).log(Level.SEVERE, null, ex);
        }
        try {
            SetSocket socket = new SetSocket();
            socket.getDOS().println(userJSONData);
            String DISLine = socket.getDIS().readLine();
           
            if("Success".equals(DISLine)){
                dg.showDialog("SignUp","Sign up Success and user data inserted","CONFIRMATION");
                handleBackAction(event);
            }else if ("PK_Violation".equals(DISLine)){
                dg.showDialog("SignUp","Failed to insert user data User Name exist","ERROR");
            }else{ dg.showDialog("SignUp","Failed to insert user data","ERROR");}
        } catch (IOException ex) {
            dg.showDialog("Server Connection","Cannot connect to server","ERROR");
        }
    }

    @FXML
    private void handleBackAction(ActionEvent event){
      
        new LoadView(event, "LoginView");
    }
    
    
    
}
