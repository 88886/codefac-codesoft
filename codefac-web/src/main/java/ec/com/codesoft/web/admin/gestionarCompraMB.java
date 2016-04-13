/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ec.com.codesoft.web.admin;

import ec.com.codesoft.model.Compra;
import ec.com.codesoft.modelo.servicios.CompraServicio;
import java.io.Serializable;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import javax.annotation.PostConstruct;
import javax.ejb.EJB;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;
import org.primefaces.context.RequestContext;

/**
 *
 * @author carlo
 */
@ManagedBean
@ViewScoped
public class gestionarCompraMB implements Serializable
{
    /**
     * Servicio de las compras
     */
    @EJB
    private CompraServicio compraServicio;
    
    /**
     * Compra seleccionada de la tabla
     */
    private Compra compraSeleccionado;
    
    /**
     * Lista de las compras para mostrar en la tabla de la vista
     */
    private List<Compra> listaCompras;
    
    /**
     * Lista de las de las compras para guardar los filtros
     */
    private List<Compra> listaComprasFilter;
    
    
    @PostConstruct
    public void postConstruct()
    {
        listaCompras=compraServicio.obtenerTodos();
        
    }
    
    /**
     * Elimina la compra
     */
    public void eliminar(Compra compra)
    {
        compraServicio.eliminar(compra);
    }
    
    
    public String abrirDialogEditar(Compra compra)
    {
 
        this.compraSeleccionado=compra;
        System.out.println("abriendo pantalla del panel para");
        return "verCompra";
    }
    
    /**
     * 
     * @return 
     */
       
    ///Metodos Get y Set

    public CompraServicio getCompraServicio() {
        return compraServicio;
    }

    public void setCompraServicio(CompraServicio compraServicio) {
        this.compraServicio = compraServicio;
    }

    public List<Compra> getListaCompras() {
        return listaCompras;
    }

    public void setListaCompras(List<Compra> listaCompras) {
        this.listaCompras = listaCompras;
    }

    public Compra getCompraSeleccionado() {
        return compraSeleccionado;
    }

    public void setCompraSeleccionado(Compra compraSeleccionado) {
        this.compraSeleccionado = compraSeleccionado;
    }

    public List<Compra> getListaComprasFilter() {
        return listaComprasFilter;
    }

    public void setListaComprasFilter(List<Compra> listaComprasFilter) {
        this.listaComprasFilter = listaComprasFilter;
    }
    
    
    
    
}
