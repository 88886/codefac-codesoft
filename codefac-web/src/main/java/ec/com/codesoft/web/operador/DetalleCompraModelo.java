/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ec.com.codesoft.web.operador;

import ec.com.codesoft.model.ProductoGeneralCompra;
import ec.com.codesoft.model.ProductoIndividualCompra;
import java.math.BigDecimal;

/**
 *
 * @author carlo
 */
public class DetalleCompraModelo 
{
    private String codigo;
    private String nombre;
    private String descripcion;
    private Integer cantidad;
    private BigDecimal costoIndividual;
    private BigDecimal subtotal;
    
    private Boolean tipoGeneral;
    private ProductoGeneralCompra productoGeneral;
    private ProductoIndividualCompra productoIndividual;

    public DetalleCompraModelo() {
    }

    /**
     * Constructor para el producto general
     * @param cantidad
     * @param costoIndividual
     * @param productoGeneral 
     */
    public DetalleCompraModelo(ProductoGeneralCompra productoGeneral) {
        this.cantidad = productoGeneral.getCantidad();
        this.costoIndividual = productoGeneral.getCostoIndividual();
        this.productoGeneral = productoGeneral;
        this.codigo=productoGeneral.getCodigoProducto().getCodigoProducto();
        this.nombre=productoGeneral.getCodigoProducto().getNombre();
        this.descripcion=productoGeneral.getCodigoProducto().getDescripcion();
        this.subtotal=costoIndividual.multiply(new BigDecimal(cantidad));      
        this.tipoGeneral=true;
    }
    
    
        public DetalleCompraModelo(ProductoIndividualCompra productoIndividual) {
        this.cantidad =1;
        this.costoIndividual = productoIndividual.getCosto();
        this.productoIndividual = productoIndividual;
        this.codigo=productoIndividual.getCodigoProducto().getCodigoProducto();
        this.nombre=productoIndividual.getCodigoProducto().getNombre();
        this.descripcion="("+ productoIndividual.getCodigoUnico() +" ) "+productoIndividual.getCodigoProducto().getDescripcion();
        this.subtotal=costoIndividual.multiply(new BigDecimal(cantidad));      
        this.tipoGeneral=false;
    }
    

    public DetalleCompraModelo(String codigo, String nombre, String descripcion, Integer cantidad, BigDecimal costoIndividual, BigDecimal subtotal, Boolean codigoGeneral) {
        this.codigo = codigo;
        this.nombre = nombre;
        this.descripcion = descripcion;
        this.cantidad = cantidad;
        this.costoIndividual = costoIndividual;
        this.subtotal = subtotal;
        this.tipoGeneral = codigoGeneral;
    }

    public String getCodigo() {
        return codigo;
    }

    public void setCodigo(String codigo) {
        this.codigo = codigo;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getDescripcion() {
        return descripcion;
    }

    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }

    public Integer getCantidad() {
        return cantidad;
    }

    public void setCantidad(Integer cantidad) {
        this.cantidad = cantidad;
    }

    public BigDecimal getCostoIndividual() {
        return costoIndividual;
    }

    public void setCostoIndividual(BigDecimal costoIndividual) {
        this.costoIndividual = costoIndividual;
    }

    public BigDecimal getSubtotal() {
        return subtotal;
    }

    public void setSubtotal(BigDecimal subtotal) {
        this.subtotal = subtotal;
    }

    public Boolean getCodigoGeneral() {
        return tipoGeneral;
    }

    public void setCodigoGeneral(Boolean codigoGeneral) {
        this.tipoGeneral = codigoGeneral;
    }

    public ProductoGeneralCompra getProductoGeneral() {
        return productoGeneral;
    }

    public void setProductoGeneral(ProductoGeneralCompra productoGeneral) {
        this.productoGeneral = productoGeneral;
    }

    public ProductoIndividualCompra getProductoIndividual() {
        return productoIndividual;
    }

    public void setProductoIndividual(ProductoIndividualCompra productoIndividual) {
        this.productoIndividual = productoIndividual;
    }

    @Override
    public String toString() {
        return "DetalleCompraModelo{" + "codigo=" + codigo + ", nombre=" + nombre + ", descripcion=" + descripcion + ", cantidad=" + cantidad + ", costoIndividual=" + costoIndividual + ", subtotal=" + subtotal + ", tipoGeneral=" + tipoGeneral + ", productoGeneral=" + productoGeneral + ", productoIndividual=" + productoIndividual + '}';
    }
    
    
    
}
