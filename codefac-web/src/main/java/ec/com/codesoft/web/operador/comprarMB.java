/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ec.com.codesoft.web.operador;

import ec.com.codesoft.model.CatalagoProducto;
import ec.com.codesoft.model.Compra;
import ec.com.codesoft.model.Distribuidor;
import ec.com.codesoft.model.ProductoGeneralCompra;
import ec.com.codesoft.model.ProductoIndividualCompra;
import ec.com.codesoft.modelo.servicios.CatalogoServicio;
import ec.com.codesoft.modelo.servicios.CompraServicio;
import ec.com.codesoft.modelo.servicios.DistribuidorServicio;
import ec.com.codesoft.modelo.servicios.ProductoGeneralCompraServicio;
import java.io.Serializable;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
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
public class comprarMB implements Serializable {

    /**
     * Variable que contiene informacion de la compra
     */
    private Compra compra;
    private List<DetalleCompraModelo> detalleCompra;

    /**
     * Variables para controlar el ingreso de productos
     */
    private String codigoDetalle;
    private Integer cantidadDetalle;
    private BigDecimal costoDetalle;

    /**
     * variable para almacenar el codigo especifico a almacenar
     */
    private String codigoEspecificoDetalle;

    /**
     * contador para saber cuantos productos especificos va a ingresar
     */
    private Integer contadorIngresoDetalleEspecifico;

    /**
     * Referencia para guardar el catalogo seleccionando para agregar al detalle
     */
    private CatalagoProducto catalogo;

    /*
     Servicios para consultar del distribuidor
     */
    @EJB
    private DistribuidorServicio distribServicio;

    @EJB
    private CatalogoServicio catalogoServicio;

    @EJB
    private ProductoGeneralCompraServicio productoGeneralServicio;

    @EJB
    private CompraServicio compraServicio;

    @PostConstruct
    public void postConstruct() {
        System.out.println("reiniciando ...");
        this.compra = new Compra();
        this.detalleCompra = new ArrayList<DetalleCompraModelo>();
        this.compra.setRuc(new Distribuidor());

    }

    ///////////////////////////METODOS PARA LA VISTA ///////////////////////////
    /**
     * Consulta un distribuidor de la base
     */
    public void consultarDistribuidor() {
        System.out.println("buscar distribuidor ...");
        Distribuidor distribuidoAux = this.distribServicio.buscarDistribuidor(compra.getRuc().getRuc());
        if (distribuidoAux != null) {
            this.compra.setRuc(distribuidoAux);
            System.out.println(distribuidoAux);
        } else {
            //cuando el distribuidor no existe mostrar un dialogo para crear un nuevo Ditribuidor
            RequestContext.getCurrentInstance().execute("PF('confirmarDistribuidor').show()");
            //confirmarDistribuidor
        }
    }

    /**
     * Agrega un producto a la tabla
     */
    public void agregarProducto() {
        System.out.println("agregando detalle ...");
        DetalleCompraModelo detalle = new DetalleCompraModelo();
        catalogo = catalogoServicio.buscarCatalogo(codigoDetalle);
        //verifica si el producto existe
        if (catalogo != null) {
            System.out.println(catalogo.getTipoProducto());
            if (catalogo.getTipoProducto().equals('G')) {
                ProductoGeneralCompra detalleGeneral = new ProductoGeneralCompra();
                detalleGeneral.setCantidad(cantidadDetalle);
                detalleGeneral.setCostoIndividual(costoDetalle);
                detalleGeneral.setCodigoProducto(catalogo);

                detalle = new DetalleCompraModelo(detalleGeneral);
                detalleCompra.add(detalle);

            } else {
                contadorIngresoDetalleEspecifico = cantidadDetalle;
                RequestContext.getCurrentInstance().execute("PF('dlgProductoGeneral').show()");
            }
        }
    }

