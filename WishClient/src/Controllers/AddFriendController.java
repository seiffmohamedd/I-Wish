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
import java.util.HashSet;
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
    private String loggedInUser; 
    private ObservableList<User> searchResults = FXCollections.observableArrayList();
    @FXML
    private TableColumn<User, Button> actionColumn;
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        loggedInUser = ProfileViewController.loggedInUser;
        usernameColumn.setCellValueFactory(new PropertyValueFactory<>("instanceUserName"));
        actionColumn.setCellValueFactory(new PropertyValueFactory<>("addButton")); 
        
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
//                searchReq.put("userName",  User.getUserName()); 
                searchReq.put("userName", loggedInUser); 
                searchReq.put("query", searchQuery);
                socket.getDOS().println(searchReq);
                String data = socket.getDIS().readLine();

                
                updateSearchResultsFromString(data);
            } catch (IOException | JSONException ex) {
                Logger.getLogger(AddFriendController.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
    }
    
     @FXML
    private void handleBackButton(ActionEvent event) {
        new LoadView(event, "ProfileView");
    }
    
    
    
    
    private void updateSearchResultsFromString(String data) {
        searchResults.clear();

        try {
            if (data == null || data.trim().isEmpty()) { 
                System.out.println("Server returned an empty response.");
                new Dialog().showDialog("Search Result", "No users found matching your search.", "INFORMATION");
                return;
            }

            if (!data.trim().startsWith("[")) { 
                System.out.println("Invalid JSON response from server: " + data);
                new Dialog().showDialog("Search Result", "No users found matching your search.", "INFORMATION");

                return;
            }

            JSONArray userArray = new JSONArray(data);
            HashSet<String> uniqueUsers = new HashSet<>();
            ObservableList<User> freshList = FXCollections.observableArrayList();

            System.out.println("JSON from Server: " + data);

            if (userArray.length() == 0) {  // ✅ Handle "No Users Found" Case
                System.out.println("No users found.");
                new Dialog().showDialog("Search Result", "No users found matching your search.", "INFORMATION");
                return;
            }

            for (int i = 0; i < userArray.length(); i++) {
                String username = userArray.getString(i).trim();

                if (!username.isEmpty() && uniqueUsers.add(username)) {
                    System.out.println("User Added: " + username);

                    Button addButton = new Button("Add");
                    String finalUsername = username;
                    addButton.setOnAction(event -> sendFriendRequestToServer(finalUsername));
                            
                    User user = new User(finalUsername, addButton);
                    freshList.add(user);
                    
                }
            }

            searchResults.setAll(freshList);
            searchResultsTable.setItems(searchResults);
            searchResultsTable.refresh();

        } catch (JSONException ex) {
            Logger.getLogger(AddFriendController.class.getName()).log(Level.SEVERE, "JSON Parsing Error", ex);
            new Dialog().showDialog("Error", "An error occurred while processing the search results.", "ERROR");
        }
    }


    @FXML
    private void sendFriendRequestToServer(String friendUsername) {
        if (loggedInUser == null || loggedInUser.equals("UnknownUser")) {
            System.out.println("Error: No logged-in user found.");
            return;
        }

        try {
            SetSocket socket = new SetSocket();
            JSONObject request = new JSONObject();
            request.put("Command", "sendFriendRequest");
            request.put("fromUser", loggedInUser);
            request.put("toUser", friendUsername);
            request.put("status", "Pending");

            socket.getDOS().println(request);
            String response = socket.getDIS().readLine();

            System.out.println("Friend request response: " + response);
            
            if ("Success".equalsIgnoreCase(response)) {
            new Dialog().showDialog("Friend Request", "Friend request sent to " + friendUsername + "!", "INFORMATION");
             // Remove user from the table after sending the request
            searchResults.removeIf(user -> user.getInstanceUserName().equals(friendUsername));
            searchResultsTable.refresh();
        } else {
            new Dialog().showDialog("Friend Request Failed", "Could not send friend request. Try again later.", "ERROR");
        }
        } catch (IOException | JSONException ex) {
            Logger.getLogger(AddFriendController.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

}
