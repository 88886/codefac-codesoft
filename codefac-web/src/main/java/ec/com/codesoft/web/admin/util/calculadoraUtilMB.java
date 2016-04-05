/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ec.com.codesoft.web.admin.util;

import java.io.Serializable;
import javax.annotation.PostConstruct;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;
import org.primefaces.event.CloseEvent;
import org.primefaces.event.MoveEvent;

/**
 *
 * @author carlo
 */
@ManagedBean
@SessionScoped
public class calculadoraUtilMB implements Serializable{
    
    private int x;
    private int y;
    private int visible;
    
    @PostConstruct
    public void postConstuct()
    {
        x=100;
        y=100;
    }
    
    /////////////////////METODOS IMPLEMENTADOS///////////////////
    public void handleClose(CloseEvent event)
    {
        
    }
    
    public void handleMove(MoveEvent event)
    {
        //event.get
    }
    
    ////////////////////////METODOS GET AND SET////////////////

    public int getX() {
        return x;
    }

    public void setX(int x) {
        this.x = x;
    }

    public int getY() {
        return y;
    }

    public void setY(int y) {
        this.y = y;
    }
    
    
    
}
