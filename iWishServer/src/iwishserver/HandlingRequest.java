/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package iwishserver;

import iwishserver.DBO.signup;
import java.io.DataInputStream;
import java.io.IOException;
import java.net.Socket;
import java.text.ParseException;
import java.util.logging.Level;
import java.util.logging.Logger;
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
                jsonObject = new JSONObject(data);
                System.out.println(jsonObject.toString());
                
                switch (jsonObject.getString("Command")) {
                    case "Signup":
                        signup su = new signup();
                        {
                            try {
                                su.signUser(jsonObject);
                            } catch (ParseException ex) {
                                Logger.getLogger(HandlingRequest.class.getName()).log(Level.SEVERE, null, ex);
                            }
                        }
                        break;
                        
                    default:
                        System.out.println("not sign up");;
                }
                
                try {
                    
                    
                    inputData.close();
                    
                  
                } catch (IOException ex) {
                    Logger.getLogger(HandlingRequest.class.getName()).log(Level.SEVERE, null, ex);
                }
            } catch (IOException ex) {
            Logger.getLogger(HandlingRequest.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
}
