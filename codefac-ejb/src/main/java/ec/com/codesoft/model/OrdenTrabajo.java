/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ec.com.codesoft.model;

import ec.com.codesoft.util.TiempoUtil;
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
import javax.persistence.OneToOne;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.validation.constraints.Size;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlTransient;

/**
 *
 * @author carlo
 */
@Entity
@Table(name = "orden_trabajo")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "OrdenTrabajo.findAll", query = "SELECT o FROM OrdenTrabajo o"),
    @NamedQuery(name = "OrdenTrabajo.findByIdOrdenTrabajo", query = "SELECT o FROM OrdenTrabajo o WHERE o.idOrdenTrabajo = :idOrdenTrabajo"),
    @NamedQuery(name = "OrdenTrabajo.findByFechaEmision", query = "SELECT o FROM OrdenTrabajo o WHERE o.fechaEmision = :fechaEmision"),
    @NamedQuery(name = "OrdenTrabajo.findByFechaEntrega", query = "SELECT o FROM OrdenTrabajo o WHERE o.fechaEntrega = :fechaEntrega"),
    @NamedQuery(name = "OrdenTrabajo.findByObservacion", query = "SELECT o FROM OrdenTrabajo o WHERE o.observacion = :observacion"),
    @NamedQuery(name = "OrdenTrabajo.findByTotal", query = "SELECT o FROM OrdenTrabajo o WHERE o.total = :total"),
    @NamedQuery(name = "OrdenTrabajo.findByAdelanto", query = "SELECT o FROM OrdenTrabajo o WHERE o.adelanto = :adelanto"),
    @NamedQuery(name = "OrdenTrabajo.findByEstado", query = "SELECT o FROM OrdenTrabajo o WHERE o.estado = :estado"),
    @NamedQuery(name = "OrdenTrabajo.findByDiagnostico", query = "SELECT o FROM OrdenTrabajo o WHERE o.diagnostico = :diagnostico"),
    @NamedQuery(name = "OrdenTrabajo.findByDescuento", query = "SELECT o FROM OrdenTrabajo o WHERE o.descuento = :descuento")})
