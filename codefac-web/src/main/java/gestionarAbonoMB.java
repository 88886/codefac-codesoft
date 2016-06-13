
import ec.com.codesoft.model.AbonoVentaCredito;
import ec.com.codesoft.model.Cliente;
import ec.com.codesoft.model.CreditoFactura;
import ec.com.codesoft.model.Venta;
import ec.com.codesoft.modelo.servicios.ClienteServicio;
import ec.com.codesoft.modelo.servicios.FacturaServicio;
import java.io.Serializable;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import javax.annotation.PostConstruct;
import javax.ejb.EJB;
import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;
import javax.faces.context.FacesContext;
import org.primefaces.context.RequestContext;
import org.primefaces.event.CellEditEvent;
import org.primefaces.event.RowEditEvent;
import org.primefaces.event.SelectEvent;

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
/**
 *
 * @author Suco
 */
@ManagedBean
@ViewScoped

public class gestionarAbonoMB implements Serializable {

    private String campoBuscar;
    private String codigoBuscar;
    private String placeHolder;
    private List<CreditoFactura> creditoFacturaObtenidos;
    private List<Venta> ventasTipoPago;
    private CreditoFactura creditoFacturaSeleccionada;
    private AbonoVentaCredito abonoVentaSeleccionada;
    private Boolean mostrarAbono;
    private AbonoVentaCredito nuevoAbono;
    private List<Cliente> clientesLista;
    private Cliente clienteSeleccionado;
    private Boolean mostrarBotonBuscar;
    Integer bandera;
    private Boolean enNnuevo;
    private Boolean enEditar;
    Boolean activarEditar;

    @EJB
    private FacturaServicio facturaServicio;

    @EJB
    private ClienteServicio clienteServicio;

    @PostConstruct
    public void inicializar() {
        campoBuscar = "Cliente";
        placeHolder = "Ingrese la Cédula o RUC";
        creditoFacturaObtenidos = new ArrayList<CreditoFactura>();
        clientesLista = clienteServicio.obtenerTodos();
        mostrarAbono = false;
        mostrarBotonBuscar = true;
        enNnuevo = true;
        enEditar = false;
        activarEditar=true;
    }

    public void escojerFiltro() {
        System.out.println(campoBuscar);
        if (campoBuscar.equals("Cliente")) {
            mostrarBotonBuscar = true;
            placeHolder = "Ingrese la Cédula o RUC";
        } else {
            placeHolder = "Ingrese el N de Factura";
            mostrarBotonBuscar = false;
        }
    }

    public BigDecimal obtenerSaldoAnterior(Integer fila) {

        List<AbonoVentaCredito> abonoTem = new ArrayList<AbonoVentaCredito>();
        abonoTem = creditoFacturaSeleccionada.getAbonoVentaCreditoList();
        BigDecimal saldoAnterior = new BigDecimal("0.0");
        for (int i = 0; i <= fila; i++) {
            saldoAnterior = saldoAnterior.add(abonoTem.get(i).getCantidad());
        }
        return saldoAnterior;
    }

    public void buscarDatos() {
        System.out.println("BuscarDatos");
        //mostrarAbono = false;
        if (campoBuscar.equals("Cliente")) {
            ventasTipoPago = facturaServicio.obtenerVentaTipo(codigoBuscar, "Credito");
            if (ventasTipoPago != null) {
                creditoFacturaObtenidos = new ArrayList<CreditoFactura>();
                for (int i = 0; i < ventasTipoPago.size(); i++) {
                    CreditoFactura creditoTemporal = new CreditoFactura();
                    creditoTemporal = facturaServicio.obtenerCreditoFactura(ventasTipoPago.get(i).getCodigoFactura(), "Proceso");
                    //System.out.println("Credito"+creditoTemporal);
                    if (creditoTemporal != null) {
                        creditoTemporal.setAbonoVentaCreditoList(facturaServicio.obtenerAbonosCredito(creditoTemporal.getCodigoFacturaCredito()));
                        System.out.println("Credito" + creditoTemporal.toString());
                        System.out.println("Abonos" + creditoTemporal.getAbonoVentaCreditoList().toString());
                        creditoFacturaObtenidos.add(creditoTemporal);
                    }
                }
            } else {
                FacesMessage msg = new FacesMessage("No se Encontraron Datos");
                FacesContext.getCurrentInstance().addMessage(null, msg);
            }

        } else {

        }
    }

