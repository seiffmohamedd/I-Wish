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
public class WishList implements Serializable{
    private int itemid;
    private String itemName;
    private String itemDescription;
    private double price;
    private double remaining;

    public WishList(int itemid , String itemName, String itemDescription, double price, double remaining) {
        this.itemid = itemid;
        this.itemName = itemName;
        this.itemDescription = itemDescription;
        this.price = price;
        this.remaining = remaining;
    }

    public WishList(int itemid ,String itemName) {
        this.itemName = itemName;
        this.itemid = itemid;
    }

    public WishList(int itemid, String itemName, double price, String itemDescription) {
        this.itemid = itemid;
        this.itemName = itemName;
        this.itemDescription = itemDescription;
        this.price = price;
    }

    
    public String getItemName() {
        return itemName;
    }

    public void setItemName(String itemName) {
        this.itemName = itemName;
    }

    public String getItemDescription() {
        return itemDescription;
    }

    public void setItemDescription(String itemDescription) {
        this.itemDescription = itemDescription;
    }

    public double getPrice() {
        return price;
    }

    public void setPrice(double price) {
        this.price = price;
    }

    public double getRemaining() {
        return remaining;
    }

    public void setRemaining(double remaining) {
        this.remaining = remaining;
    }

    public int getItemid() {
        return itemid;
    }
    

    @Override
    public String toString() {
        return "{"
                 + "\"ItemID\": \"" + itemid + "\", "
                + "\"itemName\": \"" + itemName + "\", "
                + "\"itemDescription\": \"" + itemDescription + "\", "
                + "\"price\": " + price + ", "
                + "\"remaining\": " + remaining
                + "}";
    }
    
    
    
}
