/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ec.com.codesoft.web.operador;

import ec.com.codesoft.model.Banco;
import ec.com.codesoft.model.CatalagoProducto;
import ec.com.codesoft.model.Cliente;
import ec.com.codesoft.model.DetalleProductoGeneral;
import ec.com.codesoft.model.DetalleProductoIndividual;
import ec.com.codesoft.model.DetallesServicio;
import ec.com.codesoft.model.Intereses;
import ec.com.codesoft.model.PeriodoContable;
import ec.com.codesoft.model.ProductoGeneralVenta;
import ec.com.codesoft.model.ProductoIndividualCompra;
import ec.com.codesoft.model.Venta;
import ec.com.codesoft.modelo.servicios.CatalogoServicio;
import ec.com.codesoft.modelo.servicios.ClienteServicio;
import ec.com.codesoft.modelo.servicios.CompraServicio;
import ec.com.codesoft.modelo.servicios.FacturaServicio;
import ec.com.codesoft.web.reportes.FacturaDetalleModeloReporte;
import ec.com.codesoft.web.reportes.FacturaModeloReporte;
import ec.com.codesoft.web.reportes.NotaVentaModeloReporte;
import ec.com.codesoft.web.reportes.ProformaModelo;
import java.io.IOException;
import java.math.BigDecimal;
import java.math.MathContext;
import java.text.SimpleDateFormat;
import java.util.AbstractList;
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
    private List<ProductoIndividualCompra> productosIndividualesDetalles;
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
        tipoCliente = "";
        codigoDocumento = 0;//facturaServicio.getCodigoFactura();
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
        descuentoSeleccionado=new BigDecimal("0.0");

        //devolver todos los bancos
        bancos = facturaServicio.devolverBancos();
        nombreBanco = bancos.get(0).getNombre();
        intereses = bancos.get(0).getInteresesList();

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
                        interesTarjeta = interesTarjeta.setScale(2, BigDecimal.ROUND_UP);
                        totalPagar = totalPagar.setScale(2, BigDecimal.ROUND_UP);
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
            msjCliente = "Cliente No Encontrado";
            //mostrarPanel = false;

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

        } else {

            System.out.println("Encontrado");
            msjCliente = "Cliente Encontrado";
            //mostrarCompra = true;
            // mostrarPanel = true;
            //tabCompra = true;
        }
    }

    public void recibirDatos(SelectEvent event) {
        clienteEncontrado = ((Cliente) event.getObject());
        cedCliente = clienteEncontrado.getCedulaRuc();
        //System.out.println("dato recibido");
        //System.out.println(compra.getRuc());
    }

    /*
    // Aplicar descuentos funcion que aplica descuentos
    */
    public void aplicarDescuentos(DetallesVenta detallesVentaRecibido){
        total=new BigDecimal("0.0");
        subtotal=new BigDecimal("0.0");
        System.out.println(detallesVentaRecibido.getNombre()+detallesVentaRecibido.getCosto());
        for(int i=0;i<detallesVenta.size();i++){
            if(detallesVenta.get(i).getCodigo().equals(detallesVentaRecibido.getCodigo())){
                System.out.println("Es igual el producto");
                detallesVenta.get(i).setCosto(descuentoSeleccionado);
                detallesVenta.get(i).setTotal(descuentoSeleccionado.multiply(new BigDecimal(detallesVenta.get(i).getCantidad())));
            }
            System.out.println("total: "+ total);
                totalRegistro = detallesVenta.get(i).getTotal();
                subtotalRegistro = totalRegistro.multiply(new BigDecimal("1.12"));
                subtotal = subtotal.add(totalRegistro);
                if (tipoCliente.equals("C")) {
                    iva = new BigDecimal("0.0");
                    iva = iva.setScale(2, BigDecimal.ROUND_UP);
                    //BigDecimal subTotalTemp=subtotal.multiply(descuento.divide(new BigDecimal(100).add(new BigDecimal(1))));
                    System.out.println("total: "+ total);
                    total = subtotal;
                    total = total.setScale(2, BigDecimal.ROUND_UP);
                    totalPagar=total;
                } else {
                    //BigDecimal subTotalTemp=subtotal.multiply(descuento.divide(new BigDecimal(100).add(new BigDecimal(1))));
                    iva = subtotal.multiply(new BigDecimal("0.12"));
                    iva = iva.setScale(2, BigDecimal.ROUND_UP);
                    total = subtotal.multiply(new BigDecimal("1.12"));
                    total = total.setScale(2, BigDecimal.ROUND_UP);
                    totalPagar=total;

                }
                
                
        }
        
       // detallesVentaRecibido.setCosto(descuentoSeleccionado);
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

                stock = facturaServicio.devolverStockIndividual(catalogoSeleccionado.getCodigoProducto());

                RequestContext.getCurrentInstance().execute("PF('infProductoE').show()");
                estadoDialogo = true;
                //mostrarInformacion = true;
                System.out.println(stock);
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
//            System.out.println(tipoCliente);
//            todoPanel = true;
//            cedCliente = "";
//            clienteEncontrado.setNombre("");
        } else {
            System.out.println("CF" + tipoCliente);
            //clienteEncontrado.setNombre("Consumidor Final");
            //cedCliente = "9999999999";
            ///clienteEncontrado = clienteServicio.buscarCliente(cedCliente);

            //clienteEncontrado.setCedulaRuc("9999999999");
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
            subtotalRegistro = totalRegistro.multiply(new BigDecimal("1.12"));
            subtotal = subtotal.add(totalRegistro);
            if (tipoCliente.equals("C")) {
                iva = new BigDecimal("0.0");
                iva = iva.setScale(2, BigDecimal.ROUND_UP);
                total = subtotal;
                total = total.setScale(2, BigDecimal.ROUND_UP);
            } else {
                iva = subtotal.multiply(new BigDecimal("0.12"));
                iva = iva.setScale(2, BigDecimal.ROUND_UP);
                total = subtotal.multiply(new BigDecimal("1.12"));
                total = total.setScale(2, BigDecimal.ROUND_UP);
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
                productosIndividualesDetalles = new ArrayList<ProductoIndividualCompra>();
                productosIndividualesDetalles = facturaServicio.obtenerProductoIndivudualCantidad(1, catalogoSeleccionado.getCodigoProducto());
                for (int i = 0; i < cantidadComprar; i++) {
                    totalRegistro = productosIndividualesDetalles.get(i).getCosto().multiply(new BigDecimal(cantidadComprar));
                    subtotalRegistro = totalRegistro.multiply(new BigDecimal("1.12"));
                    subtotal = subtotal.add(totalRegistro);
                    if (tipoCliente.equals("C")) {
                        iva = new BigDecimal("0.0");
                        iva = iva.setScale(2, BigDecimal.ROUND_UP);
                        total = subtotal;
                        total = total.setScale(2, BigDecimal.ROUND_UP);
                    } else {
                        iva = subtotal.multiply(new BigDecimal("0.12"));
                        iva = iva.setScale(2, BigDecimal.ROUND_UP);
                        total = subtotal.multiply(new BigDecimal("1.12"));
                        total = total.setScale(2, BigDecimal.ROUND_UP);
                    }

                    DetallesVenta detalles = new DetallesVenta(cantidadComprar, productosIndividualesDetalles.get(i).getCodigoUnico(),
                            productosIndividualesDetalles.get(i).getCodigoProducto().getNombre(),
                            productosIndividualesDetalles.get(i).getCosto(), totalRegistro);
                    detallesVenta.add(detalles);
                    catalogoSeleccionado = new CatalagoProducto();
                    DetalleProductoIndividual detalle = new DetalleProductoIndividual();
                    detalle.setCodigoUnico(productosIndividualesDetalles.get(i));
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
        if (tipoCliente == "" || tipoCliente == null) {
            FacesMessage msg = new FacesMessage("Escoja el tipo de documento");
            FacesContext.getCurrentInstance().addMessage(null, msg);
            cerrarDialogo();
            cerrarDialogoG();
        } else {

            cerrarDialogoG();
            msjCodUnico = "";
            msjStock = "";
            //msjStock = "";
            System.out.println("Si hay stock");
            if ((catalogoSeleccionado.getTipoProducto()) == 'G' || (catalogoSeleccionado.getTipoProducto()) == 'g') {

                System.out.println("En venta");

                totalRegistro = catalogoSeleccionado.getPrecio().multiply(new BigDecimal(cantidadComprar));
                subtotalRegistro = totalRegistro.multiply(new BigDecimal("1.12"));
                subtotal = subtotal.add(totalRegistro);
                if (tipoCliente.equals("C")) {
                    iva = new BigDecimal("0.0");
                    iva = iva.setScale(2, BigDecimal.ROUND_UP);
                    //BigDecimal subTotalTemp=subtotal.multiply(descuento.divide(new BigDecimal(100).add(new BigDecimal(1))));
                    total = subtotal;
                    total = total.setScale(2, BigDecimal.ROUND_UP);
                    totalPagar=total;
                } else {
                    //BigDecimal subTotalTemp=subtotal.multiply(descuento.divide(new BigDecimal(100).add(new BigDecimal(1))));
                    iva = subtotal.multiply(new BigDecimal("0.12"));
                    iva = iva.setScale(2, BigDecimal.ROUND_UP);
                    total = subtotal.multiply(new BigDecimal("1.12"));
                    total = total.setScale(2, BigDecimal.ROUND_UP);
                    totalPagar=total;

                }

                DetallesVenta detalles = new DetallesVenta(cantidadComprar,
                        productoGeneral.getCodigo() + "", catalogoSeleccionado.getNombre(),
                        catalogoSeleccionado.getPrecio(), totalRegistro);
                Descuentos precioMayorista = new Descuentos("Precio Mayorista", catalogoSeleccionado.getPrecioMayorista());
                Descuentos precioDescuento = new Descuentos("Precio Descuento", catalogoSeleccionado.getDescuento());
                List<Descuentos> descuentos = new ArrayList<Descuentos>();
                descuentos.add(precioMayorista);
                descuentos.add(precioDescuento);
                detalles.setDescuentos(descuentos);
                detallesVenta.add(detalles);

                DetalleProductoGeneral detalle = new DetalleProductoGeneral();
                detalle.setCantidad(cantidadComprar);
                detalle.setCodigoProducto(catalogoSeleccionado);
                detalle.setSubtotal(subtotalRegistro);
                detalle.setCodigoDetallGeneral(0);
                detallesGeneralVenta.add(detalle);

                //IMPRIMIR MANDAR AL CARLOS
//                    FacturaModeloReporte factura=new FacturaModeloReporte();
//                    factura.setCodigoFactura("000");
//                    FacturaDetalleModeloReporte detallesFactura= new FacturaDetalleModeloReporte();
//                    detallesFactura.setCantidad(1);
//                    factura.agregarDetalle(detallesFactura);
//                    factura.exportarPDF();
                //}
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
                    productosIndividualesDetalles = new ArrayList<ProductoIndividualCompra>();
                    productosIndividualesDetalles = facturaServicio.obtenerProductoIndivudualCantidad(1, catalogoSeleccionado.getCodigoProducto());
                    for (int i = 0; i < cantidadComprar; i++) {
                        totalRegistro = productosIndividualesDetalles.get(i).getCosto().multiply(new BigDecimal(cantidadComprar));
                        subtotalRegistro = totalRegistro.multiply(new BigDecimal("1.12"));
                        subtotal = subtotal.add(totalRegistro);
                        if (tipoCliente.equals("C")) { //nota de venta C= tipo de documento
                            iva = new BigDecimal("0.0");
                            iva = iva.setScale(2, BigDecimal.ROUND_UP);
                            total = subtotal;
                            total = total.setScale(2, BigDecimal.ROUND_UP);
                            totalPagar=total;
                        } else {
                            iva = subtotal.multiply(new BigDecimal("0.12"));
                            iva = iva.setScale(2, BigDecimal.ROUND_UP);
                            total = subtotal.multiply(new BigDecimal("1.12"));
                            total = total.setScale(2, BigDecimal.ROUND_UP);
                            totalPagar=total;
                        }

                        DetallesVenta detalles = new DetallesVenta(cantidadComprar, productosIndividualesDetalles.get(i).getCodigoUnico(),
                                productosIndividualesDetalles.get(i).getCodigoProducto().getNombre(),
                                productosIndividualesDetalles.get(i).getCosto(), totalRegistro);
                        Descuentos precioMayorista = new Descuentos("Precio Mayorista", catalogoSeleccionado.getPrecioMayorista());
                        Descuentos precioDescuento = new Descuentos("Precio Descuento", catalogoSeleccionado.getDescuento());
                        List<Descuentos> descuentos = new ArrayList<Descuentos>();
                        descuentos.add(precioMayorista);
                        descuentos.add(precioDescuento);
                        detalles.setDescuentos(descuentos);
                        detallesVenta.add(detalles);
                        catalogoSeleccionado = new CatalagoProducto();
                        DetalleProductoIndividual detalle = new DetalleProductoIndividual();
                        detalle.setCodigoUnico(productosIndividualesDetalles.get(i));
                        detalle.setSubtotal(subtotalRegistro);
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
                venta.setTotal(total);
                venta.setDescuento(descuento);
                venta.setCodigoDocumento(codigoDocumento);
                facturaServicio.guardarFactura(venta);
                codigoFactura = venta.getCodigoFactura();

                if (detallesIndividualVenta != null) {
                    for (int i = 0; i < detallesIndividualVenta.size(); i++) {
                        detallesIndividualVenta.get(i).setCodigoFactura(venta);
                    }
                    facturaServicio.insertarDetalleProductoIndividual(detallesIndividualVenta);
                    for (int i = 0; i < detallesIndividualVenta.size(); i++) {
                        prodIndividual = new ProductoIndividualCompra();
                        prodIndividual = facturaServicio.devolverProductoIndividual(detallesIndividualVenta.get(i).getCodigoUnico().getCodigoUnico());
                        prodIndividual.setEstadoProceso("Vendido");
                        facturaServicio.actulizarStocIndividual(prodIndividual);

                    }

                }

                if (detallesGeneralVenta != null) {
                    for (int j = 0; j < detallesGeneralVenta.size(); j++) {
                        detallesGeneralVenta.get(j).setCodigoFactura(venta);
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
                System.out.println("Facturado");
                FacesMessage msg = new FacesMessage("Factura Completa");
                FacesContext.getCurrentInstance().addMessage(null, msg);
                // return "factura";

                if (tipoCliente.equals("C")) {

                    NotaVentaModeloReporte notaVenta = new NotaVentaModeloReporte();
                    notaVenta.setDireccion(clienteEncontrado.getDireccion());
                    SimpleDateFormat sdf = new SimpleDateFormat("yyyy/MM/dd");
                    notaVenta.setFechaFactura(sdf.format(venta.getFecha()));
                    notaVenta.setFechaaFactura(sdf.format(venta.getFecha()));
                    notaVenta.setFormaPago("Efectivo");
                    notaVenta.setNombreCliente(clienteEncontrado.getNombre());
                    notaVenta.setTelefono(clienteEncontrado.getTelefono());
                    notaVenta.setTotal(total);

                    for (DetallesVenta detalle : detallesVenta) {
                        FacturaDetalleModeloReporte detallesFactura = new FacturaDetalleModeloReporte();
                        detallesFactura.setCantidad(detalle.getCantidad() + "");
                        detallesFactura.setCodigo(detalle.getCodigo());
                        detallesFactura.setDescripcion(detalle.getNombre());
                        detallesFactura.setDescuento(" ");
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
                    FacturaModeloReporte facturaReporte = new FacturaModeloReporte();
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
                        detallesFactura.setDescuento(" ");
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

        }

        //return null;
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

            ProformaModelo facturaReporte = new ProformaModelo();
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
                detallesFactura.setDescuento(" ");
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
        iva = subtotal.multiply(new BigDecimal("0.12"));
        iva = iva.setScale(2, BigDecimal.ROUND_UP);
        total = subtotal.multiply(new BigDecimal("1.12"));
        total = total.setScale(2, BigDecimal.ROUND_UP);

    }

    public void ventaGeneral() {
        cerrarDialogo();
    }

    public void onRowUnSelect(SelectEvent event) {

    }

    public void calcularDescuento() {
        BigDecimal descuentoPorcentaje = descuento.divide(new BigDecimal(100)).add(new BigDecimal(1));
        System.out.println("porcetaje " + descuentoPorcentaje);
        BigDecimal subTotalDescuento = subtotal.divide(descuentoPorcentaje, 2, BigDecimal.ROUND_FLOOR);
        System.out.println(subTotalDescuento);

        subTotalDescuento.setScale(2, BigDecimal.ROUND_UP);

        iva = subTotalDescuento.multiply(new BigDecimal("0.12"), MathContext.DECIMAL32);
        iva = iva.divide(new BigDecimal(1), 2, BigDecimal.ROUND_UP);

        iva.setScale(2, BigDecimal.ROUND_UP);

        total = subTotalDescuento.multiply(new BigDecimal("1.12"), MathContext.DECIMAL32);
        total = total.divide(new BigDecimal(1), 2, BigDecimal.ROUND_UP);

        total.setScale(2, BigDecimal.ROUND_UP);
        totalPagar=total;
    }

    // metodo para escojer el tipo de pago
    public void escojerTipoPago() {
        System.out.println("EscojerTipoPago  " + tipoPago);
        if (tipoPago.equals("Efectivo")) {

        } else if (tipoPago.equals("Tarjeta Credito")) {
            System.out.println("tarjeta");
            estCheue = false;
            estBanco = true;
        } else if (tipoPago.equals("Credito")) {

        } else if (tipoPago.equals("Cheque")) {
            System.out.println("cheque");
            estBanco = false;
            estCheue = true;
        }
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
    

}
