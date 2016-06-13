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

/**
 *
 * @author Suco
 */
@Entity
@Table(name = "declaraciones")
@NamedQueries({
    @NamedQuery(name = "Declaraciones.findAll", query = "SELECT d FROM Declaraciones d")})
public class Declaraciones implements Serializable {
    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "ID_DECLARACION")
    private Integer idDeclaracion;
    @Column(name = "FECHA_INICIAL")
    @Temporal(TemporalType.DATE)
    private Date fechaInicial;
    @Column(name = "FECHA_FINAL")
    @Temporal(TemporalType.DATE)
    private Date fechaFinal;
    @Column(name = "FACTURA_INICIAL")
    private Integer facturaInicial;
    @Column(name = "FACTURA_FINAL")
    private Integer facturaFinal;
    // @Max(value=?)  @Min(value=?)//if you know range of your decimal fields consider using these annotations to enforce field validation
    @Column(name = "SUBTOTAL_VENTAS")
    private BigDecimal subtotalVentas;
    @Column(name = "SUBTOTAL_COMPRAS")
    private BigDecimal subtotalCompras;
    @Column(name = "IVA_GENERADO")
    private BigDecimal ivaGenerado;
    @Column(name = "ESTADO")
    private String estado;
    @Column(name = "IVA_RETENIDO")
    private BigDecimal ivaRetenido;
    @Column(name = "SUB_TOTAL_NOTAS")
    private BigDecimal subTotalNotas;
    @Column(name = "NOTA_FINAL")
    private Integer notaFinal;
    @JoinColumn(name = "CODIGO_PERIDO", referencedColumnName = "CODIGO_PERIDO")
    @ManyToOne
    private PeriodoContable codigoPerido;

    public Declaraciones() {
    }

    public Declaraciones(Integer idDeclaracion) {
        this.idDeclaracion = idDeclaracion;
    }

    public Integer getIdDeclaracion() {
        return idDeclaracion;
    }

    public void setIdDeclaracion(Integer idDeclaracion) {
        this.idDeclaracion = idDeclaracion;
    }

    public Date getFechaInicial() {
        return fechaInicial;
    }

    public void setFechaInicial(Date fechaInicial) {
        this.fechaInicial = fechaInicial;
    }

    public Date getFechaFinal() {
        return fechaFinal;
    }

    public void setFechaFinal(Date fechaFinal) {
        this.fechaFinal = fechaFinal;
    }

    public Integer getFacturaInicial() {
        return facturaInicial;
    }

    public void setFacturaInicial(Integer facturaInicial) {
        this.facturaInicial = facturaInicial;
    }

    public Integer getFacturaFinal() {
        return facturaFinal;
    }

    public void setFacturaFinal(Integer facturaFinal) {
        this.facturaFinal = facturaFinal;
    }

    public BigDecimal getSubtotalVentas() {
        return subtotalVentas;
    }

    public void setSubtotalVentas(BigDecimal subtotalVentas) {
        this.subtotalVentas = subtotalVentas;
    }

    public BigDecimal getSubtotalCompras() {
        return subtotalCompras;
    }

    public void setSubtotalCompras(BigDecimal subtotalCompras) {
        this.subtotalCompras = subtotalCompras;
    }

    public BigDecimal getIvaGenerado() {
        return ivaGenerado;
    }

    public void setIvaGenerado(BigDecimal ivaGenerado) {
        this.ivaGenerado = ivaGenerado;
    }

    public String getEstado() {
        return estado;
    }

    public void setEstado(String estado) {
        this.estado = estado;
    }

    public BigDecimal getIvaRetenido() {
        return ivaRetenido;
    }

    public void setIvaRetenido(BigDecimal ivaRetenido) {
        this.ivaRetenido = ivaRetenido;
    }

    public BigDecimal getSubTotalNotas() {
        return subTotalNotas;
    }

    public void setSubTotalNotas(BigDecimal subTotalNotas) {
        this.subTotalNotas = subTotalNotas;
    }

    public Integer getNotaFinal() {
        return notaFinal;
    }

    public void setNotaFinal(Integer notaFinal) {
        this.notaFinal = notaFinal;
    }

    public PeriodoContable getCodigoPerido() {
        return codigoPerido;
    }

    public void setCodigoPerido(PeriodoContable codigoPerido) {
        this.codigoPerido = codigoPerido;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (idDeclaracion != null ? idDeclaracion.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof Declaraciones)) {
            return false;
        }
        Declaraciones other = (Declaraciones) object;
        if ((this.idDeclaracion == null && other.idDeclaracion != null) || (this.idDeclaracion != null && !this.idDeclaracion.equals(other.idDeclaracion))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "javaapplication1.modelo.Declaraciones[ idDeclaracion=" + idDeclaracion + " ]";
    }
    
}


