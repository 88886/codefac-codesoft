/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ec.com.codesoft.web.operador;

import ec.com.codesoft.model.CatalagoProducto;
import ec.com.codesoft.model.ProductoGeneralCompra;
import ec.com.codesoft.model.ProductoGeneralVenta;
import ec.com.codesoft.modelo.servicios.CatalogoServicio;
import ec.com.codesoft.modelo.servicios.FacturaServicio;
import ec.com.codesoft.modelo.servicios.SistemaServicio;
import ec.com.codesoft.web.seguridad.SistemaMB;
import ec.com.codesoft.web.util.CalculosMB;
import java.io.Serializable;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Locale;
import javax.annotation.PostConstruct;
import javax.ejb.EJB;
import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ManagedProperty;
import javax.faces.bean.ViewScoped;
import javax.faces.context.FacesContext;
import org.primefaces.context.RequestContext;

/**
 *
 * @author carlo
 */
@ManagedBean
@ViewScoped
public class GestionProductoMB implements Serializable {

    /**
     * Lista para obtener y mostrar la lista de productos
     */
    private List<CatalagoProducto> catalagoProductos;
    private List<CatalagoProducto> catalagoProductosStock;
    private List<CatalagoProducto> catalagoProductosOrdenar;
    private Integer stock;
    /**
     * Propiedad para seleccionar en la tabla
     */
    private CatalagoProducto catalogoSeleccionado;

    /**
     * Lista de los productos filtrados en la tabla
     */
    private List<CatalagoProducto> catalagoProductosFiltrados;

    private List<ProductoGeneralCompra> preciosProveedor;

    /**
     * Variable para poder crear un nuevo catalago
     */
    private CatalagoProducto catalagoProducto;

    /**
     * Variable para saber si el dialogo esta abierto
     */
    private Boolean dialogoAbierto;
    private Boolean dialogoEditAbierto;

    /**
     * Variables para saber si el precios incluyen o no incluyen iva
     */
    private String ivaCosto;
    private String ivaMayorista;

    //iva
    private BigDecimal ivaTotal; //iva traido de la configuracion
    private BigDecimal ivaSubTotal; //iva traido de la configuracion
    private BigDecimal ivaMostrar;

    @EJB
    private CatalogoServicio catalogoServicio;

    @EJB
    private SistemaServicio sistemaServicio;

    @EJB
    FacturaServicio facturaServicio;

    @ManagedProperty(value = "#{sistemaMB}")
    private SistemaMB sistemaMB;

    @ManagedProperty("#{calculosMB}")
    private CalculosMB calculosMB;
    //private gestionarCompraMB gestionarCompra;

