/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Main.java to edit this template
 */
package iwishserver;

import java.io.DataInputStream;
import java.io.IOException;
import java.io.PrintStream;
import java.net.ServerSocket;
import java.net.Socket;
import org.json.JSONObject;
/**
 *
 * @author mohamedhekal
 */
public class IWishServer {
    ServerSocket serverSocket;
    public IWishServer() throws IOException{
        serverSocket = new ServerSocket(5555);
        while(true){
        Socket s = serverSocket.accept();
        new HandlingRequest(s);
        
        }
    }
   
    public static void main(String[] args) throws IOException {
        new IWishServer();
    }
    
}
