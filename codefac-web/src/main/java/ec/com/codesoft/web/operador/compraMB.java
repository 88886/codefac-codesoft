/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ec.com.codesoft.web.operador;

import ec.com.codesoft.model.CatalagoProducto;
import ec.com.codesoft.model.Compra;
import ec.com.codesoft.model.Distribuidor;
import ec.com.codesoft.model.PeriodoContable;
import ec.com.codesoft.model.ProductoGeneralCompra;
import ec.com.codesoft.model.ProductoIndividualCompra;
import ec.com.codesoft.modelo.servicios.CatalogoServicio;
import ec.com.codesoft.modelo.servicios.CompraServicio;
import ec.com.codesoft.modelo.servicios.DistribuidorServicio;
import ec.com.codesoft.modelo.servicios.ProductoGeneralCompraServicio;
import ec.com.codesoft.web.seguridad.SessionMB;
import java.io.Serializable;
import java.math.BigDecimal;
import java.math.BigInteger;
import java.math.MathContext;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
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
 * @author Suco
 */
@ManagedBean
@ViewScoped
public class compraMB implements Serializable {

    private String codDistribuidor;
    private Distribuidor distriEncontrado;
    private CatalagoProducto catalogoEncontrado;
    private String msjDistri;
    private String msjCat;
    private Boolean mostrarPanel;
    private int cantidad;
    private String codigoP;
    private CatalagoProducto catalogo;
    private Character tipoProd;
    private Boolean tabGeneral;
    private Boolean tabEspecifico;
    private Boolean tabCompra;
    private List<CatalagoProducto> listaCatalogo;
    private ProductoGeneralCompra productoGeneral;
    private ProductoIndividualCompra productoEspecifico;
    private ProductoIndividualCompra productoEspecificoInd;
    private Compra compra;
    private String codDocumento;
    private BigDecimal total;
    private BigDecimal descuento;
    private boolean estadoDialogo;
    private boolean estadoDialogoEspecifico;
    private boolean mostrarCompra;
    private int codCompra;
    private boolean soloLectura;
    private DetallesCompra detalleCompra;
    private List<DetallesCompra> detallesCompra;
    private BigDecimal totalRegistro;
    private boolean mostrarTabla;
    private BigDecimal totalCompra;
    private boolean regCompra;
    private int cantidadEspecifico;
    private BigDecimal costoEspecifico;
    private String codUnico;
    private String estadoPInd;

    ///////////////Nuevas Variables ////////////////////////////////
    private boolean mostrarIngresoGeneral;
    private boolean mostrarIngresoEspecifico;
    private boolean mostrarPanelGeneral;

    /////////////// Variables para tener una tabla de los catalogos ////////
    private List<CatalagoProducto> listaCatalogos;
    private CatalagoProducto catalogoProductoSeleccionado;

    @ManagedProperty(value = "#{sessionMB}")
    private SessionMB sesion;

    @EJB
    private DistribuidorServicio distribServicio;

    @EJB
    private CatalogoServicio catalogoServicio;

    @EJB
    private ProductoGeneralCompraServicio productoGeneralServicio;

    @EJB
    private CompraServicio compraServicio;

    @PostConstruct
    public void inicializar() {
        msjDistri = "";
        mostrarPanel = false;
        distriEncontrado = new Distribuidor();
        catalogoEncontrado = new CatalagoProducto();
        tabGeneral = false;
        tabEspecifico = false;
        tabCompra = false;
        estadoDialogo = false;
        estadoDialogoEspecifico = false;
        soloLectura = false;
        detallesCompra = new ArrayList<DetallesCompra>();
        mostrarTabla = false;
        regCompra = false;
        compra = new Compra();
        productoEspecificoInd = new ProductoIndividualCompra();
        //compra.llenarCampos();

        // catalogo = new CatalagoProducto();
        this.mostrarIngresoGeneral = false;
        this.mostrarIngresoEspecifico = false;
        this.mostrarPanelGeneral = true;

        //iniciar la tabla del catalogo servicio
        this.listaCatalogos = catalogoServicio.obtenerTodos();

    }

    public void verificarDialogo() {
        if (estadoDialogo) {
            RequestContext.getCurrentInstance().execute("PF('nuevoCatalogo').show()");
        }
    }

    public void cerrarDialogo() {
        RequestContext.getCurrentInstance().execute("PF('nuevoCatalogo').hide()");
        estadoDialogo = false;
    }

    public void verificarDialogoEspecifico() {
        if (estadoDialogo) {
            RequestContext.getCurrentInstance().execute("PF('productoEspecifico').show()");
        }
    }

    public void cerrarDialogoEspecifico() {
        RequestContext.getCurrentInstance().execute("PF('productoEspecifico').hide()");
        estadoDialogo = false;
    }

