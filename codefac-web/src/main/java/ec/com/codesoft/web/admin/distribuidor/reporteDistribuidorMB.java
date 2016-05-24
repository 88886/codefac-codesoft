/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ec.com.codesoft.web.admin.distribuidor;

import ec.com.codesoft.model.CatalagoProducto;
import ec.com.codesoft.model.Distribuidor;
import ec.com.codesoft.model.ProductoGeneralCompra;
import ec.com.codesoft.modelo.servicios.CatalogoServicio;
import ec.com.codesoft.modelo.servicios.DistribuidorServicio;
import java.io.Serializable;
import java.util.List;
import javax.annotation.PostConstruct;
import javax.ejb.EJB;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;

/**
 *
 * @author Suco
 */
@ManagedBean
@ViewScoped
public class reporteDistribuidorMB implements Serializable {

    private List<Distribuidor> distribuidores;
    private List<ProductoGeneralCompra> preciosProveedor;
    private List<CatalagoProducto> catalagoProductosFiltrados;
    
    @EJB
    private DistribuidorServicio distribuidorServicio;
    
     @EJB
    private CatalogoServicio catalogoServicio;

    @PostConstruct
    public void inicializar() {
        distribuidores = distribuidorServicio.obtenerTodos();
        
        //preciosProveedor = catalogoServicio.listaPreciosProductosGCompra(catalogo.getCodigoProducto());
    }
    
    public List<CatalagoProducto> devolverProductos(Distribuidor distri){
        return catalogoServicio.obtenerProductosDistribuidor(distri.getRuc());
    }

    public List<Distribuidor> getDistribuidores() {
        return distribuidores;
    }

    public void setDistribuidores(List<Distribuidor> distribuidores) {
        this.distribuidores = distribuidores;
    }

}
