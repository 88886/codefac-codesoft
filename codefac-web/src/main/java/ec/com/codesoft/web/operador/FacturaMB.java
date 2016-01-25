/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ec.com.codesoft.web.operador;

import ec.com.codesoft.model.CatalagoProducto;
import ec.com.codesoft.model.Cliente;
import ec.com.codesoft.model.ProductoGeneralCompra;
import ec.com.codesoft.modelo.servicios.CatalogoServicio;
import ec.com.codesoft.modelo.servicios.ClienteServicio;
import ec.com.codesoft.modelo.servicios.FacturaServicio;
import java.util.List;
import javax.annotation.PostConstruct;
import javax.ejb.EJB;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;
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
    private boolean estadoDialogoEspecifico;
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

    @EJB
    ClienteServicio clienteServicio;

    @EJB
    private CatalogoServicio catalogoServicio;

    @EJB
    FacturaServicio facturaServicio;

    @PostConstruct
    public void inicializar() {
        estadoDialogo = false;
        estadoDialogoEspecifico = false;
        msjCliente = "";
        clienteEncontrado = new Cliente();
        mostrarPanel = false;
        cantidadComprar = 1;
        mostrarInformacion = false;
        catalogosLista = catalogoServicio.obtenerTodos();
        catalogoSeleccionado = new CatalagoProducto();
    }

    public void verificarDialogo() {
        if (estadoDialogo) {
            RequestContext.getCurrentInstance().execute("PF('nuevoCatalogo').show()");
        }
    }

    public void buscarCliente() {

        System.out.println("Buscar");
        clienteEncontrado = clienteServicio.buscarCliente(cedCliente);
        if (clienteEncontrado == null) {
            System.out.println("NNEncontrado");
            msjCliente = "Cliente No Encontrado";
            mostrarPanel = false;

        } else {

            System.out.println("Encontrado");
            msjCliente = "Cliente Encontrado";
            //mostrarCompra = true;
            mostrarPanel = true;
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
            productoGeneral=new ProductoGeneralCompra();
            System.out.println("Encontrado");
            if (catalogoSeleccionado.getTipoProducto() == 'g') {
                System.out.println("general");
                
                productoGeneral=facturaServicio.devolverStockGeneral(catalogoSeleccionado.getCodigoProducto());
                System.out.println(productoGeneral);
                stock=productoGeneral.getCantidad();
                
                RequestContext.getCurrentInstance().execute("PF('infProducto').show()");
                mostrarPanel = false;

            } else {
                System.out.println("Espe");
                stock = facturaServicio.devolverStockIndividual(catalogoSeleccionado.getCodigoProducto());
                RequestContext.getCurrentInstance().execute("PF('infProducto').show()");
                mostrarInformacion = true;
                System.out.println(stock);
                //mostrarPanel = false;

            }
            // msjDistri = "Encontrado";
            // mostrarPanel = true;
        }
        catalogoSeleccionado = new CatalagoProducto();

    }

    public void onRowUnSelect(SelectEvent event) {

    }

    public boolean getEstadoDialogo() {
        return estadoDialogo;
    }

    public void setEstadoDialogo(boolean estadoDialogo) {
        this.estadoDialogo = estadoDialogo;
    }

    public boolean getEstadoDialogoEspecifico() {
        return estadoDialogoEspecifico;
    }

    public void setEstadoDialogoEspecifico(boolean estadoDialogoEspecifico) {
        this.estadoDialogoEspecifico = estadoDialogoEspecifico;
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

    public boolean isMostrarInformacion() {
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

}
