/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package ec.com.codesoft.web.operador;

import java.io.Serializable;
import javax.annotation.PostConstruct;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;
import org.primefaces.context.RequestContext;

/**
 *
 * @author Suco
 */
@ManagedBean
@ViewScoped
public class Ejemplo implements Serializable{
    
    private String focus;
    
    @PostConstruct
    private void init()
    {
        focus="formulario:campo1";
        RequestContext.getCurrentInstance().execute("PF('"+focus+"').show()");
    }
    
    public void imprimir(){
        System.out.println("JJJJJJJJJ");
    }
    
    public void cambiarfocus(String id)
    {
        System.out.println("cambiando focus");
        focus=id;
        RequestContext.getCurrentInstance().execute("focusField('123');");
       
    }

    public String getFocus() {
        return focus;
    }

    public void setFocus(String focus) {
        this.focus = focus;
    }
    
    
    
}
