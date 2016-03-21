/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ec.com.codesoft.web.reportes.ordenTrabajo;

import ec.com.codesoft.web.reportes.ReporteJasper;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 *
 * @author carlo
 */
public class OrdenTrabajoReporte extends ReporteJasper<OrdenTrabajoDetalleReporte> {

    private String cedula;
    private String telefono;
    private String nombre;
    private String fechaRecepcion;
    private String observacion;
    private String ordenTrabajo;

    private String monto;
    private String abono;
    private String saldo;

    private List<OrdenTrabajoDetalleReporte> detalles;

    public OrdenTrabajoReporte(String cedula, String telefono, String nombre, String fechaRecepcion, String observacion, String ordenTrabajo, String monto, String abono, String saldo, String raiz) {
        super(raiz);
        this.cedula = cedula;
        this.telefono = telefono;
        this.nombre = nombre;
        this.fechaRecepcion = fechaRecepcion;
        this.observacion = observacion;
        this.ordenTrabajo = ordenTrabajo;
        this.monto = monto;
        this.abono = abono;
        this.saldo = saldo;
        this.detalles = new ArrayList<OrdenTrabajoDetalleReporte>();
    }

    public OrdenTrabajoReporte(String raiz) {
        super(raiz);
        this.detalles = new ArrayList<OrdenTrabajoDetalleReporte>();
    }

    @Override
    public List<OrdenTrabajoDetalleReporte> getLista() {
        return detalles;
    }

    @Override
    public Map<String, Object> getParametros() {
        Map<String, Object> lista = new HashMap<String, Object>();

        if (this.cedula != null) {
            lista.put("cedula", this.cedula);
        } else {
            lista.put("cedula", "");
        }

        if (this.telefono != null) {
            lista.put("telefono", this.telefono);
        } else {
            lista.put("telefono", "");
        }

        if (this.nombre != null) {
            lista.put("nombre", this.nombre);
        } else {
            lista.put("nombre", "");
        }

        if (this.fechaRecepcion != null) {
            lista.put("recepcion", this.fechaRecepcion);
        } else {
            lista.put("recepcion", "");
        }

        if (this.observacion != null) {
            lista.put("observaciones", this.observacion);
        } else {
            lista.put("observaciones", "");
        }
        
        lista.put("numeroOrden",this.ordenTrabajo);
        lista.put("monto",this.monto);
        lista.put("abono",this.abono);
        lista.put("saldo",this.saldo);
        

        return lista;
    }

    @Override
    public String getPath() {
        return "reportes/ordenTrabajo.jasper";
    }

    public String getCedula() {
        return cedula;
    }

    public void setCedula(String cedula) {
        this.cedula = cedula;
    }

    public String getTelefono() {
        return telefono;
    }

    public void setTelefono(String telefono) {
        this.telefono = telefono;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getFechaRecepcion() {
        return fechaRecepcion;
    }

    public void setFechaRecepcion(String fechaRecepcion) {
        this.fechaRecepcion = fechaRecepcion;
    }

    public String getObservacion() {
        return observacion;
    }

    public void setObservacion(String observacion) {
        this.observacion = observacion;
    }

    public String getOrdenTrabajo() {
        return ordenTrabajo;
    }

    public void setOrdenTrabajo(String ordenTrabajo) {
        this.ordenTrabajo = ordenTrabajo;
    }

    public String getMonto() {
        return monto;
    }

    public void setMonto(String monto) {
        this.monto = monto;
    }

    public String getAbono() {
        return abono;
    }

    public void setAbono(String abono) {
        this.abono = abono;
    }

    public String getSaldo() {
        return saldo;
    }

    public void setSaldo(String saldo) {
        this.saldo = saldo;
    }

    public List<OrdenTrabajoDetalleReporte> getDetalles() {
        return detalles;
    }

    public void setDetalles(List<OrdenTrabajoDetalleReporte> detalles) {
        this.detalles = detalles;
    }

}
