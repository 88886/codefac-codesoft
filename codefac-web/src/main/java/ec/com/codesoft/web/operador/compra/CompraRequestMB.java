/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ec.com.codesoft.web.operador.compra;

import ec.com.codesoft.model.Compra;
import java.io.Serializable;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.RequestScoped;
import javax.faces.bean.SessionScoped;

/**
 *
 * @author carlo
 */
@ManagedBean
@RequestScoped
public class CompraRequestMB implements Serializable
{
    public Compra compraEditar;

    public String abrirEditar(Compra compra)
    {
        System.out.println("abriendo la ventana editar...");
        compraEditar=compra;
        return "compraEditar";
    }
    
    
    public Compra getCompraEditar() {
        return compraEditar;
    }

    public void setCompraEditar(Compra compraEditar) 
    {
        this.compraEditar = compraEditar;
        
    }
    
    
    
}
