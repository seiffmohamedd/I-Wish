/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Controllers;

import javafx.scene.control.Alert;

/**
 *
 * @author Windo
 */
public class Dialog {
    
    public void  showDialog(String title,String message, String type){
        Alert aboutDialog;
        if("CONFIRMATION".equals(type)){
            aboutDialog = new Alert(Alert.AlertType.CONFIRMATION);}
        else if("INFORMATION".equals(type)){
            aboutDialog = new Alert(Alert.AlertType.INFORMATION);}
        else if("ERROR".equals(type)){
            aboutDialog = new Alert(Alert.AlertType.ERROR);}
        else{
            aboutDialog = new Alert(Alert.AlertType.CONFIRMATION);}
    
        aboutDialog.setTitle(title);
        aboutDialog.setContentText(message);
        aboutDialog.showAndWait();
        return;
    }
    
}
