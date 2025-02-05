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
import wishclient.SetSocket;

public class LoginViewController implements Initializable {

    @FXML
    private TextField usernameField;
    @FXML
    private PasswordField passwordField;

    private Socket server;
    private DataInputStream inputData;
    private PrintStream outputData;
    @FXML
    private Button signInButton;
    @FXML
    private Button signUpButton;

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
            
            if("Success".equals(DISLine)){
                dg.showDialog("Login","Login Success","CONFIRMATION");
                profileView(event);
            }else{ 
                dg.showDialog("Login","Failed to Login user data is wrong","ERROR");
            }
            
        } catch (IOException | JSONException ex) {
            Logger.getLogger(LoginViewController.class.getName()).log(Level.SEVERE, null, ex);
        }
        
    }

    @FXML
    private void handleSignUp(ActionEvent event) {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/Views/SignUpView.fxml"));
            Parent root = loader.load();
            Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
            Scene scene = new Scene(root);
            stage.setScene(scene);
            stage.show();
        } catch (IOException ex) {
            Logger.getLogger(LoginViewController.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
    
    private void profileView(ActionEvent event) {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/Views/ProfileView.fxml"));
            Parent root = loader.load();     
            Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
            Scene scene = new Scene(root);
            stage.setScene(scene);
            stage.show();
        } catch (IOException ex) {
            Logger.getLogger(LoginViewController.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
    
}
