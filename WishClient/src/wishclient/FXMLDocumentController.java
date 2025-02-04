/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/javafx/FXMLController.java to edit this template
 */
package wishclient;
import com.fasterxml.jackson.core.JsonProcessingException;
import wishclient.dbo.Person;
import java.io.DataInputStream;
import java.io.IOException;
import java.io.PrintStream;
import java.net.ConnectException;
import java.net.Socket;
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


/**
 * FXML Controller class
 *
 * @author mohamedhekal
 */
public class FXMLDocumentController implements Initializable {

    @FXML
    private TextField ID;
    private TextField Name;
    private TextField Age;
    @FXML
    private TextField Fname;
    @FXML
    private TextField Lname;
    @FXML
    private TextField password;
    @FXML
    private TextField gender;
    @FXML
    private TextField birthDate;
    @FXML
    private TextField phone;

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
       
    }    
    Socket server;
    DataInputStream inputData;
    PrintStream outputData;
    Person person;
    JSONObject jsonObject;
    String jsonString;
    
    @FXML
     public void handleBackAction(ActionEvent event) {
        try {
            Parent loginView = FXMLLoader.load(getClass().getResource("LoginView.fxml"));
            Scene loginScene = new Scene(loginView);
            Stage window = (Stage) ((Node) event.getSource()).getScene().getWindow();
            window.setScene(loginScene);
            window.show();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @FXML
    private void SignupAction(ActionEvent event) throws JsonProcessingException {
        if (ID.getText().isEmpty() || Fname.getText().isEmpty() || Lname.getText().isEmpty() || 
            password.getText().isEmpty() || gender.getText().isEmpty() || birthDate.getText().isEmpty() || phone.getText().isEmpty()) {
            Alert aboutDialog = new Alert(Alert.AlertType.INFORMATION);
            aboutDialog.setTitle("Empty Fields");
            aboutDialog.setContentText("Signup Failed, All fields are required!");
            aboutDialog.showAndWait();
            return;
        }

        person = new Person(ID.getText(), Fname.getText(), Lname.getText(), password.getText(), gender.getText(), birthDate.getText(), phone.getText());

        jsonObject = new JSONObject(person);
        try {
            jsonObject.put("Command", "Signup");
        } catch (JSONException ex) {
            Logger.getLogger(FXMLDocumentController.class.getName()).log(Level.SEVERE, null, ex);
        }

        try {
            server = new Socket("127.0.0.1", 5555);
            inputData = new DataInputStream(server.getInputStream());
            outputData = new PrintStream(server.getOutputStream());

            outputData.println(jsonObject.toString());

            String response = inputData.readLine();
            JSONObject jsonResponse = new JSONObject(response);

//            if (jsonResponse.getString("status").equals("username_exists")) {
//                Alert aboutDialog = new Alert(Alert.AlertType.INFORMATION);
//                aboutDialog.setTitle("Signup Failed");
//                aboutDialog.setContentText("Username already exists!");
//                aboutDialog.showAndWait();
//            } else
                if (jsonResponse.getString("status").equals("success")) {
                Alert aboutDialog = new Alert(Alert.AlertType.INFORMATION);
                aboutDialog.setTitle("Success");
                aboutDialog.setContentText("Signup successful! Redirecting to login...");
                aboutDialog.showAndWait();
                try {
                FXMLLoader loader = new FXMLLoader(getClass().getResource("LoginView.fxml"));
                Parent root = loader.load();
                Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
                Scene scene = new Scene(root);
                stage.setScene(scene);
                stage.show();
            } catch (IOException ex) {
                Logger.getLogger(LoginViewController.class.getName()).log(Level.SEVERE, null, ex);
            }

            }

        } catch (ConnectException e) {
            Alert aboutDialog = new Alert(Alert.AlertType.ERROR);
            aboutDialog.setTitle("Connection Failed");
            aboutDialog.setContentText("Server might be down. Please try again later.");
            aboutDialog.showAndWait();
        } catch (IOException | JSONException ex) {
            Logger.getLogger(FXMLDocumentController.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
   
}