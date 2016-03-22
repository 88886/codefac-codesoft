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
import javax.persistence.OneToOne;
import javax.persistence.Table;
import javax.validation.constraints.Size;
import javax.xml.bind.annotation.XmlRootElement;

/**
 *
 * @author carlo
 */
@Entity
@Table(name = "detalle_venta_orden_trabajo")
public class DetalleVentaOrdenTrabajo implements Serializable {
    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "ID_ORDEN_TRABAJO")
    private Integer idOrdenTrabajo;

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
    @JoinColumn(name = "ID_ORDEN_TRABAJO", referencedColumnName = "ID_ORDEN_TRABAJO", insertable = false, updatable = false)
    @OneToOne(optional = false)
    private OrdenTrabajo ordenTrabajo;
    @JoinColumn(name = "CODIGO_FACTURA", referencedColumnName = "CODIGO_FACTURA")
    @ManyToOne
    private Venta codigoFactura;
    @JoinColumn(name = "NICK", referencedColumnName = "NICK")
    @ManyToOne
    private Usuario nick;

    public DetalleVentaOrdenTrabajo() {
    }

    public DetalleVentaOrdenTrabajo(Integer idOrdenTrabajo) {
        this.idOrdenTrabajo = idOrdenTrabajo;
    }

    public Integer getIdOrdenTrabajo() {
        return idOrdenTrabajo;
    }

    public void setIdOrdenTrabajo(Integer idOrdenTrabajo) {
        this.idOrdenTrabajo = idOrdenTrabajo;
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

    public OrdenTrabajo getOrdenTrabajo() {
        return ordenTrabajo;
    }

    public void setOrdenTrabajo(OrdenTrabajo ordenTrabajo) {
        this.ordenTrabajo = ordenTrabajo;
    }

    public Venta getCodigoFactura() {
        return codigoFactura;
    }

    public void setCodigoFactura(Venta codigoFactura) {
        this.codigoFactura = codigoFactura;
    }

    public Usuario getNick() {
        return nick;
    }

    public void setNick(Usuario nick) {
        this.nick = nick;
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
        if (!(object instanceof DetalleVentaOrdenTrabajo)) {
            return false;
        }
        DetalleVentaOrdenTrabajo other = (DetalleVentaOrdenTrabajo) object;
        if ((this.idOrdenTrabajo == null && other.idOrdenTrabajo != null) || (this.idOrdenTrabajo != null && !this.idOrdenTrabajo.equals(other.idOrdenTrabajo))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "modelo.DetalleVentaOrdenTrabajo[ idOrdenTrabajo=" + idOrdenTrabajo + " ]";
    }
    
}
