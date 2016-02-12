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
@Table(name = "nota_credito_debito")
@NamedQueries({
    @NamedQuery(name = "NotaCreditoDebito.findAll", query = "SELECT n FROM NotaCreditoDebito n")})
public class NotaCreditoDebito implements Serializable {
    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    //@Basic(optional = false)
    //@NotNull
    @Column(name = "CODIGO_DEBITO_CREDITO")
    private Integer codigoDebitoCredito;
    @Size(max = 20)
    @Column(name = "CODIGO_DOCUMENTO")
    private String codigoDocumento;
    @Size(max = 16)
    @Column(name = "TIPO_NOTA")
    private String tipoNota;
    @Column(name = "FECHA")
    @Temporal(TemporalType.DATE)
    private Date fecha;
    // @Max(value=?)  @Min(value=?)//if you know range of your decimal fields consider using these annotations to enforce field validation
    @Column(name = "CANTIDAD")
    private BigDecimal cantidad;
    @Size(max = 512)
    @Column(name = "DESCRIPCION")
    private String descripcion;
    @JoinColumn(name = "CODIGO_FACTURA", referencedColumnName = "CODIGO_FACTURA")
    @ManyToOne
    private Venta codigoFactura;

    public NotaCreditoDebito() {
    }

    public NotaCreditoDebito(Integer codigoDebitoCredito) {
        this.codigoDebitoCredito = codigoDebitoCredito;
    }

    public Integer getCodigoDebitoCredito() {
        return codigoDebitoCredito;
    }

    public void setCodigoDebitoCredito(Integer codigoDebitoCredito) {
        this.codigoDebitoCredito = codigoDebitoCredito;
    }

    public String getCodigoDocumento() {
        return codigoDocumento;
    }

    public void setCodigoDocumento(String codigoDocumento) {
        this.codigoDocumento = codigoDocumento;
    }

    public String getTipoNota() {
        return tipoNota;
    }

    public void setTipoNota(String tipoNota) {
        this.tipoNota = tipoNota;
    }

    public Date getFecha() {
        return fecha;
    }

    public void setFecha(Date fecha) {
        this.fecha = fecha;
    }

    public BigDecimal getCantidad() {
        return cantidad;
    }

    public void setCantidad(BigDecimal cantidad) {
        this.cantidad = cantidad;
    }

    public String getDescripcion() {
        return descripcion;
    }

    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
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
        hash += (codigoDebitoCredito != null ? codigoDebitoCredito.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof NotaCreditoDebito)) {
            return false;
        }
        NotaCreditoDebito other = (NotaCreditoDebito) object;
        if ((this.codigoDebitoCredito == null && other.codigoDebitoCredito != null) || (this.codigoDebitoCredito != null && !this.codigoDebitoCredito.equals(other.codigoDebitoCredito))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "ec.com.codesoft.model.NotaCreditoDebito[ codigoDebitoCredito=" + codigoDebitoCredito + " ]";
    }
    
}
