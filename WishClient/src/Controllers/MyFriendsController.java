package Controllers;

import BDO.User;
import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.util.regex.Pattern;
import java.util.regex.Matcher;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ListView;
import javafx.scene.input.MouseButton;
import javafx.scene.input.MouseEvent;
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
            getFriendsReq.put("userName", User.getUserName());

            socket.getDOS().println(getFriendsReq);
            String friendsData = socket.getDIS().readLine();
            updateFriendsListFromString(friendsData);
            System.out.println("Friends List Data: " + friendsData);

            // Add double-click event listener
            friendsListView.setOnMouseClicked(this::handleListViewDoubleClick);

        } catch (java.net.ConnectException ex) {
        new Dialog().showDialog("Connection Error", "Failed to connect to the server. Please try again later.", "ERROR");
    }catch (IOException | JSONException ex) {
            Logger.getLogger(MyFriendsController.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    


    public void updateFriendsListFromString(String input) {
        friendsListData.clear();

        Pattern pattern = Pattern.compile("friendUserName='(.*?)'");
        Matcher matcher = pattern.matcher(input);

        while (matcher.find()) {
            String friendName = matcher.group(1).trim();
            friendsListData.add(friendName);
        }

        friendsListView.setItems(friendsListData);
    }

    @FXML
    private void handleBackButton(ActionEvent event) {
        new LoadView(event, "ProfileView");
    }

    private void handleListViewDoubleClick(MouseEvent event) {
        if (event.getButton() == MouseButton.PRIMARY && event.getClickCount() == 2) {
            String selectedFriend = friendsListView.getSelectionModel().getSelectedItem();
            if (selectedFriend != null) {
                openFriendProfile(selectedFriend);
                System.out.println("the firend selected is : "+ selectedFriend);
            }
        }
    }

    private void openFriendProfile(String friendUserName) {

       try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/Views/WishListContributionView.fxml"));
            Parent root = loader.load();

            // Pass the selected friend's username to the new controller
            WishListContributionViewController controller = loader.getController();
            controller.setFriendUserName(friendUserName);
            

            Stage stage = new Stage();
            stage.setScene(new Scene(root));
            stage.setTitle(friendUserName + "'s Wish List");
            stage.show();
        } catch (IOException ex) {
            Logger.getLogger(MyFriendsController.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
   }
