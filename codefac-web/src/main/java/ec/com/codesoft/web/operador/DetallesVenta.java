/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ec.com.codesoft.web.operador;

import java.math.BigDecimal;
import java.math.BigInteger;
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
    private String escogerDescuento;
    private BigDecimal valorVerdaderoMayorista;
    private BigDecimal valorVerdaderoPVP;
    private BigDecimal valorDescuento;
    private String tipoDetalle ;
    private Boolean mostrarDescuentoManual;
    

    public DetallesVenta() {
    }

    public DetallesVenta(int cantidad, String codigo, String nombre, BigDecimal costo, BigDecimal total) {
        this.cantidad = cantidad;
        this.codigo = codigo;
        this.nombre = nombre;
        this.costo = costo;
        this.total = total;
        valorDescuento = new BigDecimal("0.0");
    }

    public DetallesVenta(int cantidad, String codigo, String nombre, BigDecimal costo, BigDecimal total,BigDecimal valorDescuento) 
    {
        this.cantidad = cantidad;
        this.codigo = codigo;
        this.nombre = nombre;
        this.costo = costo;
        this.total = total;
        this.valorDescuento = valorDescuento;
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

    public BigDecimal getValorVerdaderoMayorista() {
        return valorVerdaderoMayorista;
    }

    public void setValorVerdaderoMayorista(BigDecimal valorVerdaderoMayorista) {
        this.valorVerdaderoMayorista = valorVerdaderoMayorista;
    }

    public BigDecimal getValorVerdaderoPVP() {
        return valorVerdaderoPVP;
    }

    public void setValorVerdaderoPVP(BigDecimal valorVerdaderoPVP) {
        this.valorVerdaderoPVP = valorVerdaderoPVP;
    }

    public BigDecimal getValorDescuento() {
        return valorDescuento;
    }

    public void setValorDescuento(BigDecimal valorDescuento) {
        this.valorDescuento = valorDescuento;
    }

    public String getTipoDetalle() {
        return tipoDetalle;
    }

    public void setTipoDetalle(String tipoDetalle) {
        this.tipoDetalle = tipoDetalle;
    }

    public Boolean getMostrarDescuentoManual() {
        return mostrarDescuentoManual;
    }

    public void setMostrarDescuentoManual(Boolean mostrarDescuentoManual) {
        this.mostrarDescuentoManual = mostrarDescuentoManual;
    }
    
    

   
    
    
    

}
