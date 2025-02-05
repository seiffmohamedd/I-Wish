/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package DAL;
import DBO.User;
import Requests.SendRespond;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
/**
 *
 * @author Windo
 */
public class SignUp {
    private final User user;
    private final Connection DBCon;
    private final String query = "insert into Person(userName, firstName, lastName,gender, password, birthDate,phone) values(?,?,?,?,?,?,?)";
    private final String ifUserExistsQuery = "SELECT COUNT(*) as isexist FROM PERSON WHERE USERNAME = ? ";
    private int executeResult;
    public SignUp(User user) throws SQLException, ParseException{
        this.user = user;
        DBCon = establishConnection();
//        System.out.println("db connection established");
        if(isExists()){
            executeResult = -5;
            System.out.println("user exists and exe result is " +executeResult );
            DBCon.close();
        }else{
            signUser();
            DBCon.close();
        }
//        System.out.println("db connection closed");
    }
    
    private Connection establishConnection() throws SQLException{
        return new DBConnection().getConection();
    }

    public int getExecuteResult() {
        return executeResult;
    }
    
    
    private boolean isExists() throws SQLException{
        PreparedStatement statement = DBCon.prepareStatement(ifUserExistsQuery);
        statement.setString(1, user.getUserName());
        ResultSet rs = statement.executeQuery();
        rs.next();
        if (rs.getInt(1) > 0 )
                return true;
        else
            return false;
            
    }
    private void signUser() throws SQLException, ParseException{
        PreparedStatement statement = DBCon.prepareStatement(query);
        statement.setString(1, user.getUserName());
        statement.setString(2, user.getFirstName());
        statement.setString(3, user.getLastName());
        statement.setString(4, user.getGender());
        statement.setString(5, user.getPassword());
        String birthDateStr = user.getBirthDate();
        // casting sting to sql date
        // must be handeled in the user enter a string not date
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd"); 
        java.util.Date parsedDate = sdf.parse(birthDateStr);
        java.sql.Date sqlDate = new java.sql.Date(parsedDate.getTime());
        statement.setDate(6, sqlDate);
        statement.setString(7, user.getPhone());
        int result = statement.executeUpdate();
        executeResult = getSignUpResponse(result);;
//        System.out.println("the execution result is : "  + result);
//        System.out.println("user signed up and the execution result is : "  + executeResult);
        
    }
    
    private int getSignUpResponse(int requestResult){
        if (requestResult > 0) {
            return 1;
        } else {
            return 0;
        }
    }
    
    
}
