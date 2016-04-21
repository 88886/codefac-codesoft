/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ec.com.codesoft.web.util;

import javax.mail.Authenticator;
import javax.mail.PasswordAuthentication;

/**
 *
 * @author carlo
 */
public class SmtpAuthenticator extends Authenticator {

    private String Username;
    private String PassWord;
    
//    public SmtpAuthenticator() {
//
//        super();
//        Username = "carlosmast2302@gmail.com";
//        PassWord = "angelicaespe";
//    }

    public SmtpAuthenticator(String Username, String PassWord) {
        super();
        this.Username = Username;
        this.PassWord = PassWord;
    }
    
    

    @Override
    public PasswordAuthentication getPasswordAuthentication() {

        if ((Username != null) && (Username.length() > 0) && (PassWord != null)
                && (PassWord.length() > 0)) {

            return new PasswordAuthentication(Username, PassWord);
        }

        return null;
    }
}
