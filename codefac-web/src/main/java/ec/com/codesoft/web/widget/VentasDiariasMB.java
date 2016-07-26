/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ec.com.codesoft.web.widget;

import ec.com.codesoft.model.Banco;
import ec.com.codesoft.model.CatalagoProducto;
import ec.com.codesoft.model.CategoriaTrabajo;
import ec.com.codesoft.model.Cliente;
import ec.com.codesoft.model.Creditobanco;
import ec.com.codesoft.model.DetalleOrdenTrabajo;
import ec.com.codesoft.model.DetalleProductoGeneral;
import ec.com.codesoft.model.DetalleProductoIndividual;
import ec.com.codesoft.model.DetalleVentaOrdenTrabajo;
import ec.com.codesoft.model.DetallesServicio;
import ec.com.codesoft.model.Intereses;
import ec.com.codesoft.model.OrdenTrabajo;
import ec.com.codesoft.model.ProductoGeneralVenta;
import ec.com.codesoft.model.ProductoIndividualCompra;
import ec.com.codesoft.model.Servicios;
import ec.com.codesoft.model.Venta;
import ec.com.codesoft.modelo.servicios.BancoServicio;
import ec.com.codesoft.modelo.servicios.CatalogoServicio;
import ec.com.codesoft.modelo.servicios.ClienteServicio;
import ec.com.codesoft.modelo.servicios.CompraServicio;
import ec.com.codesoft.modelo.servicios.FacturaServicio;
import ec.com.codesoft.modelo.servicios.OrdenTrabajoServicio;
import ec.com.codesoft.modelo.servicios.SistemaServicio;
import ec.com.codesoft.web.operador.Descuentos;
import ec.com.codesoft.web.operador.DetallesVenta;
import ec.com.codesoft.web.operador.FacturaMB;
import ec.com.codesoft.web.reportes.FacturaDetalleModeloReporte;
import ec.com.codesoft.web.reportes.FacturaModeloReporte;
import ec.com.codesoft.web.reportes.NotaVentaModeloReporte;
import ec.com.codesoft.web.seguridad.SessionMB;
import java.io.IOException;
import java.io.Serializable;
import java.math.BigDecimal;
import java.math.BigInteger;
import java.math.MathContext;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.annotation.PostConstruct;
import javax.ejb.EJB;
import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ManagedProperty;
import javax.faces.bean.SessionScoped;
import javax.faces.context.FacesContext;
import javax.faces.model.SelectItem;
import javax.faces.model.SelectItemGroup;
import net.sf.jasperreports.engine.JRException;
import org.primefaces.context.RequestContext;
import org.primefaces.event.SelectEvent;

/**
 *
 * @author Suco
 */
@ManagedBean
@SessionScoped
public class VentasDiariasMB extends CommonWidGet implements Serializable {

    private boolean estadoDialogo;
    private boolean estadoDialogoGeneral;
    private String cedCliente;
    private String msjCliente;
    private Cliente clienteEncontrado;
    private boolean mostrarPanel;
    private String codigoP;
    private CatalagoProducto catalogoEncontrado;
    private CatalagoProducto catalogo;
    private int stock;
    private int cantidadComprar;
    private boolean mostrarInformacion;
    private List<CatalagoProducto> catalogosLista;
    private CatalagoProducto catalogoSeleccionado;
    private ProductoGeneralVenta productoGeneral;
    private String msjStock;
    private String tipoCliente;
    private boolean todoPanel;
    private ProductoIndividualCompra productosIndividualesDetalles;
    private List<DetallesVenta> detallesVenta;
    private BigDecimal totalRegistro;
    private BigDecimal total;
    private BigDecimal subtotal;
    private BigDecimal descuento;
    private BigDecimal subtotalRegistro;
    private BigDecimal iva;
    private String codPEspe;
    private ProductoIndividualCompra detalleIndividual;
    private String msjCodUnico;
    private List<DetalleProductoIndividual> detallesIndividualVenta;
    private List<DetalleProductoGeneral> detallesGeneralVenta;
    private List<DetallesServicio> detallesServicio;
    private List<DetalleVentaOrdenTrabajo> detallesVentaOrdenTrabajo;
    Integer codigoFactura;
    private List<Cliente> clientesLista;
    private Cliente clienteSeleccionado;
    private ProductoGeneralVenta prodGeneral;
    private ProductoIndividualCompra prodIndividual;
    private BigDecimal recibo;
    private BigDecimal vuelto;
    private boolean estPanPagos; //esatdo paneles de tipos de pagos
    private boolean estBanco; //panel Banco
    private boolean estCheue; //panel Cheque
    private String tipoPago; //tipo de pago cheque banco etc
    private String tituloPago; //variable que indica si es Banco o Cheque-> titulo
    private String NCheque; //variable que contiene el N de Cheque
    private List<Intereses> intereses;
    private List<Banco> bancos;
    private Banco bancoBuscar;
    private List<Intereses> interesesPorBanco;
    private List<SelectItem> selectGeneral;
    private List<SelectItemGroup> selectGroupBancos; //lista de Selects Groups Bancos
    private List<SelectItemGroup> selectMeses; //lista de Selects Groups Meses
    private String nombreBanco;
    private BigDecimal campoInteres; // variable para mostrar el intereas del banco 
    private boolean estadoInteres;
    private Integer mesSeleccionado; //mesSeleccionado
    private Boolean banderaInteres; //controlar la 1 vez que calcule el interes
    private BigDecimal interesTarjeta;
    private BigDecimal totalPagar;
    private BigDecimal descuentoSeleccionado;
    private List<String> desiciones;
    private String tipoPrecio;
    private String cliMayorista;
    private boolean creditoDirecto;
    private Date fechaInicio;
    private Date fechaFinal;
    private Creditobanco creditoBanco;
    private BigDecimal ivaTotal; //iva traido de la configuracion
    private BigDecimal ivaSubTotal; //iva traido de la configuracion
    private BigDecimal ivaMostrar;
    private Integer maxItemFactura;
    private Integer maxItemNota;
    private Integer maxItems;
    private boolean mostrarDescuentoManual;
    private Integer cantidaServicio;
    private BigDecimal precioServicio;
    private List<Servicios> servicioLista; //lista de servicios disponibles
    private Servicios servicioSeleccionado;

    //
    private BigDecimal descuentoManual;

    //ordenes de trabajo
    private List<OrdenTrabajo> ordenesTemporal;
    private List<OrdenTrabajo> ordenesTrabajo;
    private OrdenTrabajo ordenTrabajoSeleccionada;
    private List<DetalleVentaOrdenTrabajo> detallesOrdenTrabajo;
    private List<DetalleOrdenTrabajo> detallesOrdenMostrar;
    private List<DetalleOrdenTrabajo> detallesOrdenSeleccionadas;
    private List<DetalleOrdenTrabajo> detallesCambiarEstado;

    /**
     * venta Diarias
     */
    private Venta ventaDiaria;
    private int codFactura;

    /**
     * Porpiedad para enlazar el numero de factura
     */
    private Integer codigoDocumento;

    @EJB
    ClienteServicio clienteServicio;

    @EJB
    private CatalogoServicio catalogoServicio;

    @EJB
    FacturaServicio facturaServicio;

    @EJB
    private CompraServicio compraServicio;

    @EJB
    private SistemaServicio sistemaServicio;

    @EJB
    private BancoServicio bancoServicio;

    @EJB
    private OrdenTrabajoServicio ordenTrabajoServicio;

    //sesion 
    @ManagedProperty(value = "#{sessionMB}")
    private SessionMB sesion;

