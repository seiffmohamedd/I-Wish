package Controllers;

import BDO.User;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import org.json.JSONObject;
import wishclient.SetSocket;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.stage.Stage;
import org.json.JSONArray;
import org.json.JSONException;

public class AddFriendController implements Initializable {

    @FXML
    private TextField searchField;
    @FXML
    private Button searchButton;
    @FXML
    private TableView<User> searchResultsTable;
    @FXML
    private TableColumn<User, String> usernameColumn;
    @FXML
    private Button backButton;

    private ObservableList<User> searchResults = FXCollections.observableArrayList();

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // Set the username column to display the username from the User object
        usernameColumn.setCellValueFactory(new PropertyValueFactory<>("userName"));
        
        // Set up the table to display the users
        searchResultsTable.setItems(searchResults);
    }

    @FXML
    private void handleSearchButton() {
        String searchQuery = searchField.getText().trim();
        if (!searchQuery.isEmpty()) {
            try {
                SetSocket socket = new SetSocket();
                JSONObject searchReq = new JSONObject();
                searchReq.put("Command", "searchUsers");
                searchReq.put("userName", User.getUserName());
                searchReq.put("query", searchQuery);  // Send the query entered by the user

                socket.getDOS().println(searchReq);  // Send the request to the server
                String data = socket.getDIS().readLine();  // Read the server's response

                // Update the search results with the received data
                updateSearchResultsFromString(data);
            } catch (IOException | JSONException ex) {
                Logger.getLogger(AddFriendController.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
    }

    private void updateSearchResultsFromString(String data) {
     searchResults.clear();  
     try {
         JSONArray userArray = new JSONArray(data);
         for (int i = 0; i < userArray.length(); i++) {
             String username = userArray.getString(i).trim();

             if (!username.isEmpty()) {
                 User user = new User(username);  
                 searchResults.add(user);
             }
         }
     } catch (JSONException ex) {
         Logger.getLogger(AddFriendController.class.getName()).log(Level.SEVERE, null, ex);
     }
 }


    @FXML
    private void handleBackButton(ActionEvent event) {
        new LoadView(event, "ProfileView");
    }
}
