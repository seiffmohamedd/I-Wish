package Controllers;

import BDO.User;
import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import org.json.JSONException;
import org.json.JSONObject;
import java.io.DataInputStream;
import java.io.PrintStream;
import java.net.ConnectException;
import java.net.Socket;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import wishclient.SetSocket;

public class LoginViewController implements Initializable {

    @FXML
    private TextField usernameField;
    @FXML
    private TextField passwordField;

    private Socket server;
    private DataInputStream inputData;
    private PrintStream outputData;
    @FXML
    private Button signInButton;
    @FXML
    private Button signUpButton;
    @FXML
    private Label logo;
    @FXML
    private Label welcome;
    @FXML
    private Label signinLable;
    @FXML
    private Label dontHaveAccountLabel;
    @FXML
    private Label orlabel;

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        dg = new Dialog();
    }
    Dialog dg ;
    @FXML
    private void handleSignIn(ActionEvent event) {
       
        if (usernameField.getText().isEmpty() || passwordField.getText().isEmpty()) {
            dg.showDialog("Login Error", "Login Failed All fields are required!", "ERROR");
        }
       
        User user = new User(usernameField.getText(),"", "", passwordField.getText(),"", "", "");
        JSONObject userJSONData = new JSONObject(user);
        try {
            userJSONData.put("Command", "Login");
            SetSocket socket = new SetSocket();
            socket.getDOS().println(userJSONData);
            String DISLine = socket.getDIS().readLine();
            String response = socket.getDIS().readLine();
            // Check if response is valid JSON before parsing
            if (response == null || response.isEmpty() || !response.trim().startsWith("{")) {
                System.err.println("Invalid JSON response: " + response);
            } else {
                new User(new JSONObject(response));
            }
            
            if("Success".equals(DISLine)){
                dg.showDialog("Login","Login Success","CONFIRMATION");
                new LoadView(event, "ProfileView");
            }else{ 
                dg.showDialog("Login","Failed to Login user data is wrong","ERROR");
            }
            
        } catch (java.net.ConnectException ex) {
        new Dialog().showDialog("Connection Error", "Failed to connect to the server. Please try again later.", "ERROR");
    }catch (IOException | JSONException ex) {
            Logger.getLogger(LoginViewController.class.getName()).log(Level.SEVERE, null, ex);
        }
        
    }

    @FXML
    private void handleSignUp(ActionEvent event) {
         new LoadView(event, "SignUpView");

    }
 
}
