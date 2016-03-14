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
public class NotaVentaModeloReporte extends ReporteJasper<FacturaDetalleModeloReporte>{

    //private String codigoFactura;
    private String nombreCliente;
    private String direccion;
    private String telefono;
    
    private String fechaFactura;
    private String formaPago;      
    
    private BigDecimal total;
    
    private List<FacturaDetalleModeloReporte> detalles;

    public NotaVentaModeloReporte(String raiz) 
    {
        super(raiz);
        this.detalles= new ArrayList<FacturaDetalleModeloReporte>();
        this.nombreCliente="";
        this.direccion="";
        

        this.telefono="";
        
        this.fechaFactura="";
        this.formaPago="";

        
        this.total=new BigDecimal("0.0");

        
        
    }

    public NotaVentaModeloReporte(String nombreCliente, String direccion, String telefono, String fechaFactura, String formaPago, BigDecimal total, List<FacturaDetalleModeloReporte> detalles,String raiz) {
        super(raiz);
        this.nombreCliente = nombreCliente;
        this.direccion = direccion;
        this.telefono = telefono;
        this.fechaFactura = fechaFactura;
        this.formaPago = formaPago;
        this.total = total.setScale(2,BigDecimal.ROUND_DOWN);
        this.detalles = detalles;
    }
    
    
    public void agregarDetalle(FacturaDetalleModeloReporte detalle)
    {
        detalles.add(detalle);
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


    public BigDecimal getTotal() {
        return total;
    }

    public void setTotal(BigDecimal total) {
        this.total = total.setScale(2,RoundingMode.UP);
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
        if(this.nombreCliente!=null)            
        lista.put("nombreCliente",nombreCliente);
        else
            lista.put("nombreCliente","");
        
        if(this.direccion!=null)
            lista.put("direccion",direccion);
        else
            lista.put("direccion","");
        
        if(this.telefono!=null)
            lista.put("telefono", telefono);
        else
            lista.put("telefono", "");
        
        
        lista.put("fechaFactura",fechaFactura);
        lista.put("formaPago",formaPago);
        lista.put("total",total);
        
        return lista;
        
    }

    @Override
    public List<FacturaDetalleModeloReporte> getLista() 
    {
        return detalles;
    }

    @Override
    public String getPath() {
        return "reportes/notaVenta.jasper";
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