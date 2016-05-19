/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package ec.com.codesoft.model;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;
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
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

/**
 *
 * @author Suco
 */
@Entity
@Table(name = "abono_venta_credito")
@NamedQueries({
    @NamedQuery(name = "AbonoVentaCredito.findAll", query = "SELECT a FROM AbonoVentaCredito a")})
public class AbonoVentaCredito implements Serializable {
    private static final long serialVersionUID = 1L;
    @Id
    @Basic(optional = false)
    @NotNull
    @Column(name = "CODIGO_ABONO")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer codigoAbono;
    // @Max(value=?)  @Min(value=?)//if you know range of your decimal fields consider using these annotations to enforce field validation
    @Column(name = "CANTIDAD")
    private BigDecimal cantidad;
    @Column(name = "FECHA")
    @Temporal(TemporalType.TIMESTAMP)
    private Date fecha;
    @Size(max = 512)
    @Column(name = "DESCRIPCION")
    private String descripcion;
    @JoinColumn(name = "CODIGO_FACTURA_CREDITO", referencedColumnName = "CODIGO_FACTURA_CREDITO")
    @ManyToOne
    private CreditoFactura codigoFacturaCredito;

    public AbonoVentaCredito() {
    }

    public AbonoVentaCredito(Integer codigoAbono) {
        this.codigoAbono = codigoAbono;
    }

    public Integer getCodigoAbono() {
        return codigoAbono;
    }

    public void setCodigoAbono(Integer codigoAbono) {
        this.codigoAbono = codigoAbono;
    }

    public BigDecimal getCantidad() {
        return cantidad;
    }

    public void setCantidad(BigDecimal cantidad) {
        this.cantidad = cantidad;
    }

    public Date getFecha() {
        return fecha;
    }

    public void setFecha(Date fecha) {
        this.fecha = fecha;
    }

    public String getDescripcion() {
        return descripcion;
    }

    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }

    public CreditoFactura getCodigoFacturaCredito() {
        return codigoFacturaCredito;
    }

    public void setCodigoFacturaCredito(CreditoFactura codigoFacturaCredito) {
        this.codigoFacturaCredito = codigoFacturaCredito;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (codigoAbono != null ? codigoAbono.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof AbonoVentaCredito)) {
            return false;
        }
        AbonoVentaCredito other = (AbonoVentaCredito) object;
        if ((this.codigoAbono == null && other.codigoAbono != null) || (this.codigoAbono != null && !this.codigoAbono.equals(other.codigoAbono))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "ec.com.codesoft.model.AbonoVentaCredito[ codigoAbono=" + codigoAbono + " ]";
    }
    
}
