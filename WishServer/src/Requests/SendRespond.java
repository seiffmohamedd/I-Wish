/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Requests;

/**
 *
 * @author Windo
 */
public class SendRespond {
    public int getSignUpResponse(int requestResult){
        if (requestResult > 0) {
            return 1;
        } else {
            return 0;
        }
    }
}
