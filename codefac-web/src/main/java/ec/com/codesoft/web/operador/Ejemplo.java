/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package ec.com.codesoft.web.operador;

import java.io.Serializable;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;

/**
 *
 * @author Suco
 */
@ManagedBean
@ViewScoped
public class Ejemplo implements Serializable{
    
    public void imprimir(){
        System.out.println("JJJJJJJJJ");
    }
    
}
