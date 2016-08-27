/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ec.com.codesoft.web.operador;

import ec.com.codesoft.model.AbonoVentaCredito;
import ec.com.codesoft.model.Banco;
import ec.com.codesoft.model.CatalagoProducto;
import ec.com.codesoft.model.Cliente;
import ec.com.codesoft.model.CreditoFactura;
import ec.com.codesoft.model.Creditobanco;
import ec.com.codesoft.model.DetalleOrdenTrabajo;
import ec.com.codesoft.model.DetalleProductoGeneral;
import ec.com.codesoft.model.DetalleProductoIndividual;
import ec.com.codesoft.model.DetalleVentaOrdenTrabajo;
import ec.com.codesoft.model.DetallesServicio;
import ec.com.codesoft.model.Intereses;
import ec.com.codesoft.model.OrdenTrabajo;
import ec.com.codesoft.model.PeriodoContable;
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
import ec.com.codesoft.web.reportes.FacturaDetalleModeloReporte;
import ec.com.codesoft.web.reportes.FacturaModeloReporte;
import ec.com.codesoft.web.reportes.NotaVentaModeloReporte;
import ec.com.codesoft.web.reportes.ProformaModelo;
import ec.com.codesoft.web.seguridad.SessionMB;
import ec.com.codesoft.web.seguridad.SistemaMB;
import ec.com.codesoft.web.util.CorreoMB;
import java.io.IOException;
import java.math.BigDecimal;
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
import javax.ejb.TransactionAttribute;
import javax.ejb.TransactionAttributeType;
import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ManagedProperty;
import javax.faces.bean.ViewScoped;
import javax.faces.context.FacesContext;
import javax.faces.model.SelectItem;
import javax.faces.model.SelectItemGroup;
import javax.validation.constraints.Min;
import net.sf.jasperreports.engine.JRException;
import org.primefaces.context.RequestContext;
import org.primefaces.event.SelectEvent;

/**
 *
 * @author Suco
 */
@ManagedBean
@ViewScoped

public class FacturaMB {

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

    @Min(value = 1)
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
    private String tipoDescuento;

    private Venta ventaImprimir; //venta q se facturara
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
     * Porpiedad para enlazar el numero de factura
     */
    private Integer codigoDocumento;

    /**
     * propiedad para enviar correo
     */
    private boolean estadoCorreo;

    //abono de la factura a credito
    private BigDecimal abono;
    private CreditoFactura creditoFactura;
    private List<CreditoFactura> creditoFacturaObtenidos;
    private List<Venta> ventasTipoPago;
    private boolean mostrarDeudas;

    //imagen para agrandar en la Ayuda
    private String rutaImagenAgrandar;

    //fecha actual
    private Date fechaActual;

    private List<Servicios> servicioLista; //lista de servicios disponibles
    private Servicios servicioSeleccionado;
    private BigDecimal precioServicio;

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

    //correo
    @ManagedProperty(value = "#{sistemaMB}")
    private SistemaMB sistemaMB;

    /**
     * ****************************************************PostConstruct***************************************
     */
    @PostConstruct
    public void inicializar() {
        rutaImagenAgrandar = " ";
        estadoDialogo = false;
        estadoDialogoGeneral = false;
        msjCliente = "";
        clienteEncontrado = new Cliente();
        mostrarPanel = false;
        cantidadComprar = 1;
        mostrarInformacion = false;
        catalogosLista = catalogoServicio.obtenerTodos();
        servicioLista = new ArrayList<>();
        servicioLista = catalogoServicio.obtenerServicios();//obtener los servicios
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
        estCheue = false;
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
        bancos = facturaServicio.devolverBancos();
        nombreBanco = bancos.get(0).getNombre();
        intereses = bancos.get(0).getInteresesList();
        ivaMostrar = sistemaServicio.getConfiguracion().getIva();
        ivaSubTotal = (sistemaServicio.getConfiguracion().getIva()).divide(new BigDecimal("100"));
        ivaTotal = ivaSubTotal.add(new BigDecimal("1"));
        System.out.println(ivaTotal + " -- " + ivaSubTotal);

        descuentoManual = new BigDecimal("0.0");
        tipoDescuento = "porcentaje";
        //numero de items maximo
        maxItemFactura = sistemaServicio.getConfiguracion().getMaxItemFactura();
        maxItemNota = sistemaServicio.getConfiguracion().getMaxItemNota();
        //por defecto esta factura al iniciar la venta
        maxItems = maxItemFactura;

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

//        if (sesion.getPerfilBuscado().getTipo().equals("admin")) {
//            mostrarDescuentoManual = true;
//        } else {
//            mostrarDescuentoManual = false;
//        }
        verificarUsuario();

        //variable correo
        estadoCorreo = true;

        //abono de la factura a credito
        abono = new BigDecimal("0.0");
        creditoFacturaObtenidos = new ArrayList<CreditoFactura>();
        mostrarDeudas = false;

        fechaActual = new Date();

    }

    /**
     * **************************************************PostConstuct**********************************************************************
     */
    //obtener la fecha actual
    public String obtenerFechaActual() {
        Date ahora = new Date();
        SimpleDateFormat formateador = new SimpleDateFormat("yyyy-MM-dd");
        return formateador.format(ahora);
    }

    public String verificarUsuario() {
        //habilitarDEescuento Manual
        //System.out.println("Sesion" + sesion);
        if (sesion.getPerfilBuscado() != null) {
            System.out.println("Si hay login");
            if (sesion.getPerfilBuscado().getTipo().equals("admin")) {
                mostrarDescuentoManual = true;
            } else {
                mostrarDescuentoManual = false;
            }
        } else {
            System.out.println("no hay login");
            return "login";
        }
        return null;
    }

    public void cambiarEstadoCorreo() {
        if (estadoCorreo == true) {
            estadoCorreo = true;
            System.out.println(estadoCorreo);
        } else {
            estadoCorreo = false;
            System.out.println(estadoCorreo);
        }
    }

    public void pruebar() {
        System.out.println("Pruebar");
    }

    public void calcularlInteres() {
        System.err.println("Calcular Interes");
        for (int i = 0; i < bancos.size(); i++) {
            if (bancos.get(i).getNombre().equals(nombreBanco)) {
                for (int j = 0; j < bancos.get(i).getInteresesList().size(); j++) {
                    if (bancos.get(i).getInteresesList().get(j).getMeses() == mesSeleccionado) {

                        estadoInteres = true;
                        campoInteres = bancos.get(i).getInteresesList().get(j).getValor();
                        interesTarjeta = (total.multiply(campoInteres.divide(new BigDecimal("100"))));
                        totalPagar = total.add(interesTarjeta);
                        System.err.println("Total " + total + "  Interes" + interesTarjeta);
                        //interesTarjeta = interesTarjeta.setScale(2, BigDecimal.ROUND_UP);
                        //totalPagar = totalPagar.setScale(2, BigDecimal.ROUND_UP);
                    }
                }
            }
        }
    }