    @PostConstruct
    public void inicializar() {
        estadoDialogo = false;
        estadoDialogoGeneral = false;
        msjCliente = "";
        clienteEncontrado = new Cliente();
        clienteEncontrado.setCedulaRuc("1212121212");
        clienteEncontrado.setTipo("PVP");
        clienteEncontrado.setNombre("Venta Diaria");

        mostrarPanel = false;
        cantidadComprar = 1;
        mostrarInformacion = false;
        catalogosLista = catalogoServicio.obtenerTodos();
        servicioLista = new ArrayList<>();
        servicioLista = catalogoServicio.obtenerServicios();//obtener los servicios
        precioServicio = new BigDecimal("0.0");
        servicioSeleccionado = new Servicios();
        catalogoSeleccionado = new CatalagoProducto();
        msjStock = "";
        todoPanel = false;
        totalRegistro = new BigDecimal("0.0");
        total = new BigDecimal("0.0");
        subtotal = new BigDecimal("0.0");
        descuento = new BigDecimal("0");
        subtotalRegistro = new BigDecimal("0.0");
        iva = new BigDecimal("0.0");
        detallesVenta = new ArrayList<DetallesVenta>();
        msjCodUnico = "";
        detallesIndividualVenta = new ArrayList<DetalleProductoIndividual>();
        detallesGeneralVenta = new ArrayList<DetalleProductoGeneral>();
        detallesServicio = new ArrayList<DetallesServicio>();
        clientesLista = clienteServicio.obtenerTodos();
        recibo = new BigDecimal("0.0");
        tipoCliente = "F";

        //codigoDocumento = 0;//facturaServicio.getCodigoFactura();
        codigoDocumento = facturaServicio.getCodigoFactura("Factura");
        estPanPagos = false;
        estBanco = false;
        estBanco = false;
        tipoPago = "Efectivo";
        NCheque = "";
        estadoInteres = false;
        campoInteres = new BigDecimal("0.0");
        banderaInteres = true;
        interesTarjeta = new BigDecimal("0.0");
        totalPagar = new BigDecimal("0.0");
        descuentoSeleccionado = new BigDecimal("0.0");
        desiciones = new ArrayList<String>();
        desiciones.add("si");
        desiciones.add("no");
        tipoPrecio = "PVP";

        //devolver todos los bancos
        ivaMostrar = sistemaServicio.getConfiguracion().getIva();
        ivaSubTotal = (sistemaServicio.getConfiguracion().getIva()).divide(new BigDecimal("100"));
        ivaTotal = ivaSubTotal.add(new BigDecimal("1"));
        System.out.println(ivaTotal + " -- " + ivaSubTotal);

        descuentoManual = new BigDecimal("0.0");
        //numero de items maximo
        maxItemFactura = sistemaServicio.getConfiguracion().getMaxItemFactura();
        maxItemNota = sistemaServicio.getConfiguracion().getMaxItemNota();
        //por defecto esta factura al iniciar la venta
        maxItems = maxItemFactura;

        //exclusivo para ordenes de Trabajo
        ordenesTrabajo = ordenTrabajoServicio.obtenerOrdenesTrabajo();
        detallesOrdenTrabajo = new ArrayList<DetalleVentaOrdenTrabajo>();

        /**
         * Ventas Diaarias cargar los detalles con fecha Actual
         */
        System.out.println("FechaActual: " + getFechaActual());
        ventaDiaria = facturaServicio.devolverDetallesVentasDiarias(getFechaActual());//("2016-03-23");

        if (ventaDiaria == null) {
            System.err.println("nulo");
            ventaDiaria = new Venta();
        } else {
            List<DetalleProductoGeneral> productosGeneral = facturaServicio.devolverVentaDiariaDetallesGeneral(ventaDiaria.getCodigoFactura());
            List<DetalleProductoIndividual> productosIndivudual = facturaServicio.devolverVentaDiariaDetallesIndividual(ventaDiaria.getCodigoFactura());
            List<DetallesServicio> servicios = facturaServicio.devolverVentaDiariaDetallesServicio(ventaDiaria.getCodigoFactura());
            List<DetalleVentaOrdenTrabajo> detallesOrdenTrabajo = facturaServicio.devolverVentaDiariaDetallesOrdenTrabajo(ventaDiaria.getCodigoFactura());
            System.out.println("Items " + productosGeneral.size());
            //System.out.println("Items " + productosIndivudual.size());

            codFactura = ventaDiaria.getCodigoFactura();// codigo de la factura

            if (productosGeneral.size() > 0) {
                for (int i = 0; i < productosGeneral.size(); i++) {
                    DetallesVenta detalles = new DetallesVenta();
                    detalles.setCantidad(productosGeneral.get(i).getCantidad());
                    detalles.setNombre(productosGeneral.get(i).getCodigoProducto().getNombre());
                    detalles.setCodigo(productosGeneral.get(i).getCodigoProducto().getCodigoProducto());
                    detalles.setCosto(productosGeneral.get(i).getPrecioIndividual());
                    detalles.setTotal(productosGeneral.get(i).getSubtotal());
                    detallesVenta.add(detalles);
                    DetalleProductoGeneral detalle = new DetalleProductoGeneral();
                    detalle.setCantidad(cantidadComprar);
                    detalle.setCodigoProducto(catalogoSeleccionado);
                    detalle.setSubtotal(subtotalRegistro);
                    detalle.setCodigoDetallGeneral(0);
                    detallesGeneralVenta.add(detalle);
                }
            }

            if (productosIndivudual.size() > 0) {
                for (int i = 0; i < productosIndivudual.size(); i++) {
                    DetallesVenta detalles = new DetallesVenta();
                    detalles.setCantidad(1);
                    detalles.setNombre(productosIndivudual.get(i).getProductoIndividualCompra().getCodigoProducto().getNombre());
                    detalles.setCodigo(productosIndivudual.get(i).getProductoIndividualCompra().getCodigoUnico());
                    detalles.setCosto(productosIndivudual.get(i).getPrecioIndividual());
                    detalles.setTotal(productosIndivudual.get(i).getSubtotal());
                    detallesVenta.add(detalles);
                    DetalleProductoIndividual detalle = new DetalleProductoIndividual();
                    detalle.setProductoIndividualCompra(detalleIndividual);
                    detalle.setSubtotal(subtotalRegistro);
                    detalle.setPrecioIndividual(detalles.getCosto());
                    detallesIndividualVenta.add(detalle);
                }
            }

            if (servicios.size() > 0) {
                for (int i = 0; i < servicios.size(); i++) {
                    DetallesVenta detalles = new DetallesVenta();
                    detalles.setCantidad(1);
                    detalles.setNombre(servicios.get(i).getNombre());
                    detalles.setCodigo(servicios.get(i).getCodDetalleServicio().toString());
                    detalles.setCosto(servicios.get(i).getTotal());
                    detalles.setTotal(servicios.get(i).getTotal());
                    detallesVenta.add(detalles);
                    detallesServicio.add(servicios.get(i));
                }
            }
            
            //  DetallesVenta detalles = new DetallesVenta(1, detallesOrdenSeleccionadas.get(i).getIdDetalleOrdenTrabajo().toString(),
                      //      detallesOrdenSeleccionadas.get(i).devolverDetalles() + " --> Estado: " + estadoDetalle,
                       //     totalRegistro, totalRegistro);
            
            if (detallesOrdenTrabajo.size() > 0) {
                for (int i = 0; i < detallesOrdenTrabajo.size(); i++) {
                    DetallesVenta detalles = new DetallesVenta();
                    detalles.setCantidad(1);
                    detalles.setNombre(detallesOrdenTrabajo.get(i).getIdDetalleOrdenTrabajo().getEquipo()+" --> Estado "+detallesOrdenTrabajo.get(i).getIdDetalleOrdenTrabajo().getEstado());
                    detalles.setCodigo(detallesOrdenTrabajo.get(i).getIdDetalleOrdenTrabajo().getIdDetalleOrdenTrabajo().toString());
                    detalles.setCosto(detallesOrdenTrabajo.get(i).getTotal());
                    detalles.setTotal(detallesOrdenTrabajo.get(i).getTotal());
                    detallesVenta.add(detalles);
                    System.out.println("detalleorden "+detallesOrdenTrabajo.get(i));
                   //detallesVentaOrdenTrabajo.add(detallesOrdenTrabajo.get(i));
                }
            }


            cargarDetalles();

        }

        //cantidad de los servicios
        cantidaServicio = 1;

        //exclusivo para ordenes de Trabajo
        detallesCambiarEstado = new ArrayList<DetalleOrdenTrabajo>();
        detallesOrdenTrabajo = new ArrayList<DetalleVentaOrdenTrabajo>();
        //filtar por ordenes facturadas
        ordenesTrabajo = new ArrayList<OrdenTrabajo>();
        ordenesTemporal = ordenTrabajoServicio.obtenerOrdenesTrabajo();

        for (int i = 0; i < ordenesTemporal.size(); i++) {
            List<DetalleOrdenTrabajo> detallesOrden = new ArrayList<DetalleOrdenTrabajo>();
            detallesOrden = ordenesTemporal.get(i).getDetalleOrdenTrabajoList(); //cargo los detalles orden trabajo
            int verDetalles = 0; //verificar si existe algun detalle venta vacio
            for (int j = 0; j < detallesOrden.size(); j++) {
                List<DetalleVentaOrdenTrabajo> detallesVentaOrden = new ArrayList<DetalleVentaOrdenTrabajo>();
                detallesVentaOrden = detallesOrden.get(j).getDetalleVentaOrdenTrabajoList();
                if (detallesVentaOrden.isEmpty()) {
                    verDetalles += 1;
                }
                //las ordenes estan facturadas
            }
            if (verDetalles != 0) {
                ordenesTrabajo.add(ordenesTemporal.get(i));
            }

        }

        //inicializar total
        //caragr detalles
        //position del widget
        setX(0);
        setY(530);
        setNameVar("dlgDetalles");

    }

    public String getFechaActual() {
        Date ahora = new Date();
        SimpleDateFormat formateador = new SimpleDateFormat("yyyy-MM-dd");
        return formateador.format(ahora);
    }

    public void pruebar() {
        System.out.println("Pruebar");
    }

    public void verificarDialogo() {
        System.out.println("VeriE");
        if (estadoDialogo) {
            RequestContext.getCurrentInstance().execute("PF('infProductoE').show()");
        }
    }

    public void cerrarDialogo() {
        RequestContext.getCurrentInstance().execute("PF('infProductoE).hide()");
        estadoDialogo = false;
    }

    public void verificarDialogoG() {
        System.out.println("VeriG");
        if (estadoDialogoGeneral) {
            RequestContext.getCurrentInstance().execute("PF('infProducto').show()");
        }
    }

    public void cerrarDialogoG() {
        RequestContext.getCurrentInstance().execute("PF('infProducto').hide()");
        estadoDialogoGeneral = false;
    }

