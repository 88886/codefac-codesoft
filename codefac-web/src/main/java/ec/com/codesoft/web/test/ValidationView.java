/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ec.com.codesoft.web.test;

import java.io.Serializable;
import java.util.logging.Level;
import java.util.logging.Logger;

import javax.faces.bean.ViewScoped;
import javax.validation.constraints.Max;
import javax.validation.constraints.Min;

import javax.faces.bean.ManagedBean;
 
@ManagedBean
public class ValidationView {
     
    private String text;
    private Integer integer;
 
    public void time()
    {        
        System.out.println("iniciando tiempo espera ...");
        try {
            Thread.sleep(2000);
        } catch (InterruptedException ex) {
            Logger.getLogger(ValidationView.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
    public String getText() {
        return text;
    }
    public void setText(String text) {
        this.text = text;
    }
 
    public Integer getInteger() {
        return integer;
    }
    public void setInteger(Integer integer) {
        this.integer = integer;
    }
}