    public void enviarCorreo(Venta venta) {
        System.out.println("En enviar");
        if (clienteEncontrado.getCorreo() == null || clienteEncontrado.equals(venta)) {
            System.out.println("No hay correo");
        } else {
            String cabecera = "";
            String detalles = "";
            System.out.println("enviando correo ...");
            System.out.println(sistemaMB.getConfiguracion());
            CorreoMB correo = new CorreoMB(sistemaMB.getConfiguracion().getEmailServicioTecnico(), sistemaMB.getConfiguracion().getClaveEmailServicioTecnico());

            //detalles de la factura
            detalles = detalles + "<table align='center' style='border: #3f5da2;width: 100%'>";
            detalles = detalles + "<tr>";
            detalles = detalles + "<td style='background-color: #485798;color: #ffffff'>" + "Cantidad" + "</td>";
            detalles = detalles + "<td style='background-color: #485798;color: #ffffff'>" + "Código" + "</td>";
            detalles = detalles + "<td style='background-color: #485798;color: #ffffff'>" + "Descripción" + "</td>";
            detalles = detalles + "<td style='background-color: #485798;color: #ffffff'>" + "P.Unitario" + "</td>";
            detalles = detalles + "<td style='background-color: #485798;color: #ffffff'>" + "Total" + "</td>";
            detalles = detalles + "</tr>";
            for (int i = 0; i < detallesVenta.size(); i++) {
                detalles = detalles + "<tr>";
                detalles = detalles + "<td>" + detallesVenta.get(i).getCantidad() + "</td>";
                detalles = detalles + "<td>" + detallesVenta.get(i).getCodigo() + "</td>";
                detalles = detalles + "<td>" + detallesVenta.get(i).getNombre() + "</td>";
                detalles = detalles + "<td>" + detallesVenta.get(i).getCosto().setScale(2, BigDecimal.ROUND_DOWN) + "</td>";
                detalles = detalles + "<td>" + detallesVenta.get(i).getTotal().setScale(2, BigDecimal.ROUND_DOWN) + "</td>";
                detalles = detalles + "</tr>";
            }
            detalles = detalles + "</table>";

            //cabecera de la Factura
            cabecera = "<div style=\"width: 80%;padding: 10px;border-style: dashed;margin: 0 auto;border-color: #cccccc\">\n"
                    + "	<div style=\"width: 100%;text-align: center\"><h2>" + sistemaMB.getEmpresa().getNombre() + "</h2></div>\n"
                    + "	<div style=\"width: 100%;text-align: center\"><h4>" + sistemaMB.getEmpresa().getEslogan() + "</h4></div>\n"
                    + "	<br/>\n"
                    + "	<table style=\"width: 100%\">\n"
                    + "			<tr>\n"
                    + "				<td style=\"width: 20%;text-align: center\">\n"
                    + "					<img src=\"http://thumbs.subefotos.com/bd228d061d5c2675698d5be2f301129eo.jpg\" width=\"80px\" height=\"80px\">\n"
                    + "				</td>\n"
                    + "				<td style=\"width: 70%\">\n"
                    + "					<table style=\"width: 100%;height: 100%\">\n"
                    + "						<tr>\n"
                    + "							<td><b>" + sistemaMB.getEmpresa().getPropietario() + "</b></td>\n"
                    + "							<td><b>" + sistemaMB.getEmpresa().getRucEmpresa() + "</b></td>\n"
                    + "							<td><b>Factura N.</b></td>\n"
                    + "						</tr>\n"
                    + "						<tr>\n"
                    + "							<td><b>" + sistemaMB.getEmpresa().getDireccion() + "</b></td>\n"
                    + "							<td><b>" + sistemaMB.getEmpresa().getTelefonos() + "</b></td>\n"
                    + "							<td><div style=\"border-style: solid;width: 80px;text-align: center\">" + ventaImprimir.getCodigoDocumento() + "</div></td>\n"
                    + "						</tr>\n"
                    + "\n"
                    + "					</table>\n"
                    + "				</td>\n"
                    + "			</tr>\n"
                    + "		</table>\n"
                    + "		<br/>\n"
                    + "		<div style=\"border-style: solid;width: 100%;\"></div>\n"
                    + "		<br/>\n"
                    + "		 <table style=\"width: 100%\">\n"
                    + "						<tr>\n"
                    + "							<td><b>CI/RUC</b></td>\n"
                    + "							<td>" + clienteEncontrado.getCedulaRuc() + "</td>\n"
                    + "							<td><b>Teléfono.</b></td>\n"
                    + "							<td>" + clienteEncontrado.getTelefono() + "</td>\n"
                    + "							<td><b>Observaciones</b></td>\n"
                    + "							<td><p></p></td>\n"
                    + "						</tr>\n"
                    + "						<tr>\n"
                    + "							<td><b>Nombre</b></td>\n"
                    + "							<td>" + clienteEncontrado.getNombre() + "</td>\n"
                    + "							<td><b>Fecha </b></td>\n"
                    + "							<td>" + obtenerFechaActual() + "</td>\n"
                    + "						</tr>\n"
                    + "\n"
                    + "		  </table>\n"
                    + "		  \n"
                    + "		  <br/>\n"
                    + "		  <div style=\"border-style: solid;width: 100%;text-align: center;background-color: #666666;color: #ffffff\">D E T A L L E S</div>\n"
                    + detalles
                    + "<br/>"
                    + "<div style=\"text-align: right\">"
                    + "<br/> <b>Subtotal: </b>" + subtotal.setScale(2, BigDecimal.ROUND_DOWN)
                    + "<br/> <b>Iva " + ivaMostrar.setScale(0, BigDecimal.ROUND_UP) + " % : </b>" + venta.getIva().setScale(2, BigDecimal.ROUND_UP)
                    + "<br/> <b>Total: </b>" + venta.getTotal().setScale(2, BigDecimal.ROUND_UP)
                    + "</div>"
                    + "<br/>"
                    + "<b>Este correo fué generado por Codefac Sistema de Facturación</b>"
                    + "<div style=\"background-color: #485798;color: #ffffff\">"
                    + "<p><b>Codesoft: Servicios de Mantenimiento, Desarrollo de Software, Consultoría Informática</b></p>"
                    + "<br/>"
                    + "<b>Dirección: </b>" + sistemaMB.getEmpresa().getDireccion() + " - " + sistemaMB.getEmpresa().getTelefonos() + ""
                    + "</div>"
                    + "</div>";
            System.out.println(cabecera);
            correo.EnviarCorreoSinArchivoAdjunto(clienteEncontrado.getCorreo(), "Codesoft", cabecera);
        }
    }

    public void devolverBancoNombre() {

        bancoBuscar = facturaServicio.devolverInteresBanco(nombreBanco);
        intereses = bancoBuscar.getInteresesList();
        campoInteres = new BigDecimal("0.0");
//        System.err.println(nombreBanco);
//        System.out.println(mesSeleccionado);
//        calcularlInteres();

    }

    public void verificarDialogo() {
        System.out.println("VeriE");
        if (estadoDialogo) {
            RequestContext.getCurrentInstance().execute("PF('infProductoE').show()");
        }
    }

