package Controllers;

import BDO.PersonFriends;
import BDO.User;
import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.TableCell;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.HBox;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import wishclient.SetSocket;

public class FriendRequestController implements Initializable {

    @FXML
    private TableView<PersonFriends> friendRequestTable;
    @FXML
    private TableColumn<PersonFriends, String> friendUserNameCol;
    @FXML
    private TableColumn<PersonFriends, Void> actionCol; 

    private ObservableList<PersonFriends> friendRequestsList = FXCollections.observableArrayList();
    private SetSocket socket;
    private Dialog dg; 

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        dg = new Dialog();
        loadFriendRequests();
        setupActionColumn();
    }

    private void loadFriendRequests() {
        try {
            socket = new SetSocket();
            JSONObject request = new JSONObject();
            request.put("Command", "getFriendRequests");
            request.put("friendUserName", User.getUserName());

            socket.getDOS().println(request);
            socket.getDOS().flush();

            String response = socket.getDIS().readLine();

            if (response == null || response.trim().isEmpty()) {
                System.err.println("Error: Empty response from server.");
            } else if (response.trim().startsWith("[")) {
                JSONArray data = new JSONArray(response);
                updateFriendRequestsTable(data);
            } else {
                dg.showDialog("Friend Requests", "No friend requests found.", "INFO");
            }

            socket.closeStreams();
        } catch (JSONException | IOException ex) {
            Logger.getLogger(FriendRequestController.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    private void updateFriendRequestsTable(JSONArray jsonArray) {
        friendRequestsList.clear(); // Clear old data

        for (int i = 0; i < jsonArray.length(); i++) {
            try {
                String requesterUsername = jsonArray.getString(i);
                friendRequestsList.add(new PersonFriends(requesterUsername, User.getUserName(), "Pending"));
            } catch (JSONException ex) {
                dg.showDialog("ERROR", "Error in data retrieval", "ERROR");
            }
        }

        // Set column property to display usernames
        friendUserNameCol.setCellValueFactory(new PropertyValueFactory<>("personUserName"));

        // Bind data to TableView
        friendRequestTable.setItems(friendRequestsList);
        
    }
 private void setupActionColumn() {
    actionCol.setCellFactory(param -> new TableCell<PersonFriends, Void>() { // Explicitly define generic types
        private final Button acceptButton = new Button("Accept");
        private final Button declineButton = new Button("Decline");

        {
            acceptButton.setOnAction(event -> {
                PersonFriends request = getTableView().getItems().get(getIndex());
                handleFriendRequest(request, "Accepted");
            });

            declineButton.setOnAction(event -> {
                PersonFriends request = getTableView().getItems().get(getIndex());
                handleFriendRequest(request, "Declined");
            });
        }
        
       

        @Override
        protected void updateItem(Void item, boolean empty) {
            super.updateItem(item, empty);
            if (empty) {
                setGraphic(null);
            } else {
                HBox buttons = new HBox(10, acceptButton, declineButton);
                setGraphic(buttons);
            }
        }
    });
}
 private void handleFriendRequest(PersonFriends request, String status) {
    try {
        socket = new SetSocket();
        JSONObject requestJson = new JSONObject();
        requestJson.put("Command", "updateFriendRequest");
        requestJson.put("personUserName", request.getPersonUserName());
        requestJson.put("friendUserName", User.getUserName());
        requestJson.put("status", status);

        socket.getDOS().println(requestJson);
        socket.getDOS().flush();

        String response = socket.getDIS().readLine();
        socket.closeStreams();

        if ("Success".equalsIgnoreCase(response)) {
            dg.showDialog("Success", "Friend request " + status.toLowerCase() + "!", "INFO");
            friendRequestsList.remove(request); 
        } else {
            dg.showDialog("Error", "Failed to update friend request!", "ERROR");
        }
    } catch (IOException | JSONException ex) {
        Logger.getLogger(FriendRequestController.class.getName()).log(Level.SEVERE, null, ex);
    }
 }

     @FXML
    private void backbtnAction(ActionEvent event) {
        new LoadView(event, "ProfileView");
    }
}

