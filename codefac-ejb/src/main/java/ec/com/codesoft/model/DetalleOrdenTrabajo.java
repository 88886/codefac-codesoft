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
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;
import javax.persistence.Table;
import javax.validation.constraints.Size;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlTransient;

/**
 *
 * @author carlo
 */
@Entity
@Table(name = "detalle_orden_trabajo")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "DetalleOrdenTrabajo.findAll", query = "SELECT d FROM DetalleOrdenTrabajo d"),
    @NamedQuery(name = "DetalleOrdenTrabajo.findByIdOrdenTrabajo", query = "SELECT d FROM DetalleOrdenTrabajo d WHERE d.idOrdenTrabajo = :idOrdenTrabajo"),
    @NamedQuery(name = "DetalleOrdenTrabajo.findByIdDetalleOrdenTrabajo", query = "SELECT d FROM DetalleOrdenTrabajo d WHERE d.idDetalleOrdenTrabajo = :idDetalleOrdenTrabajo"),
    @NamedQuery(name = "DetalleOrdenTrabajo.findByEquipo", query = "SELECT d FROM DetalleOrdenTrabajo d WHERE d.equipo = :equipo"),
    @NamedQuery(name = "DetalleOrdenTrabajo.findByDescripcion", query = "SELECT d FROM DetalleOrdenTrabajo d WHERE d.descripcion = :descripcion"),
    @NamedQuery(name = "DetalleOrdenTrabajo.findByProblema", query = "SELECT d FROM DetalleOrdenTrabajo d WHERE d.problema = :problema"),
    @NamedQuery(name = "DetalleOrdenTrabajo.findByTrabajoRealizar", query = "SELECT d FROM DetalleOrdenTrabajo d WHERE d.trabajoRealizar = :trabajoRealizar"),
    @NamedQuery(name = "DetalleOrdenTrabajo.findByPrecio", query = "SELECT d FROM DetalleOrdenTrabajo d WHERE d.precio = :precio")})
public class DetalleOrdenTrabajo implements Serializable {
    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "ID_ORDEN_TRABAJO")
    private Integer idOrdenTrabajo;
    @Column(name = "ID_DETALLE_ORDEN_TRABAJO")
    private Integer idDetalleOrdenTrabajo;
    @Size(max = 64)
    @Column(name = "EQUIPO")
    private String equipo;
    @Size(max = 64)
    @Column(name = "DESCRIPCION")
    private String descripcion;
    @Size(max = 128)
    @Column(name = "PROBLEMA")
    private String problema;
    @Size(max = 256)
    @Column(name = "TRABAJO_REALIZAR")
    private String trabajoRealizar;
    // @Max(value=?)  @Min(value=?)//if you know range of your decimal fields consider using these annotations to enforce field validation
    @Column(name = "PRECIO")
    private BigDecimal precio;
    @JoinColumn(name = "ID_ORDEN_TRABAJO", referencedColumnName = "ID_ORDEN_TRABAJO", insertable = false, updatable = false)
    @OneToOne(optional = false)
    private OrdenTrabajo ordenTrabajo;
    @JoinColumn(name = "CODIGO_SERVICIO", referencedColumnName = "CODIGO_SERVICIO")
    @ManyToOne
    private Servicios codigoServicio;
    @OneToMany(mappedBy = "idOrdenTrabajo")
    private List<DetalleProductoOrdenTrabajo> detalleProductoOrdenTrabajoList;

    public DetalleOrdenTrabajo() {
    }

    public DetalleOrdenTrabajo(Integer idOrdenTrabajo) {
        this.idOrdenTrabajo = idOrdenTrabajo;
    }

    public Integer getIdOrdenTrabajo() {
        return idOrdenTrabajo;
    }

    public void setIdOrdenTrabajo(Integer idOrdenTrabajo) {
        this.idOrdenTrabajo = idOrdenTrabajo;
    }

    public Integer getIdDetalleOrdenTrabajo() {
        return idDetalleOrdenTrabajo;
    }

    public void setIdDetalleOrdenTrabajo(Integer idDetalleOrdenTrabajo) {
        this.idDetalleOrdenTrabajo = idDetalleOrdenTrabajo;
    }

    public String getEquipo() {
        return equipo;
    }

    public void setEquipo(String equipo) {
        this.equipo = equipo;
    }

    public String getDescripcion() {
        return descripcion;
    }

    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }

    public String getProblema() {
        return problema;
    }

    public void setProblema(String problema) {
        this.problema = problema;
    }

    public String getTrabajoRealizar() {
        return trabajoRealizar;
    }

    public void setTrabajoRealizar(String trabajoRealizar) {
        this.trabajoRealizar = trabajoRealizar;
    }

    public BigDecimal getPrecio() {
        return precio;
    }

    public void setPrecio(BigDecimal precio) {
        this.precio = precio;
    }

    public OrdenTrabajo getOrdenTrabajo() {
        return ordenTrabajo;
    }

    public void setOrdenTrabajo(OrdenTrabajo ordenTrabajo) {
        this.ordenTrabajo = ordenTrabajo;
    }

    public Servicios getCodigoServicio() {
        return codigoServicio;
    }

    public void setCodigoServicio(Servicios codigoServicio) {
        this.codigoServicio = codigoServicio;
    }

    @XmlTransient
    public List<DetalleProductoOrdenTrabajo> getDetalleProductoOrdenTrabajoList() {
        return detalleProductoOrdenTrabajoList;
    }

    public void setDetalleProductoOrdenTrabajoList(List<DetalleProductoOrdenTrabajo> detalleProductoOrdenTrabajoList) {
        this.detalleProductoOrdenTrabajoList = detalleProductoOrdenTrabajoList;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (idOrdenTrabajo != null ? idOrdenTrabajo.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof DetalleOrdenTrabajo)) {
            return false;
        }
        DetalleOrdenTrabajo other = (DetalleOrdenTrabajo) object;
        if ((this.idOrdenTrabajo == null && other.idOrdenTrabajo != null) || (this.idOrdenTrabajo != null && !this.idOrdenTrabajo.equals(other.idOrdenTrabajo))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "modelo.DetalleOrdenTrabajo[ idOrdenTrabajo=" + idOrdenTrabajo + " ]";
    }
    
}
