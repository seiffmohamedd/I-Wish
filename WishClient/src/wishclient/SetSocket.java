/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package wishclient;
import java.io.DataInputStream;
import java.io.IOException;
import java.io.PrintStream;
import java.net.Socket;
/**
 *
 * @author Windo
 */
public class SetSocket {
    private final String IP = "localhost";
    private final int PORT = 5555;
    private Socket server;
    private DataInputStream DIS;
    private PrintStream DOS;
    
    
    public SetSocket() throws IOException{
        server = new Socket(IP, PORT);
        DIS = new DataInputStream(server.getInputStream());
        DOS = new PrintStream(server.getOutputStream());
    }

    public DataInputStream getDIS() {
        return DIS;
    }

    public PrintStream getDOS() {
        return DOS;
    }
    
    
}
