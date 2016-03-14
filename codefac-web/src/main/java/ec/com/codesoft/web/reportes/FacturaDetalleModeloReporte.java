/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ec.com.codesoft.web.reportes;

import java.math.BigDecimal;



/**
 *
 * @author carlo
 */
public class FacturaDetalleModeloReporte
{
    private String cantidad;
    private String codigo;
    private String descripcion;
    private String descuento;
    private String precioUnitario;
    private String total;
    

    public FacturaDetalleModeloReporte() {
    }
    
    

    public FacturaDetalleModeloReporte(String cantidad, String codigo, String descripcion, String descuento, String precioUnitario, String total) {
        this.cantidad = cantidad;
        this.codigo = codigo;
        this.descripcion = descripcion;
        this.descuento = new BigDecimal(descuento).setScale(2,BigDecimal.ROUND_DOWN).toString();
        this.precioUnitario = new BigDecimal(precioUnitario).setScale(2,BigDecimal.ROUND_DOWN).toString();
        this.total = new BigDecimal(total).setScale(2,BigDecimal.ROUND_DOWN).toString();
    }

    public String getCantidad() {
        return cantidad;
    }

    public void setCantidad(String cantidad) {
        this.cantidad = cantidad;
    }

    public String getCodigo() {
        return codigo;
    }

    public void setCodigo(String codigo) {
        this.codigo = codigo;
    }

    public String getDescripcion() {
        return descripcion;
    }

    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }

    public String getDescuento() {
        return descuento;
    }

    public void setDescuento(String descuento) {
        if(descuento==null)
            this.descuento=new BigDecimal("0.00").toString();
        else            
            this.descuento = new BigDecimal(descuento).setScale(2,BigDecimal.ROUND_DOWN).toString();
    }

    public String getPrecioUnitario() {
        return precioUnitario;
    }

    public void setPrecioUnitario(String precioUnitario) {
        this.precioUnitario = new BigDecimal(precioUnitario).setScale(2,BigDecimal.ROUND_DOWN).toString();
    }

    public String getTotal() {
        return total;
    }

    public void setTotal(String total) {
        this.total = new BigDecimal(total).setScale(2,BigDecimal.ROUND_DOWN).toString();
    }
    
    
    
}
