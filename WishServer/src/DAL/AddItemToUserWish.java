 /*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package DAL;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.sql.SQLIntegrityConstraintViolationException;
import org.json.JSONException;
import org.json.JSONObject;

/**
 *
 * @author Windo
 */
public class AddItemToUserWish {
    private Connection DBCon = DBConnection.getConnection();
    private JSONObject userrequest;
    private final String query = "insert into WISHLISTITEM values(?,?,?) ";
    private int executeResult;
    
    public AddItemToUserWish(JSONObject userRequest) throws SQLException, JSONException {
        this.userrequest = userRequest;
        executeResult = insertItem(query);
    }


    public int getExecuteResult() {
        return executeResult;
    }
    
    

    public int insertItem(String query) throws JSONException, SQLException {
       
            PreparedStatement statement = DBCon.prepareStatement(query);
            statement.setString(1, userrequest.getString("userName"));
            statement.setInt(2, userrequest.getInt("ItemID"));
            statement.setDouble(3, userrequest.getDouble("Remaining"));
            executeResult = statement.executeUpdate();
            System.out.println("the result for the excecution for insert item in user Wish is  : "+executeResult);
            return executeResult;
        
    }
}
