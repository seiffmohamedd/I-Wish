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
import java.util.logging.Level;
import java.util.logging.Logger;
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
    public void closeStreams(){
        try {
            DIS.close();
            DOS.close();
        } catch (IOException ex) {
            System.out.println("Cannot close streams");
            Logger.getLogger(SetSocket.class.getName()).log(Level.SEVERE, null, ex);
        }
        
    }
    
}
