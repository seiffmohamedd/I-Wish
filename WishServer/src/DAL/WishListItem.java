/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package DAL;

import DBO.WishList;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.ParseException;
import java.util.ArrayList;

/**
 *
 * @author Windo
 */
public class WishListItem {
    private Connection DBCon = DBConnection.getConnection();
    private final String query = "select w.remaining,I.ITEMID, I.ITEMNAME,I.ITEMPRICE, I.ITEMDESCRIPTION from wishlistitem w, item i where w.ITEMID = i.ITEMID and w.userName =  ?";
    private int executeResult;
    private String UserName;
    private ArrayList<WishList> userWishListItemsArr = new ArrayList<>();
    public WishListItem(String UserName) throws SQLException{

        this.UserName = UserName;
        System.out.println("the username is : " + UserName);
        getWishList();
        
    }

    public int getExecuteResult() {
        return executeResult;
    }

    public ArrayList<WishList> getUserWishListItemsArr() {
        return userWishListItemsArr;
    }
    
    
    
    private void getWishList() throws SQLException {
        PreparedStatement statement = DBCon.prepareStatement(query);
        statement.setString(1, UserName);
        ResultSet rs = statement.executeQuery();
        while(rs.next()){
            WishList tempWishListItem = new WishList(rs.getInt("ITEMID"),rs.getString("ITEMNAME"),rs.getString("ITEMDESCRIPTION"), rs.getDouble("ITEMPRICE"),rs.getDouble("remaining") );
            System.out.println("tempWishListItem from db is : "+ tempWishListItem.toString());
            userWishListItemsArr.add(tempWishListItem);
            executeResult = 1;
        }
        

    }
    
}
