/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package ec.com.codesoft.web.operador;

import java.math.BigDecimal;

/**
 *
 * @author Suco
 */
public class DetallesCompra {
    
    
    private int cantidad;
    private String nombre;
    private BigDecimal costo;
    private BigDecimal total;

    public DetallesCompra() {
    }

    
    public DetallesCompra(int cantidad, String nombre, BigDecimal costo, BigDecimal total) {
        this.cantidad = cantidad;
        this.nombre = nombre;
        this.costo = costo;
        this.total = total;
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
    
    
    
    
}
