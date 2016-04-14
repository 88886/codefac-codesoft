/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ec.com.codesoft.modelo.servicios;

import ec.com.codesoft.model.CatalagoProducto;
import ec.com.codesoft.model.Compra;
import ec.com.codesoft.model.PeriodoContable;
import ec.com.codesoft.model.ProductoGeneralCompra;
import ec.com.codesoft.model.ProductoGeneralVenta;
import ec.com.codesoft.model.ProductoIndividualCompra;
import ec.com.codesoft.modelo.facade.CompraFacade;
import ec.com.codesoft.modelo.facade.PeriodoContableFacade;
import ec.com.codesoft.modelo.facade.ProductoGeneralCompraFacade;
import ec.com.codesoft.modelo.facade.ProductoGeneralVentaFacade;
import ec.com.codesoft.modelo.facade.ProductoIndividualCompraFacade;
import java.math.BigDecimal;
import java.util.Date;
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
public class CompraServicio {

    @EJB
    CompraFacade compraFacade;

    @EJB
    PeriodoContableFacade periodoFacade;

    @EJB
    ProductoGeneralCompraFacade productoGFacade;

    @EJB
    ProductoIndividualCompraFacade productoEspeFacade;

    @EJB
    private ProductoGeneralVentaFacade productoGeneralFacade;
    
    @EJB
    private ProductoGeneralCompraFacade productoGeneralCompraFacade;

    public void insertar(Compra compra) {
        compra.setFecha(new Date());
        this.compraFacade.create(compra);
        Integer codigo = compra.getCodigoCompra();
        List<ProductoGeneralCompra> detalle1 = compra.getProductoGeneralCompraList();
        List<ProductoIndividualCompra> detalle2 = compra.getProductoIndividualCompraList();
        System.out.println(compraFacade);
        //Detalle General Actualizado        
        for (ProductoGeneralCompra detalle : detalle1) 
        {
            detalle.setCodigoCompra(compra);
            productoGFacade.edit(detalle);
            
            //actualizar el stock en las tablas correspondientes
            //detalle.getCodigoProducto().getco
            //System.out.println("codigo producto: "+detalle.getCodigoProducto().getCodigoProducto());
            ProductoGeneralVenta productoVenta=productoGeneralFacade.findByCodigoProducto(detalle.getCodigoProducto().getCodigoProducto());
            productoVenta.agregarProductos(detalle.getCantidad());
            productoGeneralFacade.edit(productoVenta);
            //detalle.getCantidad();
        }

        //Detalle Especifico Actualizado
        for (ProductoIndividualCompra detalle : detalle2) 
        {
            detalle.setEstadoProceso("comprado");
            detalle.setCodigoCompra(compra);
            productoEspeFacade.edit(detalle);
        }

    }
    
    /**
     * Obtiene todas las compras realizadas
     */
    public List<Compra> obtenerTodos()
    {
        return compraFacade.findOrderDesc();
    }
    
    public void consultar()
    {
        productoGeneralFacade.findByCodigoProducto("123");
    }
    
    public Compra findCompra(Integer codigo)
    {
        return compraFacade.find(codigo);
    }

    /**
     * Actualiza la compra con nuevos valores
     * @param compra 
     */
    public void actualizar(Compra compra) 
    {
        this.compraFacade.create(compra);
        
        Integer codigo = compra.getCodigoCompra();
        
        List<ProductoGeneralCompra> detalle1 = compra.getProductoGeneralCompraList();
        List<ProductoIndividualCompra> detalle2 = compra.getProductoIndividualCompraList();
        
        System.out.println(compraFacade);
        //Detalle General Actualizado        
        for (ProductoGeneralCompra detalle : detalle1) 
        {
            detalle.setCodigoCompra(compra);
            productoGFacade.edit(detalle);
            
            //actualizar el stock en las tablas correspondientes
            //detalle.getCodigoProducto().getco
            //System.out.println("codigo producto: "+detalle.getCodigoProducto().getCodigoProducto());
            ProductoGeneralVenta productoVenta=productoGeneralFacade.findByCodigoProducto(detalle.getCodigoProducto().getCodigoProducto());
            productoVenta.agregarProductos(detalle.getCantidad());
            productoGeneralFacade.edit(productoVenta);
            //detalle.getCantidad();
        }

        //Detalle Especifico Actualizado
        for (ProductoIndividualCompra detalle : detalle2) {
            
            detalle.setCodigoCompra(compra);
            productoEspeFacade.edit(detalle);
        }
    }
    
    

    public void eliminar(Compra compra) {
        compraFacade.remove(compra);
    }

    public PeriodoContable findPeriodo() {

        return periodoFacade.find("1");
    }

    public PeriodoContable buscar() {

        return periodoFacade.find(new Integer(1));
    }

    public void registrarProductoGeneral(ProductoGeneralCompra producto) {

        productoGFacade.create(producto);

    }

    public void registrarProductoEspecifico(ProductoIndividualCompra productoEspecifico) {

        productoEspeFacade.create(productoEspecifico);
    }    
    
    /**
     * Obtiene las productos generales de las ultimas compras
     * @return 
     */
    public List<ProductoGeneralCompra> getUltimosProductosComprados()
    {
        return productoGFacade.listaUltimosCostosProductoGeneral();
    }
    
    
    /**
     * Obtiene el ultimo costo por distribuidor enviado el codigo del catalago
     * y el codigo del distribuidor
     * @param idCatalogo
     * @param rucDistribuidor
     * @return 
     */
    public BigDecimal obtenerUltimoCostoDistribuidor(CatalagoProducto producto, String rucDistribuidor)
    {
        BigDecimal resultado;
        if(producto.getTipoProducto().equals('G'))
        {
            resultado=productoGeneralCompraFacade.getUltimoCostoProductoByDistribuidor(producto.getCodigoProducto(), rucDistribuidor);
        }
        else
        {
            resultado=productoGeneralCompraFacade.getUltimoCostoProductoEspecifico(producto.getCodigoDistribuidor(), rucDistribuidor);
        }
                
        
        //si el resultado es nulo envia el costo referencial
        if(resultado==null)
        {
            resultado=producto.getCosto();
        }
        return resultado;
    }
    
    /**
     * Busca si existe el producto individual con el codigo
     * @return 
     */
    public boolean existenciaProductoIndividual(String codigoGeneral, String codigoIndividual)
    {
        ProductoIndividualCompra producto= productoEspeFacade.checkExistence(codigoGeneral,codigoIndividual);
        if(producto!=null)
        {
            return true;
        }
        return false;
    }
    
    
    
    public boolean getStockProductosGeneral(String codigoCatalogo)
    {
        return true;
    }
    

}
