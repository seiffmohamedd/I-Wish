/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package DBO;

import java.io.Serializable;
import org.json.JSONException;
import org.json.JSONObject;



/**
 *
 * @author Windo
 */
public class User implements Serializable{
    private String userName;
    private String firstName;
    private String lastName;
    private String password;
    private String gender;
    private String birthDate;
    private String phone;
    private int points;
    private int wishlistID;
    
    
    
    public User(JSONObject user) throws JSONException{
        this.userName = user.getString("userName");
        this.firstName = user.getString("firstName");
        this.lastName = user.getString("lastName");
        this.password = user.getString("password");
        this.gender = user.getString("gender");
        this.birthDate = user.getString("birthDate");
        this.phone = user.getString("phone");
    }
    public User(String userName, String firstName, String lastName, String password, String gender, String birthDate, String phone) {
        this.userName = userName;
        this.firstName = firstName;
        this.lastName = lastName;
        this.password = password;
        this.gender = gender;
        this.birthDate = birthDate;
        this.phone = phone;
    }

    public User(String userName, String firstName, String lastName, String password, String gender, String birthDate, String phone, int points, int wishlistID) {
        this.userName = userName;
        this.firstName = firstName;
        this.lastName = lastName;
        this.password = password;
        this.gender = gender;
        this.birthDate = birthDate;
        this.phone = phone;
        this.points = points;
        this.wishlistID = wishlistID;
    }
    
    public User(String userName) {
        this.userName = userName;
    }
    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getGender() {
        return gender;
    }

    public void setGender(String gender) {
        this.gender = gender;
    }

    public String getBirthDate() {
        return birthDate;
    }

    public void setBirthDate(String birthDate) {
        this.birthDate = birthDate;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    @Override
    public String toString() {
        return "{"
                + "\"userName\": \"" + userName + "\", "
                + "\"firstName\": \"" + firstName + "\", "
                + "\"lastName\": \"" + lastName + "\", "
                + "\"password\": \"" + password + "\", "
                + "\"gender\": \"" + gender + "\", "
                + "\"birthDate\": \"" + birthDate + "\", "
                + "\"phone\": \"" + phone + "\", "
                + "\"points\": " + points + ", "
                + "\"wishlistID\": " + wishlistID
                + "}";
    }
    
    
    

}