public class OrdenTrabajo implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "ID_ORDEN_TRABAJO")
    private Integer idOrdenTrabajo;
    @Column(name = "FECHA_EMISION")
    @Temporal(TemporalType.TIMESTAMP)
    private Date fechaEmision;
    @Column(name = "FECHA_ENTREGA")
    @Temporal(TemporalType.TIMESTAMP)
    private Date fechaEntrega;
    @Size(max = 128)
    @Column(name = "OBSERVACION")
    private String observacion;
    // @Max(value=?)  @Min(value=?)//if you know range of your decimal fields consider using these annotations to enforce field validation
    @Column(name = "TOTAL")
    private BigDecimal total;
    @Column(name = "ADELANTO")
    private BigDecimal adelanto;
    @Size(max = 32)
    @Column(name = "ESTADO")
    private String estado;
    @Size(max = 256)

    @Column(name = "DIAGNOSTICO")
    private String diagnostico;

    @Column(name = "DESCUENTO")
    private BigDecimal descuento;

    @Column(name = "SALDO_AFAVOR")
    private BigDecimal saldoAfavor;

    @OneToMany(mappedBy = "idOrdenTrabajo", cascade = CascadeType.ALL)
    private List<DetalleOrdenTrabajo> detalleOrdenTrabajoList;

    @JoinColumn(name = "NICK", referencedColumnName = "NICK")
    @ManyToOne
    private Usuario nick;
    @JoinColumn(name = "CEDULA_RUC", referencedColumnName = "CEDULA_RUC")
    @ManyToOne
    private Cliente cedulaRuc;

    public OrdenTrabajo() {
    }

    public OrdenTrabajo(Integer idOrdenTrabajo) {
        this.idOrdenTrabajo = idOrdenTrabajo;
    }

    ///////////////METODOS AGREGADOS////////////////////////////////////
    public String toStringDetalle() {
        String texto = "";

        for (DetalleOrdenTrabajo detalle : detalleOrdenTrabajoList) {
            texto += detalle.getEquipo() + ":" + detalle.getDiagnostico() + ",";
        }
        return texto;
    }

    public String toStringEquipos() {
        String texto = "";
        for (DetalleOrdenTrabajo detalle : detalleOrdenTrabajoList) {
            texto += detalle.getEquipo() + ",";
        }
        return texto;
    }

    /**
     * Calcula los dias entre la fecha de ingreso y de entrga
     *
     * @return
     */
    public int diferenciaDiasEntrega() {
        return TiempoUtil.diferenciaEnDias(fechaEntrega, new Date());
    }

    /**
     * Tiempo de entrega
     *
     * @return
     */
    public String tiempoEntrega() {
        Date fechaActual = new Date();
        String tiempo = "";
        int dias = TiempoUtil.diferenciaEnDias(fechaEntrega, fechaActual);
        int horas = TiempoUtil.diferenciaEnHoras(fechaEntrega, fechaActual);
        int minutos = TiempoUtil.diferenciaEnMinutos(fechaEntrega, fechaActual);

        int horasRestantes = horas - dias * 24;
        int minutosRestantes = minutos - horas * 60;

        //seteando a valores positivos
        dias = Math.abs(dias);
        horasRestantes = Math.abs(horasRestantes);
        minutosRestantes = Math.abs(minutosRestantes);

        //if(dias==0)
        // {
        //     return "hoy ,"+horasRestantes+" horas " + minutosRestantes+" min";
        // }
        return dias + " días " + horasRestantes + " horas " + minutosRestantes + " min";

    }

    /////////////////////////METODOS GET AND SET ///////////////////////
    public Integer getIdOrdenTrabajo() {
        return idOrdenTrabajo;
    }

    public void setIdOrdenTrabajo(Integer idOrdenTrabajo) {
        this.idOrdenTrabajo = idOrdenTrabajo;
    }

    public Date getFechaEmision() {
        return fechaEmision;
    }

    public void setFechaEmision(Date fechaEmision) {
        this.fechaEmision = fechaEmision;
    }

    public Date getFechaEntrega() {
        return fechaEntrega;
    }

    public void setFechaEntrega(Date fechaEntrega) {
        this.fechaEntrega = fechaEntrega;
    }

    public String getObservacion() {
        if (observacion == null || observacion == "") {
            observacion = "S/N";
        }
        return observacion;
    }

    public void setObservacion(String observacion) {
        this.observacion = observacion;
    }

    public BigDecimal getTotal() {
        return total;
    }

    public void setTotal(BigDecimal total) {
        this.total = total;
    }

    public BigDecimal getAdelanto() {
        return adelanto;
    }

    public void setAdelanto(BigDecimal adelanto) {
        this.adelanto = adelanto;
    }

    public String getEstado() {
        return estado;
    }

    public void setEstado(String estado) {
        this.estado = estado;
    }

    public String getDiagnostico() {
        return diagnostico;
    }

    public void setDiagnostico(String diagnostico) {
        this.diagnostico = diagnostico;
    }

    public BigDecimal getDescuento() {
        return descuento;
    }

    public void setDescuento(BigDecimal descuento) {
        this.descuento = descuento;
    }

    @XmlTransient
    public List<DetalleOrdenTrabajo> getDetalleOrdenTrabajoList() {
        return detalleOrdenTrabajoList;
    }

    public void setDetalleOrdenTrabajoList(List<DetalleOrdenTrabajo> detalleOrdenTrabajoList) {
        this.detalleOrdenTrabajoList = detalleOrdenTrabajoList;
    }

    public Usuario getNick() {
        return nick;
    }

    public void setNick(Usuario nick) {
        this.nick = nick;
    }

    public Cliente getCedulaRuc() {
        return cedulaRuc;
    }

    public void setCedulaRuc(Cliente cedulaRuc) {
        this.cedulaRuc = cedulaRuc;
    }

    public BigDecimal getSaldoAfavor() {
        return saldoAfavor;
    }

    public void setSaldoAfavor(BigDecimal saldoAfavor) {
        this.saldoAfavor = saldoAfavor;
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
        if (!(object instanceof OrdenTrabajo)) {
            return false;
        }
        OrdenTrabajo other = (OrdenTrabajo) object;
        if ((this.idOrdenTrabajo == null && other.idOrdenTrabajo != null) || (this.idOrdenTrabajo != null && !this.idOrdenTrabajo.equals(other.idOrdenTrabajo))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "modelo.OrdenTrabajo[ idOrdenTrabajo=" + idOrdenTrabajo + " ]";
    }

}