    public void cargarDetalles() {

        total = new BigDecimal("0.0");
        totalRegistro = new BigDecimal("0.0");
        subtotal = new BigDecimal("0.0");
        for (int k = 0; k < detallesVenta.size(); k++) {
            //  System.out.println("Detalle " + k + detallesVenta.get(k).getTotal());
            //System.err.println("totalDetalle: "+detallesVenta.get(k).getTotal());

            BigDecimal valor = detallesVenta.get(k).getTotal().divide(ivaTotal, 3, BigDecimal.ROUND_DOWN);
            valor = valor.setScale(3, BigDecimal.ROUND_DOWN); //quito el iva
            totalRegistro = valor;
            subtotalRegistro = totalRegistro.multiply(ivaTotal);
            subtotal = subtotal.add(totalRegistro);
            if (tipoCliente.equals("C")) {
                iva = new BigDecimal("0.0");
                total = subtotal;
                totalPagar = total;
            } else {
                iva = subtotal.multiply(ivaSubTotal);
                total = subtotal.multiply(ivaTotal);
                totalPagar = total;

            }
        }
    }

    public void buscarProducto() {

        catalogoEncontrado = catalogoServicio.buscarCatalogo(codigoP);
        if (catalogoEncontrado == null) {
            System.out.println("NNEncontrado");
            catalogo = new CatalagoProducto();
            catalogo.setCodigoProducto(codigoP);
            estadoDialogo = true;
            //tabGeneral = true;

        } else {
            catalogo = catalogoEncontrado;
            System.out.println("Encontrado");
            if (catalogoEncontrado.getTipoProducto() == 'g') {
                System.out.println("general");
                mostrarPanel = false;

            } else {
                System.out.println("Espe");
                stock = facturaServicio.devolverStockIndividual(codigoP);
                mostrarInformacion = true;
                System.out.println(stock);
            }

        }
    }

    public void onRowSelect(SelectEvent event) {

        catalogoSeleccionado = catalogoServicio.buscarCatalogo(catalogoSeleccionado.getCodigoProducto());
        if (catalogoSeleccionado == null) {
            System.out.println("NNEncontrado");
            catalogo = new CatalagoProducto();
            catalogo.setCodigoProducto(codigoP);
            estadoDialogo = true;
            //tabGeneral = true;

        } else {
            //catalogo = catalogoEncontrado;
            productoGeneral = new ProductoGeneralVenta();
            System.out.println("Encontrado");
            if ((catalogoSeleccionado.getTipoProducto()) == 'G' || (catalogoSeleccionado.getTipoProducto()) == 'g') {
                System.out.println("general");
                System.err.println("codifoP" + catalogoSeleccionado.getCodigoProducto());
                productoGeneral = facturaServicio.devolverStockGeneral(catalogoSeleccionado.getCodigoProducto());
                // System.out.println(productoGeneral.getCantidad());
                int numDetalles = 0;
                for (int i = 0; i < detallesVenta.size(); i++) {
                    if (detallesVenta.get(i).getCodigo().equals(catalogoSeleccionado.getCodigoProducto())) {
                        numDetalles = numDetalles + 1;
                    }
                }

                stock = productoGeneral.getCantidadDisponible() - numDetalles;
                Map<String, Object> options = new HashMap<String, Object>();
                options.put("modal", true);
                RequestContext.getCurrentInstance().execute("PF('infProducto').show()");
                estadoDialogoGeneral = true;

                //mostrarPanel = false;
            } else if ((catalogoSeleccionado.getTipoProducto()) == 'E' || (catalogoSeleccionado.getTipoProducto()) == 'e') {
                System.out.println("Espe");
                int numDetalles = 0;
                for (int i = 0; i < detallesVenta.size(); i++) {
                    if (detallesVenta.get(i).getCodigo().equals(catalogoSeleccionado.getCodigoProducto())) {
                        numDetalles = numDetalles + 1;
                    }
                }
                stock = (facturaServicio.devolverStockIndividual(catalogoSeleccionado.getCodigoProducto()) - numDetalles);
                System.out.println("codCat " + catalogoSeleccionado.getCodigoProducto());
                RequestContext.getCurrentInstance().execute("PF('infProductoE').show()");
                estadoDialogo = true;
                //mostrarInformacion = true;
                System.out.println(stock + "individual");
                //mostrarPanel = false;

            } else {
                System.out.println("Servicio");
                RequestContext.getCurrentInstance().execute("PF('infProductoE').show()");
            }
            // msjDistri = "Encontrado";
            // mostrarPanel = true;
        }
        //catalogoSeleccionado = new CatalagoProducto();

    }

    public void onRowUnSelect(SelectEvent event) {

    }

    public void onRowSelectService(SelectEvent event) {
        //infEscojerServicioV
        precioServicio = new BigDecimal("0.0");
        RequestContext.getCurrentInstance().execute("PF('infEscojerServicioV').show()");
    }

    public void onRowUnSelectService(SelectEvent event) {

    }

