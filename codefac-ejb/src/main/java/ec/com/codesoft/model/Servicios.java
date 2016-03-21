/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package ec.com.codesoft.model;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.List;
import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.validation.constraints.Size;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlTransient;

/**
 *
 * @author carlo
 */
@Entity
@Table(name = "servicios")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "Servicios.findAll", query = "SELECT s FROM Servicios s"),
    @NamedQuery(name = "Servicios.findByCodigoServicio", query = "SELECT s FROM Servicios s WHERE s.codigoServicio = :codigoServicio"),
    @NamedQuery(name = "Servicios.findByNombre", query = "SELECT s FROM Servicios s WHERE s.nombre = :nombre"),
    @NamedQuery(name = "Servicios.findByDescripcion", query = "SELECT s FROM Servicios s WHERE s.descripcion = :descripcion"),
    @NamedQuery(name = "Servicios.findByCosto", query = "SELECT s FROM Servicios s WHERE s.costo = :costo")})
public class Servicios implements Serializable {
    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "CODIGO_SERVICIO")
    private Integer codigoServicio;
    @Size(max = 64)
    @Column(name = "NOMBRE")
    private String nombre;
    @Size(max = 512)
    @Column(name = "DESCRIPCION")
    private String descripcion;
    // @Max(value=?)  @Min(value=?)//if you know range of your decimal fields consider using these annotations to enforce field validation
    @Column(name = "COSTO")
    private BigDecimal costo;
    @OneToMany(mappedBy = "codigoServicio")
    private List<DetalleOrdenTrabajo> detalleOrdenTrabajoList;
    @OneToMany(mappedBy = "codigoServicio")
    private List<DetallesServicio> detallesServicioList;

    public Servicios() {
    }

    public Servicios(Integer codigoServicio) {
        this.codigoServicio = codigoServicio;
    }

    public Integer getCodigoServicio() {
        return codigoServicio;
    }

    public void setCodigoServicio(Integer codigoServicio) {
        this.codigoServicio = codigoServicio;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getDescripcion() {
        return descripcion;
    }

    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }

    public BigDecimal getCosto() {
        return costo;
    }

    public void setCosto(BigDecimal costo) {
        this.costo = costo;
    }

    @XmlTransient
    public List<DetalleOrdenTrabajo> getDetalleOrdenTrabajoList() {
        return detalleOrdenTrabajoList;
    }

    public void setDetalleOrdenTrabajoList(List<DetalleOrdenTrabajo> detalleOrdenTrabajoList) {
        this.detalleOrdenTrabajoList = detalleOrdenTrabajoList;
    }

    @XmlTransient
    public List<DetallesServicio> getDetallesServicioList() {
        return detallesServicioList;
    }

    public void setDetallesServicioList(List<DetallesServicio> detallesServicioList) {
        this.detallesServicioList = detallesServicioList;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (codigoServicio != null ? codigoServicio.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof Servicios)) {
            return false;
        }
        Servicios other = (Servicios) object;
        if ((this.codigoServicio == null && other.codigoServicio != null) || (this.codigoServicio != null && !this.codigoServicio.equals(other.codigoServicio))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "modelo.Servicios[ codigoServicio=" + codigoServicio + " ]";
    }
    
}
