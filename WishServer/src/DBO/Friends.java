package DBO;

import java.io.Serializable;
import java.sql.Timestamp;


public class Friends implements Serializable {
    private String personUserName;
    private String friendUserName;
    private Timestamp timestamp;
    private String status;

    public Friends(String personUserName, String friendUserName, Timestamp timestamp, String status) {
        this.personUserName = personUserName;
        this.friendUserName = friendUserName;
        this.timestamp = timestamp;
        this.status = status;
    }
    
    public Friends(String personUserName, String friendUserName, String status) {
        this.personUserName = personUserName;
        this.friendUserName = friendUserName;
        this.status = status;
    }

    public Friends(String personUserName, String friendUserName) {
        this.personUserName = personUserName;
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

    public Timestamp getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(Timestamp timestamp) {
        this.timestamp = timestamp;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    @Override
    public String toString() {
        return "Friends{" +
                "personUserName='" + personUserName + '\'' +
                ", friendUserName='" + friendUserName + '\'' +
                ", timestamp=" + timestamp +
                ", status='" + status + '\'' +
                '}';
    }
}