    public void buscarDistribuidor() {
        System.out.println(codDistribuidor);
        distriEncontrado = distribServicio.buscarDistribuidor(codDistribuidor);
        if (distriEncontrado == null) {
            System.out.println("NNEncontrado");
            msjDistri = "Distribuidor No Encontrado";
            mostrarPanel = false;

        } else {

            System.out.println("Encontrado");
            msjDistri = "Distribuidor Encontrado";
            mostrarCompra = true;
            mostrarPanel = true;
            tabCompra = true;
        }
    }

    public void buscarProducto() {

        catalogoEncontrado = catalogoServicio.buscarCatalogo(codigoP);
        if (catalogoEncontrado == null) {
            System.out.println("NNEncontrado");
            catalogo = new CatalagoProducto();
            catalogo.setCodigoProducto(codigoP);
            RequestContext.getCurrentInstance().execute("PF('nuevoCatalogo').show()");
            estadoDialogo = true;
            //tabGeneral = true;

        } else {
            catalogo = catalogoEncontrado;
            System.out.println("Encontrado");
            if (catalogoEncontrado.getTipoProducto() == 'G') {
                System.out.println("general");
                tabGeneral = true;
                productoGeneral = new ProductoGeneralCompra();
                productoGeneral.setCantidadCaducada(0);
                productoGeneral.setCantidadMalEstado(0);
                cerrarDialogo();
                tabGeneral = true;
                mostrarPanel = false;
                soloLectura = true;

                mostrarIngresoGeneral = true;
                mostrarIngresoEspecifico = false;
                mostrarPanelGeneral = false;
            } else {
                System.out.println("Especifico");
                cantidadEspecifico = 1;
                mostrarPanel = false;
                tabGeneral = false;
                cerrarDialogo();

                tabEspecifico = true;

                mostrarIngresoEspecifico = true;
                mostrarIngresoGeneral = false;
                mostrarPanelGeneral = false;

            }
            // msjDistri = "Encontrado";
            // mostrarPanel = true;
        }

    }

    public void imprimir() {
        System.out.println("Jola");
    }

    public void registrarCompra() {
        compra.setRuc(distriEncontrado);
        compra.setFecha(new Date());
        PeriodoContable periodo = new PeriodoContable();
        periodo = compraServicio.buscar();
        compra.setCodigoPerido(periodo);
        compra.setNick(sesion.getUsuarioLogin());
        compra.setProductoGeneralCompraList(new ArrayList<ProductoGeneralCompra>());
        compra.setProductoIndividualCompraList(new ArrayList<ProductoIndividualCompra>());
        compra.setTipoDocumento("D");
        compraServicio.insertar(compra);
        codCompra = compra.getCodigoCompra();
        System.out.println("Cod: " + codCompra);
        //totalCompra = new BigDecimal("0.0");
    }

    public void registrarCatalogo() {
        System.out.println("Entrando");
        catalogo.setTipoProducto(tipoProd);
        catalogoServicio.insertar(catalogo);
        FacesMessage msg = new FacesMessage("Proceso Ejecutado Correctamente");
        FacesContext.getCurrentInstance().addMessage(null, msg);
        System.err.println(catalogo.getTipoProducto());
        if (tipoProd == 'g') {
            //RequestContext.getCurrentInstance().execute("PF('nuevoGeneral').show()");
            System.out.println("genral");
            tabGeneral = true;
            productoGeneral = new ProductoGeneralCompra();
            productoGeneral.setCantidadCaducada(0);
            productoGeneral.setCantidadMalEstado(0);
            cerrarDialogo();
            tabGeneral = true;
            mostrarPanel = false;
            soloLectura = true;

        } else {
            System.out.println("Espe");
            cantidadEspecifico = 1;
            mostrarPanel = false;
            tabGeneral = false;
            cerrarDialogo();

            tabEspecifico = true;
        }

    }

    public void registrarProductoGeneral() {
        if (regCompra) {

        } else {
            registrarCompra();
            regCompra = true;
        }
        productoGeneral.setCodigoCompra(compra);
        productoGeneral.setCodigoProducto(catalogo);
        productoGeneralServicio.insertar(productoGeneral);
        totalRegistro = new BigDecimal("0.0");
        totalRegistro = (productoGeneral.getCostoIndividual()).multiply(new BigDecimal(productoGeneral.getCantidad()));
        detalleCompra = new DetallesCompra(productoGeneral.getCantidad(), catalogo.getNombre(), productoGeneral.getCostoIndividual(), totalRegistro);
        detallesCompra.add(detalleCompra);
        FacesMessage msg = new FacesMessage("Guardado");
        FacesContext.getCurrentInstance().addMessage(null, msg);
        codigoP = "";
        mostrarTabla = true;
        tabGeneral = false;
        totalCompra = new BigDecimal("0.0");
        for (int i = 0; i < detallesCompra.size(); i++) {
            //if (i == detallesCompra.size()) {
            totalCompra = totalCompra.add(detallesCompra.get(i).getTotal());
            //}
        }
        mostrarPanel = true;

    }

