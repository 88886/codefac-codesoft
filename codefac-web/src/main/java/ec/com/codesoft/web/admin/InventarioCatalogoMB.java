/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ec.com.codesoft.web.admin;

import ec.com.codesoft.model.CatalagoProducto;
import ec.com.codesoft.model.ProductoGeneralVenta;
import ec.com.codesoft.modelo.servicios.CatalogoServicio;
import java.io.Serializable;
import java.util.List;
import javax.annotation.PostConstruct;
import javax.ejb.EJB;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;
import org.primefaces.context.RequestContext;
import org.primefaces.event.SelectEvent;

/**
 *
 * @author carlo
 */
@ViewScoped
@ManagedBean
public class InventarioCatalogoMB implements Serializable
{
    private List<CatalagoProducto> listaCatalogo;
    private List<CatalagoProducto> listaCatalogoFiltrado;
    private CatalagoProducto catalogoProductoSeleccionado;
    private Integer cantidad;
    
    @EJB
    private CatalogoServicio catalogoServicio;
    
    
    @PostConstruct
    public void postConstruct()
    {
        listaCatalogo=catalogoServicio.obtenerTodos();
        cantidad=0;
        
    }

    public InventarioCatalogoMB() {
    }
    
    
    
    public void onRowSelect(SelectEvent event) 
    {
        System.out.println("Seleccionando fila");
        RequestContext.getCurrentInstance().execute("PF('widInventario').show()");
        System.out.println(catalogoProductoSeleccionado);
        
    }
    
    public void agregarCantidadProducto()
    {
       catalogoServicio.agregarCantidadProductoGeneral(catalogoProductoSeleccionado.getProductoGeneralVenta(),cantidad);
       cantidad=0;
       RequestContext.getCurrentInstance().execute("PF('widInventario').hide()");
       System.out.println("valor editado ...");
    }

    public List<CatalagoProducto> getListaCatalogo() {
        return listaCatalogo;
    }

    public void setListaCatalogo(List<CatalagoProducto> listaCatalogo) {
        this.listaCatalogo = listaCatalogo;
    }

    public CatalagoProducto getCatalogoProductoSeleccionado() {
        return catalogoProductoSeleccionado;
    }

    public void setCatalogoProductoSeleccionado(CatalagoProducto catalogoProductoSeleccionado) {
        this.catalogoProductoSeleccionado = catalogoProductoSeleccionado;
    }

    public List<CatalagoProducto> getListaCatalogoFiltrado() {
        return listaCatalogoFiltrado;
    }

    public void setListaCatalogoFiltrado(List<CatalagoProducto> listaCatalogoFiltrado) {
        this.listaCatalogoFiltrado = listaCatalogoFiltrado;
    }

    public InventarioCatalogoMB(Integer cantidad) {
        this.cantidad = cantidad;
    }

    public Integer getCantidad() {
        return cantidad;
    }

    public void setCantidad(Integer cantidad) {
        this.cantidad = cantidad;
    }
    
    
    
}
