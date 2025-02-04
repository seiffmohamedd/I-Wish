package wishclient;

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

public class LoginViewController implements Initializable {

    @FXML
    private TextField usernameField;
    @FXML
    private PasswordField passwordField;

    private Socket server;
    private DataInputStream inputData;
    private PrintStream outputData;

    @Override
    public void initialize(URL url, ResourceBundle rb) {
    }

    @FXML
    private void handleSignIn(ActionEvent event) {
        try {
            if (usernameField.getText().isEmpty() || passwordField.getText().isEmpty()) {
            Alert aboutDialog = new Alert(Alert.AlertType.INFORMATION);
            aboutDialog.setTitle("Fields Empty");

            aboutDialog.setContentText("Login Failed All fields are required!");
            aboutDialog.showAndWait();
            return;
        }
            JSONObject jsonObject = new JSONObject();
            jsonObject.put("Command", "Signin");
            jsonObject.put("userName", usernameField.getText());
            jsonObject.put("password", passwordField.getText());

            server = new Socket("127.0.0.1", 5555);
            inputData = new DataInputStream(server.getInputStream());
            outputData = new PrintStream(server.getOutputStream());

            outputData.println(jsonObject.toString());

            String response = inputData.readLine();
            JSONObject jsonResponse = new JSONObject(response);

            if (jsonResponse.getString("status").equals("success")) {
                Alert aboutDialog = new Alert(Alert.AlertType.INFORMATION);
               aboutDialog.setTitle("Signed In");
               aboutDialog.setContentText("Login Successfully");
               aboutDialog.showAndWait();
               return;
            } else {
                 Alert aboutDialog = new Alert(Alert.AlertType.INFORMATION);
               aboutDialog.setTitle("Cant Sign In");
               aboutDialog.setContentText("Sign-in failed! Invalid credentials");
               aboutDialog.showAndWait();
            }
            inputData.close();
            outputData.close();
            server.close();

        } catch (ConnectException e) {
            System.out.println("Connection failed: Server might be down.");
        } catch (IOException | JSONException ex) {
            Logger.getLogger(LoginViewController.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    @FXML
    private void handleSignUp(ActionEvent event) {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("FXMLDocument.fxml"));
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
