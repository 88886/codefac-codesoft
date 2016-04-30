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

/**
 *
 * @author carlo
 */
@Entity
@Table(name = "detalle_venta_orden_trabajo")
@NamedQueries({
    @NamedQuery(name = "DetalleVentaOrdenTrabajo.findAll", query = "SELECT d FROM DetalleVentaOrdenTrabajo d")})
public class DetalleVentaOrdenTrabajo implements Serializable {
    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "ID_DETALLE_VENTA_ORDEN_TRABAJO")
    private Integer idDetalleVentaOrdenTrabajo;
    @Size(max = 16)
    @Column(name = "ESTADO")
    private String estado;
    // @Max(value=?)  @Min(value=?)//if you know range of your decimal fields consider using these annotations to enforce field validation
    @Column(name = "TOTAL")
    private BigDecimal total;
    @Column(name = "IVA")
    private BigDecimal iva;
    @Column(name = "DESCUENTO")
    private BigDecimal descuento;
    @JoinColumn(name = "ID_DETALLE_ORDEN_TRABAJO", referencedColumnName = "ID_DETALLE_ORDEN_TRABAJO")
    @ManyToOne
    private DetalleOrdenTrabajo idDetalleOrdenTrabajo;
    @JoinColumn(name = "NICK", referencedColumnName = "NICK")
    @ManyToOne
    private Usuario nick;
    @JoinColumn(name = "CODIGO_FACTURA", referencedColumnName = "CODIGO_FACTURA")
    @ManyToOne
    private Venta codigoFactura;

    public DetalleVentaOrdenTrabajo() {
    }

    public DetalleVentaOrdenTrabajo(Integer idDetalleVentaOrdenTrabajo) {
        this.idDetalleVentaOrdenTrabajo = idDetalleVentaOrdenTrabajo;
    }

    public Integer getIdDetalleVentaOrdenTrabajo() {
        return idDetalleVentaOrdenTrabajo;
    }

    public void setIdDetalleVentaOrdenTrabajo(Integer idDetalleVentaOrdenTrabajo) {
        this.idDetalleVentaOrdenTrabajo = idDetalleVentaOrdenTrabajo;
    }

    public String getEstado() {
        return estado;
    }

    public void setEstado(String estado) {
        this.estado = estado;
    }

    public BigDecimal getTotal() {
        return total;
    }

    public void setTotal(BigDecimal total) {
        this.total = total;
    }

    public BigDecimal getIva() {
        return iva;
    }

    public void setIva(BigDecimal iva) {
        this.iva = iva;
    }

    public BigDecimal getDescuento() {
        return descuento;
    }

    public void setDescuento(BigDecimal descuento) {
        this.descuento = descuento;
    }

    public DetalleOrdenTrabajo getIdDetalleOrdenTrabajo() 
    {
        return idDetalleOrdenTrabajo;
    }

    public void setIdDetalleOrdenTrabajo(DetalleOrdenTrabajo idDetalleOrdenTrabajo) 
    {
        this.idDetalleOrdenTrabajo = idDetalleOrdenTrabajo;
    }

    

    public Usuario getNick() {
        return nick;
    }

    public void setNick(Usuario nick) {
        this.nick = nick;
    }

    public Venta getCodigoFactura() {
        return codigoFactura;
    }

    public void setCodigoFactura(Venta codigoFactura) {
        this.codigoFactura = codigoFactura;
    }
    
    

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (idDetalleVentaOrdenTrabajo != null ? idDetalleVentaOrdenTrabajo.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof DetalleVentaOrdenTrabajo)) {
            return false;
        }
        DetalleVentaOrdenTrabajo other = (DetalleVentaOrdenTrabajo) object;
        if ((this.idDetalleVentaOrdenTrabajo == null && other.idDetalleVentaOrdenTrabajo != null) || (this.idDetalleVentaOrdenTrabajo != null && !this.idDetalleVentaOrdenTrabajo.equals(other.idDetalleVentaOrdenTrabajo))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "modelo.DetalleVentaOrdenTrabajo[ idDetalleVentaOrdenTrabajo=" + idDetalleVentaOrdenTrabajo + " ]";
    }
    
}
