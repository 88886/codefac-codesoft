/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ec.com.codesoft.web.reportes;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 *
 * @author carlo
 */
public class ProformaModelo extends ReporteJasper<FacturaDetalleModeloReporte>{

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

    public ProformaModelo(String raiz) 
    {
        super(raiz);
        this.detalles= new ArrayList<FacturaDetalleModeloReporte>();
        this.codigoFactura="";
        this.nombreCliente="";
        this.direccion="";
        
        this.ruc="";
        this.telefono="";
        
        this.fechaFactura="";
        this.formaPago="";
        this.nota="";
        
        this.total=new BigDecimal("0.0");
        this.ivaTotal=new BigDecimal("0.0");
        this.subtotal=new BigDecimal("0.0");
        
        
    }
    
    

    public ProformaModelo(String codigoFactura, String nombreCliente, String direccion, String ruc, String telefono, String fechaFactura, String formaPago, String nota, BigDecimal total, BigDecimal ivaTotal, BigDecimal subtotal,String raiz) {
        super(raiz);
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
        this.total = total.setScale(2,RoundingMode.UP);
    }

    public BigDecimal getIvaTotal() {
        return ivaTotal;
    }

    public void setIvaTotal(BigDecimal ivaTotal) {
        this.ivaTotal = ivaTotal.setScale(2,RoundingMode.DOWN);
    }

    public BigDecimal getSubtotal() {
        return subtotal;
    }

    public void setSubtotal(BigDecimal subtotal) {
        this.subtotal = subtotal.setScale(2, RoundingMode.DOWN);
    }

    public List<FacturaDetalleModeloReporte> getDetalles() {
        return detalles;
    }

    public void setDetalles(List<FacturaDetalleModeloReporte> detalles) {
        this.detalles = detalles;
    }



    @Override
    public Map<String, Object> getParametros() 
    {
        Map<String, Object> lista=new HashMap<String,Object>();
        if(this.codigoFactura!=null)
            lista.put("codigoFactura",this.codigoFactura);
        else
            lista.put("codigoFactura","");
        
        if(this.nombreCliente!=null)
            lista.put("nombreCliente",nombreCliente);
        else
            lista.put("nombreCliente","");
        
        if(this.direccion!=null)
            lista.put("direccion",direccion);
        else
            lista.put("direccion","");
        
        if(this.ruc!=null)
            lista.put("ruc",ruc);
        else
            lista.put("ruc","");
        
        if(this.telefono!=null)
            lista.put("telefono", telefono);
        else
            lista.put("telefono","");
        
        if(this.fechaFactura!=null)
            lista.put("fechaFactura",fechaFactura);
        else
            lista.put("fechaFactura","");
        
        lista.put("formaPago","--");
        lista.put("nota"," ");
        
        lista.put("total",total);
        lista.put("ivaTotal",ivaTotal);
        lista.put("subTotal",subtotal);
        
        return lista;
        
    }

    @Override
    public List<FacturaDetalleModeloReporte> getLista() 
    {
        return detalles;
    }

    @Override
    public String getPath() {
        return "reportes/proforma.jasper";
    }    

    public String getRuc() {
        return ruc;
    }

    public void setRuc(String ruc) {
        this.ruc = ruc;
    }

    public String getTelefono() {
        return telefono;
    }

    public void setTelefono(String telefono) {
        this.telefono = telefono;
    }

    public String getFechaFactura() {
        return fechaFactura;
    }

    public void setFechaFactura(String fechaFactura) {
        this.fechaFactura = fechaFactura;
    }
    
    
    
    
}
