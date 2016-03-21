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
import javax.validation.constraints.NotNull;

/**
 *
 * @author Suco
 */
@Entity
@Table(name = "detalle_producto_general")
@NamedQueries({
    @NamedQuery(name = "DetalleProductoGeneral.findAll", query = "SELECT d FROM DetalleProductoGeneral d")})
public class DetalleProductoGeneral implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    //@Basic(optional = false)
    //@NotNull
    @Column(name = "CODIGO_DETALL_GENERAL")
    private Integer codigoDetallGeneral;
    @Column(name = "CANTIDAD")
    private Integer cantidad;
    // @Max(value=?)  @Min(value=?)//if you know range of your decimal fields consider using these annotations to enforce field validation
    @Column(name = "SUBTOTAL")
    private BigDecimal subtotal;

    @Column(name = "DESCUENTO")
    private BigDecimal descuento;

    @Column(name = "PRECIO_INDIVIDUAL")
    private BigDecimal precioIndividual;

    @JoinColumn(name = "CODIGO_FACTURA", referencedColumnName = "CODIGO_FACTURA")
    @ManyToOne
    private Venta codigoFactura;

    @JoinColumn(name = "CODIGO_PRODUCTO", referencedColumnName = "CODIGO_PRODUCTO")
    @ManyToOne
    private CatalagoProducto codigoProducto;

    @JoinColumn(name = "NICK", referencedColumnName = "NICK")
    @ManyToOne
    private Usuario nick;

    public DetalleProductoGeneral() {
    }

    public DetalleProductoGeneral(Integer codigoDetallGeneral) {
        this.codigoDetallGeneral = codigoDetallGeneral;
    }

    public Integer getCodigoDetallGeneral() {
        return codigoDetallGeneral;
    }

    public void setCodigoDetallGeneral(Integer codigoDetallGeneral) {
        this.codigoDetallGeneral = codigoDetallGeneral;
    }

    public Integer getCantidad() {
        return cantidad;
    }

    public void setCantidad(Integer cantidad) {
        this.cantidad = cantidad;
    }

    public BigDecimal getSubtotal() {
        return subtotal;
    }

    public void setSubtotal(BigDecimal subtotal) {
        this.subtotal = subtotal;
    }

    public Venta getCodigoFactura() {
        return codigoFactura;
    }

    public void setCodigoFactura(Venta codigoFactura) {
        this.codigoFactura = codigoFactura;
    }

    public CatalagoProducto getCodigoProducto() {
        return codigoProducto;
    }

    public void setCodigoProducto(CatalagoProducto codigoProducto) {
        this.codigoProducto = codigoProducto;
    }

    public BigDecimal getDescuento() {
        return descuento;
    }

    public void setDescuento(BigDecimal descuento) {
        this.descuento = descuento;
    }

    public BigDecimal getPrecioIndividual() {
        return precioIndividual;
    }

    public void setPrecioIndividual(BigDecimal precioIndividual) {
        this.precioIndividual = precioIndividual;
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
        hash += (codigoDetallGeneral != null ? codigoDetallGeneral.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof DetalleProductoGeneral)) {
            return false;
        }
        DetalleProductoGeneral other = (DetalleProductoGeneral) object;
        if ((this.codigoDetallGeneral == null && other.codigoDetallGeneral != null) || (this.codigoDetallGeneral != null && !this.codigoDetallGeneral.equals(other.codigoDetallGeneral))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "ec.com.codesoft.model.DetalleProductoGeneral[ codigoDetallGeneral=" + codigoDetallGeneral + " ]";
    }

}
