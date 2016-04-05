/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ec.com.codesoft.web.admin.dock;

import java.io.Serializable;
import javax.annotation.PostConstruct;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;

/**
 *
 * @author Suco
 */
@ManagedBean
@ViewScoped
public class dockMB implements Serializable{
    
 private Boolean mostrarNote;
 
 @PostConstruct
 public void inicializar(){
     System.out.println("Ini");
     mostrarNote=false;
 }
 
 public void mostrarNotas(){
     mostrarNote=true;
     System.out.println("Mostrar");
 }

    public Boolean getMostrarNote() {
        return mostrarNote;
    }

    public void setMostrarNote(Boolean mostrarNote) {
        this.mostrarNote = mostrarNote;
    }

    

    
 
 
    
}
