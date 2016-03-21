/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ec.com.codesoft.web.reportes.ordenTrabajo;

/**
 *
 * @author carlo
 */
public class OrdenTrabajoDetalleReporte 
{
    private String nombre;
    private String problema;
    private String descripcion;
    private String trabajoRealizar;
    private String precio;

    public OrdenTrabajoDetalleReporte() {
    }
    
    

    public OrdenTrabajoDetalleReporte(String nombre, String problema, String descripcion, String trabajoRealizar, String precio) {
        this.nombre = nombre;
        this.problema = problema;
        this.descripcion = descripcion;
        this.trabajoRealizar = trabajoRealizar;
        this.precio = precio;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getProblema() {
        return problema;
    }

    public void setProblema(String problema) {
        this.problema = problema;
    }

    public String getDescripcion() {
        return descripcion;
    }

    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }

    public String getTrabajoRealizar() {
        return trabajoRealizar;
    }

    public void setTrabajoRealizar(String trabajoRealizar) {
        this.trabajoRealizar = trabajoRealizar;
    }

    public String getPrecio() {
        return precio;
    }

    public void setPrecio(String precio) {
        this.precio = precio;
    }
    
        
    
}
