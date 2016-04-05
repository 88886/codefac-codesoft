/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ec.com.codesoft.web.widget;

import java.util.List;
import java.util.concurrent.CopyOnWriteArrayList;
import org.primefaces.context.RequestContext;
import org.primefaces.event.CloseEvent;
import org.primefaces.event.MoveEvent;

/**
 *
 * @author carlo
 */
public abstract class CommonWidGet 
{    
     /**
     * posicio del eje x
     */
    private  int x;
    /**
     * posicion del eje y
     */
    private int y;
    /**
     * Variable para controlar si esta visible el dialogo
     */
    private boolean visible;
    
    /**
     * Nombre var del dialogo
     */
    private String nameVar;
    
     
    /////////////////////METODOS IMPLEMENTADOS///////////////////
    
    /**
     * Verifica si el dialogo esta en el estado de abierto o cerrado
     */
    public void initDialogo()
    {
        System.out.println("comprobando abrir "+nameVar);
        if(this.visible)
        {
            System.out.println("cargando "+nameVar+"... -> x="+x+"y="+y);
            abrirDialogo();
        }
    }
    
    public void abrirDialogo()
    {
        System.out.println("abriendo calculadora ...");
        this.visible=true;
        RequestContext.getCurrentInstance().execute("PF('"+nameVar+"').show()");
      
    }
    /**
     * Evento cuando se cierra el dialogo
     * @param event 
     */
    public void handleClose(CloseEvent event)
    {
        System.out.println("cerrando calculadora");
        this.visible=false;
       
        
    }
    
    /**
     * Evento para mover el dialogo
     * @param event 
     */
    public void handleMove(MoveEvent event)
    {  
        
        x=event.getLeft();
        y=event.getTop();
        System.out.println("moviendo x="+x+",y="+y);
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

    public boolean isVisible() {
        return visible;
    }

    public void setVisible(boolean visible) {
        this.visible = visible;
    }

    public String getNameVar() {
        return nameVar;
    }

    public void setNameVar(String nameVar) {
        this.nameVar = nameVar;
    }
}
