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
import javax.persistence.CascadeType;
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
@Table(name = "categoria_trabajo")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "CategoriaTrabajo.findAll", query = "SELECT c FROM CategoriaTrabajo c"),
    @NamedQuery(name = "CategoriaTrabajo.findByIdCategoriaTrabajo", query = "SELECT c FROM CategoriaTrabajo c WHERE c.idCategoriaTrabajo = :idCategoriaTrabajo"),
    @NamedQuery(name = "CategoriaTrabajo.findByNombre", query = "SELECT c FROM CategoriaTrabajo c WHERE c.nombre = :nombre"),
    @NamedQuery(name = "CategoriaTrabajo.findByPrecio", query = "SELECT c FROM CategoriaTrabajo c WHERE c.precio = :precio"),
    @NamedQuery(name = "CategoriaTrabajo.findByDescripcion", query = "SELECT c FROM CategoriaTrabajo c WHERE c.descripcion = :descripcion")})
public class CategoriaTrabajo implements Serializable {
    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "ID_CATEGORIA_TRABAJO")
    private Integer idCategoriaTrabajo;
    @Size(max = 64)
    @Column(name = "NOMBRE")
    private String nombre;
    // @Max(value=?)  @Min(value=?)//if you know range of your decimal fields consider using these annotations to enforce field validation
    @Column(name = "PRECIO")
    private BigDecimal precio;
    @Size(max = 128)
    @Column(name = "DESCRIPCION")
    private String descripcion;
    
    @Size(max = 256)
    @Column(name = "TRABAJO_REALIZAR")
    private String trabajoRealizar;
    
    
    @OneToMany(mappedBy = "idCategoriaTrabajo", cascade = CascadeType.ALL)
    private List<DetalleOrdenTrabajo> detalleOrdenTrabajoList;
    @JoinColumn(name = "CODIGO_SERVICIO", referencedColumnName = "CODIGO_SERVICIO")
    @ManyToOne
    private Servicios codigoServicio;
    @OneToMany(mappedBy = "idCategoriaTrabajo", cascade = CascadeType.ALL)
    private List<DetallesServicio> detallesServicioList;

    public CategoriaTrabajo() {
    }

    public CategoriaTrabajo(Integer idCategoriaTrabajo) {
        this.idCategoriaTrabajo = idCategoriaTrabajo;
    }

    public Integer getIdCategoriaTrabajo() {
        return idCategoriaTrabajo;
    }

    public void setIdCategoriaTrabajo(Integer idCategoriaTrabajo) {
        this.idCategoriaTrabajo = idCategoriaTrabajo;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public BigDecimal getPrecio() {
        return precio;
    }

    public void setPrecio(BigDecimal precio) {
        this.precio = precio;
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
    
    

    @XmlTransient
    public List<DetalleOrdenTrabajo> getDetalleOrdenTrabajoList() {
        return detalleOrdenTrabajoList;
    }

    public void setDetalleOrdenTrabajoList(List<DetalleOrdenTrabajo> detalleOrdenTrabajoList) {
        this.detalleOrdenTrabajoList = detalleOrdenTrabajoList;
    }

    public Servicios getCodigoServicio() {
        return codigoServicio;
    }

    public void setCodigoServicio(Servicios codigoServicio) {
        this.codigoServicio = codigoServicio;
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
        hash += (idCategoriaTrabajo != null ? idCategoriaTrabajo.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof CategoriaTrabajo)) {
            return false;
        }
        CategoriaTrabajo other = (CategoriaTrabajo) object;
        if ((this.idCategoriaTrabajo == null && other.idCategoriaTrabajo != null) || (this.idCategoriaTrabajo != null && !this.idCategoriaTrabajo.equals(other.idCategoriaTrabajo))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "modelo.CategoriaTrabajo[ idCategoriaTrabajo=" + idCategoriaTrabajo + " ]";
    }
    
}
