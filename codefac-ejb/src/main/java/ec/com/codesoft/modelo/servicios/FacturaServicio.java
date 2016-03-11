/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ec.com.codesoft.modelo.servicios;

import ec.com.codesoft.model.Banco;
import ec.com.codesoft.model.CatalagoProducto;
import ec.com.codesoft.model.DetalleProductoGeneral;
import ec.com.codesoft.model.DetalleProductoIndividual;
import ec.com.codesoft.model.Intereses;
import ec.com.codesoft.model.ProductoGeneralVenta;
import ec.com.codesoft.model.ProductoIndividualCompra;
import ec.com.codesoft.model.Venta;
import ec.com.codesoft.modelo.facade.BancoFacade;
import ec.com.codesoft.modelo.facade.CompraFacade;
import ec.com.codesoft.modelo.facade.DetalleProductoGeneralFacade;
import ec.com.codesoft.modelo.facade.DetalleProductoIndividualFacade;
import ec.com.codesoft.modelo.facade.InteresesFacade;
import ec.com.codesoft.modelo.facade.ProductoGeneralCompraFacade;
import ec.com.codesoft.modelo.facade.ProductoGeneralVentaFacade;
import ec.com.codesoft.modelo.facade.ProductoIndividualCompraFacade;
import ec.com.codesoft.modelo.facade.VentaFacade;
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
public class FacturaServicio {
    
    @EJB
    CompraFacade compraFacade;

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
    
    @EJB
    InteresesFacade interesFacade;
    
    @EJB
    BancoFacade bancofacade;
    
    

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
    
    
    @TransactionAttribute(TransactionAttributeType.REQUIRED)
    public void anularVenta(Venta venta)
    {
        venta.setEstado("anulado");        
        ventaFacade.edit(venta);
        
        List<DetalleProductoGeneral> listaGeneral=venta.getDetalleProductoGeneralList();
        for (DetalleProductoGeneral detalle : listaGeneral) 
        {
            ProductoGeneralVenta productoGeneralVenta=detalle.getCodigoProducto().getProductoGeneralVenta();
            productoGeneralVenta.addStock(detalle.getCantidad());   
            productoGeneralVentaFacade.edit(productoGeneralVenta);
           
        }
        
        List<DetalleProductoIndividual> listaEspecifico=venta.getDetalleProductoIndividualList();
        
        for (DetalleProductoIndividual detalle : listaEspecifico) 
        {
            ProductoIndividualCompra productoIndividual=detalle.getCodigoUnico();
            productoIndividualFacade.edit(productoIndividual);
            
        }
        
        
        
        
    }
    
    /**
     * Busca la factura por el codigo principal
     */
    public Venta buscarVentaById(Integer id)
    {
        return ventaFacade.find(id);
    }
    
    ///////////////////////METODOS GET AND SET

  //  public int obtenerProductoIndivudualCantidad(String codP) {

      //  return detalleIndividualFacade.findProductosIndividualCantidad(cantidad, codP);
        
   // }

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
     * Obtiene todas las ventas realizadas
     * @return 
     */
    public List<Venta> obtenerVentas()
    {
        return ventaFacade.getVentas();
        //return ventaFacade.findAll();
    }
    
    /**
     * Obtiene el codigo de la siguiente factura
     * @return 
     */
    public Integer getCodigoFactura()
    {
        Integer codigo=ventaFacade.getCodigoUltimaFactura();
        if(codigo==null)
            return 1;
        
        return codigo;
    }
    
    /**
     * Busca una factura segun el numero del documento de la venta
     * @return 
     */
    public Venta buscarPorCodigoDocumento(Integer codigo)
    {
        return ventaFacade.findCodigoDocumento(codigo);
    }
    
    public List<Intereses> devolverIntereses(){
        return interesFacade.findAll();
    } 
    
    public List<Banco> devolverBancos(){
        return bancofacade.findAll();
    } 
    public Banco devolverInteresBanco(String nombre){
        return bancofacade.findInteresesBanco(nombre);
    }
}
