/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ec.com.codesoft.modelo.servicios;

import ec.com.codesoft.model.DetalleProductoGeneral;
import ec.com.codesoft.model.DetalleProductoIndividual;
import ec.com.codesoft.model.ProductoGeneralVenta;
import ec.com.codesoft.model.ProductoIndividualCompra;
import ec.com.codesoft.model.Venta;
import ec.com.codesoft.modelo.facade.DetalleProductoGeneralFacade;
import ec.com.codesoft.modelo.facade.DetalleProductoIndividualFacade;
import ec.com.codesoft.modelo.facade.ProductoGeneralCompraFacade;
import ec.com.codesoft.modelo.facade.ProductoGeneralVentaFacade;
import ec.com.codesoft.modelo.facade.ProductoIndividualCompraFacade;
import ec.com.codesoft.modelo.facade.VentaFacade;
import java.util.List;
import javax.ejb.EJB;
import javax.ejb.LocalBean;
import javax.ejb.Stateless;

/**
 *
 * @author Suco
 */
@LocalBean
@Stateless
public class FacturaServicio {

    @EJB
    ProductoIndividualCompraFacade productoIndividualFacade;

    @EJB
    ProductoGeneralCompraFacade productoGeneralFacade;
    
    @EJB
    ProductoGeneralVentaFacade productoGeneralVentaFacade;

    @EJB
    DetalleProductoIndividualFacade detalleIndividualFacade;

    @EJB
    DetalleProductoGeneralFacade detalleGeneralFacade;

    @EJB
    VentaFacade ventaFacade;

    public int devolverStockIndividual(String codP) {
        return (productoIndividualFacade.findStockIndividual(codP).intValue());
    }

    public ProductoGeneralVenta devolverStockGeneral(String codP) {

        return productoGeneralFacade.findGeneralCodP(codP);
    }

    public void insertarDetalleProductoIndividual(List<DetalleProductoIndividual> detalles) {

        for (int i = 0; i < detalles.size(); i++) {
            detalleIndividualFacade.create(detalles.get(i));
        }
    }

    public void insertarDetallesFacturaProductoGeneral(List<DetalleProductoGeneral> detalles) {

        for (int i = 0; i < detalles.size(); i++) {
            detalleGeneralFacade.create(detalles.get(i));
        }
    }

    public List<ProductoIndividualCompra> obtenerProductoIndivudualCantidad(int cantidad, String codP) {

        return detalleIndividualFacade.findProductosIndividualCantidad(cantidad, codP);
        
    }

    public ProductoIndividualCompra devolverIndividualCod(String cod, String codCat) {
        return productoIndividualFacade.findProdIndividual(cod, codCat);
    }

    public void guardarFactura(Venta venta) {

        ventaFacade.create(venta);
    }
    
    public void actulizarStockGeneral(ProductoGeneralVenta productoGeneral){
        productoGeneralVentaFacade.edit(productoGeneral);
    }
    
    public void actulizarStocIndividual(ProductoIndividualCompra prodIndividual){
       
        productoIndividualFacade.edit(prodIndividual);
    }
    
    public ProductoIndividualCompra devolverProductoIndividual(String codUnico){
        
        return productoIndividualFacade.findProdIndividualCodUnico(codUnico);
    }
    
    /**
     * Obtiene el codigo de la siguiente factura
     * @return 
     */
    public Integer getCodigoFactura()
    {
        return (ventaFacade.getCodigoUltimaFactura()+1);
    }

}
