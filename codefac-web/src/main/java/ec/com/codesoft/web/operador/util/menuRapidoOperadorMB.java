/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ec.com.codesoft.web.operador.util;

import ec.com.codesoft.web.admin.util.*;
import java.io.Serializable;
import javax.annotation.PostConstruct;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;
import javax.faces.bean.ViewScoped;
import org.primefaces.context.RequestContext;

/**
 *
 * @author carlo
 */
@ManagedBean
@SessionScoped
public class menuRapidoOperadorMB  implements Serializable{
    
    /**
     * Variable para saber si el usuario tiene la calculadora abierta
     */
    private boolean calculadoraAbierta;
    
    
    @PostConstruct
    public void postConstruct()
    {
        
    }
    
    public void abrirCalculadora()
    {
        System.out.println("abriendo calculadora ...");
        calculadoraAbierta=true;
        RequestContext.getCurrentInstance().execute("PF('dlgCalculadora').show()");
    }
    
    public void abrirNota()
    {
        System.out.println("abriendo Notas...");
        RequestContext.getCurrentInstance().execute("PF('dlgNotas').show()");
    }
    
    public void abrirVentas()
    {
        System.out.println("abriendo Ventas...");
        RequestContext.getCurrentInstance().execute("PF('dlgDetalles').show()");
    }
    
    public void verificarDialogosUtilidades()
    {
        System.out.println("verificando utilidades abiertas;");
//        if(calculadoraAbierta)
//        {
//            abrirCalculadora();
//        }
    }
}