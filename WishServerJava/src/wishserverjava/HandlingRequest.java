package wishserverjava;

import wishserverjava.DBO.signup;
import wishserverjava.DBO.SignIn; // Add SignIn class
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.Socket;
import java.text.ParseException;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.json.JSONException;
import org.json.JSONObject;

public class HandlingRequest extends Thread {
    DataInputStream inputData;
    DataOutputStream outputData;
    JSONObject jsonObject;
    Socket socket;

    public HandlingRequest(Socket s) {
        try {
            this.socket = s;
            inputData = new DataInputStream(socket.getInputStream());
            outputData = new DataOutputStream(socket.getOutputStream());
            start();
        } catch (IOException ex) {
            Logger.getLogger(HandlingRequest.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    public void run() {
        try {
            String data = inputData.readLine();
            jsonObject = new JSONObject(data);
            System.out.println(jsonObject.toString());

            JSONObject response = new JSONObject();

            switch (jsonObject.getString("Command")) {
                case "Signup":
                    signup su = new signup();
                    try {
                        su.signUser(jsonObject);
                        response.put("status", "success");
                    } catch (ParseException ex) {
                        Logger.getLogger(HandlingRequest.class.getName()).log(Level.SEVERE, null, ex);
                        response.put("status", "error");
                    }
                    break;

                case "Signin":
                    SignIn si = new SignIn();
                    boolean isAuthenticated = si.authenticateUser(jsonObject);
                    if (isAuthenticated) {
                        response.put("status", "success");
                        response.put("userName", jsonObject.getString("userName"));
                    } else {
                        response.put("status", "failed");
                    }
                    break;

                default:
                    response.put("status", "error");
                    break;
            }

            outputData.writeBytes(response.toString() + "\n");

            // Close connections
            inputData.close();
            outputData.close();
            socket.close();

        } catch (IOException | JSONException ex) {
            Logger.getLogger(HandlingRequest.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
}
