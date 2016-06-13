/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ec.com.codesoft.web.admin.distribuidor;

import ec.com.codesoft.model.Distribuidor;
import ec.com.codesoft.model.ProductoGeneralCompra;
import ec.com.codesoft.modelo.servicios.CatalogoServicio;
import ec.com.codesoft.modelo.servicios.DistribuidorServicio;
import java.io.Serializable;
import java.util.ArrayList;
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
public class productoDistribuidorMB implements Serializable {

    private List<Distribuidor> distribuidores;

    @EJB
    private DistribuidorServicio distribuidorServicio;

    @EJB
    private CatalogoServicio catalogoServicio;

    @PostConstruct
    public void inicializar() {
        distribuidores = distribuidorServicio.obtenerTodos();
    }

    public List<ProductoGeneralCompra> devolverProductos(Distribuidor distri) {
        List<ProductoGeneralCompra> productosDevolver = new ArrayList<ProductoGeneralCompra>();
        
        for (String productos : catalogoServicio.buscarProdutos(distri.getRuc())) {
            //catalogoServicio.obtenerUltimoProductoDistribuidor(productos, distri.getRuc());
            productosDevolver.add(catalogoServicio.obtenerUltimoProductoDistribuidor(productos, distri.getRuc()));
        }
        return productosDevolver;

    }

    public DistribuidorServicio getDistribuidorServicio() {
        return distribuidorServicio;
    }

    public void setDistribuidorServicio(DistribuidorServicio distribuidorServicio) {
        this.distribuidorServicio = distribuidorServicio;
    }

    public List<Distribuidor> getDistribuidores() {
        return distribuidores;
    }

}