    public void onRowSelect(SelectEvent event) {
        System.out.println("Entro");
        mostrarAbono = true;
        nuevoAbono = new AbonoVentaCredito();
        bandera = 0;

    }

    public void onRowUnSelect(SelectEvent event) {
        mostrarAbono = false;
    }

    public void onRowSelectCliente(SelectEvent event) {
        System.out.println("En sleccion");
        mostrarAbono = false;
        //clienteEncontrado = clienteSeleccionado;
        clienteSeleccionado = (Cliente) event.getObject();
        codigoBuscar = clienteSeleccionado.getCedulaRuc();
        //System.out.println(campoBuscar);
        buscarDatos();
    }

    public void onRowUnSelectCliente(SelectEvent event) {
        System.out.println("deseleccionando ...");
    }

    public void onRowSelectAbono(SelectEvent event) {
        System.out.println("EntroAbono");
        enNnuevo=false;
        activarEditar=false;
        System.out.println(abonoVentaSeleccionada.toString());
        nuevoAbono=abonoVentaSeleccionada;

    }
    

    public void onRowUnSelectAbono(SelectEvent event) {
        System.out.println("deseleccionando ...");
        enNnuevo=true;
    }
    
    public void activarEditar(){
        enNnuevo=false;
        System.out.println("Entro Editar");
    }
    public void activarNuevo(){
        enNnuevo=true;
        System.out.println("Entro Nuevo");
    }

    public void guardarAbono() {

        if (enNnuevo) {
            System.out.println("Guardar Abono");
            nuevoAbono.setCodigoFacturaCredito(creditoFacturaSeleccionada);
            nuevoAbono.setFecha(new Date());
            nuevoAbono.setCodigoAbono(0);
            System.out.println("Abono" + nuevoAbono.toString());
            facturaServicio.guardarAbono(nuevoAbono);
            buscarDatos();
            System.out.println("Lista " + facturaServicio.obtenerAbonosCredito(creditoFacturaSeleccionada.getCodigoFacturaCredito()));
            creditoFacturaSeleccionada.setAbonoVentaCreditoList(facturaServicio.obtenerAbonosCredito(creditoFacturaSeleccionada.getCodigoFacturaCredito()));
            RequestContext.getCurrentInstance().execute("PF('dlgNuevoAbono').hide()");
            System.out.println("Total "+creditoFacturaSeleccionada.getCodigoFactura().getTotal());
            System.out.println("Sumar Abono"+ sumarAbonos());
            if((creditoFacturaSeleccionada.getCodigoFactura().getTotal().compareTo(sumarAbonos())==0) ||creditoFacturaSeleccionada.getCodigoFactura().getTotal().equals(sumarAbonos())){
                //editar el estado
                creditoFacturaSeleccionada.setEstado("Completa");
                facturaServicio.editarCredito(creditoFacturaSeleccionada);
                System.out.println("Completo el credito");
            }
            nuevoAbono=new AbonoVentaCredito();
        }else{
            facturaServicio.editarAbono(nuevoAbono);
            RequestContext.getCurrentInstance().execute("PF('dlgNuevoAbono').hide()");
            nuevoAbono=new AbonoVentaCredito();
        }
    }
    
    public void eliminarAbono(AbonoVentaCredito abonoEliminar){
        facturaServicio.eliminarAbono(abonoEliminar);
        creditoFacturaSeleccionada.getAbonoVentaCreditoList().remove(abonoEliminar);
        buscarDatos();
        
    }