    //@EJB
    //private CatalogoServicio catologoServicio;
    @PostConstruct
    public void postCostruct() {
        catalagoProductos = catalogoServicio.obtenerTodos();
        catalagoProducto = new CatalagoProducto();
        catalagoProductosStock = new ArrayList<>();
        dialogoAbierto = false;
        this.dialogoEditAbierto = false;
        //iva obtenido de la base de datos
        ivaMostrar = sistemaServicio.getConfiguracion().getIva();
        ivaSubTotal = (sistemaServicio.getConfiguracion().getIva()).divide(new BigDecimal("100"));
        ivaTotal = ivaSubTotal.add(new BigDecimal("1"));

        for (CatalagoProducto catalagoProducto1 : catalagoProductos) {
            if (devolverStock(catalagoProducto1) <= sistemaMB.getConfiguracion().getStockMinimo()) {
                catalagoProductosStock.add(catalagoProducto1);
            }
        }

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

    ///////////////////////////METODOS/////////////////////////
    /**
     * Abre el dialogo y nos muestra una nueva venta para agregar un producto
     */
    public void mostrarNuevoProducto() {
        System.out.println("abriendo el dialogo ...");
        catalagoProducto = new CatalagoProducto();

        catalagoProducto.setPrecioMayorista(new BigDecimal("0.00"));
        catalagoProducto.setPrecio(new BigDecimal("0.00"));

        catalagoProducto.setDescuento(new BigDecimal("0.00"));
        catalagoProducto.setDescuentoMayorista(new BigDecimal("0.00"));
        catalagoProducto.setCosto(new BigDecimal("0.00"));

        System.out.println(catalagoProducto);
        RequestContext.getCurrentInstance().execute("PF('dialogNuevoProducto').show()");
        dialogoAbierto = true;
    }

    public void mostrarEditarProducto() {
        //dialogNuevoProductoEdit
        System.out.println("abriendo el dialogo de editar ...");
        //catalagoProducto = new CatalagoProducto();
        System.out.println(catalogoSeleccionado);
        //catalogoSeleccionado.setPrecio(catalogoSeleccionado.getPrecio().multiply(new BigDecimal("1.12")).setScale(0,RoundingMode.UP));
        //catalogoSeleccionado.setPrecio(calculosMB.incluirIva(catalogoSeleccionado.getPrecio()));
        //catalogoSeleccionado.setPrecioMayorista(catalogoSeleccionado.getPrecioMayorista().multiply(new BigDecimal("1.12")).setScale(0,RoundingMode.UP));
        //catalogoSeleccionado.setPrecioMayorista(calculosMB.incluirIva(catalogoSeleccionado.getPrecioMayorista()));

        RequestContext.getCurrentInstance().execute("PF('dialogNuevoProductoEdit').show()");
        dialogoEditAbierto = true;
    }

    /**
     * Metodo para cerrar la venta del dialogo
     */
    public void cancelarGrabar() {
        System.out.println("cancelando grabar ...");
        RequestContext.getCurrentInstance().execute("PF('dialogNuevoProducto').hide()");
        catalagoProducto = new CatalagoProducto();
        dialogoAbierto = false;
        catalagoProductos = catalogoServicio.obtenerTodos();
        mostrarMensaje("Cancelado", "El proceso de grabar fue cancelado");
    }

    public void cancelarEditar() {
        System.out.println("cancelando editar ...");
        RequestContext.getCurrentInstance().execute("PF('dialogNuevoProductoEdit').hide()");
        dialogoEditAbierto = false;
        catalagoProductos = catalogoServicio.obtenerTodos();
        mostrarMensaje("Cancelado", "El proceso de editar fue cancelado");
    }

    public void grabarCatalogo() {
        System.out.println("Grabando el catalago ...");

        //verificar si el precio viene con o sin iva
        if (ivaCosto.equals("+")) {
            System.out.println(catalagoProducto.getPrecio().setScale(3, BigDecimal.ROUND_DOWN));
            //BigDecimal iva=new BigDecimal("1.12");
            BigDecimal valor = catalagoProducto.getPrecio().divide(ivaTotal, 3, BigDecimal.ROUND_DOWN);
            //System.out.println("si:"+valor);

            valor = valor.setScale(3, BigDecimal.ROUND_DOWN);
            catalagoProducto.setPrecio(valor);

        }

        if (ivaMayorista.equals("+")) {
            BigDecimal valor = catalagoProducto.getPrecioMayorista().divide(ivaTotal, 3, BigDecimal.ROUND_DOWN);
            valor = valor.setScale(3, BigDecimal.ROUND_DOWN);
            catalagoProducto.setPrecioMayorista(valor);
            //catalagoProducto.setPrecioMayorista(catalagoProducto.getPrecioMayorista().divide(new BigDecimal("1.12")).setScale(2,BigDecimal.ROUND_UP));
        }

        catalogoServicio.insertar(catalagoProducto);
        RequestContext.getCurrentInstance().execute("PF('dialogNuevoProducto').hide()");
        catalagoProducto = new CatalagoProducto();
        catalagoProductos = catalogoServicio.obtenerTodos();
        dialogoAbierto = false;
        mostrarMensaje("Producto Grabado ..", "El catalogo fue grabado");

        catalagoProducto = catalogoServicio.buscarCatalogo(catalagoProducto.getCodigoProducto());
//        System.out.println(catalagoProducto.getProductoGeneralVenta().getCantidadDisponible());

    }

    public void editarCatalago() {
        System.out.println("Editando el catalogo ...");

        //verificar si el precio viene con o sin iva
        if (ivaCosto.equals("+")) {

            System.out.println(catalogoSeleccionado.getPrecio().setScale(3, BigDecimal.ROUND_DOWN));
            //BigDecimal iva=new BigDecimal("1.12");
            BigDecimal valor = catalogoSeleccionado.getPrecio().divide(ivaTotal, 3, BigDecimal.ROUND_DOWN);
            //System.out.println("si:"+valor);

            valor = valor.setScale(3, BigDecimal.ROUND_DOWN);
            catalogoSeleccionado.setPrecio(valor);

        }

        if (ivaMayorista.equals("+")) {
            BigDecimal valor = catalogoSeleccionado.getPrecioMayorista().divide(ivaTotal, 3, BigDecimal.ROUND_DOWN);
            valor = valor.setScale(3, BigDecimal.ROUND_DOWN);
            catalogoSeleccionado.setPrecioMayorista(valor);
            //catalagoProducto.setPrecioMayorista(catalagoProducto.getPrecioMayorista().divide(new BigDecimal("1.12")).setScale(2,BigDecimal.ROUND_UP));
        }

        catalogoServicio.actualizar(catalogoSeleccionado);
        RequestContext.getCurrentInstance().execute("PF('dialogNuevoProductoEdit').hide()");
        dialogoEditAbierto = false;
        mostrarMensaje("Producto Editado", "El catalogo fue editado");
    }

    /**
     * Verifica al inicio si el dialogo esta abierto o cerrado
     */
    public void verificarDialogo() {
        System.out.println("Verificando si el dialogo se encuentra abierto ...");
        if (dialogoAbierto) {
            RequestContext.getCurrentInstance().execute("PF('dialogNuevoProducto').show()");
        }

        if (dialogoEditAbierto) {
            RequestContext.getCurrentInstance().execute("PF('dialogNuevoProductoEdit').show()");
        }
    }

    public void mostrarMensaje(String titulo, String mensaje) {
        FacesContext context = FacesContext.getCurrentInstance();
        context.addMessage(null, new FacesMessage(titulo, mensaje));
    }

    public void verificarExisteCatalogoProducto() {
        boolean noexiste = catalogoServicio.verificarExisteProducto(catalagoProducto.getCodigoProducto());
        if (noexiste) {
            FacesMessage message = new FacesMessage(FacesMessage.SEVERITY_INFO, "El producto ya existe", "!porfavor revise los datos!");
            RequestContext.getCurrentInstance().showMessageInDialog(message);
        }

    }

    public void eliminarDetalle(CatalagoProducto catalogo) {
        System.out.println("eliminando producto...");
        catalagoProductos.remove(catalogo);

        catalogoServicio.eliminar(catalogo);
    }

    public boolean filterByName(Object value, Object filter, Locale locale) {
        String filterText = (filter == null) ? null : filter.toString().trim();
        if (filterText == null || filterText.equals("")) {
            return true;
        }

        if (value == null) {
            return false;
        }

        String carName = value.toString().toUpperCase();
        filterText = filterText.toUpperCase();

        if (carName.contains(filterText)) {
            return true;
        } else {
            return false;
        }
    }

    public void mostrarPreciosDistribuidor(CatalagoProducto catalogo) {
        RequestContext.getCurrentInstance().execute("PF('dialogProductoDistri').show()");
        preciosProveedor = catalogoServicio.listaPreciosProductosGCompra(catalogo.getCodigoProducto());
        System.out.println("tam: " + preciosProveedor.size());
    }

    //////////////////////METODOS GET Y SET ///////////////////
    public List<CatalagoProducto> getCatalagoProductos() {
        return catalagoProductos;
    }

    public void setCatalagoProductos(List<CatalagoProducto> catalagoProductos) {
        this.catalagoProductos = catalagoProductos;
    }

    public CatalagoProducto getCatalogoSeleccionado() {
        return catalogoSeleccionado;
    }

    public void setCatalogoSeleccionado(CatalagoProducto catalogoSeleccionado) {
        this.catalogoSeleccionado = catalogoSeleccionado;
    }

    public List<CatalagoProducto> getCatalagoProductosFiltrados() {
        return catalagoProductosFiltrados;
    }

    public void setCatalagoProductosFiltrados(List<CatalagoProducto> catalagoProductosFiltrados) {
        this.catalagoProductosFiltrados = catalagoProductosFiltrados;
    }

    public CatalagoProducto getCatalagoProducto() {
        return catalagoProducto;
    }

    public void setCatalagoProducto(CatalagoProducto catalagoProducto) {
        this.catalagoProducto = catalagoProducto;
    }

    public String getIvaCosto() {
        return ivaCosto;
    }

    public void setIvaCosto(String ivaCosto) {
        this.ivaCosto = ivaCosto;
    }

    public String getIvaMayorista() {
        return ivaMayorista;
    }

    public void setIvaMayorista(String ivaMayorista) {
        this.ivaMayorista = ivaMayorista;
    }

    public CalculosMB getCalculosMB() {
        return calculosMB;
    }

    public void setCalculosMB(CalculosMB calculosMB) {
        this.calculosMB = calculosMB;
    }

    public List<ProductoGeneralCompra> getPreciosProveedor() {
        return preciosProveedor;
    }

    public void setPreciosProveedor(List<ProductoGeneralCompra> preciosProveedor) {
        this.preciosProveedor = preciosProveedor;
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

    public SistemaMB getSistemaMB() {
        return sistemaMB;
    }

    public void setSistemaMB(SistemaMB sistemaMB) {
        this.sistemaMB = sistemaMB;
    }

    public List<CatalagoProducto> getCatalagoProductosStock() {
        return catalagoProductosStock;
    }

    public void setCatalagoProductosStock(List<CatalagoProducto> catalagoProductosStock) {
        this.catalagoProductosStock = catalagoProductosStock;
    }

}
