package Controllers;

import Controllers.LoadView;
import Requests.ReceiveRequests;
import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.net.URL;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import wishserver.WishServer;

public class AdminViewController extends Thread {

    private Thread serverThread;
    private boolean isServerRunning;
    private ServerSocket serverSocket;
    private WishServer server;

    @FXML
    private Button startButton;

    @FXML
    private Button stopButton;
    Dialog dg = new Dialog();
    @FXML
    private Button startButton1;
    
    @FXML
    public void startServer() {
        if (!isServerRunning) {
            try {
                serverSocket = new ServerSocket(5555);
                isServerRunning = true;
//                System.out.println("✅ Server is running...");
                  dg.showDialog("Server status", "Server is running sucessfully", "CONFIRMATION");
                start();

            } catch (IOException e) {
                Logger.getLogger(WishServer.class.getName()).log(Level.SEVERE, null, e);
                dg.showDialog("Server status", "Server Can not start", "ERROR");
            }
        } else {
           
            dg.showDialog("Server status", "Server is Already running", "CONFIRMATION");
        }
    }

    @FXML
    public void stopServer() {
        if (isServerRunning) {
            stop();
            isServerRunning = false;
            dg.showDialog("Server status", "Server is Stop Sucessfully", "CONFIRMATION");
            try {
                serverSocket.close();
            } catch (IOException ex) {
                Logger.getLogger(AdminViewController.class.getName()).log(Level.SEVERE, null, ex);
            }
            
        }else{
            dg.showDialog("Server status", "Server is Already Stop", "CONFIRMATION");
        }
    }

    public boolean isIsServerRunning() {
        return isServerRunning;
    }

    // serverThread  = new Thread(new Runnable() {
    public void run() {
        while (true) {
            try {
                Socket s = serverSocket.accept();
                new ReceiveRequests(s);
            } catch (IOException ex) {
                Logger.getLogger(WishServer.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
    }
    // });

    public void initialize(URL url, ResourceBundle rb) {

    }

    @FXML
    private void executeQueryAction(ActionEvent event) {
        try {
            new LoadView(event, "ExcuteQuery");
        } catch (IOException ex) {
            Logger.getLogger(AdminViewController.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

}
