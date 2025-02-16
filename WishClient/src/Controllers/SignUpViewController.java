package Controllers;

import BDO.User;
import java.io.IOException;
import java.net.URL;
import java.time.LocalDate;
import java.time.Period;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.DatePicker;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import org.json.JSONException;
import org.json.JSONObject;
import wishclient.SetSocket;

public class SignUpViewController implements Initializable {

    @FXML
    private TextField Fname;
    @FXML
    private TextField ID;
    @FXML
    private TextField Lname;
    @FXML
    private TextField gender;
    @FXML
    private PasswordField password;  // Changed to PasswordField
    @FXML
    private TextField phone;
    @FXML
    private DatePicker birthDate;

    private Dialog dg;

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        dg = new Dialog();
    }

    @FXML
    private void handleBackAction(ActionEvent event) {
        new LoadView(event, "LoginView");
    }

    @FXML
    private void SignupAction(ActionEvent event) {
        if (!validateInputs()) {
            return;  // Stop execution if validation fails
        }

        User user = new User(ID.getText(), Fname.getText(), Lname.getText(), password.getText(), gender.getText(), birthDate.getValue().toString(), phone.getText());
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

            if ("Success".equals(DISLine)) {
                dg.showDialog("SignUp", "Sign up Success and user data inserted", "CONFIRMATION");
                handleBackAction(event);
            } else if ("PK_Violation".equals(DISLine)) {
                dg.showDialog("SignUp", "Failed to insert user data. User ID already exists.", "ERROR");
            } else {
                dg.showDialog("SignUp", "Failed to insert user data", "ERROR");
            }
        } catch (java.net.ConnectException ex) {
        new Dialog().showDialog("Connection Error", "Failed to connect to the server. Please try again later.", "ERROR");
    }catch (IOException ex) {
            dg.showDialog("Server Connection", "Cannot connect to server", "ERROR");
        }
    }

    private boolean validateInputs() {
        String idText = ID.getText().trim();
        String firstName = Fname.getText().trim();
        String lastName = Lname.getText().trim();
        String pass = password.getText().trim();
        String phoneText = phone.getText().trim();
        String genderText = gender.getText().trim().toLowerCase();
        LocalDate birth = birthDate.getValue();

        // Validate username: must start with a letter, contain only letters, numbers, or underscores, and be at least 5 characters long
        if (!idText.matches("^[A-Za-z][A-Za-z0-9_]{4,}$")) {
            dg.showDialog("Validation Error", "Username must start with a letter, be at least 5 characters long, and contain only letters, numbers, or underscores.", "ERROR");
            return false;
        }

        // Validate first and last names: only letters, at least 2 characters
        if (!firstName.matches("[a-zA-Z]{2,}")) {
            dg.showDialog("Validation Error", "First name must contain only letters and be at least 2 characters long.", "ERROR");
            return false;
        }

        if (!lastName.matches("[a-zA-Z]{2,}")) {
            dg.showDialog("Validation Error", "Last name must contain only letters and be at least 2 characters long.", "ERROR");
            return false;
        }

        // Validate gender: must be "Male" or "Female"
        if (!genderText.equals("male") && !genderText.equals("female")) {
            dg.showDialog("Validation Error", "Gender must be either 'Male' or 'Female'.", "ERROR");
            return false;
        }

        // Validate phone number: must be numeric and 10-15 digits
        if (!phoneText.matches("\\d{10,15}")) {
            dg.showDialog("Validation Error", "Phone number must contain only numbers and be between 10 and 15 digits long.", "ERROR");
            return false;
        }

        // Validate birth date: must not be in the future and must be at least 18 years old
        if (birth == null) {
            dg.showDialog("Validation Error", "Birth date cannot be empty.", "ERROR");
            return false;
        }

        if (birth.isAfter(LocalDate.now())) {
            dg.showDialog("Validation Error", "Birth date cannot be in the future.", "ERROR");
            return false;
        }

        int age = Period.between(birth, LocalDate.now()).getYears();
        if (age < 18) {
            dg.showDialog("Validation Error", "You must be at least 18 years old to sign up.", "ERROR");
            return false;
        }

        // Validate password: at least 6 characters, must contain at least one letter and one number
        if (!pass.matches("^(?=.*[A-Za-z])(?=.*\\d)[A-Za-z\\d]{6,}$")) {
            dg.showDialog("Validation Error", "Password must be at least 6 characters long and include at least one letter and one number.", "ERROR");
            return false;
        }

        return true;
    }
}