package Controllers;

import BDO.Friends;
import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.util.regex.Pattern;
import java.util.regex.Matcher;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ListView;
import javafx.stage.Stage;
import org.json.JSONException;
import org.json.JSONObject;
import wishclient.SetSocket;

public class MyFriendsController implements Initializable {

    @FXML
    private Button backButton;
    
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
            String friendsData = socket.getDIS().readLine();
            updateFriendsListFromString(friendsData);
            System.out.println("Friends List Data: " + friendsData);

        } catch (IOException | JSONException ex) {
            Logger.getLogger(MyFriendsController.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    public void updateFriendsListFromString(String input) {
        friendsListData.clear();

        Pattern pattern = Pattern.compile("friendUserName='(.*?)'");
        Matcher matcher = pattern.matcher(input);

        while (matcher.find()) {
            String friendName = matcher.group(1).trim();
            friendsListData.add(friendName); // Add only the friend's username
        }

        friendsListView.setItems(friendsListData);
    }


    @FXML
    private void handleBackButton() {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/Views/profileview.fxml"));
            Parent root = loader.load();
            Stage stage = (Stage) backButton.getScene().getWindow();
            Scene scene = new Scene(root);
            stage.setScene(scene);
            stage.show();
        } catch (IOException ex) {
            Logger.getLogger(MyFriendsController.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
}


///FRIENDS LIST DONEEEEEEEEEE