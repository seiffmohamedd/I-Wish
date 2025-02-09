/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package DAL;

import DBO.Items;
import DBO.User;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import org.json.JSONException;
import org.json.JSONObject;

/**
 *
 * @author Windo
 */
public class GetItemLike {
    private final Connection DBCon;
    private String itemName;
    private final String query = "SELECT * from Item where lower(ITEMNAME) like ?";
    private int executeResult;
    private ArrayList<Items> ItemsList = new ArrayList<>();
    
    public GetItemLike(JSONObject userRequest) throws SQLException, JSONException {
        DBCon = establishConnection();
        itemName = userRequest.getString("item");  
        getItems(query);
        DBCon.close();
    }


    private Connection establishConnection() throws SQLException {
        return new DBConnection().getConection();
    }

    public int getExecuteResult() {
        return executeResult;
    }

    public ArrayList<Items> getItemsList() {
        return ItemsList;
    }
    
    
    private void getItems(String query) throws SQLException {
        PreparedStatement statement = DBCon.prepareStatement(query);
        statement.setString(1, "%" + itemName.toLowerCase() + "%"); 
        ResultSet rs = statement.executeQuery();
        
        while (rs.next()) {
            Items tempitem = new Items(rs.getInt(1),rs.getString(2),rs.getDouble(3),rs.getString(4));
            System.out.println("User found: " + tempitem.getItemName());
            ItemsList.add(tempitem);
            executeResult = 1;
        }
        if (ItemsList.isEmpty()) {
            executeResult = 0;
        }
    }
}
