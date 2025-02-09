/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package BDO;

import java.io.Serializable;
import org.json.JSONException;
import org.json.JSONObject;

/**
 *
 * @author Windo
 */
public class Items implements Serializable{
    private int itemID;
    private String itemName;
    private double itemPrice;
    private String itemDescription;

    public Items(int itemID, String itemName, double itemPrice, String itemDescription) {
        this.itemID = itemID;
        this.itemName = itemName;
        this.itemPrice = itemPrice;
        this.itemDescription = itemDescription;
    }
    public Items(JSONObject json) throws JSONException {
        itemID = json.getInt("itemID");
        itemName = json.getString("itemName");
        itemPrice = json.getDouble("itemPrice");
        itemDescription = json.getString("itemDescription");
    }
    
    public int getItemID() {
        return itemID;
    }

    public void setItemID(int itemID) {
        this.itemID = itemID;
    }

    public String getItemName() {
        return itemName;
    }

    public void setItemName(String itemName) {
        this.itemName = itemName;
    }

    public double getItemPrice() {
        return itemPrice;
    }

    public void setItemPrice(double itemPrice) {
        this.itemPrice = itemPrice;
    }

    public String getItemDescription() {
        return itemDescription;
    }

    public void setItemDescription(String itemDescription) {
        this.itemDescription = itemDescription;
    }

    @Override
    public String toString() {
        return "{"
                + "\"itemID\": " + itemID + ", "
                + "\"itemName\": \"" + itemName + "\", "
                + "\"itemPrice\": " + itemPrice + ", "
                + "\"itemDescription\": \"" + itemDescription + "\""
                + "}";
    }
    
    
    
}