    public void venta(String tipo) {

        System.out.println(cantidadComprar + "--" + stock);

        if (detallesVenta.size() <= 0) {

            ventaDiaria.setCedulaRuc(clienteEncontrado);
            ventaDiaria.setEstado("Diaria");
            ventaDiaria.setFecha(new Date());
            ventaDiaria.setTipoDocumento("Factura");
            ventaDiaria.setTipoPago("Efectivo");
            ventaDiaria.setBanco("");
            ventaDiaria.setCheque("");
            ventaDiaria.setDescuento(new BigDecimal("0.0"));
            ventaDiaria.setCodigoDocumento(codigoDocumento);
            ventaDiaria.setDetalleProductoGeneralList(detallesGeneralVenta);
            facturaServicio.guardarFactura(ventaDiaria);
            codFactura = ventaDiaria.getCodigoFactura();
            System.out.println("Se creo la factura xq no habian ventas Diarias");

        }

        if (tipo.equals("catalogo")) {

            if (cantidadComprar > stock) {
                //msjStock = "No existe suficiente Stock";
                FacesMessage message = new FacesMessage(FacesMessage.SEVERITY_INFO, "Error...!", "No existe suficiente Stock..!");
                RequestContext.getCurrentInstance().showMessageInDialog(message);
                //     System.out.println("No hay stock");

            } else {

                if (tipoCliente == "" || tipoCliente == null) {
                    FacesMessage msg = new FacesMessage("Escoja el tipo de documento");
                    FacesContext.getCurrentInstance().addMessage(null, msg);
                    cerrarDialogo();
                    cerrarDialogoG();
                } else if (clienteEncontrado.getCedulaRuc() == null || clienteEncontrado.getCedulaRuc() == "") {
                    estadoDialogo = false;
                    estadoDialogoGeneral = false;
                    FacesMessage message = new FacesMessage(FacesMessage.SEVERITY_INFO, "Error...!", "Ingrese el Cliente!");
                    RequestContext.getCurrentInstance().showMessageInDialog(message);
                } else {

                    cerrarDialogoG();
                    msjCodUnico = "";
                    msjStock = "";
                    //msjStock = "";
                    System.out.println("Si hay stock");
                    if ((catalogoSeleccionado.getTipoProducto()) == 'G' || (catalogoSeleccionado.getTipoProducto()) == 'g') {

                        System.out.println("En venta");
                        System.out.println(clienteEncontrado.getTipo());
                        if (clienteEncontrado.getTipo().equals("Distribuidor")) {
                            totalRegistro = catalogoSeleccionado.getPrecioMayorista().multiply(new BigDecimal(cantidadComprar));
                        } else {
                            totalRegistro = catalogoSeleccionado.getPrecio().multiply(new BigDecimal(cantidadComprar));
                        }
                        System.out.println(totalRegistro + "totalRegistro");
                        subtotalRegistro = totalRegistro.multiply(ivaTotal);
                        subtotal = subtotal.add(totalRegistro);

                        iva = subtotal.multiply(ivaSubTotal);
                        total = subtotal.multiply(ivaTotal);
                        totalPagar = total;

                        System.out.println("Codigo General " + productoGeneral.getCatalagoProducto().getCodigoProducto());
                        DetallesVenta detalles = new DetallesVenta(cantidadComprar,
                                productoGeneral.getCatalagoProducto().getCodigoProducto() + "", catalogoSeleccionado.getNombre(),
                                new BigDecimal("0.0"), totalRegistro);

                        detalles.setCosto(catalogoSeleccionado.getPrecio());

                        detalles.setValorVerdaderoMayorista(catalogoSeleccionado.getPrecioMayorista());
                        detalles.setValorVerdaderoPVP(catalogoSeleccionado.getPrecio());
                        detalles.setPrecioSeleccionado("PVP");
                        detalles.setEscogerDescuento("No");
                        detalles.setTipoDetalle("Producto");
                        detallesVenta.add(detalles);

                        DetalleProductoGeneral detalle = new DetalleProductoGeneral();
                        detalle.setCantidad(cantidadComprar);
                        detalle.setCodigoProducto(catalogoSeleccionado);
                        detalle.setSubtotal(subtotalRegistro);
                        detalle.setCodigoDetallGeneral(0);
                        detalle.setPrecioIndividual(detalles.getCosto());
                        detalle.setCodigoFactura(ventaDiaria);
                        detallesGeneralVenta.add(detalle);
                        //guardo los detalles 
                        facturaServicio.insertarDetalleFacturaProductoGeneral(detalle);
                        //actualizamos stock de producto general
                        prodGeneral = new ProductoGeneralVenta();
                        prodGeneral = facturaServicio.devolverStockGeneral(catalogoSeleccionado.getCodigoProducto());
                        Integer cantidadStock = 0;
                        cantidadStock = prodGeneral.getCantidadDisponible() - cantidadComprar;
                        prodGeneral.setCantidadDisponible(cantidadStock);
                        facturaServicio.actulizarStockGeneral(prodGeneral);

                        System.out.println("Inserto detalle General");

                    } else {
                        System.out.println("Especifico");
                        detalleIndividual = facturaServicio.devolverIndividualCod(codPEspe, catalogoSeleccionado.getCodigoProducto());
                        // System.err.println(detalleIndividual);
                        if (detalleIndividual == null) {
                            msjCodUnico = "No existe Producto con ese código";

                        } else if (detalleIndividual.getEstadoProceso().equals("Vendido")) {
                            msjCodUnico = "Producto con ese código  ya esta en Venta";
                        } else {
                            cantidadComprar = 1;
                            cerrarDialogo();
                            msjCodUnico = "";
                    //cerrarDialogo();

                            // productosIndividualesDetalles = facturaServicio.obtenerProductoIndivudualCantidad(1, codPEspe);
                            if (clienteEncontrado.getTipo().equals("Distribuidor")) {
                                System.out.println("precio Mayorista " + catalogoSeleccionado.getPrecioMayorista() + " Cantidad Comprar " + cantidadComprar);
                                totalRegistro = catalogoSeleccionado.getPrecioMayorista().multiply(new BigDecimal(cantidadComprar));
                            } else {
                                totalRegistro = catalogoSeleccionado.getPrecio().multiply(new BigDecimal(cantidadComprar));
                            }

                            subtotalRegistro = totalRegistro.multiply(ivaTotal);
                            subtotal = subtotal.add(totalRegistro);
                            if (tipoCliente.equals("C")) { //nota de venta C= tipo de documento
//                            iva = new BigDecimal("0.0");
//                            total = subtotal;
//                            totalPagar = total;
                                iva = subtotal.multiply(ivaSubTotal);
                                total = subtotal.multiply(ivaTotal);
                                totalPagar = total;
                            } else {
                                iva = subtotal.multiply(ivaSubTotal);
                                total = subtotal.multiply(ivaTotal);
                                totalPagar = total;
                            }

                            DetallesVenta detalles = new DetallesVenta(cantidadComprar, detalleIndividual.getCodigoUnico(),
                                    detalleIndividual.getCodigoProducto().getNombre(),
                                    new BigDecimal("0.0"), totalRegistro);

                            if (clienteEncontrado.getTipo().equals("Distribuidor")) {
                                detalles.setCosto(catalogoSeleccionado.getPrecioMayorista());
                            } else {
                                detalles.setCosto(catalogoSeleccionado.getPrecio());
                            }
                            Descuentos precioMayorista = new Descuentos("Prec Mayorista", catalogoSeleccionado.getPrecioMayorista());
                            Descuentos precioDescuento = new Descuentos("PVP", catalogoSeleccionado.getPrecio());
                            Descuentos dcto = new Descuentos("dctoPVP", catalogoSeleccionado.getDescuento());
                            Descuentos dctoMayorista = new Descuentos("dctoMayorista", catalogoSeleccionado.getDescuentoMayorista());
                            List<Descuentos> descuentos = new ArrayList<Descuentos>();
                            descuentos.add(precioMayorista);
                            descuentos.add(precioDescuento);
                            descuentos.add(dcto);
                            descuentos.add(dctoMayorista);
                            detalles.setValorVerdaderoMayorista(catalogoSeleccionado.getPrecioMayorista());
                            detalles.setValorVerdaderoPVP(catalogoSeleccionado.getPrecio());
                            detalles.setDescuentos(descuentos);
                            detalles.setPrecioSeleccionado("PVP");
                            detalles.setEscogerDescuento("No");
                            detalles.setTipoDetalle("Producto");
                            detallesVenta.add(detalles);
                            catalogoSeleccionado = new CatalagoProducto();
                            DetalleProductoIndividual detalle = new DetalleProductoIndividual();
                            detalle.setProductoIndividualCompra(detalleIndividual);
                            detalle.setSubtotal(subtotalRegistro);
                            detalle.setPrecioIndividual(detalles.getCosto());
                            detalle.setCodigoFactura(ventaDiaria);

                            facturaServicio.insertarDetalleProductoIndividual(detalle);
                            System.out.println("Inserto detalle Individual");
                            detallesIndividualVenta.add(detalle);

                        }
                    }

                }
            }
        } else {
            System.out.println("subtotal " + subtotal);
            System.out.println("Servicio A facturar");

            BigDecimal valor = precioServicio.divide(ivaTotal, 3, BigDecimal.ROUND_DOWN);
            valor = valor.setScale(3, BigDecimal.ROUND_DOWN);

            totalRegistro = valor.multiply(new BigDecimal(1));
            System.out.println(totalRegistro + "totalRegistroServicio");
            subtotalRegistro = totalRegistro.multiply(ivaTotal);
            subtotal = subtotal.add(totalRegistro);
            System.out.println("NuevoSubtotal " + subtotal);
            iva = subtotal.multiply(ivaSubTotal);
            total = subtotal.multiply(ivaTotal);
            totalPagar = total;

            System.out.println("Codigo Servicio " + servicioSeleccionado.getCodigoServicio());
            DetallesVenta detalles = new DetallesVenta(1,
                    servicioSeleccionado.getCodigoServicio() + " ", servicioSeleccionado.getNombre(),
                    new BigDecimal("0.0"), totalRegistro);

            detalles.setCosto(precioServicio);

            detalles.setValorVerdaderoMayorista(precioServicio);
            detalles.setValorVerdaderoPVP(precioServicio);
            detalles.setPrecioSeleccionado("PVP");
            detalles.setEscogerDescuento("No");
            detalles.setTipoDetalle("Servicio");
            detallesVenta.add(detalles);

            DetallesServicio detalleServicio = new DetallesServicio();
            detalleServicio.setCodigoFactura(ventaDiaria);
            detalleServicio.setDescripcion(servicioSeleccionado.getDescripcion());
            CategoriaTrabajo ctTemporal = new CategoriaTrabajo();
            ctTemporal.setIdCategoriaTrabajo(1);
            detalleServicio.setIdCategoriaTrabajo(ctTemporal);
            detalleServicio.setIva(iva);
            //detalleServicio.setNick();
            detalleServicio.setNombre(servicioSeleccionado.getNombre());
            detalleServicio.setTotal(precioServicio);

            detallesServicio.add(detalleServicio);
            //guardo los detalles 
            facturaServicio.insertarDetalleServicio(detalleServicio);
            RequestContext.getCurrentInstance().execute("PF('infEscojerServicioV').hide()");
            System.out.println("Inserto servicio");

        }

        /**
         * Guardar Detalles de Facturas
         */
    }

