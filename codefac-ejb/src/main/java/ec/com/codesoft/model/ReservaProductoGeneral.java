/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ec.com.codesoft.model;

import java.io.Serializable;
import java.util.Date;
import java.util.List;
import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.validation.constraints.NotNull;

/**
 *
 * @author Suco
 */
@Entity
@Table(name = "reserva_producto_general")
@NamedQueries({
    @NamedQuery(name = "ReservaProductoGeneral.findAll", query = "SELECT r FROM ReservaProductoGeneral r")})
public class ReservaProductoGeneral implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @Basic(optional = false)
    @NotNull
    @Column(name = "CODIGO_RESERVA_PROD_GENERAL")
    private Integer codigoReservaProdGeneral;
    @Column(name = "CANTIDAD")
    private Integer cantidad;
    @Column(name = "FECHA_RESERVA")
    @Temporal(TemporalType.TIMESTAMP)
    private Date fechaReserva;

    @JoinColumn(name = "CODIGO_PRODUCTO_GENERAL", referencedColumnName = "CODIGO_PRODUCTO_GENERAL")
    @ManyToOne
    private ProductoGeneralVenta codigoProductoGeneral;
    //codigoProductoGeneral

    public ReservaProductoGeneral() {
    }

    public ReservaProductoGeneral(Integer codigoReservaProdGeneral) {
        this.codigoReservaProdGeneral = codigoReservaProdGeneral;
    }

    public Integer getCodigoReservaProdGeneral() {
        return codigoReservaProdGeneral;
    }

    public void setCodigoReservaProdGeneral(Integer codigoReservaProdGeneral) {
        this.codigoReservaProdGeneral = codigoReservaProdGeneral;
    }

    public Integer getCantidad() {
        return cantidad;
    }

    public void setCantidad(Integer cantidad) {
        this.cantidad = cantidad;
    }

    public Date getFechaReserva() {
        return fechaReserva;
    }

    public void setFechaReserva(Date fechaReserva) {
        this.fechaReserva = fechaReserva;
    }

    public ProductoGeneralVenta getCodigoProductoGeneral() {
        return codigoProductoGeneral;
    }

    public void setCodigoProductoGeneral(ProductoGeneralVenta codigoProductoGeneral) {
        this.codigoProductoGeneral = codigoProductoGeneral;
    }

    
    
    @Override
    public int hashCode() {
        int hash = 0;
        hash += (codigoReservaProdGeneral != null ? codigoReservaProdGeneral.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof ReservaProductoGeneral)) {
            return false;
        }
        ReservaProductoGeneral other = (ReservaProductoGeneral) object;
        if ((this.codigoReservaProdGeneral == null && other.codigoReservaProdGeneral != null) || (this.codigoReservaProdGeneral != null && !this.codigoReservaProdGeneral.equals(other.codigoReservaProdGeneral))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "ec.com.codesoft.model.ReservaProductoGeneral[ codigoReservaProdGeneral=" + codigoReservaProdGeneral + " ]";
    }

}
