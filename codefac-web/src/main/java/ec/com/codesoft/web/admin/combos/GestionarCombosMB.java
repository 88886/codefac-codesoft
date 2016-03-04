/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ec.com.codesoft.web.admin.combos;

import ec.com.codesoft.model.CatalagoProducto;
import ec.com.codesoft.model.ComboProducto;
import ec.com.codesoft.model.DetalleComboProducto;
import ec.com.codesoft.modelo.servicios.CatalogoServicio;
import ec.com.codesoft.modelo.servicios.ComboProductoServicio;
import java.io.Serializable;
import java.math.BigDecimal;
import java.math.BigInteger;
import java.util.ArrayList;
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
@ManagedBean
@ViewScoped
public class GestionarCombosMB implements Serializable {

    /**
     * Lista de los combos cargados
     */
    private List<ComboProducto> listaCombos;

    /**
     * Detalle de los combos para grabar
     */
    private List<DetalleComboProducto> detalleCombos;

    private List<CatalagoProducto> listaCatalogos;

    private CatalagoProducto catalogoProductoSeleccionado;
    private DetalleComboProducto detalleSeleccionado;
    
    private ComboProducto comboProductoNuevo;
    
    private BigDecimal total;
    

    /**
     * Codigo del detalle a insertar
     */
    private String codigoDetalle;
    private Integer cantidad;

    @EJB
    private ComboProductoServicio comboServicio;

    @EJB
    private CatalogoServicio catalogoServicio;

    @PostConstruct
    public void postConstruct() {
        System.out.println("Iniciando la pagina ...");
        listaCombos = comboServicio.obtenerTodos();
        System.out.println(listaCombos.size());

        detalleCombos = new ArrayList<DetalleComboProducto>();
        listaCatalogos = catalogoServicio.obtenerTodos();
        comboProductoNuevo=new ComboProducto();
        total=new BigDecimal(0);
    }
    
    /**
     * Agregar el detalle segun el codigo seleccionado
     */
    public void agregarDetalle()
    {
        System.out.println("Agregando detalle ...");
        CatalagoProducto catalogo=catalogoServicio.buscarCatalogo(codigoDetalle);
        DetalleComboProducto detalle=new DetalleComboProducto();
        detalle.setCantidad(cantidad);
        detalle.setIdDetalleCombo(1);
        detalle.setCodigoProducto(catalogo);
        
        detalleCombos.add(detalle);
        System.out.println(detalleCombos.size());
        
        BigDecimal subtotal=new BigDecimal(detalle.getSubtotal());
        
        //System.out.println(subtotal+"<--");
       
        total=total.add(subtotal);
        
        //System.out.println(total+"<--");
        
        
        comboProductoNuevo.setCosto(total);
        
        
    }
    
    /**
     * grabar un combo nuevo
     */
    public void grabarCombo()
    {
        
        comboProductoNuevo.setDetalleComboProductoCollection(detalleCombos);        
        comboServicio.grabar(comboProductoNuevo);
        
        RequestContext.getCurrentInstance().execute("PF('dialogNuevo').show()");
        //dialogNuevo
        //scomboServicio.grabar();
        
    }

    public void onRowSelect(SelectEvent event) {

        codigoDetalle = ((CatalagoProducto) (event.getObject())).getCodigoProducto();
        RequestContext.getCurrentInstance().execute("PF('widgetVarCatalogo').hide()");
        //       System.out.println(codigoDetalle);
        //verificarProducto();
    }

    ///Getters and Setters
    public List<ComboProducto> getListaCombos() {
        return listaCombos;
    }

    public void setListaCombos(List<ComboProducto> listaCombos) {
        this.listaCombos = listaCombos;
    }

    public List<DetalleComboProducto> getDetalleCombos() {
        return detalleCombos;
    }

    public void setDetalleCombos(List<DetalleComboProducto> detalleCombos) {
        this.detalleCombos = detalleCombos;
    }

    public List<CatalagoProducto> getListaCatalogos() {
        return listaCatalogos;
    }

    public void setListaCatalogos(List<CatalagoProducto> listaCatalogos) {
        this.listaCatalogos = listaCatalogos;
    }

    public CatalagoProducto getCatalogoProductoSeleccionado() {
        return catalogoProductoSeleccionado;
    }

    public void setCatalogoProductoSeleccionado(CatalagoProducto catalogoProductoSeleccionado) {
        this.catalogoProductoSeleccionado = catalogoProductoSeleccionado;
    }

    public String getCodigoDetalle() {
        return codigoDetalle;
    }

    public void setCodigoDetalle(String codigoDetalle) {
        this.codigoDetalle = codigoDetalle;
    }

    public Integer getCantidad() {
        return cantidad;
    }

    public void setCantidad(Integer cantidad) {
        this.cantidad = cantidad;
    }

    public DetalleComboProducto getDetalleSeleccionado() {
        return detalleSeleccionado;
    }

    public void setDetalleSeleccionado(DetalleComboProducto detalleSeleccionado) {
        this.detalleSeleccionado = detalleSeleccionado;
    }

    public ComboProducto getComboProductoNuevo() {
        return comboProductoNuevo;
    }

    public void setComboProductoNuevo(ComboProducto comboProductoNuevo) {
        this.comboProductoNuevo = comboProductoNuevo;
    }

    
    
}
