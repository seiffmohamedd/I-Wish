package Controllers;

import BDO.Friends;
import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.ListView;
import javafx.stage.Stage;
import org.json.JSONException;
import org.json.JSONObject;
import wishclient.SetSocket;

public class MyFriendsController implements Initializable {

    @FXML
    private ListView<String> friendsListView;  // ListView to display friends
    private ObservableList<String> friendsListData = FXCollections.observableArrayList(); // Observable list for friends

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        try {
            SetSocket socket = new SetSocket();
            JSONObject getFriendsReq = new JSONObject();
            getFriendsReq.put("Command", "getFriendsList");  // Command to fetch friends list

            socket.getDOS().println(getFriendsReq);  // Send request to server
            String data = socket.getDIS().readLine();  // Read response from server

            updateFriendsListFromString(data);  // Update the ListView with friends data

            System.out.println("Friends list data received: " + data);
        } catch (IOException | JSONException ex) {
            Logger.getLogger(MyFriendsController.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    // Method to update the ListView with friends' names from the received data
    public void updateFriendsListFromString(String input) {
        javafx.application.Platform.runLater(() -> {
            friendsListView.setItems(friendsListData);  // Set the ListView items
            friendsListData.clear();  // Clear existing data

            // Pattern to extract friend usernames from the server response
            Pattern pattern = Pattern.compile("Friends\\{personUserName=.*?, friendUserName=(.*?)\\}");
            Matcher matcher = pattern.matcher(input);

            // Iterate through all matches and add them to the ObservableList
            while (matcher.find()) {
                String friendUserName = matcher.group(1).trim();
                friendsListData.add(friendUserName);  // Add each friend to the list
            }
        });
    }

    // Back button to go back to the profile view
    @FXML
    private void handleBackButton() {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/Views/profileview.fxml"));
            Parent root = loader.load();
            Stage stage = (Stage) friendsListView.getScene().getWindow();
            Scene scene = new Scene(root);
            stage.setScene(scene);
            stage.show();
        } catch (IOException ex) {
            Logger.getLogger(MyFriendsController.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
}
