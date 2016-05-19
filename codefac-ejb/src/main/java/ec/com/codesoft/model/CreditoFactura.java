/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package ec.com.codesoft.model;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;
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
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

/**
 *
 * @author Suco
 */
@Entity
@Table(name = "credito_factura")
@NamedQueries({
    @NamedQuery(name = "CreditoFactura.findAll", query = "SELECT c FROM CreditoFactura c")})
public class CreditoFactura implements Serializable {
    private static final long serialVersionUID = 1L;
    @Id
    @Basic(optional = false)
    @NotNull
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "CODIGO_FACTURA_CREDITO")
    private Integer codigoFacturaCredito;
    @Column(name = "FECHA_INICIO")
    @Temporal(TemporalType.DATE)
    private Date fechaInicio;
    @Column(name = "FECHA_FIN")
    @Temporal(TemporalType.DATE)
    private Date fechaFin;
    @Size(max = 16)
    @Column(name = "ESTADO")
    private String estado;
    @Column(name = "CALIFICACION")
    private Integer calificacion;
    @Column(name = "DIA_PAGO")
    private Integer diaPago;
    @JoinColumn(name = "CODIGO_FACTURA", referencedColumnName = "CODIGO_FACTURA")
    @ManyToOne
    private Venta codigoFactura;
    @OneToMany(mappedBy = "codigoFacturaCredito", cascade = CascadeType.ALL)
    private List<AbonoVentaCredito> abonoVentaCreditoList;
    @Column(name = "OBSERVACIONES")
    private String observaciones;

    public CreditoFactura() {
    }

    public CreditoFactura(Integer codigoFacturaCredito) {
        this.codigoFacturaCredito = codigoFacturaCredito;
    }

    public Integer getCodigoFacturaCredito() {
        return codigoFacturaCredito;
    }

    public void setCodigoFacturaCredito(Integer codigoFacturaCredito) {
        this.codigoFacturaCredito = codigoFacturaCredito;
    }

    public Date getFechaInicio() {
        return fechaInicio;
    }

    public void setFechaInicio(Date fechaInicio) {
        this.fechaInicio = fechaInicio;
    }

    public Date getFechaFin() {
        return fechaFin;
    }

    public void setFechaFin(Date fechaFin) {
        this.fechaFin = fechaFin;
    }

    public String getEstado() {
        return estado;
    }

    public void setEstado(String estado) {
        this.estado = estado;
    }

    public Integer getCalificacion() {
        return calificacion;
    }

    public void setCalificacion(Integer calificacion) {
        this.calificacion = calificacion;
    }

    public Integer getDiaPago() {
        return diaPago;
    }

    public void setDiaPago(Integer diaPago) {
        this.diaPago = diaPago;
    }

    public Venta getCodigoFactura() {
        return codigoFactura;
    }

    public void setCodigoFactura(Venta codigoFactura) {
        this.codigoFactura = codigoFactura;
    }

    public List<AbonoVentaCredito> getAbonoVentaCreditoList() {
        return abonoVentaCreditoList;
    }

    public void setAbonoVentaCreditoList(List<AbonoVentaCredito> abonoVentaCreditoList) {
        this.abonoVentaCreditoList = abonoVentaCreditoList;
    }

    public String getObservaciones() {
        return observaciones;
    }

    public void setObservaciones(String observaciones) {
        this.observaciones = observaciones;
    }
    

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (codigoFacturaCredito != null ? codigoFacturaCredito.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof CreditoFactura)) {
            return false;
        }
        CreditoFactura other = (CreditoFactura) object;
        if ((this.codigoFacturaCredito == null && other.codigoFacturaCredito != null) || (this.codigoFacturaCredito != null && !this.codigoFacturaCredito.equals(other.codigoFacturaCredito))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "ec.com.codesoft.model.CreditoFactura[ codigoFacturaCredito=" + codigoFacturaCredito + " ]";
    }
    
    public BigDecimal toStringAbonos() {
        
        BigDecimal total=new BigDecimal("0.0");
        BigDecimal debe;
        for (AbonoVentaCredito abono : abonoVentaCreditoList) {
            total=total.add(abono.getCantidad());
        }
        
        return total;
        
    }
    
}
