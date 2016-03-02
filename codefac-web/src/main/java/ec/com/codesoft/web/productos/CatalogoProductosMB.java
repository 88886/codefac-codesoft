/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ec.com.codesoft.web.productos;

import ec.com.codesoft.model.CatalagoProducto;
import ec.com.codesoft.modelo.servicios.CatalogoServicio;
import java.io.Serializable;
import java.util.List;
import javax.annotation.PostConstruct;
import javax.ejb.EJB;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;
import org.primefaces.context.RequestContext;

/**
 *Clase que me permite controlar la vista para mostrar los productos
 * @author carlo
 */
@ManagedBean
@ViewScoped
public class CatalogoProductosMB implements Serializable
{
    @EJB
    private CatalogoServicio catalogoServicio;
    private List<CatalagoProducto> listaProductos;
    private List<CatalagoProducto> listaProductoFiltro;
    private CatalagoProducto catalogoSeleccionado;
    
    private String nombre;
    
    
    @PostConstruct
    public void postConstruct()
    {
        listaProductos=catalogoServicio.obtenerTodos();
        catalogoSeleccionado=listaProductos.get(0);
        catalogoServicio=new CatalogoServicio();
        this.nombre="codigo";
        
    }
    
    public void selecionarCatalogo(CatalagoProducto catalogo)
    {
        System.out.println("seleccionando catalogo  ...");
        this.catalogoSeleccionado=catalogo;
        RequestContext.getCurrentInstance().execute("PF('dlg3').show()");
        nombre=catalogoSeleccionado.getNombre();
        System.out.println(this.catalogoSeleccionado);
    }
    
    ///Metodos get and set

    public List<CatalagoProducto> getListaProductos() {
        return listaProductos;
    }

    public void setListaProductos(List<CatalagoProducto> listaProductos) {
        this.listaProductos = listaProductos;
    }

    public CatalagoProducto getCatalogoSeleccionado() {
        return catalogoSeleccionado;
    }

    public void setCatalogoSeleccionado(CatalagoProducto catalogoSeleccionado) {
        this.catalogoSeleccionado = catalogoSeleccionado;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public List<CatalagoProducto> getListaProductoFiltro() {
        return listaProductoFiltro;
    }

    public void setListaProductoFiltro(List<CatalagoProducto> listaProductoFiltro) {
        this.listaProductoFiltro = listaProductoFiltro;
    }
    
    
    
    
    
    
}
