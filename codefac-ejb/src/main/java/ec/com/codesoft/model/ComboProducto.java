/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ec.com.codesoft.model;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Collection;
import java.util.List;
import javax.persistence.Basic;
import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.validation.constraints.Size;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlTransient;

/**
 *
 * @author carlo
 */
@Entity
@Table(name = "combo_producto")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "ComboProducto.findAll", query = "SELECT c FROM ComboProducto c"),
    @NamedQuery(name = "ComboProducto.findByIdComboProducto", query = "SELECT c FROM ComboProducto c WHERE c.idComboProducto = :idComboProducto"),
    @NamedQuery(name = "ComboProducto.findByNombre", query = "SELECT c FROM ComboProducto c WHERE c.nombre = :nombre"),
    @NamedQuery(name = "ComboProducto.findByPrecio", query = "SELECT c FROM ComboProducto c WHERE c.precio = :precio"),
    @NamedQuery(name = "ComboProducto.findByCosto", query = "SELECT c FROM ComboProducto c WHERE c.costo = :costo"),
    @NamedQuery(name = "ComboProducto.findByDescuento", query = "SELECT c FROM ComboProducto c WHERE c.descuento = :descuento"),
    @NamedQuery(name = "ComboProducto.findByDescripcion", query = "SELECT c FROM ComboProducto c WHERE c.descripcion = :descripcion"),
    @NamedQuery(name = "ComboProducto.findByStock", query = "SELECT c FROM ComboProducto c WHERE c.stock = :stock"),
    @NamedQuery(name = "ComboProducto.findByEstado", query = "SELECT c FROM ComboProducto c WHERE c.estado = :estado")})
public class ComboProducto implements Serializable 
{
    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "ID_COMBO_PRODUCTO")
    private Integer idComboProducto;
    @Size(max = 128)
    @Column(name = "NOMBRE")
    private String nombre;
    // @Max(value=?)  @Min(value=?)//if you know range of your decimal fields consider using these annotations to enforce field validation
    @Column(name = "PRECIO")
    private BigDecimal precio;
    @Column(name = "COSTO")
    private BigDecimal costo;
    @Column(name = "DESCUENTO")
    private BigDecimal descuento;
    @Size(max = 512)
    @Column(name = "DESCRIPCION")
    private String descripcion;
    @Column(name = "STOCK")
    private Integer stock;
    @Size(max = 8)
    @Column(name = "ESTADO")
    private String estado;
    
    @OneToMany(mappedBy = "idComboProducto",cascade = CascadeType.ALL,orphanRemoval = true)
    private List<DetalleComboProducto> detalleComboProductoCollection;

    public ComboProducto() {
    }

    public ComboProducto(Integer idComboProducto) {
        this.idComboProducto = idComboProducto;
    }

    public Integer getIdComboProducto() {
        return idComboProducto;
    }

    public void setIdComboProducto(Integer idComboProducto) {
        this.idComboProducto = idComboProducto;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public BigDecimal getPrecio() {
        return precio;
    }

    public void setPrecio(BigDecimal precio) {
        this.precio = precio;
    }

    public BigDecimal getCosto() {
        return costo;
    }

    public void setCosto(BigDecimal costo) {
        this.costo = costo;
    }

    public BigDecimal getDescuento() {
        return descuento;
    }

    public void setDescuento(BigDecimal descuento) {
        this.descuento = descuento;
    }

    public String getDescripcion() {
        return descripcion;
    }

    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }

    public Integer getStock() {
        return stock;
    }

    public void setStock(Integer stock) {
        this.stock = stock;
    }

    public String getEstado() {
        return estado;
    }

    public void setEstado(String estado) {
        this.estado = estado;
    }
    
    public String imprimirDetalle()
    {
        String cadena="";
        for (DetalleComboProducto detalle : detalleComboProductoCollection) 
        {
            cadena+=detalle.getCodigoProducto().getNombre()+",";
        }
        return cadena;
    }

//    @XmlTransient
//    public Collection<DetalleComboProducto> getDetalleComboProductoCollection() {
//        return detalleComboProductoCollection;
//    }
//
//    public void setDetalleComboProductoCollection(Collection<DetalleComboProducto> detalleComboProductoCollection) {
//        this.detalleComboProductoCollection = detalleComboProductoCollection;
//    }

    public List<DetalleComboProducto> getDetalleComboProductoCollection() {
        return detalleComboProductoCollection;
    }

    public void setDetalleComboProductoCollection(List<DetalleComboProducto> detalleComboProductoCollection) {
        this.detalleComboProductoCollection = detalleComboProductoCollection;
    }
    
    

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (idComboProducto != null ? idComboProducto.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof ComboProducto)) {
            return false;
        }
        ComboProducto other = (ComboProducto) object;
        if ((this.idComboProducto == null && other.idComboProducto != null) || (this.idComboProducto != null && !this.idComboProducto.equals(other.idComboProducto))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "modelo.ComboProducto[ idComboProducto=" + idComboProducto + " ]";
    }
    
}