    public void registrarProductoEspecifico() {

        if (regCompra) {

        } else {
            registrarCompra();
            regCompra = true;
        }
        System.out.println("Antes del for");
        // productoEspecifico.setEstadoFisico("Bueno");
        for (int i = 0; i < cantidadEspecifico; i++) {
            System.out.println("Despues del for");
            productoEspecifico = new ProductoIndividualCompra();
            RequestContext.getCurrentInstance().execute("PF('productoEspecifico').show()");
            estadoDialogoEspecifico = true;
            productoEspecifico.setUbicacion(catalogo.getUbicacion());
            productoEspecifico.setCodigoCompra(compra);
            productoEspecifico.setCodigoProducto(catalogo);
            productoEspecifico.setEstadoProceso("");
            productoEspecifico.setReservadoTemporalCompra(false);
            productoEspecifico.setFechaReservaTemporal(new Date());

        }

        codigoP = "";

        mostrarPanel = true;
        tabEspecifico = false;
    }

    public void guardarProdEspecifico() {

        //productoEspecifico.setCodigoUnico(codUnico);
        //  productoEspecifico.setEstadoFisico(estadoPInd);
        // productoEspecifico.setUbicacion(productoEspecificoInd.getUbicacion());
        productoEspecifico.setCosto(costoEspecifico);
        compraServicio.registrarProductoEspecifico(productoEspecifico);
        totalRegistro = new BigDecimal("0.0");
        totalRegistro = (productoEspecifico.getCosto()).multiply(new BigDecimal(1));
        detalleCompra = new DetallesCompra(1, catalogo.getNombre(), productoEspecifico.getCosto(), totalRegistro);
        detallesCompra.add(detalleCompra);
        FacesMessage msg = new FacesMessage("Guardado");
        FacesContext.getCurrentInstance().addMessage(null, msg);
        mostrarTabla = true;
        totalCompra = new BigDecimal("0.0");
        for (int i = 0; i < detallesCompra.size(); i++) {
            //  if (i == detallesCompra.size()-1) {
            totalCompra = totalCompra.add(detallesCompra.get(i).getTotal());
            //}
        }
        costoEspecifico = new BigDecimal("0.0");
        cantidadEspecifico = 1;
        cerrarDialogoEspecifico();
    }

    public String getCodDistribuidor() {
        return codDistribuidor;
    }

    public void setCodDistribuidor(String codDistribuidor) {
        this.codDistribuidor = codDistribuidor;
    }

    public Distribuidor getDistriEncontrado() {
        return distriEncontrado;
    }

    public void setDistriEncontrado(Distribuidor distriEncontrado) {
        this.distriEncontrado = distriEncontrado;
    }

    public String getMsjDistri() {
        return msjDistri;
    }

    public void setMsjDistri(String msjDistri) {
        this.msjDistri = msjDistri;
    }

    public Boolean getMostrarPanel() {
        return mostrarPanel;
    }

    public void setMostrarPanel(Boolean mostrarPanel) {
        this.mostrarPanel = mostrarPanel;
    }

    public int getCantidad() {
        return cantidad;
    }