    //nota de venta en las ventas diarias
    public void ventaNota() {
        System.out.println(cantidadComprar + "--" + stock);

        if (detallesVenta.size() <= 0) {

            ventaDiaria.setCedulaRuc(clienteEncontrado);
            ventaDiaria.setEstado("Diaria");
            ventaDiaria.setFecha(new Date());
            ventaDiaria.setTipoDocumento("Nota");
            ventaDiaria.setTipoPago("Efectivo");
            ventaDiaria.setBanco("");
            ventaDiaria.setCheque("");
            ventaDiaria.setDescuento(new BigDecimal("0.0"));
            ventaDiaria.setCodigoDocumento(codigoDocumento);
            ventaDiaria.setDetalleProductoGeneralList(detallesGeneralVenta);
            facturaServicio.guardarFactura(ventaDiaria);
            codFactura = ventaDiaria.getCodigoFactura();
            System.out.println("Se creo la factura xq no habian ventas Diarias");

        }

        if (tipoCliente == "" || tipoCliente == null) {
            FacesMessage msg = new FacesMessage("Escoja el tipo de documento");
            FacesContext.getCurrentInstance().addMessage(null, msg);
            cerrarDialogo();
            cerrarDialogoG();
        } else if (clienteEncontrado.getCedulaRuc() == null || clienteEncontrado.getCedulaRuc() == "") {
            estadoDialogo = false;
            estadoDialogoGeneral = false;
            FacesMessage message = new FacesMessage(FacesMessage.SEVERITY_INFO, "Error...!", "Ingrese el Cliente!");
            RequestContext.getCurrentInstance().showMessageInDialog(message);
        } else {

            cerrarDialogoG();
            msjCodUnico = "";
            msjStock = "";
            //msjStock = "";
            System.out.println("Si hay stock");
            if ((catalogoSeleccionado.getTipoProducto()) == 'G' || (catalogoSeleccionado.getTipoProducto()) == 'g') {

                System.out.println("En venta");
                System.out.println(clienteEncontrado.getTipo());
                if (clienteEncontrado.getTipo().equals("Distribuidor")) {
                    totalRegistro = catalogoSeleccionado.getPrecioMayorista().multiply(new BigDecimal(cantidadComprar));
                } else {
                    totalRegistro = catalogoSeleccionado.getPrecio().multiply(new BigDecimal(cantidadComprar));
                }
                System.out.println(totalRegistro + "totalRegistro");
                subtotalRegistro = totalRegistro.multiply(ivaTotal);
                subtotal = subtotal.add(totalRegistro);

                iva = subtotal.multiply(ivaSubTotal);
                total = subtotal.multiply(ivaTotal);
                totalPagar = total;

                System.out.println("Codigo General " + productoGeneral.getCatalagoProducto().getCodigoProducto());
                DetallesVenta detalles = new DetallesVenta(cantidadComprar,
                        productoGeneral.getCatalagoProducto().getCodigoProducto() + "", catalogoSeleccionado.getNombre(),
                        new BigDecimal("0.0"), totalRegistro);

                detalles.setCosto(catalogoSeleccionado.getPrecio());

                detalles.setValorVerdaderoMayorista(catalogoSeleccionado.getPrecioMayorista());
                detalles.setValorVerdaderoPVP(catalogoSeleccionado.getPrecio());
                detalles.setPrecioSeleccionado("PVP");
                detalles.setEscogerDescuento("No");
                detalles.setTipoDetalle("Producto");
                detallesVenta.add(detalles);

                DetalleProductoGeneral detalle = new DetalleProductoGeneral();
                detalle.setCantidad(cantidadComprar);
                detalle.setCodigoProducto(catalogoSeleccionado);
                detalle.setSubtotal(subtotalRegistro);
                detalle.setCodigoDetallGeneral(0);
                detalle.setPrecioIndividual(detalles.getCosto());
                detalle.setCodigoFactura(ventaDiaria);
                detallesGeneralVenta.add(detalle);
                //guardo los detalles 
                facturaServicio.insertarDetalleFacturaProductoGeneral(detalle);
                //actualizamos stock de producto general
                prodGeneral = new ProductoGeneralVenta();
                prodGeneral = facturaServicio.devolverStockGeneral(catalogoSeleccionado.getCodigoProducto());
                Integer cantidadStock = 0;
                cantidadStock = prodGeneral.getCantidadDisponible() - cantidadComprar;
                prodGeneral.setCantidadDisponible(cantidadStock);
                facturaServicio.actulizarStockGeneral(prodGeneral);

                System.out.println("Inserto detalle General");

            } else {
                System.out.println("Especifico");
                detalleIndividual = facturaServicio.devolverIndividualCod(codPEspe, catalogoSeleccionado.getCodigoProducto());
                // System.err.println(detalleIndividual);
                if (detalleIndividual == null) {
                    msjCodUnico = "No existe Producto con ese código";

                } else if (detalleIndividual.getEstadoProceso().equals("Vendido")) {
                    msjCodUnico = "Producto con ese código  ya esta en Venta";
                } else {
                    cantidadComprar = 1;
                    cerrarDialogo();
                    msjCodUnico = "";
                    //cerrarDialogo();

                    // productosIndividualesDetalles = facturaServicio.obtenerProductoIndivudualCantidad(1, codPEspe);
                    if (clienteEncontrado.getTipo().equals("Distribuidor")) {
                        System.out.println("precio Mayorista " + catalogoSeleccionado.getPrecioMayorista() + " Cantidad Comprar " + cantidadComprar);
                        totalRegistro = catalogoSeleccionado.getPrecioMayorista().multiply(new BigDecimal(cantidadComprar));
                    } else {
                        totalRegistro = catalogoSeleccionado.getPrecio().multiply(new BigDecimal(cantidadComprar));
                    }

                    subtotalRegistro = totalRegistro.multiply(ivaTotal);
                    subtotal = subtotal.add(totalRegistro);
                    if (tipoCliente.equals("C")) { //nota de venta C= tipo de documento
//                            iva = new BigDecimal("0.0");
//                            total = subtotal;
//                            totalPagar = total;
                        iva = subtotal.multiply(ivaSubTotal);
                        total = subtotal.multiply(ivaTotal);
                        totalPagar = total;
                    } else {
                        iva = subtotal.multiply(ivaSubTotal);
                        total = subtotal.multiply(ivaTotal);
                        totalPagar = total;
                    }

                    DetallesVenta detalles = new DetallesVenta(cantidadComprar, detalleIndividual.getCodigoUnico(),
                            detalleIndividual.getCodigoProducto().getNombre(),
                            new BigDecimal("0.0"), totalRegistro);

                    if (clienteEncontrado.getTipo().equals("Distribuidor")) {
                        detalles.setCosto(catalogoSeleccionado.getPrecioMayorista());
                    } else {
                        detalles.setCosto(catalogoSeleccionado.getPrecio());
                    }
                    Descuentos precioMayorista = new Descuentos("Prec Mayorista", catalogoSeleccionado.getPrecioMayorista());
                    Descuentos precioDescuento = new Descuentos("PVP", catalogoSeleccionado.getPrecio());
                    Descuentos dcto = new Descuentos("dctoPVP", catalogoSeleccionado.getDescuento());
                    Descuentos dctoMayorista = new Descuentos("dctoMayorista", catalogoSeleccionado.getDescuentoMayorista());
                    List<Descuentos> descuentos = new ArrayList<Descuentos>();
                    descuentos.add(precioMayorista);
                    descuentos.add(precioDescuento);
                    descuentos.add(dcto);
                    descuentos.add(dctoMayorista);
                    detalles.setValorVerdaderoMayorista(catalogoSeleccionado.getPrecioMayorista());
                    detalles.setValorVerdaderoPVP(catalogoSeleccionado.getPrecio());
                    detalles.setDescuentos(descuentos);
                    detalles.setPrecioSeleccionado("PVP");
                    detalles.setEscogerDescuento("No");
                    detalles.setTipoDetalle("Producto");
                    detallesVenta.add(detalles);
                    catalogoSeleccionado = new CatalagoProducto();
                    DetalleProductoIndividual detalle = new DetalleProductoIndividual();
                    detalle.setProductoIndividualCompra(detalleIndividual);
                    detalle.setSubtotal(subtotalRegistro);
                    detalle.setPrecioIndividual(detalles.getCosto());
                    detalle.setCodigoFactura(ventaDiaria);

                    facturaServicio.insertarDetalleProductoIndividual(detalle);
                    System.out.println("Inserto detalle Individual");
                    detallesIndividualVenta.add(detalle);

                }
            }

        }

        /**
         * Guardar Detalles de Facturas
         */
    }

    /**
     *
     * guarda la factura de las ventas diarias
     */
    public void facturar() {

        ventaDiaria.setEstado("Facturado Diaria");
        ventaDiaria.setTotal(totalPagar);
        ventaDiaria.setDescuento(new BigDecimal("0.0"));
        facturaServicio.editarVentaDiaria(ventaDiaria);
        detallesGeneralVenta = facturaServicio.devolverVentaDiariaDetallesGeneral(ventaDiaria.getCodigoFactura());
        detallesIndividualVenta = facturaServicio.devolverVentaDiariaDetallesIndividual(ventaDiaria.getCodigoFactura());
        ventaDiaria.setDetalleProductoGeneralList(detallesGeneralVenta);
        ventaDiaria.setDetalleProductoIndividualList(detallesIndividualVenta);

        FacturaModeloReporte facturaReporte = new FacturaModeloReporte(sistemaServicio.getConfiguracion().getPathreportes());
        facturaReporte.setCodigoFactura(ventaDiaria.getCodigoFactura() + "");
        facturaReporte.setDireccion(clienteEncontrado.getDireccion());
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy/MM/dd");
        facturaReporte.setTelefono(clienteEncontrado.getTelefono());

        facturaReporte.setRuc(cedCliente);
        facturaReporte.setFechaaFactura(sdf.format(ventaDiaria.getFecha()));
        facturaReporte.setFormaPago("Efectivo");
        facturaReporte.setIvaTotal(iva);
        facturaReporte.setNombreCliente(clienteEncontrado.getNombre());
        facturaReporte.setNota(" ");
        facturaReporte.setSubtotal(subtotal);

        //System.out.println(subtotal);
        facturaReporte.setTotal(total);
        for (DetallesVenta detalle : detallesVenta) {
            FacturaDetalleModeloReporte detallesFactura = new FacturaDetalleModeloReporte();
            detallesFactura.setCantidad(detalle.getCantidad() + "");
            detallesFactura.setCodigo(detalle.getCodigo());
            detallesFactura.setDescripcion(detalle.getNombre());
            detallesFactura.setDescuento("0");
            detallesFactura.setPrecioUnitario(detalle.getCosto().toString());
            detallesFactura.setTotal(detalle.getTotal().toString());
            if (facturaReporte != null) {
                facturaReporte.agregarDetalle(detallesFactura);
            }
        }

        try {
            facturaReporte.exportarPDF();
            //detallesFactura.setCantidad(1);
            //factura.agregarDetalle(detallesFactura);
            //factura.exportarPDF();
        } catch (JRException ex) {
            Logger.getLogger(FacturaMB.class.getName()).log(Level.SEVERE, null, ex);
        } catch (IOException ex) {
            Logger.getLogger(FacturaMB.class.getName()).log(Level.SEVERE, null, ex);
        }

//        RequestContext.getCurrentInstance().execute("PF('confirmarFactura').hide()");
//        RequestContext.getCurrentInstance().execute("PF('ok').show()");
        detallesVenta = new ArrayList< DetallesVenta>();
        subtotal = new BigDecimal("0.0");
        subtotalRegistro = new BigDecimal("0.0");
        total = new BigDecimal("0.0");
        totalPagar = new BigDecimal("0.0");
    }

