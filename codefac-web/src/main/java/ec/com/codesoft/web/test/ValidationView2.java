/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ec.com.codesoft.web.test;

import java.util.Date;
import javax.faces.bean.ManagedBean;
 
@ManagedBean
public class ValidationView2 {
     
    private String text;
    private String description;
    private Integer integer;
    private Double doubleNumber;
    private Double money;
    private String regexText;
    private Date date;
 
    
    public void imprimir()
    {
        System.out.println("entro en el metodo ...");
    }
    public String getText() {
        return text;
    }
    public void setText(String text) {
        this.text = text;
    }
 
    public String getDescription() {
        return description;
    }
    public void setDescription(String description) {
        this.description = description;
    }
 
    public Integer getInteger() {
        return integer;
    }
    public void setInteger(Integer integer) {
        this.integer = integer;
    }
 
    public Double getDoubleNumber() {
        return doubleNumber;
    }
    public void setDoubleNumber(Double doubleNumber) {
        this.doubleNumber = doubleNumber;
    }
 
    public Double getMoney() {
        return money;
    }
    public void setMoney(Double money) {
        this.money = money;
    }
 
    public String getRegexText() {
        return regexText;
    }
    public void setRegexText(String regexText) {
        this.regexText = regexText;
    }
 
    public Date getDate() {
        return date;
    }
    public void setDate(Date date) {
        this.date = date;
    } 
}