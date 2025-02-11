/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package DBO;

import java.io.Serializable;
import java.time.LocalDate;
import org.json.JSONException;
import org.json.JSONObject;

/**
 *
 * @author Windo
 */
public class ChargePoints implements Serializable{
    private String creditCardNumber;
    private int cvv;
    private LocalDate validTo;
    private String holderName;
    private String userName;
    private int numberOfPoints;

    public ChargePoints(String creditCardNumber, int cvv, LocalDate validTo, String holderName, String userName, int numberOfPoints) {
        this.creditCardNumber = creditCardNumber;
        this.cvv = cvv;
        this.validTo = validTo;
        this.holderName = holderName;
        this.userName = userName;
        this.numberOfPoints = numberOfPoints;
    }
     public ChargePoints(JSONObject userdata) throws JSONException {
        this.creditCardNumber = userdata.getString("creditCardNumber");
        this.cvv = userdata.getInt("cvv");
        this.validTo = LocalDate.parse(userdata.getString("validTo"));
        this.holderName = userdata.getString("holderName");
        this.userName = userdata.getString("userName");
        this.numberOfPoints = userdata.getInt("numberOfPoints");
    }

    public String getCreditCardNumber() {
        return creditCardNumber;
    }

    public void setCreditCardNumber(String creditCardNumber) {
        this.creditCardNumber = creditCardNumber;
    }

    public int getCvv() {
        return cvv;
    }

    public void setCvv(int cvv) {
        this.cvv = cvv;
    }

    public LocalDate getValidTo() {
        return validTo;
    }

    public void setValidTo(LocalDate validTo) {
        this.validTo = validTo;
    }

    public String getHolderName() {
        return holderName;
    }

    public void setHolderName(String holderName) {
        this.holderName = holderName;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public int getNumberOfPoints() {
        return numberOfPoints;
    }

    public void setNumberOfPoints(int numberOfPoints) {
        this.numberOfPoints = numberOfPoints;
    }

    @Override
    public String toString() {
        return "ChargePoints{" + "creditCardNumber=" + creditCardNumber + ", cvv=" + cvv + ", validTo=" + validTo + ", holderName=" + holderName + ", userName=" + userName + ", numberOfPoints=" + numberOfPoints + '}';
    }
    
}