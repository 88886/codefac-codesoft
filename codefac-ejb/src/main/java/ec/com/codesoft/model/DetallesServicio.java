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
import javax.validation.constraints.Size;
import javax.xml.bind.annotation.XmlRootElement;

/**
 *
 * @author carlo
 */
@Entity
@Table(name = "detalles_servicio")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "DetallesServicio.findAll", query = "SELECT d FROM DetallesServicio d"),
    @NamedQuery(name = "DetallesServicio.findByCodDetalleServicio", query = "SELECT d FROM DetallesServicio d WHERE d.codDetalleServicio = :codDetalleServicio"),
    @NamedQuery(name = "DetallesServicio.findByTotal", query = "SELECT d FROM DetallesServicio d WHERE d.total = :total"),
    @NamedQuery(name = "DetallesServicio.findByNombre", query = "SELECT d FROM DetallesServicio d WHERE d.nombre = :nombre"),
    @NamedQuery(name = "DetallesServicio.findByDescripcion", query = "SELECT d FROM DetallesServicio d WHERE d.descripcion = :descripcion"),
    @NamedQuery(name = "DetallesServicio.findByIva", query = "SELECT d FROM DetallesServicio d WHERE d.iva = :iva")})
public class DetallesServicio implements Serializable {
    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "COD_DETALLE_SERVICIO")
    private Integer codDetalleServicio;
    // @Max(value=?)  @Min(value=?)//if you know range of your decimal fields consider using these annotations to enforce field validation
    @Column(name = "TOTAL")
    private BigDecimal total;
    @Size(max = 64)
    @Column(name = "NOMBRE")
    private String nombre;
    @Size(max = 128)
    @Column(name = "DESCRIPCION_")
    private String descripcion;
    @Column(name = "IVA")
    private BigDecimal iva;
    @JoinColumn(name = "CODIGO_FACTURA", referencedColumnName = "CODIGO_FACTURA")
    @ManyToOne
    private Venta codigoFactura;
    @JoinColumn(name = "ID_CATEGORIA_TRABAJO", referencedColumnName = "ID_CATEGORIA_TRABAJO")
    @ManyToOne
    private CategoriaTrabajo idCategoriaTrabajo;
    @JoinColumn(name = "NICK", referencedColumnName = "NICK")
    @ManyToOne
    private Usuario nick;

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

    public BigDecimal getTotal() {
        return total;
    }

    public void setTotal(BigDecimal total) {
        this.total = total;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getDescripcion() {
        return descripcion;
    }

    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }

    public BigDecimal getIva() {
        return iva;
    }

    public void setIva(BigDecimal iva) {
        this.iva = iva;
    }

    public Venta getCodigoFactura() {
        return codigoFactura;
    }

    public void setCodigoFactura(Venta codigoFactura) {
        this.codigoFactura = codigoFactura;
    }

    public CategoriaTrabajo getIdCategoriaTrabajo() {
        return idCategoriaTrabajo;
    }

    public void setIdCategoriaTrabajo(CategoriaTrabajo idCategoriaTrabajo) {
        this.idCategoriaTrabajo = idCategoriaTrabajo;
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
        return "modelo.DetallesServicio[ codDetalleServicio=" + codDetalleServicio + " ]";
    }
    
}
