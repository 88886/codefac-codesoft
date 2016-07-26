/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ec.com.codesoft.web.admin;

import ec.com.codesoft.model.CatalagoProducto;
import ec.com.codesoft.model.Cliente;
import ec.com.codesoft.model.Distribuidor;
import ec.com.codesoft.model.ProductoGeneralVenta;
import ec.com.codesoft.modelo.servicios.CatalogoServicio;
import ec.com.codesoft.modelo.servicios.ClienteServicio;
import ec.com.codesoft.modelo.servicios.DistribuidorServicio;
import ec.com.codesoft.modelo.servicios.FacturaServicio;
import java.io.Serializable;
import java.util.List;
import javax.annotation.PostConstruct;
import javax.ejb.EJB;
import javax.faces.bean.ApplicationScoped;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;

/**
 *
 * @author carlo
 */
@ManagedBean
@ApplicationScoped
public class AdminMenuMB implements Serializable {

    private List<Cliente> clienteList;
    private List<CatalagoProducto> catalagoProductoList;
    private List<Distribuidor> distribuidorList;

    /**
     * Guarda la opcion seleccionada para buscar
     */
    private String opcionSeleccionado;
    private Integer stock;

    @EJB
    private CatalogoServicio catalogoServicio;

    @EJB
    private ClienteServicio clienteServicio;

    @EJB
    private DistribuidorServicio distribuidorServicio;

    @EJB
    FacturaServicio facturaServicio;

    @PostConstruct
    public void init() {
        opcionSeleccionado = "producto";
        System.out.println("iniciando busqueda de las listas");
        actualizarDatos();
    }

    private void actualizarDatos() {
        catalagoProductoList = catalogoServicio.obtenerTodos();
        clienteList = clienteServicio.obtenerTodos();
        distribuidorList = distribuidorServicio.obtenerTodos();
    }

    public void actualizarValores() {
        catalagoProductoList = catalogoServicio.obtenerTodos();
        clienteList = clienteServicio.obtenerTodos();
        distribuidorList = distribuidorServicio.obtenerTodos();
    }

    public Integer devolverStock(CatalagoProducto catalogoEnviado) {

        if ((catalogoEnviado.getTipoProducto()) == 'G' || (catalogoEnviado.getTipoProducto()) == 'g') {
            ProductoGeneralVenta productoGeneral = new ProductoGeneralVenta();
            productoGeneral = facturaServicio.devolverStockGeneral(catalogoEnviado.getCodigoProducto());
            if (productoGeneral == null) {
                stock = 0;
            } else {
                stock = productoGeneral.getCantidadDisponible();
            }
            return stock;
        } else {
            stock = facturaServicio.devolverStockIndividual(catalogoEnviado.getCodigoProducto());
            return stock;
        }

    }

    public void cambiarOpciones() {
        actualizarDatos();
        System.out.println("cambiando opciones ...");
        catalagoProductoList = catalogoServicio.obtenerTodos();
        clienteList = clienteServicio.obtenerTodos();
        distribuidorList = distribuidorServicio.obtenerTodos();
    }

    public void cargarDatosBusqueda() {
        actualizarDatos();
    }

    /////////////////////METODOS GET AND SET///////////////////
    public List<CatalagoProducto> getCatalagoProductoList() {
        return catalagoProductoList;
    }

    public void setCatalagoProductoList(List<CatalagoProducto> catalagoProductoList) {
        this.catalagoProductoList = catalagoProductoList;
    }

    public List<Cliente> getClienteList() {
        return clienteList;
    }

    public void setClienteList(List<Cliente> clienteList) {
        this.clienteList = clienteList;
    }

    public List<Distribuidor> getDistribuidorList() {
        return distribuidorList;
    }

    public void setDistribuidorList(List<Distribuidor> distribuidorList) {
        this.distribuidorList = distribuidorList;
    }

    public String getOpcionSeleccionado() {
        return opcionSeleccionado;
    }

    public void setOpcionSeleccionado(String opcionSeleccionado) {
        this.opcionSeleccionado = opcionSeleccionado;
    }

    public Integer getStock() {
        return stock;
    }

    public void setStock(Integer stock) {
        this.stock = stock;
    }

}
