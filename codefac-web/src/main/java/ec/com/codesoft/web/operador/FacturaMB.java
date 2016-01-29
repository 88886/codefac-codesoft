/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ec.com.codesoft.web.operador;

import ec.com.codesoft.model.CatalagoProducto;
import ec.com.codesoft.model.Cliente;
import ec.com.codesoft.model.DetalleProductoGeneral;
import ec.com.codesoft.model.DetalleProductoIndividual;
import ec.com.codesoft.model.DetallesServicio;
import ec.com.codesoft.model.PeriodoContable;
import ec.com.codesoft.model.ProductoGeneralCompra;
import ec.com.codesoft.model.ProductoIndividualCompra;
import ec.com.codesoft.model.Venta;
import ec.com.codesoft.modelo.servicios.CatalogoServicio;
import ec.com.codesoft.modelo.servicios.ClienteServicio;
import ec.com.codesoft.modelo.servicios.CompraServicio;
import ec.com.codesoft.modelo.servicios.FacturaServicio;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import javax.annotation.PostConstruct;
import javax.ejb.EJB;
import javax.ejb.TransactionAttribute;
import javax.ejb.TransactionAttributeType;
import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;
import javax.faces.context.FacesContext;
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
    private ProductoGeneralCompra productoGeneral;
    private String msjStock;
    private String tipoCliente;
    private boolean todoPanel;
    private List<ProductoIndividualCompra> productosIndividualesDetalles;
    private List<DetallesVenta> detallesVenta;
    private BigDecimal totalRegistro;
    private BigDecimal total;
    private BigDecimal subtotal;
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
    private ProductoGeneralCompra prodGeneral;
    private ProductoIndividualCompra prodIndividual;
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
        subtotalRegistro = new BigDecimal("0.0");
        iva = new BigDecimal("0.0");
        detallesVenta = new ArrayList<DetallesVenta>();
        msjCodUnico = "";
        detallesIndividualVenta = new ArrayList<DetalleProductoIndividual>();
        detallesGeneralVenta = new ArrayList<DetalleProductoGeneral>();
        detallesServicio = new ArrayList<DetallesServicio>();
        clientesLista = clienteServicio.obtenerTodos();

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

        } else {

            System.out.println("Encontrado");
            msjCliente = "Cliente Encontrado";
            //mostrarCompra = true;
            // mostrarPanel = true;
            //tabCompra = true;
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
            productoGeneral = new ProductoGeneralCompra();
            System.out.println("Encontrado");
            if ((catalogoSeleccionado.getTipoProducto()) == 'G' || (catalogoSeleccionado.getTipoProducto()) == 'g') {
                System.out.println("general");

                productoGeneral = facturaServicio.devolverStockGeneral(catalogoSeleccionado.getCodigoProducto());
                // System.out.println(productoGeneral.getCantidad());
                int numDetalles = 0;
                for (int i = 0; i < detallesVenta.size(); i++) {
                    if (detallesVenta.get(i).getCodigo() == catalogoSeleccionado.getCodigoProducto()) {
                        numDetalles = numDetalles + 1;
                    }
                }

                stock = productoGeneral.getCantidad() - numDetalles;
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
            System.out.println(tipoCliente);
            todoPanel = true;
            cedCliente = "";
            clienteEncontrado.setNombre("");
        } else {
            System.out.println("CF" + tipoCliente);
            clienteEncontrado.setNombre("Consumidor Final");
            cedCliente = "9999999999";
            clienteEncontrado=clienteServicio.buscarCliente(cedCliente);
            
            //clienteEncontrado.setCedulaRuc("9999999999");
            todoPanel = true;
        }
    }

    public void onRowSelectCliente(SelectEvent event) {

        clienteEncontrado = clienteSeleccionado;
        cedCliente = clienteEncontrado.getCedulaRuc();
        clientesLista = clienteServicio.obtenerTodos();
    }

    public void onRowUnSelectCliente(SelectEvent event) {

    }

    public void venta() {
        System.out.println(cantidadComprar + "--" + stock);
        if (cantidadComprar > stock) {
            //RequestContext.getCurrentInstance().execute("PF('infProducto').show()");
            //estadoDialogo = true;
            msjStock = "No existe suficiente Stock";
            // FacesMessage msg = new FacesMessage("No existe suficiente Stock");
            //FacesContext.getCurrentInstance().addMessage(null, msg);
            System.out.println("No hay stock");

        } else {

            cerrarDialogoG();
            msjCodUnico = "";
            msjStock = "";
            //msjStock = "";
            System.out.println("Si hay stock");
            if ((catalogoSeleccionado.getTipoProducto()) == 'G' || (catalogoSeleccionado.getTipoProducto()) == 'g') {

                System.out.println("En venta");

                totalRegistro = productoGeneral.getCostoIndividual().multiply(new BigDecimal(cantidadComprar));
                subtotalRegistro = totalRegistro.multiply(new BigDecimal("1.12"));
                subtotal = subtotal.add(totalRegistro);
                iva = subtotal.multiply(new BigDecimal("0.12"));
                iva = iva.setScale(2, BigDecimal.ROUND_UP);
                total = subtotal.multiply(new BigDecimal("1.12"));
                total = total.setScale(2, BigDecimal.ROUND_UP);
                DetallesVenta detalles = new DetallesVenta(cantidadComprar,
                        productoGeneral.getCodigoProducto().getCodigoProducto(), productoGeneral.getCodigoProducto().getNombre(),
                        productoGeneral.getCostoIndividual(), totalRegistro);
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
                        iva = subtotal.multiply(new BigDecimal("0.12"));
                        iva = iva.setScale(2, BigDecimal.ROUND_UP);
                        total = subtotal.multiply(new BigDecimal("1.12"));
                        total = total.setScale(2, BigDecimal.ROUND_UP);

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
    }

    @TransactionAttribute(TransactionAttributeType.REQUIRED)
    public String facturar() {
        System.out.println("facturando");
        if (clienteEncontrado.getCedulaRuc() == null || clienteEncontrado.getCedulaRuc() == "") {
            FacesMessage msg = new FacesMessage("Agregue Cliente");
            FacesContext.getCurrentInstance().addMessage(null, msg);
            return null;
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
                venta.setTipoDocumento("Factura");
                venta.setEstado("facturado");
                venta.setFecha(new Date());
                venta.setTotal(total);
                facturaServicio.guardarFactura(venta);
                codigoFactura = venta.getCodigoFactura();

                if (detallesIndividualVenta != null) {
                    for (int i = 0; i < detallesIndividualVenta.size(); i++) {
                        detallesIndividualVenta.get(i).setCodigoFactura(venta);
                    }
                    facturaServicio.insertarDetalleProductoIndividual(detallesIndividualVenta);
                    for (int i = 0; i < detallesIndividualVenta.size(); i++) {
                        prodIndividual=new ProductoIndividualCompra();
                        prodIndividual=facturaServicio.devolverProductoIndividual(detallesIndividualVenta.get(i).getCodigoUnico().getCodigoUnico());
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
                        prodGeneral = new ProductoGeneralCompra();
                        prodGeneral = facturaServicio.devolverStockGeneral(detallesGeneralVenta.get(j).getCodigoProducto().getCodigoProducto());
                        Integer cantidadStock = 0;
                        cantidadStock = prodGeneral.getCantidad() - detallesGeneralVenta.get(j).getCantidad();
                        prodGeneral.setCantidad(cantidadStock);
                        facturaServicio.actulizarStockGeneral(prodGeneral);
                    }
                }
                System.out.println("Facturado");
                 FacesMessage msg = new FacesMessage("Factura Completa");
            FacesContext.getCurrentInstance().addMessage(null, msg);
                return "factura";
                
            }
        }
        return null;
    }
    
    public String cancelar(){
        System.out.println("Cancelando");
        return "factura";
    }

    public void eliminarDetalle(DetallesVenta detalleVentaEliminar) {

        detallesVenta.remove(detalleVentaEliminar);
    }

    public void ventaGeneral() {
        cerrarDialogo();
    }

    public void onRowUnSelect(SelectEvent event) {

    }

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

}
