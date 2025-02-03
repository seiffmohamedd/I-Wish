/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Main.java to edit this template
 */
package wishserverjava   ;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
/**
 *
 * @author mohamedhekal
 */
public class WishServerJava {
    ServerSocket serverSocket;
    public WishServerJava() throws IOException{
        serverSocket = new ServerSocket(5555);
        while(true){
        Socket s = serverSocket.accept();
        new HandlingRequest(s);
        
        }
    }
   
    public static void main(String[] args) throws IOException {
        new WishServerJava();
    }
    
}