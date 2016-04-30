/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ec.com.codesoft.model;

import java.io.Serializable;
import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import javax.xml.bind.annotation.XmlRootElement;

/**
 *
 * @author carlo
 */
@Entity
@Table(name = "accesorios_orden_trabajo")

public class AccesoriosOrdenTrabajo implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "ID_ACCESORIOS_ORDEN_TRABAJO")
    private Integer idAccesoriosOrdenTrabajo;
    @Size(max = 64)
    @Column(name = "NOMBRE")
    private String nombre;
    @Size(max = 64)
    @Column(name = "CODIGO")
    private String codigo;
    @Size(max = 128)
    @Column(name = "DESCRIPCION")
    private String descripcion;
    @Size(max = 128)
    @Column(name = "OBSERVACIONES")
    private String observaciones;
    @Size(max = 32)
    @Column(name = "ESTADO")
    private String estado;
    @JoinColumn(name = "ID_DETALLE_ORDEN_TRABAJO", referencedColumnName = "ID_DETALLE_ORDEN_TRABAJO")
    @ManyToOne
    private DetalleOrdenTrabajo idDetalleOrdenTrabajo;

    public AccesoriosOrdenTrabajo() {
    }

    public AccesoriosOrdenTrabajo(Integer idAccesoriosOrdenTrabajo) {
        this.idAccesoriosOrdenTrabajo = idAccesoriosOrdenTrabajo;
    }

    public Integer getIdAccesoriosOrdenTrabajo() {
        return idAccesoriosOrdenTrabajo;
    }

    public void setIdAccesoriosOrdenTrabajo(Integer idAccesoriosOrdenTrabajo) {
        this.idAccesoriosOrdenTrabajo = idAccesoriosOrdenTrabajo;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
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

    public String getObservaciones() {
        return observaciones;
    }

    public void setObservaciones(String observaciones) {
        this.observaciones = observaciones;
    }

    public String getEstado() {
        return estado;
    }

    public void setEstado(String estado) {
        this.estado = estado;
    }

    public DetalleOrdenTrabajo getIdDetalleOrdenTrabajo() {
        return idDetalleOrdenTrabajo;
    }

    public void setIdDetalleOrdenTrabajo(DetalleOrdenTrabajo idDetalleOrdenTrabajo) {
        this.idDetalleOrdenTrabajo = idDetalleOrdenTrabajo;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (idAccesoriosOrdenTrabajo != null ? idAccesoriosOrdenTrabajo.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof AccesoriosOrdenTrabajo)) {
            return false;
        }
        AccesoriosOrdenTrabajo other = (AccesoriosOrdenTrabajo) object;
        if ((this.idAccesoriosOrdenTrabajo == null && other.idAccesoriosOrdenTrabajo != null) || (this.idAccesoriosOrdenTrabajo != null && !this.idAccesoriosOrdenTrabajo.equals(other.idAccesoriosOrdenTrabajo))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "modelo.AccesoriosOrdenTrabajo[ idAccesoriosOrdenTrabajo=" + idAccesoriosOrdenTrabajo + " ]";
    }

}
