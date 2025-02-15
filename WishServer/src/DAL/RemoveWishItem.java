/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package DAL;
import DBO.WishitemRemove;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;


/**
 *
 * @author Windo
 */
public class RemoveWishItem {
    private WishitemRemove itemremove;
   private Connection DBCon = DBConnection.getConnection();
    private final String query1 = "select sum(CONTRIBUTEAMOUNT) as sum_point from WISHLISTITEMCONTRIBUTE where FRIENDUSERNAME = ? and ITEMID = ?";
    private final String query2 = "UPDATE PERSON SET POINTS = POINTS + ? WHERE USERNAME = ?";
    private final String query = "Delete from WISHLISTITEM where username = ? and ITEMID = ? ";
    private int executeResult;
    private int contributionPoint;
    public RemoveWishItem(WishitemRemove itemremove) throws SQLException{
        this.itemremove = itemremove;
        
        getContributionPoints(query1);
        addPointsToUser(query2);
        removeitem(query);

    }
    
    
    public int getExecuteResult() {
        return executeResult;
    }
    
    private void getContributionPoints(String query) throws SQLException {
        PreparedStatement statement = DBCon.prepareStatement(query);
        statement.setString(1, itemremove.getUserName());
        statement.setInt(2, itemremove.getItemID());
        ResultSet rs = statement.executeQuery();
        rs.next();
        contributionPoint = rs.getInt(1);
//        System.out.println("the count of the contribution is "+ rs.getInt(1));
        executeResult = 1;
    }
    private void addPointsToUser(String query) throws SQLException {
        if(executeResult == 1){
        PreparedStatement statement = DBCon.prepareStatement(query);
        statement.setInt(1,contributionPoint );
        statement.setString(2, itemremove.getUserName());
        executeResult = statement.executeUpdate();
        System.out.println("the result for the excecution for addPointsToUser is : "+executeResult);
        }else{
            executeResult = 0;
        }
    }
    
    private void removeitem(String query) throws SQLException {
        if(executeResult == 1){
        PreparedStatement statement = DBCon.prepareStatement(query);
        statement.setString(1, itemremove.getUserName());
        statement.setInt(2, itemremove.getItemID());
        executeResult = statement.executeUpdate();
         System.out.println("the result for the excecution for remove item is : "+executeResult);
        }
        else{
            executeResult = 0;
        }
    }
}
