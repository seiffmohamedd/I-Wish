/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package wishserverjava;

import java.io.DataInputStream;
import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.text.ParseException;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.json.JSONException;
import org.json.JSONObject;

/**
 *
 * @author mohamedhekal
 */
public class HandlingRequest extends Thread {
    DataInputStream inputData ;
    JSONObject jsonObject;
    public HandlingRequest(Socket s){
        try {
            inputData = new DataInputStream(s.getInputStream());
            start();
        } catch (IOException ex) {
            Logger.getLogger(HandlingRequest.class.getName()).log(Level.SEVERE, null, ex);
        }
        
    
    }
    
    public void run(){
        try {
            String data = inputData.readLine();
            try {
                jsonObject = new JSONObject(data);
            } catch (JSONException ex) {
                Logger.getLogger(HandlingRequest.class.getName()).log(Level.SEVERE, null, ex);
            }
            System.out.println(jsonObject.toString());
            signup su = new signup();
            try {
                try {
                    su.signUser(jsonObject);
                } catch (JSONException ex) {
                    Logger.getLogger(HandlingRequest.class.getName()).log(Level.SEVERE, null, ex);
                }
            } catch (ParseException ex) {
                System.out.println("error in signup");
                Logger.getLogger(HandlingRequest.class.getName()).log(Level.SEVERE, null, ex);
            }
            inputData.close();

         
//        System.out.println(jsonObject.getInt("ID"));
        } catch (IOException ex) {
            Logger.getLogger(HandlingRequest.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
}
