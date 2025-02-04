package wishserverjava.DBO;

import wishserverjava.dbconnection;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.json.JSONException;
import org.json.JSONObject;

public class SignIn {
    Connection con;

    public SignIn() {
        try {
            dbconnection c = new dbconnection();
            con = c.con;
        } catch (SQLException ex) {
            System.out.println("Database connection failed");
            Logger.getLogger(SignIn.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    public boolean authenticateUser(JSONObject c) {
        try {
            String query = "select * from Person where userName=? AND password=?";
            PreparedStatement statement = con.prepareStatement(query);
            statement.setString(1, c.getString("userName"));
            statement.setString(2, c.getString("password"));
            ResultSet resultSet = statement.executeQuery();
            
            return resultSet.next(); 

        } catch (SQLException | JSONException ex) {
            Logger.getLogger(SignIn.class.getName()).log(Level.SEVERE, null, ex);
        }
        return false;
    }
}
