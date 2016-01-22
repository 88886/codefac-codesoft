/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ec.com.codesoft.web.operador;

import ec.com.codesoft.model.CatalagoProducto;
import ec.com.codesoft.model.Distribuidor;
import ec.com.codesoft.modelo.servicios.CatalogoServicio;
import ec.com.codesoft.modelo.servicios.DistribuidorServicio;
import java.awt.Event;
import java.io.Serializable;
import java.util.List;
import javax.annotation.PostConstruct;
import javax.ejb.EJB;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;
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
    private String tipoProd;
    private Boolean dlgCatalogo;
    private List<CatalagoProducto> listaCatalogo; 

    @EJB
    private DistribuidorServicio distribServicio;

    @EJB
    private CatalogoServicio catalogoServicio;

    @PostConstruct
    public void inicializar() {
        msjDistri = "";
        mostrarPanel = false;
        distriEncontrado = new Distribuidor();
        catalogoEncontrado = new CatalagoProducto();
        dlgCatalogo=false;
        catalogo=new CatalagoProducto();
        
        
    }

    public void buscarDistribuidor() {

        distriEncontrado = distribServicio.buscarDistribuidor(codDistribuidor);
        if (distriEncontrado == null) {
            System.out.println("NNEncontrado");
            msjDistri = "Distribuidor No Encontrado";
            mostrarPanel = false;

        } else {
            System.out.println("Encontrado");
            msjDistri = "Distribuidor Encontrado";
            mostrarPanel = true;
        }
    }

    public void buscarProducto() {

        catalogoEncontrado = catalogoServicio.buscarCatalogo(codigoP);
        if (catalogoEncontrado == null) {
            System.out.println("NNEncontrado");
            //catalogo=new CatalagoProducto();
            catalogo.setCodigoProducto(codigoP);
            RequestContext. getCurrentInstance (). execute ("PF('nuevoCatalogo').show()"); 
            dlgCatalogo=true;
           

        } else {
            System.out.println("Encontrado");
           // msjDistri = "Encontrado";
           // mostrarPanel = true;
        }

    }
    public void iimprimir(){
        System.out.println("Jola");
    }
    
    public void registrarCatalogo(){
        System.out.println("Entrando");
        if(tipoProd == "General"){
            catalogo.setTipoProducto('G');
        }else{
            catalogo.setTipoProducto('E');
        }
        
        catalogoServicio.insertar(catalogo);
        
        
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

    public String getTipoProd() {
        return tipoProd;
    }

    public void setTipoProd(String tipoProd) {
        this.tipoProd = tipoProd;
    }

    public Boolean getDlgCatalogo() {
        return dlgCatalogo;
    }

    public void setDlgCatalogo(Boolean dlgCatalogo) {
        this.dlgCatalogo = dlgCatalogo;
    }

    public List<CatalagoProducto> getListaCatalogo() {
        return listaCatalogo;
    }

    public void setListaCatalogo(List<CatalagoProducto> listaCatalogo) {
        this.listaCatalogo = listaCatalogo;
    }
    
    
    
    
    
    
    
    
    
    
    
    

}
