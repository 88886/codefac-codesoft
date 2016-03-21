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
    @NamedQuery(name = "DetalleOrdenTrabajo.findByIdDetalleOrdenTrabajo", query = "SELECT d FROM DetalleOrdenTrabajo d WHERE d.idDetalleOrdenTrabajo = :idDetalleOrdenTrabajo"),
    @NamedQuery(name = "DetalleOrdenTrabajo.findByEquipo", query = "SELECT d FROM DetalleOrdenTrabajo d WHERE d.equipo = :equipo"),
    @NamedQuery(name = "DetalleOrdenTrabajo.findByDescripcion", query = "SELECT d FROM DetalleOrdenTrabajo d WHERE d.descripcion = :descripcion"),
    @NamedQuery(name = "DetalleOrdenTrabajo.findByProblema", query = "SELECT d FROM DetalleOrdenTrabajo d WHERE d.problema = :problema"),
    @NamedQuery(name = "DetalleOrdenTrabajo.findByTrabajoRealizar", query = "SELECT d FROM DetalleOrdenTrabajo d WHERE d.trabajoRealizar = :trabajoRealizar"),
    @NamedQuery(name = "DetalleOrdenTrabajo.findByPrecio", query = "SELECT d FROM DetalleOrdenTrabajo d WHERE d.precio = :precio"),
    @NamedQuery(name = "DetalleOrdenTrabajo.findByDiagnostico", query = "SELECT d FROM DetalleOrdenTrabajo d WHERE d.diagnostico = :diagnostico")})
public class DetalleOrdenTrabajo implements Serializable {
    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
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
    @Size(max = 512)
    @Column(name = "DIAGNOSTICO")
    private String diagnostico;
    @JoinColumn(name = "ID_ORDEN_TRABAJO", referencedColumnName = "ID_ORDEN_TRABAJO")
    @ManyToOne
    private OrdenTrabajo idOrdenTrabajo;
    @JoinColumn(name = "ID_CATEGORIA_TRABAJO", referencedColumnName = "ID_CATEGORIA_TRABAJO")
    @ManyToOne
    private CategoriaTrabajo idCategoriaTrabajo;
    @OneToMany(mappedBy = "idDetalleOrdenTrabajo")
    private List<DetalleProductoOrdenTrabajo> detalleProductoOrdenTrabajoList;

    public DetalleOrdenTrabajo() {
    }

    public DetalleOrdenTrabajo(Integer idDetalleOrdenTrabajo) {
        this.idDetalleOrdenTrabajo = idDetalleOrdenTrabajo;
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

    public String getDiagnostico() {
        return diagnostico;
    }

    public void setDiagnostico(String diagnostico) {
        this.diagnostico = diagnostico;
    }

    public OrdenTrabajo getIdOrdenTrabajo() {
        return idOrdenTrabajo;
    }

    public void setIdOrdenTrabajo(OrdenTrabajo idOrdenTrabajo) {
        this.idOrdenTrabajo = idOrdenTrabajo;
    }

    public CategoriaTrabajo getIdCategoriaTrabajo() {
        return idCategoriaTrabajo;
    }

    public void setIdCategoriaTrabajo(CategoriaTrabajo idCategoriaTrabajo) {
        this.idCategoriaTrabajo = idCategoriaTrabajo;
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
        hash += (idDetalleOrdenTrabajo != null ? idDetalleOrdenTrabajo.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof DetalleOrdenTrabajo)) {
            return false;
        }
        DetalleOrdenTrabajo other = (DetalleOrdenTrabajo) object;
        if ((this.idDetalleOrdenTrabajo == null && other.idDetalleOrdenTrabajo != null) || (this.idDetalleOrdenTrabajo != null && !this.idDetalleOrdenTrabajo.equals(other.idDetalleOrdenTrabajo))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "modelo.DetalleOrdenTrabajo[ idDetalleOrdenTrabajo=" + idDetalleOrdenTrabajo + " ]";
    }
    
}