    //factura para las notas de venta Juli
    public void facturarNota() {

        ventaDiaria.setEstado("Facturado Diaria");
        ventaDiaria.setTotal(totalPagar);
        ventaDiaria.setDescuento(new BigDecimal("0.0"));
        facturaServicio.editarVentaDiaria(ventaDiaria);
        detallesGeneralVenta = facturaServicio.devolverVentaDiariaDetallesGeneral(ventaDiaria.getCodigoFactura());
        detallesIndividualVenta = facturaServicio.devolverVentaDiariaDetallesIndividual(ventaDiaria.getCodigoFactura());
        ventaDiaria.setDetalleProductoGeneralList(detallesGeneralVenta);
        ventaDiaria.setDetalleProductoIndividualList(detallesIndividualVenta);

        NotaVentaModeloReporte notaVenta = new NotaVentaModeloReporte(sistemaServicio.getConfiguracion().getPathreportes());
        notaVenta.setDireccion(clienteEncontrado.getDireccion());
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy/MM/dd");
        notaVenta.setFechaFactura(sdf.format(ventaDiaria.getFecha()));
        notaVenta.setFechaaFactura(sdf.format(ventaDiaria.getFecha()));
        notaVenta.setFormaPago(ventaDiaria.getTipoPago());
        notaVenta.setNombreCliente(clienteEncontrado.getNombre());
        notaVenta.setTelefono(clienteEncontrado.getTelefono());
        notaVenta.setTotal(total);

        for (DetallesVenta detalle : detallesVenta) {
            FacturaDetalleModeloReporte detallesFactura = new FacturaDetalleModeloReporte();
            detallesFactura.setCantidad(detalle.getCantidad() + "");
            detallesFactura.setCodigo(detalle.getCodigo());
            detallesFactura.setDescripcion(detalle.getNombre());
            detallesFactura.setDescuento(detalle.getValorDescuento().toString());
            detallesFactura.setPrecioUnitario(detalle.getCosto().toString());
            detallesFactura.setTotal(detalle.getTotal().toString());
            if (notaVenta != null) {
                notaVenta.agregarDetalle(detallesFactura);
            }
        }

        try {
            notaVenta.exportarPDF();
            //detallesFactura.setCantidad(1);
            //factura.agregarDetalle(detallesFactura);
            //factura.exportarPDF();
        } catch (JRException ex) {
            Logger.getLogger(FacturaMB.class.getName()).log(Level.SEVERE, null, ex);
        } catch (IOException ex) {
            Logger.getLogger(FacturaMB.class.getName()).log(Level.SEVERE, null, ex);
        }

//        RequestContext.getCurrentInstance().execute("PF('confirmarFactura').hide()");
//        RequestContext.getCurrentInstance().execute("PF('ok').show()");
        detallesVenta = new ArrayList< DetallesVenta>();
        total = new BigDecimal("0.0");
        totalPagar = new BigDecimal("0.0");
    }

    public void imprimirFactura() {

    }

    //PRDENES DE TRABAJO METODOS
    public void mostrarDialogoDetallesOrden(SelectEvent event) {

        detallesOrdenMostrar = new ArrayList<DetalleOrdenTrabajo>();
        System.out.println("ordenesCargar" + ordenTrabajoSeleccionada.getDetalleOrdenTrabajoList());
        for (int i = 0; i < ordenTrabajoSeleccionada.getDetalleOrdenTrabajoList().size(); i++) {
            if (ordenTrabajoSeleccionada.getDetalleOrdenTrabajoList().get(i).getEstado().equals("reparado") || ordenTrabajoSeleccionada.getDetalleOrdenTrabajoList().get(i).getEstado().equals("devolver")){ //|| ordenTrabajoSeleccionada.getDetalleOrdenTrabajoList().get(i).getEstado().equals("facturado")) {
                System.out.println("Entro a FIltrar");
                detallesOrdenMostrar.add(ordenTrabajoSeleccionada.getDetalleOrdenTrabajoList().get(i));
            }

        }

        RequestContext.getCurrentInstance().execute("PF('dlgDetallesOrdenesTrabajo').show()");

    }

    public void onRowUnSelectOrden(SelectEvent event) {
    }

    public void devolverDescuento(BigDecimal descuentoCalcular) {
        BigDecimal descuentoPorcentaje = descuentoCalcular.divide(new BigDecimal(100)).add(new BigDecimal(1));
        System.out.println("porcetaje " + descuentoPorcentaje);
        BigDecimal subTotalDescuento = subtotal.divide(descuentoPorcentaje, 2, BigDecimal.ROUND_FLOOR);
        System.out.println(subTotalDescuento);
        iva = subTotalDescuento.multiply(ivaSubTotal, MathContext.DECIMAL32);
        iva = iva.divide(new BigDecimal(1), 2, BigDecimal.ROUND_UP);
        total = subTotalDescuento.multiply(ivaTotal, MathContext.DECIMAL32);
        total = total.divide(new BigDecimal(1), 2, BigDecimal.ROUND_UP);
        totalPagar = total;
    }

    public void cambiarEstadoOrden() {

        for (int i = 0; i < ordenesTrabajo.size(); i++) {
            int bandera = 0;
            List<DetalleOrdenTrabajo> detalle = new ArrayList<DetalleOrdenTrabajo>();
            detalle = ordenesTrabajo.get(i).getDetalleOrdenTrabajoList();
            for (int j = 0; j < detalle.size(); j++) {
                if (detalle.get(j).getEstado().equals("facturado")) {
                    bandera += 1;
                }
            }
            if (bandera == detalle.size()) {
                ordenesTrabajo.get(i).setEstado("Facturada");
                ordenTrabajoServicio.editar(ordenesTrabajo.get(i));
            }
        }
    }

