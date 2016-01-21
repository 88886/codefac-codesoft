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
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

/**
 *
 * @author Suco
 */
@Entity
@Table(name = "detalles_servicio")
@NamedQueries({
    @NamedQuery(name = "DetallesServicio.findAll", query = "SELECT d FROM DetallesServicio d")})
public class DetallesServicio implements Serializable {
    private static final long serialVersionUID = 1L;
    @Id
    @Basic(optional = false)
    @NotNull
    @Column(name = "COD_DETALLE_SERVICIO")
    private Integer codDetalleServicio;
    // @Max(value=?)  @Min(value=?)//if you know range of your decimal fields consider using these annotations to enforce field validation
    @Column(name = "COSTO")
    private BigDecimal costo;
    @Size(max = 128)
    @Column(name = "DESCRIPCION_")
    private String descripcion;
    @JoinColumn(name = "CODIGO_FACTURA", referencedColumnName = "CODIGO_FACTURA")
    @ManyToOne
    private Venta codigoFactura;
    @JoinColumn(name = "CODIGO_SERVICIO", referencedColumnName = "CODIGO_SERVICIO")
    @ManyToOne
    private Servicios codigoServicio;

    public DetallesServicio() {
    }

    public DetallesServicio(Integer codDetalleServicio) {
        this.codDetalleServicio = codDetalleServicio;
    }

    public Integer getCodDetalleServicio() {
        return codDetalleServicio;
    }

    public void setCodDetalleServicio(Integer codDetalleServicio) {
        this.codDetalleServicio = codDetalleServicio;
    }

    public BigDecimal getCosto() {
        return costo;
    }

    public void setCosto(BigDecimal costo) {
        this.costo = costo;
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

    public Servicios getCodigoServicio() {
        return codigoServicio;
    }

    public void setCodigoServicio(Servicios codigoServicio) {
        this.codigoServicio = codigoServicio;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (codDetalleServicio != null ? codDetalleServicio.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof DetallesServicio)) {
            return false;
        }
        DetallesServicio other = (DetallesServicio) object;
        if ((this.codDetalleServicio == null && other.codDetalleServicio != null) || (this.codDetalleServicio != null && !this.codDetalleServicio.equals(other.codDetalleServicio))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "ec.com.codesoft.model.DetallesServicio[ codDetalleServicio=" + codDetalleServicio + " ]";
    }
    
}