    public void cerrarDialogo() {
        RequestContext.getCurrentInstance().execute("PF('infProductoE').hide()");
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

    public void buscarCliente() {

        System.out.println("Buscar");
        clienteEncontrado = clienteServicio.buscarCliente(cedCliente);
        if (clienteEncontrado == null) {
            System.out.println("NNEncontrado");
            msjCliente = "";//cliente no encontrado
            //abrir panel crearCLiente
            RequestContext.getCurrentInstance().execute("PF('confirmarCliente').show()");

        } else {

            System.out.println("Encontrado");
            msjCliente = "Cliente Encontrado";
            System.out.println("ocultando el panel");
            RequestContext.getCurrentInstance().execute("PF('acordionCliente').unselect(0)");

            if (clienteEncontrado.getTipo().equals("Distribuidor")) {
                cliMayorista = " --> Distribuidor";
            } else {
                cliMayorista = "";
            }

            //System.out.println("VentasCredito " + facturaServicio.obtenerVentaTipo(clienteEncontrado.getCedulaRuc(), "Credito"));
            ventasTipoPago = facturaServicio.obtenerVentaTipo(clienteEncontrado.getCedulaRuc(), "Credito");

            if (ventasTipoPago != null) {
                mostrarDeudas = true;
                creditoFacturaObtenidos = new ArrayList<CreditoFactura>();
                for (int i = 0; i < ventasTipoPago.size(); i++) {
                    CreditoFactura creditoTemporal = new CreditoFactura();
                    creditoTemporal = facturaServicio.obtenerCreditoFactura(ventasTipoPago.get(i).getCodigoFactura(), "Proceso");
                    //System.out.println("Credito"+creditoTemporal);
                    if (creditoTemporal != null) {
                        creditoTemporal.setAbonoVentaCreditoList(facturaServicio.obtenerAbonosCredito(creditoTemporal.getCodigoFacturaCredito()));
                        creditoFacturaObtenidos.add(creditoTemporal);
                    }
                }
            } else {
                mostrarDeudas = false;
            }
            if (creditoFacturaObtenidos.size() == 0) {
                mostrarDeudas = false;
            }
        }
    }

    /**
     * Funcion para crear cliente si no existe
     */
    public void crearCliente() {

        RequestContext.getCurrentInstance().execute("PF('confirmarCliente').hide()");
        System.out.println("abriendo nuevo panel...");
        Map<String, Object> options = new HashMap<String, Object>();
        options.put("modal", true);

        Map<String, List<String>> params = new HashMap<String, List<String>>();
        List<String> values = new ArrayList<String>();

        values.add(cedCliente);
        params.put("cedula", values);
        //options.put("width", 640);
        //options.put("height", 340);
        // options.put("contentWidth", "100%");
        // options.put("contentHeight", "100%");
        //options.put("headerElement", "customheader");
        //RequestContext.getCurrentInstance().execute("PF('confirmarDistribuidor').hide()");
        RequestContext.getCurrentInstance().openDialog("crearCliente", options, params);

    }

    public void recibirDatos(SelectEvent event) {
        clienteEncontrado = ((Cliente) event.getObject());
        if (clienteEncontrado.getTipo().equals("Distribuidor")) {
            msjCliente = "Cliente Encontrado";
            cliMayorista = " --> Distribuidor";
        } else {
            cliMayorista = "";
        }
        cedCliente = clienteEncontrado.getCedulaRuc();

        //System.out.println("dato recibido");
        //System.out.println(compra.getRuc());
    }

    /*
     // Aplicar descuentos funcion que aplica descuentos
     */
    public void aplicarDescuentos(DetallesVenta detallesVentaRecibido) {
        System.out.println("Precio " + detallesVentaRecibido.getPrecioSeleccionado() + " -- Escojio" + detallesVentaRecibido.getEscogerDescuento());

        for (int i = 0; i < detallesVenta.size(); i++) {
            if (detallesVenta.get(i).getCodigo().equals(detallesVentaRecibido.getCodigo())) {
                System.out.println("Es igual el producto");
                if (clienteEncontrado.getTipo().equals("Distribuidor")) {
                    if (detallesVentaRecibido.getEscogerDescuento().equals("Si")) {
                        //poner si en los descuentos
                        detallesVenta.get(i).setEscogerDescuento("Si");
                        detallesVenta.get(i).setMostrarDescuentoManual(true);
                        for (int j = 0; j < detallesVenta.get(i).getDescuentos().size(); j++) {
                            if (detallesVenta.get(i).getDescuentos().get(j).getNombre().equals("dctoMayorista")) {
                                //actualizar campos total-total pagar
                                BigDecimal totalDetalleRegistro = new BigDecimal("0.0");
                                totalDetalleRegistro = (detallesVenta.get(i).getValorVerdaderoMayorista().subtract(detallesVenta.get(i).getValorVerdaderoMayorista().multiply(detallesVenta.get(i).getDescuentos().get(j).getValor().divide(new BigDecimal("100")))));//.setScale(2, BigDecimal.ROUND_UP);
                                detallesVenta.get(i).setCosto(totalDetalleRegistro);
                                detallesVenta.get(i).setTotal(totalDetalleRegistro.multiply(new BigDecimal(detallesVenta.get(i).getCantidad())));//.setScale(2, BigDecimal.ROUND_UP));
                                detallesVenta.get(i).setValorDescuento(detallesVenta.get(i).getDescuentos().get(j).getValor());
                                //campoDescuento que carga el valor
                                descuentoManual = detallesVenta.get(i).getDescuentos().get(j).getValor();
                            }
                        }

                    } else {
                        detallesVenta.get(i).setEscogerDescuento("No");
                        detallesVenta.get(i).setMostrarDescuentoManual(false);
                        BigDecimal totalDetalleRegistro = new BigDecimal("0.0");
                        totalDetalleRegistro = (detallesVenta.get(i).getValorVerdaderoMayorista());
                        detallesVenta.get(i).setCosto(totalDetalleRegistro);
                        detallesVenta.get(i).setTotal(totalDetalleRegistro.multiply(new BigDecimal(detallesVenta.get(i).getCantidad())));//.setScale(2, BigDecimal.ROUND_UP));
                        detallesVenta.get(i).setValorDescuento(new BigDecimal("0.0"));
                        descuentoManual = new BigDecimal("0.00");

                    }
                } else {
                    if (detallesVentaRecibido.getEscogerDescuento().equals("Si")) {
                        for (int j = 0; j < detallesVenta.get(i).getDescuentos().size(); j++) {
                            if (detallesVenta.get(i).getDescuentos().get(j).getNombre().equals("dctoPVP")) {
                                //actualizar campos total-total pagar
                                detallesVenta.get(i).setEscogerDescuento("Si");
                                detallesVenta.get(i).setMostrarDescuentoManual(true);
                                BigDecimal totalDetalleRegistro = new BigDecimal("0.0");
                                totalDetalleRegistro = (detallesVenta.get(i).getValorVerdaderoPVP().subtract(detallesVenta.get(i).getValorVerdaderoPVP().multiply(detallesVenta.get(i).getDescuentos().get(j).getValor().divide(new BigDecimal("100")))));//.setScale(2, BigDecimal.ROUND_UP);
                                detallesVenta.get(i).setCosto(totalDetalleRegistro);
                                detallesVenta.get(i).setTotal(totalDetalleRegistro.multiply(new BigDecimal(detallesVenta.get(i).getCantidad())));//.setScale(2, BigDecimal.ROUND_UP));
                                detallesVenta.get(i).setValorDescuento(detallesVenta.get(i).getDescuentos().get(j).getValor());
                                descuentoManual = detallesVenta.get(i).getDescuentos().get(j).getValor();
                            }
                        }

                    } else {
                        detallesVenta.get(i).setEscogerDescuento("No");
                        detallesVenta.get(i).setMostrarDescuentoManual(false);
                        BigDecimal totalDetalleRegistro = new BigDecimal("0.0");
                        totalDetalleRegistro = (detallesVenta.get(i).getValorVerdaderoPVP());
                        detallesVenta.get(i).setCosto(totalDetalleRegistro);
                        detallesVenta.get(i).setTotal(totalDetalleRegistro.multiply(new BigDecimal(detallesVenta.get(i).getCantidad())));//.setScale(2, BigDecimal.ROUND_UP));
                        detallesVenta.get(i).setValorDescuento(new BigDecimal("0.0"));
                    }
                }
            }
        }

        total = new BigDecimal("0.0");
        totalRegistro = new BigDecimal("0.0");
        subtotal = new BigDecimal("0.0");
        for (int k = 0; k < detallesVenta.size(); k++) {
            System.out.println("Detalle " + k + detallesVenta.get(k).getTotal());
            //System.err.println("totalDetalle: "+detallesVenta.get(k).getTotal());
            totalRegistro = detallesVenta.get(k).getTotal();
            subtotalRegistro = totalRegistro.multiply(ivaTotal);
            subtotal = subtotal.add(totalRegistro);
            if (tipoCliente.equals("C")) {
//                iva = new BigDecimal("0.0");
//                //iva = iva.setScale(2, BigDecimal.ROUND_UP);
//                //BigDecimal subTotalTemp=subtotal.multiply(descuento.divide(new BigDecimal(100).add(new BigDecimal(1))));
//                System.out.println("total: " + total);
//                total = subtotal;
//                // total = total.setScale(2, BigDecimal.ROUND_UP);
//                totalPagar = total;

                iva = subtotal.multiply(ivaSubTotal);
                total = subtotal.multiply(ivaTotal);
                totalPagar = total;
            } else {
                System.out.println("Else: " + total);
                iva = subtotal.multiply(ivaSubTotal);
                // iva = iva.setScale(2, BigDecimal.ROUND_UP);
                total = subtotal.multiply(ivaTotal);
                //  total = total.setScale(2, BigDecimal.ROUND_UP);
                totalPagar = total;

            }
        }

    }

    /*
     // Aplicar descuentos funcion que aplica descuentos Manualmente
     */
    public void aplicarDescuentosManual(DetallesVenta detallesVentaRecibido) {
        System.out.println("Descuento Manual --- Precio " + detallesVentaRecibido.getPrecioSeleccionado() + " -- Escojio" + detallesVentaRecibido.getEscogerDescuento());
        if (descuentoManual != null) {
            if (descuentoManual.equals("")) {
                descuentoManual = new BigDecimal("0.00");
            }
        } else {
            descuentoManual = new BigDecimal("0.00");
        }
        for (int i = 0; i < detallesVenta.size(); i++) {
            if (detallesVenta.get(i).getCodigo().equals(detallesVentaRecibido.getCodigo())) {
                System.out.println("Es igual el producto");
                if (clienteEncontrado.getTipo().equals("Distribuidor")) {
                    BigDecimal totalDetalleRegistro = new BigDecimal("0.0");
                    totalDetalleRegistro = (detallesVenta.get(i).getValorVerdaderoMayorista().subtract(detallesVenta.get(i).getValorVerdaderoMayorista().multiply(detallesVenta.get(i).getValorDescuento().divide(new BigDecimal("100")))));//.setScale(2, BigDecimal.ROUND_UP);
                    detallesVenta.get(i).setCosto(totalDetalleRegistro);
                    detallesVenta.get(i).setTotal(totalDetalleRegistro.multiply(new BigDecimal(detallesVenta.get(i).getCantidad())));//.setScale(2, BigDecimal.ROUND_UP));
                    detallesVenta.get(i).setValorDescuento(detallesVenta.get(i).getValorDescuento());
                    //campoDescuento que carga el valor

                } else {
                    BigDecimal totalDetalleRegistro = new BigDecimal("0.0");
                    totalDetalleRegistro = (detallesVenta.get(i).getValorVerdaderoPVP().subtract(detallesVenta.get(i).getValorVerdaderoPVP().multiply(detallesVenta.get(i).getValorDescuento().divide(new BigDecimal("100")))));//.setScale(2, BigDecimal.ROUND_UP);
                    detallesVenta.get(i).setCosto(totalDetalleRegistro);
                    detallesVenta.get(i).setTotal(totalDetalleRegistro.multiply(new BigDecimal(detallesVenta.get(i).getCantidad())));//.setScale(2, BigDecimal.ROUND_UP));
                    detallesVenta.get(i).setValorDescuento(detallesVenta.get(i).getValorDescuento());
                }
            }
        }

        total = new BigDecimal("0.0");
        totalRegistro = new BigDecimal("0.0");
        subtotal = new BigDecimal("0.0");
        for (int k = 0; k < detallesVenta.size(); k++) {
            System.out.println("Detalle " + k + detallesVenta.get(k).getTotal());
            //System.err.println("totalDetalle: "+detallesVenta.get(k).getTotal());
            totalRegistro = detallesVenta.get(k).getTotal();
            subtotalRegistro = totalRegistro.multiply(ivaTotal);
            subtotal = subtotal.add(totalRegistro);
            if (tipoCliente.equals("C")) {
//                iva = new BigDecimal("0.0");
//                System.out.println("total: " + total);
//                total = subtotal;
//                totalPagar = total;
                iva = subtotal.multiply(ivaSubTotal);
                // iva = iva.setScale(2, BigDecimal.ROUND_UP);
                total = subtotal.multiply(ivaTotal);
                //  total = total.setScale(2, BigDecimal.ROUND_UP);
                totalPagar = total;
            } else {
                System.out.println("Else: " + total);
                iva = subtotal.multiply(ivaSubTotal);
                // iva = iva.setScale(2, BigDecimal.ROUND_UP);
                total = subtotal.multiply(ivaTotal);
                //  total = total.setScale(2, BigDecimal.ROUND_UP);
                totalPagar = total;

            }
        }

    }

    public void cargarDetalles() {

        total = new BigDecimal("0.0");
        totalRegistro = new BigDecimal("0.0");
        subtotal = new BigDecimal("0.0");
        for (int k = 0; k < detallesVenta.size(); k++) {
            System.out.println("Detalle " + k + detallesVenta.get(k).getTotal());
            //System.err.println("totalDetalle: "+detallesVenta.get(k).getTotal());
            totalRegistro = detallesVenta.get(k).getTotal();
            subtotalRegistro = totalRegistro.multiply(ivaTotal);
            subtotal = subtotal.add(totalRegistro);
            if (tipoCliente.equals("C")) {
                iva = new BigDecimal("0.0");
                //iva = iva.setScale(2, BigDecimal.ROUND_UP);
                //BigDecimal subTotalTemp=subtotal.multiply(descuento.divide(new BigDecimal(100).add(new BigDecimal(1))));
                System.out.println("total: " + total);
                total = subtotal;
                // total = total.setScale(2, BigDecimal.ROUND_UP);
                totalPagar = total;
            } else {
                System.out.println("Else: " + total);
                iva = subtotal.multiply(ivaSubTotal);
                // iva = iva.setScale(2, BigDecimal.ROUND_UP);
                total = subtotal.multiply(ivaTotal);
                //  total = total.setScale(2, BigDecimal.ROUND_UP);
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
                //mostrarPanel = false;

            }
            // msjDistri = "Encontrado";
            // mostrarPanel = true;
        }
    }

    public void mostrarDialogoDetallesOrden(SelectEvent event) {

        detallesOrdenMostrar = new ArrayList<>();
        System.out.println("ordenesCargar" + ordenTrabajoSeleccionada.getDetalleOrdenTrabajoList());
        for (int i = 0; i < ordenTrabajoSeleccionada.getDetalleOrdenTrabajoList().size(); i++) {
            if (ordenTrabajoSeleccionada.getDetalleOrdenTrabajoList().get(i).getEstado().equals("reparado") || ordenTrabajoSeleccionada.getDetalleOrdenTrabajoList().get(i).getEstado().equals("devolver") || ordenTrabajoSeleccionada.getDetalleOrdenTrabajoList().get(i).getEstado().equals("facturado")) {
                System.out.println("Entro a FIltrar " + ordenTrabajoSeleccionada.getDetalleOrdenTrabajoList().get(i).devolverDetalles());

                detallesOrdenMostrar.add(ordenTrabajoSeleccionada.getDetalleOrdenTrabajoList().get(i));
            }

        }

        RequestContext.getCurrentInstance().execute("PF('dlgDetallesOrden').show()");

    }

    public void onRowSelectOrden() {
        //detallesOrden=detallesOrdenSeleccionadas;
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
        RequestContext.getCurrentInstance().execute("PF('dlgDetallesOrden').hide()");

    }

    public void onRowUnSelectOrden(SelectEvent event) {
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
            }
            // msjDistri = "Encontrado";
            // mostrarPanel = true;
        }
        //catalogoSeleccionado = new CatalagoProducto();

    }

    public void escojerTipoCLiente() {
        System.out.println(tipoCliente);
        if (tipoCliente.equals("F")) {
            //obtiene el ultimo codigo de la factura
            codigoDocumento = facturaServicio.getCodigoFactura("Factura");
            clienteEncontrado.setNombre("");
            clienteEncontrado.setTipo("");
            cedCliente = "";
            maxItems = maxItemFactura;

//            System.out.println(tipoCliente);
//            todoPanel = true;
//            cedCliente = "";
//            clienteEncontrado.setNombre("");
        } else {
            //obtiene el ultimo codigo de las notas
            codigoDocumento = facturaServicio.getCodigoFactura("Nota");
            maxItems = maxItemNota;

            System.out.println("CF" + tipoCliente);
            clienteEncontrado.setNombre("Consumidor Final");
            clienteEncontrado.setTipo("PVP");
            cedCliente = "9999999999";
            ///clienteEncontrado = clienteServicio.buscarCliente(cedCliente);

            clienteEncontrado.setCedulaRuc("9999999999");
            todoPanel = true;
        }
    }

    public void prueba() {
        System.out.println("Ejecutando Prueba Radio");
    }

    public void onRowSelectCliente(SelectEvent event) {
        System.out.println("En sleccion");
        //clienteEncontrado = clienteSeleccionado;

        //ocultar el panel hacia arriba
        RequestContext.getCurrentInstance().execute("PF('acordionCliente').unselect(0)");
        clienteEncontrado = (Cliente) event.getObject();
        cedCliente = clienteEncontrado.getCedulaRuc();
        System.out.println("Encontrado");
        msjCliente = "Cliente Encontrado";
        if (clienteEncontrado.getTipo().equals("Distribuidor")) {
            cliMayorista = " --> Distribuidor";
        } else {
            cliMayorista = "";
        }
        ventasTipoPago = facturaServicio.obtenerVentaTipo(clienteEncontrado.getCedulaRuc(), "Credito");
        if (ventasTipoPago != null) {
            mostrarDeudas = true;
            creditoFacturaObtenidos = new ArrayList<CreditoFactura>();
            for (int i = 0; i < ventasTipoPago.size(); i++) {
                CreditoFactura creditoTemporal = new CreditoFactura();
                creditoTemporal = facturaServicio.obtenerCreditoFactura(ventasTipoPago.get(i).getCodigoFactura(), "Proceso");
                //System.out.println("Credito"+creditoTemporal);
                if (creditoTemporal != null) {
                    creditoFacturaObtenidos.add(creditoTemporal);
                }
            }
        } else {
            mostrarDeudas = false;
        }
        if (creditoFacturaObtenidos.size() == 0) {
            mostrarDeudas = false;
        }

        System.out.println(clienteEncontrado);
        RequestContext.getCurrentInstance().execute("PF('overLayBuscarCliente').hide()");
        System.out.println("ocultado panel");

        //clientesLista = clienteServicio.obtenerTodos();
    }

    public void onRowUnSelectCliente(SelectEvent event) {
        System.out.println("deseleccionando ...");
    }

    /**
     * Agrega el detalla a la proforma
     */
    public void agregarDetalleProforma() {
        System.out.println(cantidadComprar + "--" + stock);

        cerrarDialogoG();
        msjCodUnico = "";
        msjStock = "";
        //msjStock = "";
        System.out.println("Si hay stock");
        if ((catalogoSeleccionado.getTipoProducto()) == 'G' || (catalogoSeleccionado.getTipoProducto()) == 'g') {

            System.out.println("En venta");

            totalRegistro = catalogoSeleccionado.getPrecio().multiply(new BigDecimal(cantidadComprar));
            subtotalRegistro = totalRegistro.multiply(ivaTotal);
            subtotal = subtotal.add(totalRegistro);
            if (tipoCliente.equals("C")) {
//                iva = new BigDecimal("0.0");
//                //iva = iva.setScale(2, BigDecimal.ROUND_UP);
//                total = subtotal;
                // total = total.setScale(2, BigDecimal.ROUND_UP);
                iva = subtotal.multiply(ivaSubTotal);
                total = subtotal.multiply(ivaTotal);
                // totalPagar = total;
            } else {
                iva = subtotal.multiply(ivaSubTotal);
                // iva = iva.setScale(2, BigDecimal.ROUND_UP);
                total = subtotal.multiply(ivaTotal);
                // total = total.setScale(2, BigDecimal.ROUND_UP);
            }

            DetallesVenta detalles = new DetallesVenta(cantidadComprar,
                    productoGeneral.getCodigo() + "", catalogoSeleccionado.getNombre(),
                    catalogoSeleccionado.getPrecio(), totalRegistro);
            detallesVenta.add(detalles);

            DetalleProductoGeneral detalle = new DetalleProductoGeneral();
            detalle.setCantidad(cantidadComprar);
            detalle.setCodigoProducto(catalogoSeleccionado);
            detalle.setSubtotal(subtotalRegistro);
            detalle.setCodigoDetallGeneral(0);
            detallesGeneralVenta.add(detalle);

        } else if ((catalogoSeleccionado.getTipoProducto()) == 'E' || (catalogoSeleccionado.getTipoProducto()) == 'e') {
            System.out.println("Especifico");
            detalleIndividual = facturaServicio.devolverIndividualCod(codPEspe, catalogoSeleccionado.getCodigoProducto());
            // System.err.println(detalleIndividual);
            if (detalleIndividual == null) {
                msjCodUnico = "No existe Producto con ese código";
                System.err.println("No existe ese Codigo Especifico");
            } else {
                cerrarDialogo();
                msjCodUnico = "";
                //cerrarDialogo();

                //productosIndividualesDetalles = facturaServicio.obtenerProductoIndivudualCantidad(1, catalogoSeleccionado.getCodigoProducto());
                for (int i = 0; i < cantidadComprar; i++) {
                    totalRegistro = detalleIndividual.getCosto().multiply(new BigDecimal(cantidadComprar));
                    subtotalRegistro = totalRegistro.multiply(ivaTotal);
                    subtotal = subtotal.add(totalRegistro);
                    if (tipoCliente.equals("C")) {
//                        iva = new BigDecimal("0.0");
//                        // iva = iva.setScale(2, BigDecimal.ROUND_UP);
//                        total = subtotal;
//                        // total = total.setScale(2, BigDecimal.ROUND_UP);
                        iva = subtotal.multiply(ivaSubTotal);
                        total = subtotal.multiply(ivaTotal);
                        totalPagar = total;
                    } else {
                        iva = subtotal.multiply(ivaSubTotal);
                        //  iva = iva.setScale(2, BigDecimal.ROUND_UP);
                        total = subtotal.multiply(ivaTotal);
                        // total = total.setScale(2, BigDecimal.ROUND_UP);
                    }

                    DetallesVenta detalles = new DetallesVenta(cantidadComprar, productosIndividualesDetalles.getCodigoUnico(),
                            detalleIndividual.getCodigoProducto().getNombre(),
                            detalleIndividual.getCosto(), totalRegistro);
                    detallesVenta.add(detalles);
                    catalogoSeleccionado = new CatalagoProducto();
                    DetalleProductoIndividual detalle = new DetalleProductoIndividual();
                    detalle.setProductoIndividualCompra(detalleIndividual);
                    detalle.setSubtotal(subtotalRegistro);
                    detallesIndividualVenta.add(detalle);
                    //det

                }

            }
        } else {
            System.out.println("Servicio");
        }

    }

    public void venta() {
        System.out.println(cantidadComprar + "--" + stock);
        if (cantidadComprar > stock) {
            //msjStock = "No existe suficiente Stock";
            FacesMessage message = new FacesMessage(FacesMessage.SEVERITY_INFO, "Error...!", "No existe suficiente Stock..!");
            RequestContext.getCurrentInstance().showMessageInDialog(message);
            //     System.out.println("No hay stock");

        } else {
            //System.out.println("tamanio "+ detallesVenta.size());
            if (detallesVenta.size() > maxItems - 1) {
//            cerrarDialogo();
//            cerrarDialogoG();
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
                    msjCodUnico = "";
                    msjStock = "";
                    //msjStock = "";
                    System.out.println("Si hay stock");
                    System.out.println("TipoProducto " + catalogoSeleccionado.getTipoProducto());
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
                        if (tipoCliente.equals("C")) {
//                        iva = new BigDecimal("0.0");
//                        total = subtotal;
//                        totalPagar = total;
                            //BigDecimal subTotalTemp=subtotal.multiply(descuento.divide(new BigDecimal(100).add(new BigDecimal(1))));
                            iva = subtotal.multiply(ivaSubTotal);
                            // iva = iva.setScale(2, BigDecimal.ROUND_UP);
                            total = subtotal.multiply(ivaTotal);
                            //total = total.setScale(2, BigDecimal.ROUND_UP);
                            totalPagar = total;
                        } else {
                            //BigDecimal subTotalTemp=subtotal.multiply(descuento.divide(new BigDecimal(100).add(new BigDecimal(1))));
                            iva = subtotal.multiply(ivaSubTotal);
                            // iva = iva.setScale(2, BigDecimal.ROUND_UP);
                            total = subtotal.multiply(ivaTotal);
                            //total = total.setScale(2, BigDecimal.ROUND_UP);
                            totalPagar = total;

                        }
                        System.out.println("Codigo General " + productoGeneral.getCatalagoProducto().getCodigoProducto());
                        DetallesVenta detalles = new DetallesVenta(cantidadComprar,
                                productoGeneral.getCatalagoProducto().getCodigoProducto() + "", catalogoSeleccionado.getNombre(),
                                new BigDecimal("0.0"), totalRegistro);
                        if (clienteEncontrado.getTipo().equals("Distribuidor")) {
                            detalles.setCosto(catalogoSeleccionado.getPrecioMayorista());
                        } else {
                            detalles.setCosto(catalogoSeleccionado.getPrecio());
                        }

                        Descuentos precioMayorista = new Descuentos("Prec Mayorista", catalogoSeleccionado.getPrecioMayorista());
                        Descuentos precioDescuento = new Descuentos("PVP", catalogoSeleccionado.getPrecio());
                        Descuentos dcto = new Descuentos("dctoPVP", catalogoSeleccionado.getDescuento());
                        System.out.println(catalogoSeleccionado.getDescuento());
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

                        DetalleProductoGeneral detalle = new DetalleProductoGeneral();
                        detalle.setCantidad(cantidadComprar);
                        detalle.setCodigoProducto(catalogoSeleccionado);
                        detalle.setSubtotal(subtotalRegistro);
                        detalle.setCodigoDetallGeneral(0);

                        detalle.setPrecioIndividual(detalles.getCosto());

                        detallesGeneralVenta.add(detalle); //guardo los detalles 

                    } else if ((catalogoSeleccionado.getTipoProducto()) == 'E' || (catalogoSeleccionado.getTipoProducto()) == 'e') {
                        System.out.println("Especifico");
                        detalleIndividual = facturaServicio.devolverIndividualCod(codPEspe, catalogoSeleccionado.getCodigoProducto());
                        //System.err.println(detalleIndividual);
                        //System.out.println("Estado PRod" + detalleIndividual.getEstadoProceso());
                        if (detalleIndividual == null) {
                            //msjCodUnico = "No existe Producto con ese código";
                            FacesMessage message = new FacesMessage(FacesMessage.SEVERITY_INFO, "Error...!", "No existe Producto con ese código!");
                            RequestContext.getCurrentInstance().showMessageInDialog(message);
                            codPEspe = "";
                            // RequestContext.getCurrentInstance().execute("PF('infProductoE').show()");
                            System.out.println("No existe ese codigo");

                        } else if (detalleIndividual.getEstadoProceso().equals("Vendido")) {
                            //msjCodUnico = "Producto con ese código  ya vendido";
                            FacesMessage message = new FacesMessage(FacesMessage.SEVERITY_INFO, "Error...!", "Producto con ese código  ya vendido!");
                            RequestContext.getCurrentInstance().showMessageInDialog(message);
                            codPEspe = "";
                            //RequestContext.getCurrentInstance().execute("PF('infProductoE').show()");
                            System.out.println("Ya se vendio");
                        } else {
                            System.out.println("Else");
                            cantidadComprar = 1;
                            //cerrarDialogo();
                            msjCodUnico = "";

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
                            detallesIndividualVenta.add(detalle);
                            System.out.println("Termino");
                            // RequestContext.getCurrentInstance().execute("PF('dlgInformacionE').hide()");
                            //det

                        }
                    } else {

                        System.out.println("Servicio");
                    }

                }
            }
        }
        cantidadComprar = 1;
        codPEspe = "";
        // }
    }

    @TransactionAttribute(TransactionAttributeType.REQUIRED)
    public void facturar() {
        System.out.println("facturando");

        if (detallesVenta.size() < 1) {
            FacesMessage message = new FacesMessage(FacesMessage.SEVERITY_INFO, "Error...!", "No existen detalles que se puedan facturar..!");
            RequestContext.getCurrentInstance().showMessageInDialog(message);
        } else {

            if (clienteEncontrado.getCedulaRuc() == null || clienteEncontrado.getCedulaRuc() == "") {
                //if(clienteEncontrado.get)
                FacesMessage msg = new FacesMessage("Agregue Cliente");
                FacesContext.getCurrentInstance().addMessage(null, msg);
                // return null;
            } else {

                if (detallesVenta == null) {
                    FacesMessage msg = new FacesMessage("Agregue Ventas");
                    FacesContext.getCurrentInstance().addMessage(null, msg);
                } else {
                    Venta venta = new Venta();
                    venta.setCedulaRuc(clienteEncontrado);
                    PeriodoContable periodo = new PeriodoContable();
                    periodo = compraServicio.buscar();
                    venta.setCodigoPerido(periodo);
                    //venta.setDetalleProductoGeneralList(detallesGeneralVenta);
                    //venta.setDetalleProductoIndividualList(detallesIndividualVenta);
                    //venta.setDetallesServicioList(detallesServicio);
                    if (tipoCliente.equals("C")) {
                        venta.setTipoDocumento("Nota");
                    } else {
                        venta.setTipoDocumento("Factura");
                    }
                    venta.setEstado("facturado");
                    venta.setFecha(fechaActual);
                    venta.setTotal(total.setScale(2, BigDecimal.ROUND_UP));
                    venta.setDescuento(descuento);
                    venta.setCodigoDocumento(codigoDocumento);
                    venta.setTipoPago(devolverTipoPago());

                    if (devolverTipoPago().equals("Cheque")) {
                        System.out.println("banco " + nombreBanco + " Cheque" + NCheque);
                        venta.setBanco(nombreBanco);
                        venta.setCheque(NCheque);
                    }

                    venta.setDescuento(descuento);// descuento general
                    venta.setIva(iva.setScale(2, BigDecimal.ROUND_UP)); //guarda el iva de la venta
                    facturaServicio.guardarFactura(venta);
                    //guardarfactura Carlos reportes
                    venta.setDetalleProductoGeneralList(detallesGeneralVenta);
                    venta.setDetalleProductoIndividualList(detallesIndividualVenta);

                    // guardar Creditos Directos
                    if (devolverTipoPago().equals("Credito")) {
                        System.out.println("Credito Directo" + creditoFactura.toString());
                        creditoFactura.setCodigoFacturaCredito(0);
                        //creditoFactura.setAbonoVentaCreditoList(abonos);
                        creditoFactura.setFechaInicio(new Date());
                        creditoFactura.setCalificacion(0);
                        creditoFactura.setCodigoFactura(venta);
                        creditoFactura.setDiaPago(0);
                        creditoFactura.setEstado("Proceso");
                        facturaServicio.guardarCreditoFactura(creditoFactura);

                        //guardar abono inicial
                        List<AbonoVentaCredito> abonos = new ArrayList<AbonoVentaCredito>();
                        AbonoVentaCredito abonoGuardar = new AbonoVentaCredito();
                        abonoGuardar.setCodigoAbono(0);
                        System.out.println("Abono " + abono);
                        abonoGuardar.setCantidad(abono);
                        abonoGuardar.setCodigoFacturaCredito(creditoFactura);
                        abonoGuardar.setFecha(new Date());
                        abonoGuardar.setDescripcion("");
                        facturaServicio.guardarAbonos(abonoGuardar);
                        abonos.add(abonoGuardar);
                        creditoFactura.setAbonoVentaCreditoList(abonos);

                    }

                    codigoFactura = venta.getCodigoFactura();

                    //guardar banco
                    if (estBanco) {
                        for (int i = 0; i < bancos.size(); i++) {
                            if (bancos.get(i).getNombre().equals(nombreBanco)) {
                                for (int j = 0; j < bancos.get(i).getInteresesList().size(); j++) {
                                    if (bancos.get(i).getInteresesList().get(j).getMeses() == mesSeleccionado) {
                                        creditoBanco = new Creditobanco();
                                        creditoBanco.setCodigoFactura(venta);
                                        creditoBanco.setCodinteres(bancos.get(i).getInteresesList().get(j));
                                        bancoServicio.guardarCreditoBanco(creditoBanco);
                                    }
                                }
                            }
                        }
                    }

                    //guardar los detalles de las facturas
                    if (detallesIndividualVenta != null) {
                        for (int i = 0; i < detallesIndividualVenta.size(); i++) {

                            detallesIndividualVenta.get(i).setCodigoFactura(venta);
                        }
                        for (int i = 0; i < detallesVenta.size(); i++) {
                            //System.out.println(detallesVenta.get(i).getCodigo() + " -- " + detallesIndividualVenta.get(i).getCodigoUnico());
                            for (int j = 0; j < detallesIndividualVenta.size(); j++) {
                                if (detallesVenta.get(i).getCodigo().equals(detallesIndividualVenta.get(j).getProductoIndividualCompra().getCodigoUnico())) {
                                    detallesIndividualVenta.get(j).setDescuento(detallesVenta.get(i).getValorDescuento());
                                    detallesIndividualVenta.get(j).setSubtotal(detallesVenta.get(i).getTotal());
                                }
                            }
                        }
                        facturaServicio.insertarDetalleProductoIndividual(detallesIndividualVenta);
                        for (int i = 0; i < detallesIndividualVenta.size(); i++) {
                            prodIndividual = new ProductoIndividualCompra();
                            prodIndividual = facturaServicio.devolverProductoIndividual(detallesIndividualVenta.get(i).getProductoIndividualCompra().getCodigoUnico());
                            prodIndividual.setEstadoProceso("Vendido");
                            facturaServicio.actulizarStocIndividual(prodIndividual);

                        }

                    }

                    if (detallesGeneralVenta != null) {
                        for (int j = 0; j < detallesGeneralVenta.size(); j++) {
                            detallesGeneralVenta.get(j).setCodigoFactura(venta);
                        }
                        for (int i = 0; i < detallesVenta.size(); i++) {
                            for (int j = 0; j < detallesGeneralVenta.size(); j++) {
                                // System.out.println(detallesVenta.get(i).getCodigo() + " -- " + detallesGeneralVenta.get(j).getCodigoProducto().getCodigoProducto());
                                if (detallesVenta.get(i).getCodigo().equals(detallesGeneralVenta.get(j).getCodigoProducto().getCodigoProducto())) {
                                    //System.out.println("DetallesGVenta" + detallesVenta.get(i).getValorDescuento());
                                    detallesGeneralVenta.get(j).setDescuento(detallesVenta.get(i).getValorDescuento());
                                    detallesGeneralVenta.get(j).setSubtotal(detallesVenta.get(i).getTotal());
                                }
                            }
                        }
                        facturaServicio.insertarDetallesFacturaProductoGeneral(detallesGeneralVenta);
                        for (int j = 0; j < detallesGeneralVenta.size(); j++) {
                            prodGeneral = new ProductoGeneralVenta();
                            prodGeneral = facturaServicio.devolverStockGeneral(detallesGeneralVenta.get(j).getCodigoProducto().getCodigoProducto());
                            Integer cantidadStock = 0;
                            cantidadStock = prodGeneral.getCantidadDisponible() - detallesGeneralVenta.get(j).getCantidad();
                            prodGeneral.setCantidadDisponible(cantidadStock);
                            facturaServicio.actulizarStockGeneral(prodGeneral);
                        }
                    }

                    //insertarDetalles orden Trabajo
                    int numOrdenes = 0;
                    for (int i = 0; i < detallesCambiarEstado.size(); i++) {
                        // if (detallesVenta.get(i).getTipoDetalle().equals("Orden Trabajo")) {

                        DetalleVentaOrdenTrabajo detalleOrdenTrabajo = new DetalleVentaOrdenTrabajo();
                        detalleOrdenTrabajo.setCodigoFactura(venta);
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

//                   
                    //System.out.println("detalleG: "+detallesGeneralVenta);
                    //System.out.println("detalleI: "+detallesIndividualVenta);
                    System.out.println("Facturado");
                    ///FacesMessage msg = new FacesMessage("Factura Completa");
                    //FacesContext.getCurrentInstance().addMessage(null, msg);
                    // return "factura";

                    //dlgImprimir
                    ventaImprimir = venta;
                    System.out.println("Correo" + estadoCorreo);
                    if (estadoCorreo) {
                        enviarCorreo(ventaImprimir);
                    }

                    RequestContext.getCurrentInstance().execute("PF('dlgImprimir').show()");

                }

            }

        }

        //return null;
    }

    /**
     * Cambiar estado a los detallesOrdenTrabajo
     */
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

    public void onRowUnSelectService(SelectEvent event) {

    }

    public void onRowSelectService(SelectEvent event) {
        //infEscojerServicioV
        precioServicio = new BigDecimal("0.0");
        RequestContext.getCurrentInstance().execute("PF('infEscojerServicioF').show()");
    }

    public void imprimirFactura() {
        if (tipoCliente.equals("C")) {

            NotaVentaModeloReporte notaVenta = new NotaVentaModeloReporte(sistemaServicio.getConfiguracion().getPathreportes());
            notaVenta.setDireccion(clienteEncontrado.getDireccion());
            SimpleDateFormat sdf = new SimpleDateFormat("yyyy/MM/dd");
            notaVenta.setFechaFactura(sdf.format(ventaImprimir.getFecha()));
            notaVenta.setFechaaFactura(sdf.format(ventaImprimir.getFecha()));
            notaVenta.setFormaPago(devolverTipoPago());
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

        } else {
            FacturaModeloReporte facturaReporte = new FacturaModeloReporte(sistemaServicio.getConfiguracion().getPathreportes());
            facturaReporte.setCodigoFactura(ventaImprimir.getCodigoFactura() + "");
            facturaReporte.setDireccion(clienteEncontrado.getDireccion());
            SimpleDateFormat sdf = new SimpleDateFormat("yyyy/MM/dd");
            facturaReporte.setTelefono(clienteEncontrado.getTelefono());

            facturaReporte.setRuc(cedCliente);
            facturaReporte.setFechaaFactura(sdf.format(ventaImprimir.getFecha()));
            facturaReporte.setFormaPago(devolverTipoPago());
            facturaReporte.setIvaTotal(ventaImprimir.getIva());
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
                detallesFactura.setDescuento(detalle.getValorDescuento().toString());
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
        }
        RequestContext.getCurrentInstance().execute("PF('confirmarFactura').hide()");
    }

    public String devolverTipoPago() {
        if (estBanco) {
            return "Credito Banco";
        } else if (estCheue) {
            return "Cheque";
        } else if (creditoDirecto) {
            return "Credito";
        } else {
            return "Efectivo";
        }
    }

    /**
     * Metodo que me permite generar la proforma
     */
    public void proformar() {
        System.out.println("Proformando ...");

        if (detallesVenta == null) {
            FacesMessage msg = new FacesMessage("Agregue Ventas");
            FacesContext.getCurrentInstance().addMessage(null, msg);
        } else {
            Venta venta = new Venta();
            venta.setCedulaRuc(clienteEncontrado);

            venta.setTipoDocumento("Proforma");

            venta.setEstado("Proformando");
            venta.setFecha(new Date());
            venta.setTotal(total);

            if (detallesIndividualVenta != null) {
                for (int i = 0; i < detallesIndividualVenta.size(); i++) {
                    detallesIndividualVenta.get(i).setCodigoFactura(venta);
                }

            }

            if (detallesGeneralVenta != null) {
                for (int j = 0; j < detallesGeneralVenta.size(); j++) {
                    detallesGeneralVenta.get(j).setCodigoFactura(venta);
                }
            }

            FacesMessage msg = new FacesMessage("Proforma Completa ...");
            FacesContext.getCurrentInstance().addMessage(null, msg);
            // return "factura";

            ProformaModelo facturaReporte = new ProformaModelo(sistemaServicio.getConfiguracion().getPathreportes());
            facturaReporte.setCodigoFactura(venta.getCodigoFactura() + "");
            facturaReporte.setDireccion(clienteEncontrado.getDireccion());
            SimpleDateFormat sdf = new SimpleDateFormat("yyyy/MM/dd");
            facturaReporte.setTelefono(clienteEncontrado.getTelefono());

            facturaReporte.setRuc(cedCliente);
            facturaReporte.setFechaaFactura(sdf.format(venta.getFecha()));
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

            RequestContext.getCurrentInstance().execute("PF('confirmarFactura').hide()");
        }

    }

    public String cancelar() {
        System.out.println("Cancelando");
        return "factura";
    }

    public void darVuelto() {
        System.out.println(recibo);
        vuelto = new BigDecimal("0.0");
        System.err.println("Vuelto");
        vuelto = recibo.subtract(total);
        System.err.println(vuelto);
    }

    public void eliminarDetalle(DetallesVenta detalleVentaEliminar) {

        detallesVenta.remove(detalleVentaEliminar);
        subtotal = subtotal.subtract(detalleVentaEliminar.getTotal());
        iva = subtotal.multiply(ivaSubTotal);
        //iva = iva.setScale(2, BigDecimal.ROUND_UP);
        total = subtotal.multiply(ivaTotal);
        totalPagar = total;
        cargarDetalles();
        //total = total.setScale(2, BigDecimal.ROUND_UP);

    }

    public void ventaGeneral() {
        cerrarDialogo();
    }

    public void onRowUnSelect(SelectEvent event) {

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

    public void calcularDescuento() {

        if (tipoDescuento.equals("porcentaje")) {
            if (descuento.equals(0)) {
                cargarDetalles();
            } else {
                cargarDetalles();
                devolverDescuento(descuento);
            }
        } else {
            System.out.println("totalPagar" + descuento);

            if (descuento.equals(0)) {
                System.out.println("Cargar Detalles");
                cargarDetalles();
            } else {
                cargarDetalles();
                totalPagar = totalPagar.subtract(descuento);
                subtotal = totalPagar.divide(ivaTotal, 2, BigDecimal.ROUND_FLOOR);
                System.out.println("Total " + totalPagar + " Subtotal" + subtotal);
                //subtotal=totalPagar.divide(i);
                BigDecimal descuentoCalcular = new BigDecimal("0.0");
                //descuentoCalcular=(descuento.multiply(new BigDecimal("100"))).divide(subtotal,2,BigDecimal.ROUND_FLOOR);
                devolverDescuento(descuentoCalcular);
            }
        }

    }
    /*
     escojer tipo descuento 
     */

    public void escojerTipoDescuento() {
        System.out.println("Desceunto" + tipoDescuento);
        if (tipoDescuento.equals("porcentaje")) {
            tipoDescuento = "porcentaje";
        } else {
            tipoDescuento = "directo";
        }
    }

    // metodo para escojer el tipo de pago
    public void escojerTipoPago() {
        System.out.println("EscojerTipoPago  " + tipoPago);
        if (tipoPago.equals("Efectivo")) {
            estCheue = false;
            estBanco = false;
            creditoDirecto = false;
            quitarRecargoTarjeta();

        } else if (tipoPago.equals("Tarjeta Credito")) {
            System.out.println("tarjeta");
            estCheue = false;
            estBanco = true;
            creditoDirecto = false;
            System.out.println("Credito: " + estBanco);
        } else if (tipoPago.equals("Credito")) {
            System.out.println("cred Directo");
            estCheue = false;
            estBanco = false;
            creditoDirecto = true;
            creditoFactura = new CreditoFactura();
            quitarRecargoTarjeta();
        } else if (tipoPago.equals("Cheque")) {
            System.out.println("cheque");
            estBanco = false;
            estCheue = true;
            creditoDirecto = false;
            quitarRecargoTarjeta();
        }
    }

    public void quitarRecargoTarjeta() {

        interesTarjeta = new BigDecimal("0.0");
        totalPagar = total;
    }

    //Agrandar la imagen en el dialogo
    public void agrandarImagen(String ruta) {
        rutaImagenAgrandar = ruta;
        RequestContext.getCurrentInstance().execute("PF('dlgImagen').show()");

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

    public String getTipoDescuento() {
        return tipoDescuento;
    }

    public void setTipoDescuento(String tipoDescuento) {
        this.tipoDescuento = tipoDescuento;
    }

    //get y set del sesion 
    public boolean getEstadoCorreo() {
        return estadoCorreo;
    }

    public void setEstadoCorreo(boolean estadoCorreo) {
        this.estadoCorreo = estadoCorreo;
    }

    public SistemaMB getSistemaMB() {
        return sistemaMB;
    }

    public void setSistemaMB(SistemaMB sistemaMB) {
        this.sistemaMB = sistemaMB;
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

    public BigDecimal getAbono() {
        return abono;
    }

    public void setAbono(BigDecimal abono) {
        this.abono = abono;
    }

    public CreditoFactura getCreditoFactura() {
        return creditoFactura;
    }

    public void setCreditoFactura(CreditoFactura creditoFactura) {
        this.creditoFactura = creditoFactura;
    }

    public List<CreditoFactura> getCreditoFacturaObtenidos() {
        return creditoFacturaObtenidos;
    }

    public void setCreditoFacturaObtenidos(List<CreditoFactura> creditoFacturaObtenidos) {
        this.creditoFacturaObtenidos = creditoFacturaObtenidos;
    }

    public List<Venta> getVentasTipoPago() {
        return ventasTipoPago;
    }

    public void setVentasTipoPago(List<Venta> ventasTipoPago) {
        this.ventasTipoPago = ventasTipoPago;
    }

    public boolean getMostrarDeudas() {
        return mostrarDeudas;
    }

    public void setMostrarDeudas(boolean mostrarDeudas) {
        this.mostrarDeudas = mostrarDeudas;
    }

    public String getRutaImagenAgrandar() {
        return rutaImagenAgrandar;
    }

    public void setRutaImagenAgrandar(String rutaImagenAgrandar) {
        this.rutaImagenAgrandar = rutaImagenAgrandar;
    }

    public Date getFechaActual() {
        return fechaActual;
    }

    public void setFechaActual(Date fechaActual) {
        this.fechaActual = fechaActual;
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

}
