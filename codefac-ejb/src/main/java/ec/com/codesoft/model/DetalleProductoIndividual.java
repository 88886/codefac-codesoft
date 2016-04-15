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
@Table(name = "detalle_producto_individual")
@NamedQueries({
    @NamedQuery(name = "DetalleProductoIndividual.findAll", query = "SELECT d FROM DetalleProductoIndividual d")})
public class DetalleProductoIndividual implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "CODIGO_DETALLE_INDIVIDUAL")
    private Integer codigoDetalleIndividual;
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

    @JoinColumn(name = "NICK", referencedColumnName = "NICK")
    @ManyToOne
    private Usuario nick;

    @JoinColumn(name = "ID_PRODUCTO_INDIVIDUAL_COMPRA", referencedColumnName = "ID_PRODUCTO_INDIVIDUAL_COMPRA")
    @ManyToOne
    private ProductoIndividualCompra idProductoIndividualCompra;

    public DetalleProductoIndividual() {
    }

    public DetalleProductoIndividual(Integer codigoDetalleIndividual) {
        this.codigoDetalleIndividual = codigoDetalleIndividual;
    }

    public Integer getCodigoDetalleIndividual() {
        return codigoDetalleIndividual;
    }

    public void setCodigoDetalleIndividual(Integer codigoDetalleIndividual) {
        this.codigoDetalleIndividual = codigoDetalleIndividual;
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

    public ProductoIndividualCompra getProductoIndividualCompra() {
        return idProductoIndividualCompra;
    }

    public void setProductoIndividualCompra(ProductoIndividualCompra productoIndividualCompra) {
        this.idProductoIndividualCompra = productoIndividualCompra;
    }
    
    

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (codigoDetalleIndividual != null ? codigoDetalleIndividual.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof DetalleProductoIndividual)) {
            return false;
        }
        DetalleProductoIndividual other = (DetalleProductoIndividual) object;
        if ((this.codigoDetalleIndividual == null && other.codigoDetalleIndividual != null) || (this.codigoDetalleIndividual != null && !this.codigoDetalleIndividual.equals(other.codigoDetalleIndividual))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "ec.com.codesoft.model.DetalleProductoIndividual[ codigoDetalleIndividual=" + codigoDetalleIndividual + " ]";
    }

}
