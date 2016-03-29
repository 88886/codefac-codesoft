/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ec.com.codesoft.web.admin.catalogo;

import ec.com.codesoft.model.CatalagoProducto;
import ec.com.codesoft.model.ProductoGeneralCompra;
import ec.com.codesoft.modelo.servicios.CatalogoServicio;
import ec.com.codesoft.modelo.servicios.CompraServicio;
import java.io.Serializable;
import java.math.BigDecimal;
import java.util.List;
import javax.annotation.PostConstruct;
import javax.ejb.EJB;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;

/**
 *
 * @author carlo
 */
@ViewScoped
@ManagedBean
public class ReporteCatalogoMB implements Serializable
{
    @EJB
    private CatalogoServicio catologoServicio ;
    
    private List<CatalagoProducto> listaCatalogo;
    
    @EJB
    private CompraServicio compraServicio;
    
    /**
     * Variable para obtener el listado de las ultimas compras con los precios
     */
    private List<ProductoGeneralCompra> productoGeneralCompraList;
    
    
    @PostConstruct
    public void postConstruct()
    {
        listaCatalogo=catologoServicio.obtenerTodos();
    }
    
    /**
     * Genera el reporte de los productos por distribuidor
     */
    public void reporteProductoPorDistribuidor()
    {
        System.out.println("generando reporte por distribuidor");
        productoGeneralCompraList=compraServicio.getUltimosProductosComprados();
//        for (Object detalles : productoGeneralCompraList) 
//        {
//            System.out.println(detalles);
        //}
    //    System.out.println(productoGeneralCompraList.size());
    }
    
    /**
     * Metodo para agregar el iva a una cantidad
     * @return 
     */
    public BigDecimal addIva(BigDecimal cantidad)
    {
        BigDecimal resultado=new BigDecimal(0);
               
        //resultado=cantidad.setScale(2,BigDecimal.ROUND_UP);
        
        resultado=cantidad.multiply(new BigDecimal(1.12f));
       
        resultado=resultado.setScale(0,BigDecimal.ROUND_UP);
        System.out.println("iva"+resultado); 
       return resultado;
    }
    
    public BigDecimal iva(BigDecimal cantidad)
    {
        BigDecimal resultado=new BigDecimal(0);
               
        //resultado=cantidad.setScale(2,BigDecimal.ROUND_UP);
        
        resultado=cantidad.multiply(new BigDecimal(0.12f));
       
        resultado=resultado.setScale(0,BigDecimal.ROUND_UP);
        System.out.println("iva"+resultado); 
       return resultado;
    }

    public List<CatalagoProducto> getListaCatalogo() {
        return listaCatalogo;
    }

    public void setListaCatalogo(List<CatalagoProducto> listaCatalogo) {
        this.listaCatalogo = listaCatalogo;
    }   
    
    
}
