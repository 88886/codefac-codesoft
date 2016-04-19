/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ec.com.codesoft.web.test;

import java.io.Serializable;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;

/**
 *
 * @author carlo
 */
@ManagedBean
@ViewScoped
public class StatusBean implements Serializable
{
    private String name;
    private String otroAtributo;
    
    public void test()
    {
        System.out.println("metodo enter presionado... "+name);
        System.out.println("otro atributo... "+otroAtributo);
    }
    
    

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getOtroAtributo() {
        return otroAtributo;
    }

    public void setOtroAtributo(String otroAtributo) {
        this.otroAtributo = otroAtributo;
    }
    
    
    
}
