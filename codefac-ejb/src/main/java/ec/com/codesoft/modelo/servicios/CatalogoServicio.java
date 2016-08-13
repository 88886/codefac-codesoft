/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ec.com.codesoft.modelo.servicios;

import ec.com.codesoft.model.CatalagoProducto;
import ec.com.codesoft.model.ProductoGeneralCompra;
import ec.com.codesoft.model.ProductoGeneralVenta;
import ec.com.codesoft.model.Servicios;
import ec.com.codesoft.modelo.facade.CatalagoProductoFacade;
import ec.com.codesoft.modelo.facade.ProductoGeneralCompraFacade;
import ec.com.codesoft.modelo.facade.ProductoGeneralVentaFacade;
import ec.com.codesoft.modelo.facade.ServiciosFacade;
import java.util.List;
import javax.ejb.EJB;
import javax.ejb.LocalBean;
import javax.ejb.Stateless;
import javax.ejb.TransactionAttribute;
import javax.ejb.TransactionAttributeType;

/**
 *
 * @author Suco
 */
@LocalBean
@Stateless
public class CatalogoServicio {

    @EJB
    private ProductoGeneralCompraFacade productoGCFacade;

    @EJB
    private CatalagoProductoFacade catalogoFacade;

    @EJB
    private ProductoGeneralVentaFacade productoGeneralFacade;
    
    @EJB
    private ServiciosFacade serviciosFacade;

    @TransactionAttribute(TransactionAttributeType.REQUIRED)
    public void insertar(CatalagoProducto catalogo) {
        //verifica si el producto es general o inididual
        this.catalogoFacade.create(catalogo);

        if (catalogo.getTipoProducto().toString().toUpperCase().equals("G")) {
            ProductoGeneralVenta productoGeneral = new ProductoGeneralVenta();
            productoGeneral.setCantidadBaja(0);
            productoGeneral.setCantidadCaducada(0);
            productoGeneral.setCantidadDisponible(0);
            productoGeneral.setCantidadRobado(0);
            productoGeneral.setCantidadVendida(0);
            productoGeneral.setCatalagoProducto(catalogo);
           // productoGeneral.setCodigoProducto(catalogo.getCodigoProducto());
            //productoGeneral.setCodigo(Integer.SIZE);
            // System.out.println("CODIGO: "+productoGeneral.getCodigoProducto()); 
            this.productoGeneralFacade.create(productoGeneral);

            catalogo.setProductoGeneralVenta(productoGeneral);
        }

       // this.catalogoFacade.refresh(catalogo);
    }

    public void agregarCantidadProductoGeneral(ProductoGeneralVenta producto, int cantidad) {
        producto.agregarProductos(cantidad);
        productoGeneralFacade.edit(producto);
    }

    public void actualizar(CatalagoProducto catalogo) {
        catalogoFacade.edit(catalogo);
    }

    public void eliminar(CatalagoProducto catalogo) {
        catalogoFacade.remove(catalogo);
    }

    public List<CatalagoProducto> obtenerTodos() {

        return catalogoFacade.findAll();
    }

    public CatalagoProducto buscarCatalogo(String codigoP) {
        return catalogoFacade.findCatalogo(codigoP);

    }

    public boolean verificarExisteProducto(String codigoP) {
        if (catalogoFacade.findCatalogo(codigoP) == null) {
            return false;
        } else {
            return true;
        }
    }

    /**
     * Obtiene un listado de los productos generales por cada distribuidor
     *
     * @return
     */
    public List<ProductoGeneralCompra> listaPreciosProductosGCompra(String codigo) {
        return productoGCFacade.listaCostosProductoGeneral(codigo);
    }

    public List<CatalagoProducto> obtenerProductosDistribuidor(String codDistribuidor) {
        return productoGCFacade.obtenerProductosDistribuidor(codDistribuidor);
    }

    public List<String> buscarProdutos(String codDistri) {
        return productoGCFacade.obtenerProductos(codDistri);

    }

    public ProductoGeneralCompra obtenerUltimoProductoDistribuidor(String codPRoducto,String ruc) {
        return productoGCFacade.getUltimoProductoByDistribuidor(codPRoducto,ruc);

    }
    
    /**
     * Obtener el listado de los servicios
     * @return List
     */
    public List<Servicios> obtenerServicios(){
        return serviciosFacade.findAll();
    }
}
