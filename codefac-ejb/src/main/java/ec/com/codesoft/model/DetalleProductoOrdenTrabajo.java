/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ec.com.codesoft.model;


import java.io.Serializable;
import java.math.BigDecimal;
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
import javax.validation.constraints.Size;
import javax.xml.bind.annotation.XmlRootElement;

/**
 *
 * @author carlo
 */
@Entity
@Table(name = "detalle_producto_orden_trabajo")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "DetalleProductoOrdenTrabajo.findAll", query = "SELECT d FROM DetalleProductoOrdenTrabajo d"),
    @NamedQuery(name = "DetalleProductoOrdenTrabajo.findByIdDetalleOrdenTrabajo", query = "SELECT d FROM DetalleProductoOrdenTrabajo d WHERE d.idDetalleOrdenTrabajo = :idDetalleOrdenTrabajo"),
    @NamedQuery(name = "DetalleProductoOrdenTrabajo.findByCantidad", query = "SELECT d FROM DetalleProductoOrdenTrabajo d WHERE d.cantidad = :cantidad"),
    @NamedQuery(name = "DetalleProductoOrdenTrabajo.findByDescuento", query = "SELECT d FROM DetalleProductoOrdenTrabajo d WHERE d.descuento = :descuento"),
    @NamedQuery(name = "DetalleProductoOrdenTrabajo.findByDescripcion", query = "SELECT d FROM DetalleProductoOrdenTrabajo d WHERE d.descripcion = :descripcion"),
    @NamedQuery(name = "DetalleProductoOrdenTrabajo.findBySubtotal", query = "SELECT d FROM DetalleProductoOrdenTrabajo d WHERE d.subtotal = :subtotal")})
public class DetalleProductoOrdenTrabajo implements Serializable {
    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "ID_DETALLE_ORDEN_TRABAJO")
    private Integer idDetalleOrdenTrabajo;
    @Column(name = "CANTIDAD")
    private Integer cantidad;
    // @Max(value=?)  @Min(value=?)//if you know range of your decimal fields consider using these annotations to enforce field validation
    @Column(name = "DESCUENTO")
    private BigDecimal descuento;
    @Size(max = 64)
    @Column(name = "DESCRIPCION")
    private String descripcion;
    @Column(name = "SUBTOTAL")
    private BigDecimal subtotal;
    @JoinColumn(name = "ID_ORDEN_TRABAJO", referencedColumnName = "ID_ORDEN_TRABAJO")
    @ManyToOne
    private DetalleOrdenTrabajo idOrdenTrabajo;
    @JoinColumn(name = "CODIGO_PRODUCTO", referencedColumnName = "CODIGO_PRODUCTO")
    @ManyToOne
    private CatalagoProducto codigoProducto;

    public DetalleProductoOrdenTrabajo() {
    }

    public DetalleProductoOrdenTrabajo(Integer idDetalleOrdenTrabajo) {
        this.idDetalleOrdenTrabajo = idDetalleOrdenTrabajo;
    }

    public Integer getIdDetalleOrdenTrabajo() {
        return idDetalleOrdenTrabajo;
    }

    public void setIdDetalleOrdenTrabajo(Integer idDetalleOrdenTrabajo) {
        this.idDetalleOrdenTrabajo = idDetalleOrdenTrabajo;
    }

    public Integer getCantidad() {
        return cantidad;
    }

    public void setCantidad(Integer cantidad) {
        this.cantidad = cantidad;
    }

    public BigDecimal getDescuento() {
        return descuento;
    }

    public void setDescuento(BigDecimal descuento) {
        this.descuento = descuento;
    }

    public String getDescripcion() {
        return descripcion;
    }

    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }

    public BigDecimal getSubtotal() {
        return subtotal;
    }

    public void setSubtotal(BigDecimal subtotal) {
        this.subtotal = subtotal;
    }

    public DetalleOrdenTrabajo getIdOrdenTrabajo() {
        return idOrdenTrabajo;
    }

    public void setIdOrdenTrabajo(DetalleOrdenTrabajo idOrdenTrabajo) {
        this.idOrdenTrabajo = idOrdenTrabajo;
    }

    public CatalagoProducto getCodigoProducto() {
        return codigoProducto;
    }

    public void setCodigoProducto(CatalagoProducto codigoProducto) {
        this.codigoProducto = codigoProducto;
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
        if (!(object instanceof DetalleProductoOrdenTrabajo)) {
            return false;
        }
        DetalleProductoOrdenTrabajo other = (DetalleProductoOrdenTrabajo) object;
        if ((this.idDetalleOrdenTrabajo == null && other.idDetalleOrdenTrabajo != null) || (this.idDetalleOrdenTrabajo != null && !this.idDetalleOrdenTrabajo.equals(other.idDetalleOrdenTrabajo))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "modelo.DetalleProductoOrdenTrabajo[ idDetalleOrdenTrabajo=" + idDetalleOrdenTrabajo + " ]";
    }
    
}