    /**
     * Metodo que me permite agregar un producto especifico
     */
    public void agregarProductoEspecifico() {

        System.out.println(catalogo);

        ProductoIndividualCompra detalleIndividual = new ProductoIndividualCompra();
        //detalleIndividual.setCantidad(cantidadDetalle);
        detalleIndividual.setCosto(costoDetalle);
        detalleIndividual.setCodigoProducto(catalogo);
        detalleIndividual.setCodigoUnico(codigoEspecificoDetalle);

        DetalleCompraModelo detalle = new DetalleCompraModelo(detalleIndividual);
        detalleCompra.add(detalle);

        //limpiar ventana para agregar mas detalles
        contadorIngresoDetalleEspecifico--;
        //RequestContext.getCurrentInstance().execute("PF('dlgProductoGeneral').hide()");
        codigoEspecificoDetalle = "";
        if (contadorIngresoDetalleEspecifico == 0) {
            RequestContext.getCurrentInstance().execute("PF('dlgProductoGeneral').hide()");

        }

    }

    public void ejecutarCompra() {

        List<ProductoIndividualCompra> detalleIndividual = new ArrayList<ProductoIndividualCompra>();
        List<ProductoGeneralCompra> detalleGeneral = new ArrayList<ProductoGeneralCompra>();

        for (DetalleCompraModelo detalle : detalleCompra) {
            if (detalle.getCodigoGeneral()) {
                detalleGeneral.add(detalle.getProductoGeneral());
            } else {
                detalleIndividual.add(detalle.getProductoIndividual());
            }
        }
        System.out.println(compra);
        compra.setProductoGeneralCompraList(detalleGeneral);
        compra.setProductoIndividualCompraList(detalleIndividual);
        compraServicio.insertar(compra);
    }

    public void crearNuevoDistribuidor() {
        System.out.println("abriendo nuevo panel...");
        Map<String, Object> options = new HashMap<String, Object>();
        options.put("modal", true);

        Map<String, List<String>> params = new HashMap<String, List<String>>();
        List<String> values = new ArrayList<String>();
        
        values.add(compra.getRuc().getRuc());
        params.put("ruc", values);
        //options.put("width", 640);
        //options.put("height", 340);
        // options.put("contentWidth", "100%");
        // options.put("contentHeight", "100%");
        //options.put("headerElement", "customheader");
        RequestContext.getCurrentInstance().execute("PF('confirmarDistribuidor').hide()");
        RequestContext.getCurrentInstance().openDialog("crearDistribuidor", options, params);
        
        //RequestContext.getCurrentInstance().op

    }
    
    public void recibirDatos(SelectEvent event)
    {
        compra.setRuc((Distribuidor) event.getObject());   
        System.out.println("dato recibido");
        System.out.println(compra.getRuc());
    }

    /////////////////////////////METODOS GET Y SET//////////////////////////////
    public Compra getCompra() {
        return compra;
    }

    public void setCompra(Compra compra) {
        this.compra = compra;
    }

    public List<DetalleCompraModelo> getDetalleCompra() {
        return detalleCompra;
    }

    public void setDetalleCompra(List<DetalleCompraModelo> detalleCompra) {
        this.detalleCompra = detalleCompra;
    }

    public String getCodigoDetalle() {
        return codigoDetalle;
    }

    public void setCodigoDetalle(String codigoDetalle) {
        this.codigoDetalle = codigoDetalle;
    }

    public Integer getCantidadDetalle() {
        return cantidadDetalle;
    }

    public void setCantidadDetalle(Integer cantidadDetalle) {
        this.cantidadDetalle = cantidadDetalle;
    }

    public BigDecimal getCostoDetalle() {
        return costoDetalle;
    }

    public void setCostoDetalle(BigDecimal costoDetalle) {
        this.costoDetalle = costoDetalle;
    }

    public String getCodigoEspecificoDetalle() {
        return codigoEspecificoDetalle;
    }

    public void setCodigoEspecificoDetalle(String codigoEspecificoDetalle) {
        this.codigoEspecificoDetalle = codigoEspecificoDetalle;
    }

}
