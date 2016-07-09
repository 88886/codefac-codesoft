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
import ec.com.codesoft.modelo.servicios.FacturaServicio;
import ec.com.codesoft.modelo.servicios.ProductoGeneralCompraServicio;
import ec.com.codesoft.modelo.servicios.SistemaServicio;
import ec.com.codesoft.web.seguridad.SistemaMB;
import java.io.Serializable;
import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import javax.annotation.PostConstruct;
import javax.ejb.EJB;
import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ManagedProperty;
import javax.faces.bean.ViewScoped;
import javax.faces.context.FacesContext;
import javax.validation.constraints.Digits;
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
    private String focus;

    /**
     * Variable temporal para modificar el precio de venta final del producto
     * cargado
     */
    private BigDecimal cambiarCosto;

    @Digits(integer = 8, fraction = 3)
    private BigDecimal costoDetalle;

    private boolean visibleDetalleAgregar;

    /**
     * Variable para saber el valor estimado total de la compra
     */
    private BigDecimal sumaTotalCompra;

    private String total;
    private String nombre = "";

    /**
     * Variable para saber cual es la opcion que esocogio para ingresar el costo
     */
    private String ivaCosto;

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

    /**
     * Lista para cargar los distribuidores en una tabla
     */
    private List<Distribuidor> listaDistribuidores;
    private Distribuidor distribuidorSeleccionado;

    private List<CatalagoProducto> listaCatalogos;
    private CatalagoProducto catalogoProductoSeleccionado;
    
    /**
     * Variable para mostrar el Stock seleccionado del catalogo
     */
    private Integer stockProductoSeleccionado;
    
    /**
     * ivas
     */
    private BigDecimal ivaTotal; //iva traido de la configuracion
    private BigDecimal ivaSubTotal; //iva traido de la configuracion
    private BigDecimal ivaMostrar;
    private BigDecimal baseImponibleIva; //subtotal 2 descontal

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
    
    @EJB
    private FacturaServicio facturaServicio;
    
    @EJB
    private SistemaServicio sistemaServicio;
    
    
    @ManagedProperty(value = "#{sistemaMB}")
    /**
     * Variable para obtener las variables del sistema
     */
    private SistemaMB sistemaMB;
    
    private Integer iva;
    
    

    //ejb que me permiten cominucarme entre beans
    //@ManagedProperty("#{gestionarCompraMB}")
    //private gestionarCompraMB gestionarCompra;
    @PostConstruct
    public void postConstruct() {
        System.out.println("reiniciando ...");
        focus="formDetalleDistribuidor:txtRuc";
        iva=sistemaMB.getConfiguracion().getIva().intValueExact();

        // Map params = FacesContext.getCurrentInstance().getExternalContext().getRequestParameterMap();
        // String id = (String) params.get("id");
        // System.out.println("compra" + id);
        // compra = compraServicio.findCompra(Integer.parseInt(id));
        // this.catalogo=new CatalagoProducto();
        this.visibleDetalleAgregar = false;
        this.compra = new Compra();
        this.detalleCompra = new ArrayList<DetalleCompraModelo>();
        this.cantidadDetalle = 0;
        this.costoDetalle = new BigDecimal(0);

        Distribuidor distribuidorVacio = new Distribuidor();
        distribuidorVacio.setRuc("");
        this.compra.setRuc(distribuidorVacio);
        //this.compra.setFechaIngreso(new Date());
        this.compra.setTotal(new BigDecimal("0.0"));
        this.compra.setDescuento(new BigDecimal("0.0"));
        this.compra.setIva(new BigDecimal("0"));
        listaDistribuidores = distribServicio.obtenerTodos();
        sumaTotalCompra = new BigDecimal(0);
        baseImponibleIva=new BigDecimal(0);

        //iniciar la tabla del catalogo servicio
        this.listaCatalogos = catalogoServicio.obtenerTodos();
        
        //iva obtenido de la base de datos
        ivaMostrar = sistemaServicio.getConfiguracion().getIva().setScale(0);
        ivaSubTotal = (sistemaServicio.getConfiguracion().getIva()).divide(new BigDecimal("100"));
        ivaTotal = ivaSubTotal.add(new BigDecimal("1"));

    }

    public comprarMB() {

    }

    ///////////////////////////METODOS PARA LA VISTA ///////////////////////////
    /**
     * Consulta un distribuidor de la base
     */
    public void preRender() {
        System.out.println("Prerender ...");
        Map params = FacesContext.getCurrentInstance().getExternalContext().getRequestParameterMap();

        String id = (String) params.get("id");
        System.out.println("-->" + id + "<--");
        this.compra.setTotal(new BigDecimal("0.0"));
        this.compra.setDescuento(new BigDecimal("0.0"));
        this.compra.setIva(ivaMostrar);

        if (id != null) {
            compra = compraServicio.findCompra(Integer.parseInt(id));
            cargarDatosEditar();
        }

    }
    
    //public void cambiarIva()
    //{
        
    //}

    /**
     * Funcion que me permite editar el precio al momento de editar
     */
    public void cambiarPrecioEditar() 
    {
        if (ivaCosto.equals("+")) 
        {
            cambiarCosto=cambiarCosto.multiply(ivaTotal);
            cambiarCosto=cambiarCosto.setScale(3,RoundingMode.UP);
        }
        else
        {
            cambiarCosto=cambiarCosto.divide(ivaTotal,3,RoundingMode.UP);
        }
    }
        /**
         * Funcion que abre el dialogo para cambiar de precio
         */
    public void abrirDialogoCambiarPrecio() {
        System.out.println("abriendo dialogo editar ...");
        cambiarCosto = new BigDecimal(catalogo.getPrecio().toString());
        System.out.println("aumentado el iva al costo del producto");
        cambiarCosto = cambiarCosto.multiply(ivaTotal);
        cambiarCosto=cambiarCosto.setScale(3,RoundingMode.UP);

        RequestContext.getCurrentInstance().execute("PF('widCambiarPrecio').show()");
        //dialogEditPrecio
    }

    public void editarPrecio() 
    {
        System.out.println("editando el precio del producto ...");
        if (ivaCosto.equals("+")) 
        {
            catalogo.setPrecio(cambiarCosto.divide(ivaTotal,4,RoundingMode.DOWN));
            catalogoServicio.actualizar(catalogo);
        }
        else
        {
            catalogoServicio.actualizar(catalogo);
        }        
        
        System.out.println("-->"+catalogo.getPrecio());       
        
        RequestContext.getCurrentInstance().execute("PF('widCambiarPrecio').hide()");
    }

    public void consultarDistribuidor() {
        System.out.println("buscar distribuidor ...");
        Distribuidor distribuidoAux = this.distribServicio.buscarDistribuidor(compra.getRuc().getRuc());
        if (distribuidoAux != null) {
            this.compra.setRuc(distribuidoAux);
            System.out.println(distribuidoAux);

            focus="formBuscarProducto:txtCodigoDetalle";
        } else {
            //cuando el distribuidor no existe mostrar un dialogo para crear un nuevo Ditribuidor
            focus="formAgregarDistribuidor";
            RequestContext.getCurrentInstance().execute("PF('confirmarDistribuidor').show()");
            //confirmarDistribuidor
        }
    }

    /**
     * Agrega un producto a la tabla
     */
    public void agregarProducto() {
        System.out.println("agregando detalle ...");

        this.visibleDetalleAgregar = false;
        // this.cantidadDetalle=1;
        // this.costoDetalle=new BigDecimal(0);

        DetalleCompraModelo detalle = new DetalleCompraModelo();
        catalogo = catalogoServicio.buscarCatalogo(codigoDetalle);
        //verifica si el producto existe
        if (catalogo != null) {
            System.out.println(catalogo.getTipoProducto());
            if (catalogo.getTipoProducto().equals('G')) {
                ProductoGeneralCompra detalleGeneral = new ProductoGeneralCompra();

                detalleGeneral.setCantidad(cantidadDetalle);
                detalleGeneral.setCostoIndividual(costoDetalle);
                //ystem.out.println(cantidadDetalle+"-"+costoDetalle);
                //detalleGeneral.setCantidad(can);
                //detalleGeneral.setCostoIndividual(new BigDecimal(5));

                detalleGeneral.setCodigoProducto(catalogo);

                System.out.println(cantidadDetalle + "-" + costoDetalle);

                detalle = new DetalleCompraModelo(detalleGeneral);

                detalleCompra.add(detalle);
                sumaTotalCompra = sumaTotalCompra.add(detalle.getSubtotal());

                System.out.println("Suma:" + sumaTotalCompra);
                //compra.setTotal(sumaTotalCompra.multiply(compra.getIva().divide(new BigDecimal("100")).add(new BigDecimal("1"))));
                actualizarValoresTotales();

            } else {
                contadorIngresoDetalleEspecifico = cantidadDetalle;
                RequestContext.getCurrentInstance().execute("PF('dlgProductoGeneral').show()");
            }
        }
    }

    public void actualizarValoresTotales() {
        //BigDecimal calculo = sumaTotalCompra.divide(compra.getDescuento().divide(new BigDecimal(100)).add(new BigDecimal(1)), 2, RoundingMode.HALF_UP);
        
//Subtotal2 del subtotal menos el desuento
        BigDecimal calculo = sumaTotalCompra.subtract(compra.getDescuento());
        baseImponibleIva=calculo.setScale(2,RoundingMode.HALF_UP);
        
        compra.setSubtotal(sumaTotalCompra.setScale(2, RoundingMode.HALF_UP));
        
        compra.setDescuento(compra.getDescuento().setScale(2,RoundingMode.HALF_UP));
        System.out.println("bi="+calculo.multiply(new BigDecimal("0.12")));
        System.out.println("bi="+calculo.multiply(new BigDecimal("0.12")).setScale(2,RoundingMode.HALF_UP));
        //System.out.println(calculo);
        //Valor en porcenaje para calcular el iva
        //BigDecimal ivaPorcentaje=new BigDecimal(iva);
        BigDecimal ivaPorcentaje=ivaMostrar;
                
        
        //System.out.println(compra.getIva());

        compra.setTotal(calculo.multiply(ivaPorcentaje.divide(new BigDecimal("100"), 2, RoundingMode.HALF_UP).add(new BigDecimal("1"))).setScale(2,RoundingMode.HALF_UP));
        //compra.setTotal(compra.getTotal().divide(new BigDecimal(1), 2, RoundingMode.HALF_UP));
        //compra.setIva(compra.getTotal().subtract(compra.getTotal().multiply(ivaPorcentaje.divide(new BigDecimal("100")))));
        compra.setIva(compra.getTotal().subtract(compra.getSubtotal().subtract(compra.getDescuento())));
        
        System.out.println("iva:"+compra.getIva() );
    }

    /**
     * Metodo que me permite agregar un producto especifico
     */
    public void agregarProductoEspecifico() {

        System.out.println(catalogo);

        //Verificar que no exista el codigo del producto individual en la base de datos
        if (!compraServicio.existenciaProductoIndividual(catalogo.getCodigoProducto(), codigoEspecificoDetalle)) {

            //Verifica que no exista el mismo codigo individual ingresado
            boolean prodInvidualRepetido = false;
            for (DetalleCompraModelo detalle : detalleCompra) {
                if (!detalle.getCodigoGeneral()) {
                    if (detalle.getProductoIndividual().getCodigoUnico().equals(codigoEspecificoDetalle) && detalle.getProductoIndividual().getCodigoProducto().getCodigoProducto().equals(catalogo.getCodigoProducto())) {
                        prodInvidualRepetido = true;
                    }
                }
            }

            //Si el producto individual no existe procede a grabar
            if (!prodInvidualRepetido) {

                ProductoIndividualCompra detalleIndividual = new ProductoIndividualCompra();
                //detalleIndividual.setCantidad(cantidadDetalle);
                detalleIndividual.setCosto(costoDetalle);
                detalleIndividual.setCodigoProducto(catalogo);
                detalleIndividual.setCodigoUnico(codigoEspecificoDetalle);

                DetalleCompraModelo detalle = new DetalleCompraModelo(detalleIndividual);
                detalleCompra.add(detalle);

                //limpiar ventana para agregar mas detalles
                contadorIngresoDetalleEspecifico--;

                codigoEspecificoDetalle = "";

                sumaTotalCompra = sumaTotalCompra.add(detalle.getSubtotal());
                actualizarValoresTotales();

                //verifica que ya se hayan ingresado todos los productos
                if (contadorIngresoDetalleEspecifico == 0) {
                    RequestContext.getCurrentInstance().execute("PF('dlgProductoGeneral').hide()");

                }
            } else {
                FacesMessage message = new FacesMessage(FacesMessage.SEVERITY_INFO, "Alerta", "El codigo especifico ya se agrego");
                RequestContext.getCurrentInstance().showMessageInDialog(message);
            }

        } else {
            FacesMessage message = new FacesMessage(FacesMessage.SEVERITY_INFO, "Alerta", "El codigo especifico ya existe");
            RequestContext.getCurrentInstance().showMessageInDialog(message);
        }
    }

    /**
     * Guarda toda la compra con los detalles individuales y generales
     */
    public void ejecutarCompra() {

        //validar que este seleccionado un distribuidor
        //System.out.println("->"+compra.getRuc()+"<--");
        if (compra.getRuc().getRuc().equals("")) {
            FacesMessage message = new FacesMessage(FacesMessage.SEVERITY_INFO, "Alerta", "No existen un distribuidor para realizar la compra");
            RequestContext.getCurrentInstance().showMessageInDialog(message);
        } else {
            //validar que existan Items para comprar
            if (detalleCompra.size() > 0) {

                List<ProductoIndividualCompra> detalleIndividual = new ArrayList<ProductoIndividualCompra>();
                List<ProductoGeneralCompra> detalleGeneral = new ArrayList<ProductoGeneralCompra>();

                System.out.println(detalleCompra.size());

                for (DetalleCompraModelo detalle : detalleCompra) {
                    if (detalle.getCodigoGeneral()) {
                        detalleGeneral.add(detalle.getProductoGeneral());
                    } else {
                        detalleIndividual.add(detalle.getProductoIndividual());
                    }
                }

                System.out.println(detalleGeneral.size() + "-" + detalleIndividual.size());
                System.out.println("comprando ....");
                System.out.println(compra);
                System.out.println("fecha ingreso: " + compra.getFechaIngreso());

                compra.setProductoGeneralCompraList(detalleGeneral);
                compra.setProductoIndividualCompraList(detalleIndividual);

                compraServicio.insertar(compra);
                //compraServicio.actualizar(compra);
                RequestContext.getCurrentInstance().execute("PF('dialogNuevaCompra').show()");
            } else {
                FacesMessage message = new FacesMessage(FacesMessage.SEVERITY_INFO, "Alerta", "No existen items para realizar la compra");
                RequestContext.getCurrentInstance().showMessageInDialog(message);
            }
        }
        //dialogNuevaCompra
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

    public void recibirDatos(SelectEvent event) {
        Distribuidor distribuidorAux = (Distribuidor) event.getObject();
        if (distribuidorAux != null) {
            compra.setRuc(distribuidorAux);
        }

        System.out.println("dato recibido");
        System.out.println(compra.getRuc());
    }

    /**
     * Metodo que controla al ingresar un caracter
     *
     * @param ae
     */
    public void verificarProducto() {
        //validacion para saber si selecciono un distribuidor
        if (compra.getRuc().getRuc().equals("")) {
            FacesMessage message = new FacesMessage(FacesMessage.SEVERITY_INFO, "Alerta", "No se encuentra seleccionado un distribuidor para agregar productos");
            RequestContext.getCurrentInstance().showMessageInDialog(message);
        } //procede a agregar los detalles cuando ya esta escogido el distribuidor
        else {
            focus="formDetalleCompra:txtCantidadDetalle";
            cantidadDetalle = 1;
            costoDetalle = new BigDecimal("0");
            costoDetalle = costoDetalle.setScale(3, RoundingMode.DOWN);
            System.out.println(compra);
            System.out.println("Total" + total);
            catalogo = catalogoServicio.buscarCatalogo(codigoDetalle);
            if (catalogo != null) {
                System.out.println("El producto existe");
                costoDetalle = compraServicio.obtenerUltimoCostoDistribuidor(catalogo, compra.getRuc().getRuc());
                costoDetalle = costoDetalle.setScale(3, RoundingMode.DOWN);
                if(catalogo.getTipoProducto().equals("G"))
                {
                    stockProductoSeleccionado=catalogo.getProductoGeneralVenta().getCantidadDisponible();
                }
                else
                {
                    stockProductoSeleccionado=facturaServicio.devolverStockIndividual(catalogo.getCodigoProducto());
                }
                
                this.visibleDetalleAgregar = true;
            } else {
                System.out.println("El producto no existe");
                RequestContext.getCurrentInstance().execute("PF('confirmarProducto').show()");
                //confirmarProducto

            }
        }

    }

    /**
     * Carga los datos desde un formulario de edicion
     */
    public void cargarDatosEditar() {
        System.out.println("cargando datos editar ...");
       // Compra compraEditar = compraServicio.obtenerTodos().get(1);
        //System.out.println(compraParametro.toString());

        //compra = compraEditar;
        //detalleCompra=compraServicio.obtenerTodos();
        List<ProductoGeneralCompra> detalleGeneral = compra.getProductoGeneralCompraList();

        for (ProductoGeneralCompra detalle : detalleGeneral) {
            DetalleCompraModelo modelo = new DetalleCompraModelo(
                    detalle.getCodigoProducto().getCodigoProducto(),
                    detalle.getCodigoProducto().getNombre(),
                    detalle.getCodigoProducto().getDescripcion(),
                    detalle.getCantidad(),
                    detalle.getCostoIndividual(),
                    detalle.getSubtotal(),
                    true);

            System.out.println(modelo.toString());

            detalleCompra.add(modelo);

            sumaTotalCompra = sumaTotalCompra.add(detalle.getSubtotal());
        }

        List<ProductoIndividualCompra> detalleIndividual = compra.getProductoIndividualCompraList();

        for (ProductoIndividualCompra detalle : detalleIndividual) {
            DetalleCompraModelo modelo = new DetalleCompraModelo(
                    detalle.getCodigoProducto().getCodigoProducto(),
                    detalle.getCodigoProducto().getNombre(),
                    detalle.getCodigoProducto().getDescripcion(),
                    1,
                    detalle.getCosto(),
                    detalle.getCosto(),
                    false);

            detalleCompra.add(modelo);
            sumaTotalCompra = sumaTotalCompra.add(detalle.getCosto());
        }
        actualizarValoresTotales();
        //System.out.println(detalleGeneral.size()+"-"+detalleIndividual.size());

        //compra=new Compra();
        //compra=compra.setTipoDocumento(total);
        //;
    }

    /**
     * Funcion que me permite llamar a un nuevo dialogo para crear un formulario
     */
    public void crearNuevoCatalogo() {
        System.out.println("abriendo nuevo panel del catalogo...");
        Map<String, Object> options = new HashMap<String, Object>();
        options.put("codigo", true);

        Map<String, List<String>> params = new HashMap<String, List<String>>();
        List<String> values = new ArrayList<String>();

        values.add(codigoDetalle);
        params.put("codigo", values);

        RequestContext.getCurrentInstance().execute("PF('confirmarProducto').hide()");
        RequestContext.getCurrentInstance().openDialog("crearCatalogoProducto", options, params);

    }

    public void recibirCatalogo(SelectEvent event) {
        CatalagoProducto catalogoAux = (CatalagoProducto) event.getObject();
        if (catalogoAux != null) {
            catalogo = (CatalagoProducto) event.getObject();
            codigoDetalle = catalogo.getCodigoProducto();
            System.out.println(catalogo);
        }
    }

    public void imprimirCompra() {
        //System.out.println("Imprimiendo la compra ...");
        //System.out.println(compra);
        compraServicio.consultar();
    }

    public void filaSeleccionadaDistribuidor(SelectEvent event) {
        System.out.println("fila seleccionada ...");
        System.out.println(distribuidorSeleccionado);
        compra.setRuc(distribuidorSeleccionado);
        RequestContext.getCurrentInstance().execute("PF('overlayDistribuidor').hide()");
    }

    /**
     * Eliminar un item en el detalle
     *
     * @return
     */
    public void eliminarDetalle(DetalleCompraModelo detalle) {
        detalleCompra.remove(detalle);
        sumaTotalCompra = sumaTotalCompra.subtract(detalle.getSubtotal());
        actualizarValoresTotales();
    }

    public void onRowSelect(SelectEvent event) {
        //widgetVarCatalogo
        //FacesMessage msg = new FacesMessage("Catalogo Seleccionado", ((CatalagoProducto) event.getObject()).getCodigoProducto());
        //FacesContext.getCurrentInstance().addMessage(null, msg);
        codigoDetalle = ((CatalagoProducto) (event.getObject())).getCodigoProducto();
        //visibleDetalleAgregar = true;
        // System.out.println("ocultando panel ...");
        RequestContext.getCurrentInstance().execute("PF('widgetVarCatalogo').hide()");
        System.out.println(codigoDetalle);
        //flagBoton1 = false;
        //pro = clienteSeleccionado;
        //System.out.println(cliente.getNombre());
        verificarProducto();
    }
    
     public void cambiarfocus(String id)
    {
        
        focus=id;
       
    }

    public void onRowUnSelect(SelectEvent event) {
        System.out.println("deseleccionando ...");
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

    public comprarMB(CatalagoProducto catalogo) {
        this.catalogo = catalogo;
    }

    public CatalagoProducto getCatalogo() {
        return catalogo;
    }

    public void setCatalogo(CatalagoProducto catalogo) {
        this.catalogo = catalogo;
    }

    public String getTotal() {
        return total;
    }

    public void setTotal(String total) {
        this.total = total;
    }

    public List<Distribuidor> getListaDistribuidores() {
        return listaDistribuidores;
    }

    public void setListaDistribuidores(List<Distribuidor> listaDistribuidores) {
        this.listaDistribuidores = listaDistribuidores;
    }

    public Distribuidor getDistribuidorSeleccionado() {
        return distribuidorSeleccionado;
    }

    public void setDistribuidorSeleccionado(Distribuidor distribuidorSeleccionado) {
        this.distribuidorSeleccionado = distribuidorSeleccionado;
    }

    public BigDecimal getSumaTotalCompra() {
        return sumaTotalCompra;
    }

    public void setSumaTotalCompra(BigDecimal sumaTotalCompra) {
        this.sumaTotalCompra = sumaTotalCompra;
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

    public boolean isVisibleDetalleAgregar() {
        return visibleDetalleAgregar;
    }

    public void setVisibleDetalleAgregar(boolean visibleDetalleAgregar) {
        this.visibleDetalleAgregar = visibleDetalleAgregar;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public BigDecimal getCambiarCosto() {
        return cambiarCosto;
    }

    public void setCambiarCosto(BigDecimal cambiarCosto) {
        this.cambiarCosto = cambiarCosto;
    }

    public String getIvaCosto() {
        return ivaCosto;
    }

    public void setIvaCosto(String ivaCosto) {
        this.ivaCosto = ivaCosto;
    }

    public Integer getStockProductoSeleccionado() {
        return stockProductoSeleccionado;
    }

    public void setStockProductoSeleccionado(Integer stockProductoSeleccionado) {
        this.stockProductoSeleccionado = stockProductoSeleccionado;
    }

    public SistemaMB getSistemaMB() {
        return sistemaMB;
    }

    public void setSistemaMB(SistemaMB sistemaMB) {
        this.sistemaMB = sistemaMB;
    }

    public Integer getIva() {
        return iva;
    }

    public void setIva(Integer iva) {
        this.iva = iva;
    }

    public String getFocus() {
        return focus;
    }

    public void setFocus(String focus) {
        this.focus = focus;
    }

    public BigDecimal getIvaMostrar() {
        return ivaMostrar;
    }

    public void setIvaMostrar(BigDecimal ivaMostrar) {
        this.ivaMostrar = ivaMostrar;
    }

    public BigDecimal getBaseImponibleIva() {
        return baseImponibleIva;
    }

    public void setBaseImponibleIva(BigDecimal baseImponibleIva) {
        this.baseImponibleIva = baseImponibleIva;
    }
    
    
    

}