    public void onRowSelectOrden() {
        //detallesOrden=detallesOrdenSeleccionadas;

        if (detallesVenta.size() <= 0) {

            ventaDiaria.setCedulaRuc(clienteEncontrado);
            ventaDiaria.setEstado("Diaria");
            ventaDiaria.setFecha(new Date());
            ventaDiaria.setTipoDocumento("Nota");
            ventaDiaria.setTipoPago("Efectivo");
            ventaDiaria.setBanco("");
            ventaDiaria.setCheque("");
            ventaDiaria.setDescuento(new BigDecimal("0.0"));
            ventaDiaria.setCodigoDocumento(codigoDocumento);
            ventaDiaria.setDetalleProductoGeneralList(detallesGeneralVenta);
            facturaServicio.guardarFactura(ventaDiaria);
            codFactura = ventaDiaria.getCodigoFactura();
            System.out.println("Se creo la factura xq no habian ventas Diarias");

        }

        System.out.println("Venta Orden");
        if (detallesVenta.size() > maxItems - 1) {

            estadoDialogo = false;
            estadoDialogoGeneral = false;
            FacesMessage message = new FacesMessage(FacesMessage.SEVERITY_INFO, "Advertencia...!", "Número Máximo de Detalles Alcanzado..!");
            RequestContext.getCurrentInstance().showMessageInDialog(message);
        } else {
            if (tipoCliente == "" || tipoCliente == null) {
                FacesMessage msg = new FacesMessage("Escoja el tipo de documento");
                FacesContext.getCurrentInstance().addMessage(null, msg);
                cerrarDialogo();
                cerrarDialogoG();
            } else if (clienteEncontrado.getCedulaRuc() == null || clienteEncontrado.getCedulaRuc() == "") {
                estadoDialogo = false;
                estadoDialogoGeneral = false;
                FacesMessage message = new FacesMessage(FacesMessage.SEVERITY_INFO, "Error...!", "Ingrese el Cliente!");
                RequestContext.getCurrentInstance().showMessageInDialog(message);
            } else {

                cerrarDialogoG();
                //DetalleVentaOrdenTrabajo detalleOrdenTrabajo = new DetalleVentaOrdenTrabajo();

                //cargar a los detallesVenta los DetallesOrdenesTrabajo
                System.out.println("Antes del for");
                System.out.println(detallesOrdenSeleccionadas);

                //FOR recorre lista de los detalles orden
                for (int i = 0; i < detallesOrdenSeleccionadas.size(); i++) {
                    System.out.println("Entro al for " + i);
                    totalRegistro = detallesOrdenSeleccionadas.get(i).getPrecio().divide(ivaTotal, 2, BigDecimal.ROUND_FLOOR);
                    subtotalRegistro = totalRegistro.multiply(ivaTotal);
                    subtotal = subtotal.add(totalRegistro);
                    if (tipoCliente.equals("C")) { //nota de venta C= tipo de documento
                        iva = new BigDecimal("0.0");
                        total = subtotal;
                        totalPagar = total;
                        devolverDescuento(new BigDecimal("0.0"));
//                iva = subtotal.multiply(ivaSubTotal);
//                total = subtotal.multiply(ivaTotal);
//                totalPagar = total;
                    } else {
//                iva = subtotal.multiply(ivaSubTotal);
//                total = subtotal.multiply(ivaTotal);
//                totalPagar = total;
                        iva = new BigDecimal("0.0");
                        total = subtotal;
                        totalPagar = total;
                        devolverDescuento(new BigDecimal("0.0"));
                    }

                    //descomentar para que coja la orden sin el iva
//                DetallesVenta detalles = new DetallesVenta(1, ordenTrabajoSeleccionada.getIdOrdenTrabajo().toString(),
//                        ordenTrabajoSeleccionada.toStringDetalle(),
//                        ordenTrabajoSeleccionada.getTotal(), totalRegistro);
                    String estadoDetalle = "";
                    if (detallesOrdenSeleccionadas.get(i).getEstado().equals("devolver")) {
                        estadoDetalle = "Devuelto";
                    } else {
                        estadoDetalle = detallesOrdenSeleccionadas.get(i).getEstado();
                    }
                    DetallesVenta detalles = new DetallesVenta(1, detallesOrdenSeleccionadas.get(i).getIdDetalleOrdenTrabajo().toString(),
                            detallesOrdenSeleccionadas.get(i).devolverDetalles() + " --> Estado: " + estadoDetalle,
                            totalRegistro, totalRegistro);

                    //descomentar para que coja el descuento con el precio sin iva
//                Descuentos precioMayorista = new Descuentos("Prec Mayorista", ordenTrabajoSeleccionada.getTotal());
//                Descuentos precioDescuento = new Descuentos("PVP", ordenTrabajoSeleccionada.getTotal());
                    Descuentos precioMayorista = new Descuentos("Prec Mayorista", totalRegistro);
                    Descuentos precioDescuento = new Descuentos("PVP", totalRegistro);
                    Descuentos dcto = new Descuentos("dctoPVP", new BigDecimal("0.0"));
                    //System.out.println(catalogoSeleccionado.getDescuento());
                    Descuentos dctoMayorista = new Descuentos("dctoMayorista", new BigDecimal("0.0"));
                    List<Descuentos> descuentos = new ArrayList<Descuentos>();
                    descuentos.add(precioMayorista);
                    descuentos.add(precioDescuento);
                    descuentos.add(dcto);
                    descuentos.add(dctoMayorista);
                    detalles.setValorVerdaderoMayorista(totalRegistro);
                    detalles.setValorVerdaderoPVP(totalRegistro);
                    detalles.setDescuentos(descuentos);
                    detalles.setPrecioSeleccionado("PVP");
                    detalles.setEscogerDescuento("No");
                    detalles.setTipoDetalle("Orden Trabajo");
                    //REVISAR
                    //detalleOrdenTrabajo.setIdOrdenTrabajo(ordenTrabajoSeleccionada);
                    detallesVenta.add(detalles);
                    detallesCambiarEstado.add(detallesOrdenSeleccionadas.get(i));
                    System.out.println("Detallles: " + detallesVenta);
                }
            }
        }
        //cambiar el estado y agregar los detalles
        //insertarDetalles orden Trabajo
        int numOrdenes = 0;
        for (int i = 0; i < detallesCambiarEstado.size(); i++) {
            // if (detallesVenta.get(i).getTipoDetalle().equals("Orden Trabajo")) {

            DetalleVentaOrdenTrabajo detalleOrdenTrabajo = new DetalleVentaOrdenTrabajo();
            detalleOrdenTrabajo.setCodigoFactura(ventaDiaria);
            //detalleOrdenTrabajo.setIdOrdenTrabajo(0);
            detalleOrdenTrabajo.setDescuento(detallesVenta.get(i).getValorDescuento());
            detalleOrdenTrabajo.setEstado("Facturado");
            detalleOrdenTrabajo.setIva(new BigDecimal("0.0"));
            detallesCambiarEstado.get(i).setEstado("facturado");
            facturaServicio.actualizarDetalleOrden(detallesCambiarEstado.get(i));

            //REVISAR                           
            detalleOrdenTrabajo.setIdDetalleOrdenTrabajo(detallesCambiarEstado.get(i));
            //detalleOrdenTrabajo.setNick;
            detalleOrdenTrabajo.setTotal(detallesVenta.get(i).getTotal());
            System.out.println("DetalleOrden " + detalleOrdenTrabajo);
            detallesOrdenTrabajo.add(detalleOrdenTrabajo);
            numOrdenes = i + 1;

        }
        cambiarEstadoOrden(); //cambiarEstadoOrden

        //guaradmos los detalles orden trabajo
        if (numOrdenes >= 1) {
            facturaServicio.insertarDetallesVentaOrdenTrabajo(detallesOrdenTrabajo);
        }

        RequestContext.getCurrentInstance().execute("PF('dlgDetallesOrden').hide()");

    }

    ////////////////////METODOS GET Y SET /////////////////////
    public boolean getEstadoDialogo() {
        return estadoDialogo;
    }

    public void setEstadoDialogo(boolean estadoDialogo) {
        this.estadoDialogo = estadoDialogo;
    }

    public boolean getEstadoDialogoGeneral() {
        return estadoDialogoGeneral;
    }

    public void setEstadoDialogoGeneral(boolean estadoDialogoGeneral) {
        this.estadoDialogoGeneral = estadoDialogoGeneral;
    }

    public String getCedCliente() {
        return cedCliente;
    }

    public void setCedCliente(String cedCliente) {
        this.cedCliente = cedCliente;
    }

    public String getMsjCliente() {
        return msjCliente;
    }

    public void setMsjCliente(String msjCliente) {
        this.msjCliente = msjCliente;
    }

    public Cliente getClienteEncontrado() {
        return clienteEncontrado;
    }

    public void setClienteEncontrado(Cliente clienteEncontrado) {
        this.clienteEncontrado = clienteEncontrado;
    }

    public boolean getMostrarPanel() {
        return mostrarPanel;
    }

    public void setMostrarPanel(boolean mostrarPanel) {
        this.mostrarPanel = mostrarPanel;
    }

    public String getCodigoP() {
        return codigoP;
    }

    public void setCodigoP(String codigoP) {
        this.codigoP = codigoP;
    }

    public CatalagoProducto getCatalogoEncontrado() {
        return catalogoEncontrado;
    }

    public void setCatalogoEncontrado(CatalagoProducto catalogoEncontrado) {
        this.catalogoEncontrado = catalogoEncontrado;
    }

    public CatalagoProducto getCatalogo() {
        return catalogo;
    }

    public void setCatalogo(CatalagoProducto catalogo) {
        this.catalogo = catalogo;
    }

    public int getStock() {
        return stock;
    }

    public void setStock(int stock) {
        this.stock = stock;
    }

    public int getCantidadComprar() {
        return cantidadComprar;
    }

    public void setCantidadComprar(int cantidadComprar) {
        this.cantidadComprar = cantidadComprar;
    }

    public boolean getMostrarInformacion() {
        return mostrarInformacion;
    }

    public void setMostrarInformacion(boolean mostrarInformacion) {
        this.mostrarInformacion = mostrarInformacion;
    }

    public List<CatalagoProducto> getCatalogosLista() {
        return catalogosLista;
    }

    public void setCatalogosLista(List<CatalagoProducto> catalogosLista) {
        this.catalogosLista = catalogosLista;
    }

    public CatalagoProducto getCatalogoSeleccionado() {
        return catalogoSeleccionado;
    }

    public void setCatalogoSeleccionado(CatalagoProducto catalogoSeleccionado) {
        this.catalogoSeleccionado = catalogoSeleccionado;
    }

    public String getMsjStock() {
        return msjStock;
    }

    public void setMsjStock(String msjStock) {
        this.msjStock = msjStock;
    }

    public String getTipoCliente() {
        return tipoCliente;
    }

    public void setTipoCliente(String tipoCliente) {
        this.tipoCliente = tipoCliente;
    }

    public boolean getTodoPanel() {
        return todoPanel;
    }

    public void setTodoPanel(boolean todoPanel) {
        this.todoPanel = todoPanel;
    }

    public List<DetallesVenta> getDetallesVenta() {
        return detallesVenta;
    }

    public void setDetallesVenta(List<DetallesVenta> detallesVenta) {
        this.detallesVenta = detallesVenta;
    }

    public BigDecimal getTotal() {
        return total;
    }

    public void setTotal(BigDecimal total) {
        this.total = total;
    }

    public String getCodPEspe() {
        return codPEspe;
    }

    public void setCodPEspe(String codPEspe) {
        this.codPEspe = codPEspe;
    }

    public String getMsjCodUnico() {
        return msjCodUnico;
    }

    public void setMsjCodUnico(String msjCodUnico) {
        this.msjCodUnico = msjCodUnico;
    }

    public BigDecimal getTotalRegistro() {
        return totalRegistro;
    }

    public void setTotalRegistro(BigDecimal totalRegistro) {
        this.totalRegistro = totalRegistro;
    }

    public BigDecimal getSubtotal() {
        return subtotal;
    }

    public void setSubtotal(BigDecimal subtotal) {
        this.subtotal = subtotal;
    }

    public BigDecimal getIva() {
        return iva;
    }

