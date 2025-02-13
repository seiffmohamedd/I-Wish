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
public class ContributeData implements Serializable{
    private String userName;
    private int ItemID;
    private String firendUserName;
    private Double remaining;
    private Double contributionAmount;

    public ContributeData(String userName, int ItemID, String firendUserName, Double remaining, Double contributionAmount) {
        this.userName = userName;
        this.ItemID = ItemID;
        this.firendUserName = firendUserName;
        this.remaining = remaining;
        this.contributionAmount = contributionAmount;
    }
    
    public ContributeData(JSONObject userData) throws JSONException {
        this.userName = userData.getString("userName");
        this.ItemID = userData.getInt("itemID");
        this.firendUserName = userData.getString("firendUserName");
        this.remaining = userData.getDouble("remaining");
        this.contributionAmount = userData.getDouble("contributionAmount");
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public int getItemID() {
        return ItemID;
    }

    public void setItemID(int ItemID) {
        this.ItemID = ItemID;
    }

    public String getFirendUserName() {
        return firendUserName;
    }

    public void setFirendUserName(String firendUserName) {
        this.firendUserName = firendUserName;
    }

    public Double getRemaining() {
        return remaining;
    }

    public void setRemaining(Double remaining) {
        this.remaining = remaining;
    }

    public Double getContributionAmount() {
        return contributionAmount;
    }

    public void setContributionAmount(Double contributionAmount) {
        this.contributionAmount = contributionAmount;
    }

    @Override
    public String toString() {
        return "ContributeData{" + "userName=" + userName + ", ItemID=" + ItemID + ", firendUserName=" + firendUserName + ", remaining=" + remaining + ", contributionAmount=" + contributionAmount + '}';
    }
    
    
}
