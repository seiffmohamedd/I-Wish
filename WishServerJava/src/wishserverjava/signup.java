/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package wishserverjava;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.json.JSONException;
import org.json.JSONObject;


/**
 *
 * @author mohamedhekal
 */
public class signup {
      
       Connection con ;
       public signup(){
           try {
                 dbconnection c = new dbconnection();
                 con = c.con;
           } catch (SQLException ex) {
               
               System.out.println("database connection failed");
               Logger.getLogger(signup.class.getName()).log(Level.SEVERE, null, ex);
           }
       
       }
       
       
       public void signUser(JSONObject c) throws ParseException, JSONException{
           
           try {
                String query = "insert into Person(userName, firstName, lastName,gender, password, birthDate,phone) values(?,?,?,?,?,?,?)";
                PreparedStatement statement = con.prepareStatement(query);
                statement.setString(1, c.getString("userName"));
                statement.setString(2, c.getString("FirstName"));
                statement.setString(3, c.getString("LastName"));
                statement.setString(4, c.getString("gender"));
                statement.setString(5, c.getString("Password"));
               
                String birthDateStr = c.getString("BirthDate");
                SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");  // Adjust format if needed
                java.util.Date parsedDate = sdf.parse(birthDateStr);
                java.sql.Date sqlDate = new java.sql.Date(parsedDate.getTime());
                
                statement.setDate(6, sqlDate);
                statement.setString(7, c.getString("phone"));
                int result = statement.executeUpdate();
                System.out.println("the querey exc result is " + result);
            } catch (SQLException ex) {
                Logger.getLogger(signup.class.getName()).log(Level.SEVERE, null, ex);
            }

       }
    
}
