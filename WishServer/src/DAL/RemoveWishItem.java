/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package DAL;
import DBO.WishitemRemove;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;


/**
 *
 * @author Windo
 */
public class RemoveWishItem {
    private WishitemRemove itemremove;
    private final Connection DBCon;
    private final String query = "Delete from WISHLISTITEM where username = ? and ITEMID = ? ";
    private int executeResult;
    
    public RemoveWishItem(WishitemRemove itemremove) throws SQLException{
        this.itemremove = itemremove;
        DBCon = establishConnection();
        removeitem(query);
        DBCon.close();
    }
    
    private Connection establishConnection() throws SQLException {
        return new DBConnection().getConection();
    }
    
    public int getExecuteResult() {
        return executeResult;
    }
    
     private void removeitem(String query) throws SQLException {
        PreparedStatement statement = DBCon.prepareStatement(query);
        statement.setString(1, itemremove.getUserName());
        statement.setInt(2, itemremove.getItemID());
        executeResult = statement.executeUpdate();
         System.out.println("the result for the excecution for remove item is : "+executeResult);
     }
}
