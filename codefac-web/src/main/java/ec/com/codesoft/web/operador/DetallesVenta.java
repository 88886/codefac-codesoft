/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package ec.com.codesoft.web.operador;

import java.math.BigDecimal;
import java.util.List;

/**
 *
 * @author Suco
 */
public class DetallesVenta {
    
       
    private int cantidad;
    private String codigo;
    private String nombre;
    private BigDecimal costo;
    private BigDecimal total;
    private List<Descuentos> descuentos;
    private String precioSeleccionado;
    private String  escogerDescuento;

    public DetallesVenta() {
    }

    public DetallesVenta(int cantidad, String codigo, String nombre, BigDecimal costo, BigDecimal total) {
        this.cantidad = cantidad;
        this.codigo = codigo;
        this.nombre = nombre;
        this.costo = costo;
        this.total = total;
    }

    

    public String getCodigo() {
        return codigo;
    }

    public void setCodigo(String codigo) {
        this.codigo = codigo;
    }

    public int getCantidad() {
        return cantidad;
    }

    public void setCantidad(int cantidad) {
        this.cantidad = cantidad;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public BigDecimal getCosto() {
        return costo;
    }

    public void setCosto(BigDecimal costo) {
        this.costo = costo;
    }

    public BigDecimal getTotal() {
        return total;
    }

    public void setTotal(BigDecimal total) {
        this.total = total;
    }

    public List<Descuentos> getDescuentos() {
        return descuentos;
    }

    public void setDescuentos(List<Descuentos> descuentos) {
        this.descuentos = descuentos;
    }

    

    public String getPrecioSeleccionado() {
        return precioSeleccionado;
    }

    public void setPrecioSeleccionado(String precioSeleccionado) {
        this.precioSeleccionado = precioSeleccionado;
    }

    public String getEscogerDescuento() {
        return escogerDescuento;
    }

    public void setEscogerDescuento(String escogerDescuento) {
        this.escogerDescuento = escogerDescuento;
    }
    
    
    
    
    
    

   
    

  

    
    
   
    
    
    
}
