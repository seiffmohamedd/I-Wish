package Requests;

import java.io.DataInputStream;
import java.io.IOException;
import java.io.PrintStream;
import java.net.Socket;
import java.sql.SQLException;
import java.text.ParseException;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.json.JSONException;
import org.json.JSONObject;

public class ReceiveRequests extends Thread {
    private DataInputStream DIS;
    private PrintStream DOS;
    private JSONObject jsonObject;

    public ReceiveRequests(Socket s) throws IOException {
        DIS = new DataInputStream(s.getInputStream());
        DOS = new PrintStream(s.getOutputStream());
        start();
    }

    public void run() {
        try {
            String dataInDIS = DIS.readLine();
            jsonObject = new JSONObject(dataInDIS);
            System.out.println(jsonObject.toString());

            HandleRequests userRequest;
            try {
                userRequest = new HandleRequests(jsonObject);

                if ("GetProfileData".equals(userRequest.getCommand())) {
                    DOS.println(userRequest.getUserWishListItems());
                    DOS.println(userRequest.getUserNotificationArr());       
                }
                else if ("getFriendsList".equals(userRequest.getCommand())) {  
                    DOS.println(userRequest.getUserFriendsList());  
                }else if (("RemoveWishListItem".equals(userRequest.getCommand()))|| ("addItemToWish".equals(userRequest.getCommand())) || ("ChargePoints".equals(userRequest.getCommand()))|| ("Contribute".equals(userRequest.getCommand()))|| ("GETWISHLIST".equals(userRequest.getCommand()))){  
                    DOS.println(userRequest.getHandlingResult());
                }else if ("getItemsLike".equals(userRequest.getCommand())){  
                    DOS.println(userRequest.getSearchItems());

                }else {
                    DOS.println(userRequest.getHandlingResult());
                    DOS.println(userRequest.getUserData());
                }

            } catch (SQLException | ParseException ex) {
                Logger.getLogger(ReceiveRequests.class.getName()).log(Level.SEVERE, null, ex);
            }

        } catch (IOException | JSONException ex) {
            Logger.getLogger(ReceiveRequests.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
}
