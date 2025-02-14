/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package BDO;

/**
 *
 * @author Lenovo
 */
public class PersonFriends {
    private String personUserName ;
    private String friendUserName ;
    private String status;

    public PersonFriends(String personUserName, String friendUserName, String status) {
        this.personUserName = personUserName;
        this.friendUserName = friendUserName;
        this.status = status;
    }

    public PersonFriends(String friendUserName) {
        this.friendUserName = friendUserName;
    }
    
    
    public String getPersonUserName() {
        return personUserName;
    }

    public void setPersonUserName(String personUserName) {
        this.personUserName = personUserName;
    }

    public String getFriendUserName() {
        return friendUserName;
    }

    public void setFriendUserName(String friendUserName) {
        this.friendUserName = friendUserName;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }
    
    
}
