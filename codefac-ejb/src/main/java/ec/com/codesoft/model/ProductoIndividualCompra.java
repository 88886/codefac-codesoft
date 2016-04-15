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
@Table(name = "producto_individual_compra")
@NamedQueries({
    @NamedQuery(name = "ProductoIndividualCompra.findAll", query = "SELECT p FROM ProductoIndividualCompra p")})
public class ProductoIndividualCompra implements Serializable {

    private static final long serialVersionUID = 1L;
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "ID_PRODUCTO_INDIVIDUAL_COMPRA")
    private Integer idProductoIndividualCompra;
    
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 64)
    @Column(name = "CODIGO_UNICO")
    private String codigoUnico;
    
// @Max(value=?)  @Min(value=?)//if you know range of your decimal fields consider using these annotations to enforce field validation
    @Column(name = "COSTO")
    private BigDecimal costo;
    @Size(max = 16)
    @Column(name = "ESTADO_FISICO")
    private String estadoFisico;
    @Size(max = 16)
    @Column(name = "ESTADO_PROCESO")
    private String estadoProceso;
    @Size(max = 16)
    @Column(name = "UBICACION")
    private String ubicacion;
    @Column(name = "RESERVADO_TEMPORAL_COMPRA")
    private Boolean reservadoTemporalCompra;
    @Column(name = "FECHA_RESERVA_TEMPORAL")
    @Temporal(TemporalType.TIMESTAMP)
    private Date fechaReservaTemporal;
    @OneToMany(mappedBy = "idProductoIndividualCompra")
    private List<DetalleProductoIndividual> detalleProductoIndividualList;

    @JoinColumn(name = "CODIGO_COMPRA", referencedColumnName = "CODIGO_COMPRA")
    @ManyToOne()
    private Compra codigoCompra;

    @JoinColumn(name = "CODIGO_PRODUCTO", referencedColumnName = "CODIGO_PRODUCTO")
    @ManyToOne
    private CatalagoProducto codigoProducto;

    public ProductoIndividualCompra() {
    }

    public ProductoIndividualCompra(String codigoUnico) {
        this.codigoUnico = codigoUnico;
    }

    public String getCodigoUnico() {
        return codigoUnico;
    }

    public void setCodigoUnico(String codigoUnico) {
        this.codigoUnico = codigoUnico;
    }

    public BigDecimal getCosto() {
        return costo;
    }

    public void setCosto(BigDecimal costo) {
        this.costo = costo;
    }

    public String getEstadoFisico() {
        return estadoFisico;
    }

    public void setEstadoFisico(String estadoFisico) {
        this.estadoFisico = estadoFisico;
    }

    public String getEstadoProceso() {
        return estadoProceso;
    }

    public void setEstadoProceso(String estadoProceso) {
        this.estadoProceso = estadoProceso;
    }

    public String getUbicacion() {
        return ubicacion;
    }

    public void setUbicacion(String ubicacion) {
        this.ubicacion = ubicacion;
    }

    public Boolean getReservadoTemporalCompra() {
        return reservadoTemporalCompra;
    }

    public void setReservadoTemporalCompra(Boolean reservadoTemporalCompra) {
        this.reservadoTemporalCompra = reservadoTemporalCompra;
    }

    public Date getFechaReservaTemporal() {
        return fechaReservaTemporal;
    }

    public void setFechaReservaTemporal(Date fechaReservaTemporal) {
        this.fechaReservaTemporal = fechaReservaTemporal;
    }

    public List<DetalleProductoIndividual> getDetalleProductoIndividualList() {
        return detalleProductoIndividualList;
    }

    public void setDetalleProductoIndividualList(List<DetalleProductoIndividual> detalleProductoIndividualList) {
        this.detalleProductoIndividualList = detalleProductoIndividualList;
    }

    public Compra getCodigoCompra() {
        return codigoCompra;
    }

    public void setCodigoCompra(Compra codigoCompra) {
        this.codigoCompra = codigoCompra;
    }

    public CatalagoProducto getCodigoProducto() {
        return codigoProducto;
    }

    public void setCodigoProducto(CatalagoProducto codigoProducto) {
        this.codigoProducto = codigoProducto;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (codigoUnico != null ? codigoUnico.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof ProductoIndividualCompra)) {
            return false;
        }
        ProductoIndividualCompra other = (ProductoIndividualCompra) object;
        if ((this.codigoUnico == null && other.codigoUnico != null) || (this.codigoUnico != null && !this.codigoUnico.equals(other.codigoUnico))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "ec.com.codesoft.model.ProductoIndividualCompra[ codigoUnico=" + codigoUnico + " ]";
    }

}
