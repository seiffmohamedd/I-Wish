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
import javafx.fxml.Initializable;
import javafx.scene.control.TextField;
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
    private void SignupAction(ActionEvent event) throws JsonProcessingException {
          person = new Person(ID.getText(),
                                Fname.getText(),
                                Lname.getText(),
                                password.getText(),
                                gender.getText(),
                                birthDate.getText(),
                                phone.getText());
          
          
        // Convert Person object to JSON
            
            jsonObject = new JSONObject(person);
        try {
            jsonObject.put("Command", "Signup");
            
//        jsonObject.put("userName", person.getUserName());
//        jsonObject.put("FirstName", person.getFirstName());
//        jsonObject.put("LastName", person.getLastName());
//        jsonObject.put("Password", person.getPassword());
//        jsonObject.put("gender", person.getGender());
//        jsonObject.put("BirthDate", person.getBirthDate());
//        jsonObject.put("phone", person.getPhone());
        } catch (JSONException ex) {
            Logger.getLogger(FXMLDocumentController.class.getName()).log(Level.SEVERE, null, ex);
        }
        

        

        // Print the JSON string
        
//        jsonString = jsonObject.toString();
//        System.out.println("JSON String: " + jsonString);
        
         try {
           
            server = new Socket("127.0.0.1",5555);
            inputData = new DataInputStream(server.getInputStream());
            outputData = new PrintStream(server.getOutputStream());
            outputData.println(jsonObject);
            
        }catch (ConnectException e) {
            System.out.println("Connection failed: Server might be down.");
        } catch (IOException ex) {
            Logger.getLogger(FXMLDocumentController.class.getName()).log(Level.SEVERE, null, ex);
        } 
         
    
    }
    
   
}