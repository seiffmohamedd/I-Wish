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
import java.util.HashSet;
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
    private TableColumn<User, Button> actionColumn;
    @FXML
    private Button backButton;

    private ObservableList<User> searchResults = FXCollections.observableArrayList();
    private String loggedInUser; 

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
                searchReq.put("query", searchQuery);
                searchReq.put("userName", loggedInUser); 

                socket.getDOS().println(searchReq);
                String data = socket.getDIS().readLine();

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
            HashSet<String> uniqueUsers = new HashSet<>();  

            System.out.println("JSON from Server: " + data); 

            ObservableList<User> freshList = FXCollections.observableArrayList(); 

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

            for (User user : freshList) {
                System.out.println(" - " + user.getInstanceUserName());  
            }

        } catch (JSONException ex) {
            Logger.getLogger(AddFriendController.class.getName()).log(Level.SEVERE, null, ex);
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
        } catch (IOException | JSONException ex) {
            Logger.getLogger(AddFriendController.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    @FXML
    private void handleBackButton() {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/Views/ProfileView.fxml"));
            Scene profileViewScene = new Scene(loader.load());
            Stage stage = (Stage) backButton.getScene().getWindow();
            stage.setScene(profileViewScene);
            stage.show();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}

//THIS WORKSSSSSSSSSSSSSSSSSSSSSSSSSS YSEIFFF
//FINALLLLLL