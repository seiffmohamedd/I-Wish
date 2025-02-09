/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package BDO;

import java.io.Serializable;

/**
 *
 * @author Windo
 */
public class WishitemRemove implements Serializable{
    private int ItemID;
    private String userName;

    public WishitemRemove(int ItemID, String userName) {
        this.ItemID = ItemID;
        this.userName = userName;
    }

     public int getItemID() {
        return ItemID;
    }

    public void setItemID(int ItemID) {
        this.ItemID = ItemID;
    }
    
    
  

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }
    
}
