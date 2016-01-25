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
public class FacturaDetalleModeloReporte{
    private Integer cantidad;
    private String codigo;
    private String descripcion;
    private BigDecimal descuento;
    private BigDecimal precioUnitario;
    private BigDecimal total;

    public FacturaDetalleModeloReporte(Integer cantidad, String codigo, String descripcion, BigDecimal descuento, BigDecimal precioUnitario, BigDecimal total) {
        this.cantidad = cantidad;
        this.codigo = codigo;
        this.descripcion = descripcion;
        this.descuento = descuento;
        this.precioUnitario = precioUnitario;
        this.total = total;
    }

    public Integer getCantidad() {
        return cantidad;
    }

    public void setCantidad(Integer cantidad) {
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

    public BigDecimal getDescuento() {
        return descuento;
    }

    public void setDescuento(BigDecimal descuento) {
        this.descuento = descuento;
    }

    public BigDecimal getPrecioUnitario() {
        return precioUnitario;
    }

    public void setPrecioUnitario(BigDecimal precioUnitario) {
        this.precioUnitario = precioUnitario;
    }

    public BigDecimal getTotal() {
        return total;
    }

    public void setTotal(BigDecimal total) {
        this.total = total;
    }
    
    
    
}
