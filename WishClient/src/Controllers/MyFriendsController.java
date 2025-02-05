package Controllers;

import BDO.Friends;  
import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
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
    private ListView<String> friendsListView; 
    private ObservableList<String> friendsListData = FXCollections.observableArrayList();

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        try {
            SetSocket socket = new SetSocket();
            JSONObject getFriendsReq = new JSONObject();
            getFriendsReq.put("Command", "getFriendsList");

            socket.getDOS().println(getFriendsReq);
            String data = socket.getDIS().readLine();

            updateFriendsListFromString(data);

            System.out.println("Friends list data: " + data);
        } catch (IOException | JSONException ex) {
            Logger.getLogger(MyFriendsController.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    public void updateFriendsListFromString(String input) {
        friendsListView.setItems(friendsListData);  
        friendsListData.clear(); 

        String[] friendsArray = input.split(",");
        for (String friend : friendsArray) {
            friendsListData.add(friend.trim()); 
        }
    }

    @FXML
    private void handleBackButton(ActionEvent event) {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/Views/profileview.fxml"));
            Parent root = loader.load();
            Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
            stage.setScene(new Scene(root));
            stage.show();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
