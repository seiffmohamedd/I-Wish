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
import javafx.scene.control.Button;
import wishserver.WishServer;

public class AdminViewController extends Thread {

    private Thread serverThread;
    private boolean isServerRunning = false;
    private ServerSocket serverSocket;

    @FXML
    private Button startButton;

    @FXML
    private Button stopButton;
    
    Dialog dg = new Dialog();

    @FXML
    public void startServer() {
        if (!isServerRunning) {
            try {
                serverSocket = new ServerSocket(5555);
                isServerRunning = true;
                dg.showDialog("Server Status", "Server is running successfully", "CONFIRMATION");

                // Start the server thread
                serverThread = new Thread(() -> {
                    while (isServerRunning) {
                        try {
                            Socket s = serverSocket.accept();
                            new ReceiveRequests(s);
                        } catch (IOException ex) {
                            if (isServerRunning) { // Only log error if the server was running
                                Logger.getLogger(WishServer.class.getName()).log(Level.SEVERE, null, ex);
                            }
                        }
                    }
                });

                serverThread.start();
            } catch (IOException e) {
                Logger.getLogger(WishServer.class.getName()).log(Level.SEVERE, null, e);
                dg.showDialog("Server Status", "Server cannot start", "ERROR");
            }
        } else {
            dg.showDialog("Server Status", "Server is already running", "INFORMATION");
        }
    }

    @FXML
    public void stopServer() {
        if (isServerRunning) {
            isServerRunning = false; // Stop the server loop

            try {
                if (serverSocket != null && !serverSocket.isClosed()) {
                    serverSocket.close(); // Close the server socket
                }

                if (serverThread != null && serverThread.isAlive()) {
                    serverThread.interrupt(); // Stop the thread
                    serverThread = null; // Reset the thread
                }

                dg.showDialog("Server Status", "Server stopped successfully", "CONFIRMATION");
            } catch (IOException ex) {
                Logger.getLogger(AdminViewController.class.getName()).log(Level.SEVERE, null, ex);
                dg.showDialog("Server Status", "Error while stopping the server", "ERROR");
            }
        } else {
            dg.showDialog("Server Status", "Server is already stopped", "INFORMATION");
        }
    }

    public void initialize(URL url, ResourceBundle rb) {
        // No specific initialization needed
    }

    @FXML
    private void executeQueryAction(ActionEvent event) {
        try {
            new LoadView(event, "ExecuteQuery");
        } catch (IOException ex) {
            Logger.getLogger(AdminViewController.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
}
