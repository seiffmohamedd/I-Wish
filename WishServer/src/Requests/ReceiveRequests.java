/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
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

/**
 *
 * @author hekal
 */
public class ReceiveRequests extends Thread{
    private DataInputStream DIS ;
    private PrintStream DOS;
    private JSONObject jsonObject;

    public ReceiveRequests(Socket s) throws IOException{
        DIS = new DataInputStream(s.getInputStream());
        DOS = new PrintStream(s.getOutputStream());
        start();
    
    }
    
    public void run(){
        try {
            String dataInDIS = DIS.readLine();
            jsonObject = new JSONObject(dataInDIS);
            System.out.println(jsonObject.toString()); //just print the request Json
            HandleRequests userRequest;
            try {
                userRequest = new HandleRequests(jsonObject);
//                System.out.println("the user request is handeled");
                if("getWishList".equals(userRequest.getCommand()))
                    DOS.println(userRequest.getUserWishListItems());
                else
                    DOS.println(userRequest.getHandlingResult());
                
            } catch (SQLException ex) {
                Logger.getLogger(ReceiveRequests.class.getName()).log(Level.SEVERE, null, ex);
            } catch (ParseException ex) {
                Logger.getLogger(ReceiveRequests.class.getName()).log(Level.SEVERE, null, ex);
            }
            
        } catch (IOException ex) {
            Logger.getLogger(ReceiveRequests.class.getName()).log(Level.SEVERE, null, ex);
        }catch (JSONException ex) {
            Logger.getLogger(ReceiveRequests.class.getName()).log(Level.SEVERE, null, ex);
        } 
                                                
    }
    
}