    public void setIva(BigDecimal iva) {
        this.iva = iva;
    }

    public List<Cliente> getClientesLista() {
        return clientesLista;
    }

    public void setClientesLista(List<Cliente> clientesLista) {
        this.clientesLista = clientesLista;
    }

    public Cliente getClienteSeleccionado() {
        return clienteSeleccionado;
    }

    public void setClienteSeleccionado(Cliente clienteSeleccionado) {
        this.clienteSeleccionado = clienteSeleccionado;
    }

    public BigDecimal getRecibo() {
        return recibo;
    }

    public void setRecibo(BigDecimal recibo) {
        this.recibo = recibo;
    }

    public BigDecimal getVuelto() {
        return vuelto;
    }

    public void setVuelto(BigDecimal vuelto) {
        this.vuelto = vuelto;
    }

    public Integer getCodigoDocumento() {
        return codigoDocumento;
    }

    public void setCodigoDocumento(Integer codigoDocumento) {
        this.codigoDocumento = codigoDocumento;
    }

    public BigDecimal getDescuento() {
        return descuento;
    }

    public void setDescuento(BigDecimal descuento) {
        this.descuento = descuento;
    }

    public boolean getEstPanPagos() {
        return estPanPagos;
    }

    public void setEstPanPagos(boolean estPanPagos) {
        this.estPanPagos = estPanPagos;
    }

    public String getTipoPago() {
        return tipoPago;
    }

    public void setTipoPago(String tipoPago) {
        this.tipoPago = tipoPago;
    }

    public String getTituloPago() {
        return tituloPago;
    }

    public void setTituloPago(String tituloPago) {
        this.tituloPago = tituloPago;
    }

    public String getNCheque() {
        return NCheque;
    }

    public void setNCheque(String NCheque) {
        this.NCheque = NCheque;
    }

    public boolean getEstBanco() {
        return estBanco;
    }

    public void setEstBanco(boolean estBanco) {
        this.estBanco = estBanco;
    }

    public boolean getEstCheue() {
        return estCheue;
    }

    public void setEstCheue(boolean estCheue) {
        this.estCheue = estCheue;
    }

    public List<SelectItem> getSelectGeneral() {
        return selectGeneral;
    }

    public void setSelectGeneral(List<SelectItem> selectGeneral) {
        this.selectGeneral = selectGeneral;
    }

    public BigDecimal getCampoInteres() {
        return campoInteres;
    }

    public void setCampoInteres(BigDecimal campoInteres) {
        this.campoInteres = campoInteres;
    }

    public String getNombreBanco() {
        return nombreBanco;
    }

    public void setNombreBanco(String nombreBanco) {
        this.nombreBanco = nombreBanco;
    }

    public List<SelectItemGroup> getSelectGroupBancos() {
        return selectGroupBancos;
    }

    public void setSelectGroupBancos(List<SelectItemGroup> selectGroupBancos) {
        this.selectGroupBancos = selectGroupBancos;
    }

    public List<Banco> getBancos() {
        return bancos;
    }

    public void setBancos(List<Banco> bancos) {
        this.bancos = bancos;
    }

    public List<Intereses> getIntereses() {
        return intereses;
    }

    public void setIntereses(List<Intereses> intereses) {
        this.intereses = intereses;
    }

    public boolean getEstadoInteres() {
        return estadoInteres;
    }

    public void setEstadoInteres(boolean estadoInteres) {
        this.estadoInteres = estadoInteres;
    }

    public Integer getMesSeleccionado() {
        return mesSeleccionado;
    }

    public void setMesSeleccionado(Integer mesSeleccionado) {
        this.mesSeleccionado = mesSeleccionado;
    }

    public BigDecimal getInteresTarjeta() {
        return interesTarjeta;
    }

    public void setInteresTarjeta(BigDecimal interesTarjeta) {
        this.interesTarjeta = interesTarjeta;
    }

    public BigDecimal getTotalPagar() {
        return totalPagar;
    }

    public void setTotalPagar(BigDecimal totalPagar) {
        this.totalPagar = totalPagar;
    }

    public BigDecimal getDescuentoSeleccionado() {
        return descuentoSeleccionado;
    }

    public void setDescuentoSeleccionado(BigDecimal descuentoSeleccionado) {
        this.descuentoSeleccionado = descuentoSeleccionado;
    }

    public List<String> getDesiciones() {
        return desiciones;
    }

    public void setDesiciones(List<String> desiciones) {
        this.desiciones = desiciones;
    }

    public String getTipoPrecio() {
        return tipoPrecio;
    }

    public void setTipoPrecio(String tipoPrecio) {
        this.tipoPrecio = tipoPrecio;
    }

    public String getCliMayorista() {
        return cliMayorista;
    }

    public void setCliMayorista(String cliMayorista) {
        this.cliMayorista = cliMayorista;
    }

    public boolean getCreditoDirecto() {
        return creditoDirecto;
    }

    public void setCreditoDirecto(boolean creditoDirecto) {
        this.creditoDirecto = creditoDirecto;
    }

    public Date getFechaInicio() {
        return fechaInicio;
    }

    public void setFechaInicio(Date fechaInicio) {
        this.fechaInicio = fechaInicio;
    }

    public Date getFechaFinal() {
        return fechaFinal;
    }

    public void setFechaFinal(Date fechaFinal) {
        this.fechaFinal = fechaFinal;
    }

    public BigDecimal getIvaTotal() {
        return ivaTotal;
    }

    public void setIvaTotal(BigDecimal ivaTotal) {
        this.ivaTotal = ivaTotal;
    }

    public BigDecimal getIvaSubTotal() {
        return ivaSubTotal;
    }

    public void setIvaSubTotal(BigDecimal ivaSubTotal) {
        this.ivaSubTotal = ivaSubTotal;
    }

    public BigDecimal getIvaMostrar() {
        return ivaMostrar;
    }

    public void setIvaMostrar(BigDecimal ivaMostrar) {
        this.ivaMostrar = ivaMostrar;
    }

    //ORDENES TRABAJO
    public List<OrdenTrabajo> getOrdenesTrabajo() {
        return ordenesTrabajo;
    }

    public void setOrdenesTrabajo(List<OrdenTrabajo> ordenesTrabajo) {
        this.ordenesTrabajo = ordenesTrabajo;
    }

    public OrdenTrabajo getOrdenTrabajoSeleccionada() {
        return ordenTrabajoSeleccionada;
    }

    public void setOrdenTrabajoSeleccionada(OrdenTrabajo ordenTrabajoSeleccionada) {
        this.ordenTrabajoSeleccionada = ordenTrabajoSeleccionada;
    }

    public SessionMB getSesion() {
        return sesion;
    }

    public void setSesion(SessionMB sesion) {
        this.sesion = sesion;
    }

    public BigDecimal getDescuentoManual() {
        return descuentoManual;
    }

    public void setDescuentoManual(BigDecimal descuentoManual) {
        this.descuentoManual = descuentoManual;
    }

    public boolean getMostrarDescuentoManual() {
        return mostrarDescuentoManual;
    }

    public void setMostrarDescuentoManual(boolean mostrarDescuentoManual) {
        this.mostrarDescuentoManual = mostrarDescuentoManual;
    }

    public Venta getVentaDiaria() {
        return ventaDiaria;
    }

    public void setVentaDiaria(Venta ventaDiaria) {
        this.ventaDiaria = ventaDiaria;
    }

    public Integer getCantidaServicio() {
        return cantidaServicio;
    }

    public void setCantidaServicio(Integer cantidaServicio) {
        this.cantidaServicio = cantidaServicio;
    }

    public List<Servicios> getServicioLista() {
        return servicioLista;
    }

    public void setServicioLista(List<Servicios> servicioLista) {
        this.servicioLista = servicioLista;
    }

    public Servicios getServicioSeleccionado() {
        return servicioSeleccionado;
    }

    public void setServicioSeleccionado(Servicios servicioSeleccionado) {
        this.servicioSeleccionado = servicioSeleccionado;
    }

    public BigDecimal getPrecioServicio() {
        return precioServicio;
    }

    public void setPrecioServicio(BigDecimal precioServicio) {
        this.precioServicio = precioServicio;
    }

    public List<DetalleVentaOrdenTrabajo> getDetallesOrdenTrabajo() {
        return detallesOrdenTrabajo;
    }

    public void setDetallesOrdenTrabajo(List<DetalleVentaOrdenTrabajo> detallesOrdenTrabajo) {
        this.detallesOrdenTrabajo = detallesOrdenTrabajo;
    }

    public List<DetalleOrdenTrabajo> getDetallesOrdenMostrar() {
        return detallesOrdenMostrar;
    }

    public void setDetallesOrdenMostrar(List<DetalleOrdenTrabajo> detallesOrdenMostrar) {
        this.detallesOrdenMostrar = detallesOrdenMostrar;
    }

    public List<DetalleOrdenTrabajo> getDetallesOrdenSeleccionadas() {
        return detallesOrdenSeleccionadas;
    }

    public void setDetallesOrdenSeleccionadas(List<DetalleOrdenTrabajo> detallesOrdenSeleccionadas) {
        this.detallesOrdenSeleccionadas = detallesOrdenSeleccionadas;
    }

}