    public void setCantidad(int cantidad) {
        this.cantidad = cantidad;
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

    public String getMsjCat() {
        return msjCat;
    }

    public void setMsjCat(String msjCat) {
        this.msjCat = msjCat;
    }

    public CatalagoProducto getCatalogo() {
        return catalogo;
    }

    public void setCatalogo(CatalagoProducto catalogo) {
        this.catalogo = catalogo;
    }

    public Character getTipoProd() {
        return tipoProd;
    }

    public void setTipoProd(Character tipoProd) {
        this.tipoProd = tipoProd;
    }

    public Boolean getTabGeneral() {
        return tabGeneral;
    }

    public void setTabGeneral(Boolean tabGeneral) {
        this.tabGeneral = tabGeneral;
    }

    public List<CatalagoProducto> getListaCatalogo() {
        return listaCatalogo;
    }

    public void setListaCatalogo(List<CatalagoProducto> listaCatalogo) {
        this.listaCatalogo = listaCatalogo;
    }

    public ProductoGeneralCompra getProductoGeneral() {
        return productoGeneral;
    }

    public void setProductoGeneral(ProductoGeneralCompra productoGeneral) {
        this.productoGeneral = productoGeneral;
    }

    public Compra getCompra() {
        return compra;
    }

    public void setCompra(Compra compra) {
        this.compra = compra;
    }

    public SessionMB getSesion() {
        return sesion;
    }

    public void setSesion(SessionMB sesion) {
        this.sesion = sesion;
    }

    public Boolean getTabCompra() {
        return tabCompra;
    }

    public void setTabCompra(Boolean tabCompra) {
        this.tabCompra = tabCompra;
    }

    public String getCodDocumento() {
        return codDocumento;
    }

    public void setCodDocumento(String codDocumento) {
        this.codDocumento = codDocumento;
    }

    public BigDecimal getTotal() {
        return total;
    }

    public void setTotal(BigDecimal total) {
        this.total = total;
    }

    public BigDecimal getDescuento() {
        return descuento;
    }

    public void setDescuento(BigDecimal descuento) {
        this.descuento = descuento;
    }

    public boolean getEstadoDialogo() {
        return estadoDialogo;
    }

    public void setEstadoDialogo(boolean estadoDialogo) {
        this.estadoDialogo = estadoDialogo;
    }

    public boolean getMostrarCompra() {
        return mostrarCompra;
    }

    public void setMostrarCompra(boolean mostrarCompra) {
        this.mostrarCompra = mostrarCompra;
    }

    public int getCodCompra() {
        return codCompra;
    }

    public void setCodCompra(int codCompra) {
        this.codCompra = codCompra;
    }

    public boolean getSoloLectura() {
        return soloLectura;
    }

    public void setSoloLectura(boolean soloLectura) {
        this.soloLectura = soloLectura;
    }

    public List<DetallesCompra> getDetallesCompra() {
        return detallesCompra;
    }

    public void setDetallesCompra(List<DetallesCompra> detallesCompra) {
        this.detallesCompra = detallesCompra;
    }

    public BigDecimal getTotalRegistro() {
        return totalRegistro;
    }

    public void setTotalRegistro(BigDecimal totalRegistro) {
        this.totalRegistro = totalRegistro;
    }

    public boolean getMostrarTabla() {
        return mostrarTabla;
    }

    public void setMostrarTabla(boolean mostrarTabla) {
        this.mostrarTabla = mostrarTabla;
    }

    public BigDecimal getTotalCompra() {
        return totalCompra;
    }

    public void setTotalCompra(BigDecimal totalCompra) {
        this.totalCompra = totalCompra;
    }

    public ProductoIndividualCompra getProductoEspecifico() {
        return productoEspecifico;
    }

    public void setProductoEspecifico(ProductoIndividualCompra productoEspecifico) {
        this.productoEspecifico = productoEspecifico;
    }

    public boolean getEstadoDialogoEspecifico() {
        return estadoDialogoEspecifico;
    }

    public void setEstadoDialogoEspecifico(boolean estadoDialogoEspecifico) {
        this.estadoDialogoEspecifico = estadoDialogoEspecifico;
    }

    public boolean getRegCompra() {
        return regCompra;
    }

    public void setRegCompra(boolean regCompra) {
        this.regCompra = regCompra;
    }

    public Boolean getTabEspecifico() {
        return tabEspecifico;
    }

    public void setTabEspecifico(Boolean tabEspecifico) {
        this.tabEspecifico = tabEspecifico;
    }

    public int getCantidadEspecifico() {
        return cantidadEspecifico;
    }

    public void setCantidadEspecifico(int cantidadEspecifico) {
        this.cantidadEspecifico = cantidadEspecifico;
    }

    public BigDecimal getCostoEspecifico() {
        return costoEspecifico;
    }

    public void setCostoEspecifico(BigDecimal costoEspecifico) {
        this.costoEspecifico = costoEspecifico;
    }

    public ProductoIndividualCompra getProductoEspecificoInd() {
        return productoEspecificoInd;
    }

    public void setProductoEspecificoInd(ProductoIndividualCompra productoEspecificoInd) {
        this.productoEspecificoInd = productoEspecificoInd;
    }

    public String getCodUnico() {
        return codUnico;
    }

    public void setCodUnico(String codUnico) {
        this.codUnico = codUnico;
    }

    public String getEstadoPInd() {
        return estadoPInd;
    }

    public void setEstadoPInd(String estadoPInd) {
        this.estadoPInd = estadoPInd;
    }

    public boolean isMostrarIngresoGeneral() {
        return mostrarIngresoGeneral;
    }

    public void setMostrarIngresoGeneral(boolean mostrarIngresoGeneral) {
        this.mostrarIngresoGeneral = mostrarIngresoGeneral;
    }

    public boolean isMostrarIngresoEspecifico() {
        return mostrarIngresoEspecifico;
    }

    public void setMostrarIngresoEspecifico(boolean mostrarIngresoEspecifico) {
        this.mostrarIngresoEspecifico = mostrarIngresoEspecifico;
    }

    public boolean isMostrarPanelGeneral() {
        return mostrarPanelGeneral;
    }

    public void setMostrarPanelGeneral(boolean mostrarPanelGeneral) {
        this.mostrarPanelGeneral = mostrarPanelGeneral;
    }

}