    public BigDecimal sumarAbonos() {
        List<AbonoVentaCredito> listaAbonos = new ArrayList<AbonoVentaCredito>();
        listaAbonos = creditoFacturaSeleccionada.getAbonoVentaCreditoList();
        BigDecimal sumaAbono = new BigDecimal("0.0");
        for (AbonoVentaCredito listaAbono : listaAbonos) {
            sumaAbono = sumaAbono.add(listaAbono.getCantidad());
        }
        return sumaAbono;

    }

    public void imprimir() {
        System.out.println("imprimir");
    }

    /**
     * editar las celdas de la tabla abono
     *
     * @param event
     */
    public void onRowEdit(RowEditEvent event) {
        FacesMessage msg = new FacesMessage("Abono Editado", ((AbonoVentaCredito) event.getObject()).getCodigoAbono().toString());
        FacesContext.getCurrentInstance().addMessage(null, msg);
    }

    public void onRowCancel(RowEditEvent event) {
        FacesMessage msg = new FacesMessage("Edición Cancelada", ((AbonoVentaCredito) event.getObject()).getCodigoAbono().toString());
        FacesContext.getCurrentInstance().addMessage(null, msg);
    }

    public void onCellEdit(CellEditEvent event) {
        Object oldValue = event.getOldValue();
        Object newValue = event.getNewValue();

        if (newValue != null && !newValue.equals(oldValue)) {
            FacesMessage msg = new FacesMessage(FacesMessage.SEVERITY_INFO, "Dato cambiado", "Aniguo: " + oldValue + ", Nuevo:" + newValue);
            FacesContext.getCurrentInstance().addMessage(null, msg);
        }
    }

    /**
     * Getter and Setter
     *
     * @return
     */
    public String getCampoBuscar() {
        return campoBuscar;
    }

    public void setCampoBuscar(String campoBuscar) {
        this.campoBuscar = campoBuscar;
    }

    public String getCodigoBuscar() {
        return codigoBuscar;
    }

    public void setCodigoBuscar(String codigoBuscar) {
        this.codigoBuscar = codigoBuscar;
    }

    public String getPlaceHolder() {
        return placeHolder;
    }

    public void setPlaceHolder(String placeHolder) {
        this.placeHolder = placeHolder;
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

    public CreditoFactura getCreditoFacturaSeleccionada() {
        return creditoFacturaSeleccionada;
    }

    public void setCreditoFacturaSeleccionada(CreditoFactura creditoFacturaSeleccionada) {
        this.creditoFacturaSeleccionada = creditoFacturaSeleccionada;
    }

    public AbonoVentaCredito getAbonoVentaSeleccionada() {
        return abonoVentaSeleccionada;
    }

    public void setAbonoVentaSeleccionada(AbonoVentaCredito abonoVentaSeleccionada) {
        this.abonoVentaSeleccionada = abonoVentaSeleccionada;
    }

    public Boolean getMostrarAbono() {
        return mostrarAbono;
    }

    public void setMostrarAbono(Boolean mostrarAbono) {
        this.mostrarAbono = mostrarAbono;
    }

    public AbonoVentaCredito getNuevoAbono() {
        return nuevoAbono;
    }

    public void setNuevoAbono(AbonoVentaCredito nuevoAbono) {
        this.nuevoAbono = nuevoAbono;
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

    public Boolean getMostrarBotonBuscar() {
        return mostrarBotonBuscar;
    }

    public void setMostrarBotonBuscar(Boolean mostrarBotonBuscar) {
        this.mostrarBotonBuscar = mostrarBotonBuscar;
    }

    public Boolean getActivarEditar() {
        return activarEditar;
    }

    public void setActivarEditar(Boolean activarEditar) {
        this.activarEditar = activarEditar;
    }
    

}
