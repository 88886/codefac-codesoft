/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ec.com.codesoft.web.operador;

import ec.com.codesoft.model.Banco;
import ec.com.codesoft.model.CatalagoProducto;
import ec.com.codesoft.model.Cliente;
import ec.com.codesoft.model.Creditobanco;
import ec.com.codesoft.model.DetalleProductoGeneral;
import ec.com.codesoft.model.DetalleProductoIndividual;
import ec.com.codesoft.model.DetalleVentaOrdenTrabajo;
import ec.com.codesoft.model.DetallesServicio;
import ec.com.codesoft.model.Intereses;
import ec.com.codesoft.model.OrdenTrabajo;
import ec.com.codesoft.model.PeriodoContable;
import ec.com.codesoft.model.ProductoGeneralVenta;
import ec.com.codesoft.model.ProductoIndividualCompra;
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
    private List<OrdenTrabajo> ordenesTrabajo;
    private OrdenTrabajo ordenTrabajoSeleccionada;
    private List<DetalleVentaOrdenTrabajo> detallesOrdenTrabajo;
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
        mostrarPanel = false;
        cantidadComprar = 1;
        mostrarInformacion = false;
        catalogosLista = catalogoServicio.obtenerTodos();
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
        ordenesTrabajo = ordenTrabajoServicio.obtenerOrdenesTrabajo();
        detallesOrdenTrabajo = new ArrayList<DetalleVentaOrdenTrabajo>();

        //habilitarDEescuento Manual
        System.out.println("Sesion" + sesion);
//        System.out.println("Usuario Login "+sesion.getUsuarioLogin());
//        System.out.println("PerfilBuscado "+ sesion.getPerfilBuscado().getTipo());
//        
        if (sesion.getPerfilBuscado().getTipo().equals("admin")) {
            mostrarDescuentoManual = true;
        } else {
            mostrarDescuentoManual = false;
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
            if (clienteEncontrado.getTipo().equals("Distribuidor")) {
                cliMayorista = " --> Distribuidor";
            } else {
                cliMayorista = "";
            }

            //mostrarCompra = true;
            // mostrarPanel = true;
            //tabCompra = true;
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

    public void onRowSelectOrden(SelectEvent event) {

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
                DetalleVentaOrdenTrabajo detalleOrdenTrabajo = new DetalleVentaOrdenTrabajo();
                totalRegistro = ordenTrabajoSeleccionada.getTotal().divide(ivaTotal, 2, BigDecimal.ROUND_FLOOR);
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
                  DetallesVenta detalles = new DetallesVenta(1, ordenTrabajoSeleccionada.getIdOrdenTrabajo().toString(),
                        ordenTrabajoSeleccionada.toStringDetalle(),
                        totalRegistro, totalRegistro);

                  //descomentar para que coja el descuento con el precio sin iva
//                Descuentos precioMayorista = new Descuentos("Prec Mayorista", ordenTrabajoSeleccionada.getTotal());
//                Descuentos precioDescuento = new Descuentos("PVP", ordenTrabajoSeleccionada.getTotal());
                Descuentos precioMayorista = new Descuentos("Prec Mayorista", totalRegistro);
                Descuentos precioDescuento = new Descuentos("PVP",totalRegistro);
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
                detalleOrdenTrabajo.setIdOrdenTrabajo(ordenTrabajoSeleccionada);
                detallesVenta.add(detalles);
                System.out.println("Detallles: " + detallesVenta);
            }
        }

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
            } else {
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
        clienteEncontrado = (Cliente) event.getObject();
        if (clienteEncontrado.getTipo().equals("Distribuidor")) {
            cliMayorista = " --> Distribuidor";
        } else {
            cliMayorista = "";
        }
        cedCliente = clienteEncontrado.getCedulaRuc();
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

        } else {
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
        }

    }

    public void venta() {
        System.out.println(cantidadComprar + "--" + stock);
       // if (cantidadComprar > stock) {
        //     msjStock = "No existe suficiente Stock";
        //     System.out.println("No hay stock");

        //} else {
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
                        detallesIndividualVenta.add(detalle);
                        //det

                    }
                }

            }
        }
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
                    venta.setFecha(new Date());
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
                    venta.setIva(iva); //guarda el iva de la venta
                    facturaServicio.guardarFactura(venta);
                    //guardarfactura Carlos reportes
                    venta.setDetalleProductoGeneralList(detallesGeneralVenta);
                    venta.setDetalleProductoIndividualList(detallesIndividualVenta);

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
                                System.out.println(detallesVenta.get(i).getCodigo() + " -- " + detallesGeneralVenta.get(j).getCodigoProducto().getCodigoProducto());
                                if (detallesVenta.get(i).getCodigo().equals(detallesGeneralVenta.get(j).getCodigoProducto().getCodigoProducto())) {
                                    System.out.println("DetallesGVenta" + detallesVenta.get(i).getValorDescuento());
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
                    for (int i = 0; i < detallesVenta.size(); i++) {
                        if (detallesVenta.get(i).getTipoDetalle().equals("Orden Trabajo")) {
                            DetalleVentaOrdenTrabajo detalleOrdenTrabajo = new DetalleVentaOrdenTrabajo();
                            detalleOrdenTrabajo.setCodigoFactura(venta);
                            //detalleOrdenTrabajo.setIdOrdenTrabajo(0);
                            detalleOrdenTrabajo.setDescuento(detallesVenta.get(i).getValorDescuento());
                            detalleOrdenTrabajo.setEstado("Facturado");
                            detalleOrdenTrabajo.setIva(new BigDecimal("0.0"));
                            OrdenTrabajo orden = new OrdenTrabajo();
                            orden.setIdOrdenTrabajo(Integer.parseInt(detallesVenta.get(i).getCodigo()));
                            detalleOrdenTrabajo.setIdOrdenTrabajo(orden);
                            //detalleOrdenTrabajo.setNick;
                            detalleOrdenTrabajo.setTotal(detallesVenta.get(i).getTotal());
                            detallesOrdenTrabajo.add(detalleOrdenTrabajo);
                            numOrdenes = i + 1;
                        }

                    }

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
                    RequestContext.getCurrentInstance().execute("PF('dlgImprimir').show()");
                    ventaImprimir = venta;
                }

            }

        }

        //return null;
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
            return "Credito Directo";
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
        } else if (tipoPago.equals("Credito")) {
            System.out.println("cred Directo");
            estCheue = false;
            estBanco = false;
            creditoDirecto = true;
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
}
