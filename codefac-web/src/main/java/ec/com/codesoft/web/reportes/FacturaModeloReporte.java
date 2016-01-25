/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ec.com.codesoft.web.reportes;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 *
 * @author carlo
 */
public class FacturaModeloReporte extends ReporteJasper<FacturaDetalleModeloReporte>{

    private String codigoFactura;
    private String nombreCliente;
    private String direccion;
    
    private String ruc;
    private String telefono;
    
    private String fechaFactura;
    private String formaPago;
    private String nota;
    
    private BigDecimal total;
    private BigDecimal ivaTotal;
    private BigDecimal subtotal;
    
    private List<FacturaDetalleModeloReporte> detalles;

    public FacturaModeloReporte(String codigoFactura, String nombreCliente, String direccion, String ruc, String telefono, String fechaFactura, String formaPago, String nota, BigDecimal total, BigDecimal ivaTotal, BigDecimal subtotal) {
        this.codigoFactura = codigoFactura;
        this.nombreCliente = nombreCliente;
        this.direccion = direccion;
        this.ruc = ruc;
        this.telefono = telefono;
        this.fechaFactura = fechaFactura;
        this.formaPago = formaPago;
        this.nota = nota;
        this.total = total;
        this.ivaTotal = ivaTotal;
        this.subtotal = subtotal;
        this.detalles= new ArrayList<FacturaDetalleModeloReporte>();
        
    }
    
    
    public void agregarDetalle(FacturaDetalleModeloReporte detalle)
    {
        detalles.add(detalle);
    }

    public String getCodigoFactura() {
        return codigoFactura;
    }

    public void setCodigoFactura(String codigoFactura) {
        this.codigoFactura = codigoFactura;
    }

    public String getNombreCliente() {
        return nombreCliente;
    }

    public void setNombreCliente(String nombreCliente) {
        this.nombreCliente = nombreCliente;
    }

    public String getDireccion() {
        return direccion;
    }

    public void setDireccion(String direccion) {
        this.direccion = direccion;
    }

    public String getFechaaFactura() {
        return fechaFactura;
    }

    public void setFechaaFactura(String fechaaFactura) {
        this.fechaFactura = fechaaFactura;
    }

    public String getFormaPago() {
        return formaPago;
    }

    public void setFormaPago(String formaPago) {
        this.formaPago = formaPago;
    }

    public String getNota() {
        return nota;
    }

    public void setNota(String nota) {
        this.nota = nota;
    }

    public BigDecimal getTotal() {
        return total;
    }

    public void setTotal(BigDecimal total) {
        this.total = total;
    }

    public BigDecimal getIvaTotal() {
        return ivaTotal;
    }

    public void setIvaTotal(BigDecimal ivaTotal) {
        this.ivaTotal = ivaTotal;
    }

    public BigDecimal getSubtotal() {
        return subtotal;
    }

    public void setSubtotal(BigDecimal subtotal) {
        this.subtotal = subtotal;
    }

    public List<FacturaDetalleModeloReporte> getDetalles() {
        return detalles;
    }

    public void setDetalles(List<FacturaDetalleModeloReporte> detalles) {
        this.detalles = detalles;
    }



    @Override
    public Map<String, Object> getParametros() {
        Map<String, Object> lista=new HashMap<String,Object>();
        lista.put("codigoFactura",this.codigoFactura);
        lista.put("nombreCliente",nombreCliente);
        lista.put("direccion",direccion);
        lista.put("ruc",ruc);
        lista.put("telefono", telefono);
        lista.put("fechaFactura",fechaFactura);
        lista.put("formaPago",formaPago);
        lista.put("nota",nota);
        lista.put("total",total);
        lista.put("ivaTotal",ivaTotal);
        lista.put("subtotal",subtotal);
        return lista;
        
    }

    @Override
    public List<FacturaDetalleModeloReporte> getLista() 
    {
        return detalles;
    }

    @Override
    public String getPath() {
        return "reportes/reporteFactura.jasper";
    }    
    
    
}
