/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package wishserver;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import Requests.ReceiveRequests;

/**
 *
 * @author Windo
 */
public class WishServer {

    ServerSocket serverSocket;
    public WishServer() throws IOException {
        serverSocket = new ServerSocket(5555);
        while(true){
            Socket s = serverSocket.accept();
            new ReceiveRequests(s);
        }
    }
    
    
    public static void main(String[] args) throws IOException {
        new WishServer();
    }
    
}
