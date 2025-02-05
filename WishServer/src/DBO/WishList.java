/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package DBO;

import java.io.Serializable;

/**
 *
 * @author Windo
 */
public class WishList implements Serializable{
    private String itemName;
    private String itemDescription;
    private double price;
    private double remaining;

    public WishList(String itemName, String itemDescription, double price, double remaining) {
        this.itemName = itemName;
        this.itemDescription = itemDescription;
        this.price = price;
        this.remaining = remaining;
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

    @Override
    public String toString() {
        return "WishList{" + "itemName=" + itemName + ", itemDescription=" + itemDescription + ", price=" + price + ", remaining=" + remaining + '}';
    }
    
    
}
