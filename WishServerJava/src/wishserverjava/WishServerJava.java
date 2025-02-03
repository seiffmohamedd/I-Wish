
package wishserverjava;
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

