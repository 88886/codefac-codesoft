/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ec.com.codesoft.model;

import java.io.Serializable;
import java.math.BigDecimal;
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
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

/**
 *
 * @author Suco
 */
@Entity(name = "CatalagoProducto")
@Table(name = "catalago_producto")
@NamedQueries({
    @NamedQuery(name = "CatalagoProducto.findAll", query = "SELECT c FROM CatalagoProducto c")})
public class CatalagoProducto implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @Column(name = "CODIGO_PRODUCTO")
    private String codigoProducto;

    @Column(name = "CODIGO_DISTRIBUIDOR")
    private String codigoDistribuidor;

    @Size(max = 256)
    @Column(name = "NOMBRE")
    private String nombre;

    @Size(max = 512)
    @Column(name = "DESCRIPCION")
    private String descripcion;

    @Size(max = 32)
    @Column(name = "MARCA")
    private String marca;
    // @Max(value=?)  @Min(value=?)//if you know range of your decimal fields consider using these annotations to enforce field validation
    @Column(name = "PRECIO")
    private BigDecimal precio;

    @Column(name = "PRECIO_MAYORISTA")
    private BigDecimal precioMayorista;

    @Column(name = "COSTO")
    private BigDecimal costo;

    @Column(name = "DESCUENTO")
    private BigDecimal descuento;

    @Column(name = "DESCUENTO_MAYORISTA")
    private BigDecimal descuentoMayorista;

    @Size(max = 64)
    @Column(name = "UBICACION")
    private String ubicacion;

    @Size(max = 8)
    @Column(name = "UNIDADES")
    private String unidades;

    @Column(name = "TIPO_PRODUCTO")
    private Character tipoProducto;

    @OneToMany(mappedBy = "codigoProducto")
    private List<DetalleProductoGeneral> detalleProductoGeneralList;

    @OneToMany(mappedBy = "codigoProducto")
    private List<ProductoIndividualCompra> productoIndividualCompraList;

    @OneToMany(mappedBy = "codigoProducto")
    private List<ProductoGeneralCompra> productoGeneralCompraList;

    @OneToMany(mappedBy = "codigoProducto")
    private List<DetalleComboProducto> detalleComboProductoCollection;

    @OneToOne(cascade = CascadeType.ALL, mappedBy = "catalagoProducto")
    private ProductoGeneralVenta productoGeneralVenta;

    @JoinColumn(name = "ID_CATEGORIA", referencedColumnName = "ID_CATEGORIA")
    @ManyToOne
    private CategoriaProducto catNombre;

    public CatalagoProducto() {
        this.codigoProducto = "";
        this.descripcion = "";
        this.descuento = new BigDecimal("0");
        this.marca = "";
        this.nombre = "";
        this.precio = new BigDecimal(0.0);
        this.tipoProducto = new Character('G');
        this.ubicacion = "";
        this.unidades = "";

    }

    public CatalagoProducto(String codigoProducto) {
        this.codigoProducto = codigoProducto;
    }
    
    
    //////////////////////METODOS GET AND SET ///////////////////

    public String getCodigoProducto() {
        return codigoProducto;
    }

    public void setCodigoProducto(String codigoProducto) {
        this.codigoProducto = codigoProducto;
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

    public String getMarca() {
        return marca;
    }

    public void setMarca(String marca) {
        this.marca = marca;
    }

    public BigDecimal getPrecio() {
        return precio;
    }

    public void setPrecio(BigDecimal precio) {
        this.precio = precio;
    }

    public String getUbicacion() {
        return ubicacion;
    }

    public void setUbicacion(String ubicacion) {
        this.ubicacion = ubicacion;
    }

    public String getUnidades() {
        return unidades;
    }

    public void setUnidades(String unidades) {
        this.unidades = unidades;
    }

    public List<DetalleProductoGeneral> getDetalleProductoGeneralList() {
        return detalleProductoGeneralList;
    }

    public void setDetalleProductoGeneralList(List<DetalleProductoGeneral> detalleProductoGeneralList) {
        this.detalleProductoGeneralList = detalleProductoGeneralList;
    }

    public List<ProductoIndividualCompra> getProductoIndividualCompraList() {
        return productoIndividualCompraList;
    }

    public void setProductoIndividualCompraList(List<ProductoIndividualCompra> productoIndividualCompraList) {
        this.productoIndividualCompraList = productoIndividualCompraList;
    }

    public List<ProductoGeneralCompra> getProductoGeneralCompraList() {
        return productoGeneralCompraList;
    }

    public void setProductoGeneralCompraList(List<ProductoGeneralCompra> productoGeneralCompraList) {
        this.productoGeneralCompraList = productoGeneralCompraList;
    }

    public ProductoGeneralVenta getProductoGeneralVenta() {
        return productoGeneralVenta;
    }

    public void setProductoGeneralVenta(ProductoGeneralVenta productoGeneralVenta) {
        this.productoGeneralVenta = productoGeneralVenta;
    }

    public CategoriaProducto getCatNombre() {
        return catNombre;
    }

    public void setCatNombre(CategoriaProducto catNombre) {
        this.catNombre = catNombre;
    }

    public Character getTipoProducto() {
        return tipoProducto;
    }

    public void setTipoProducto(Character tipoProducto) {
        this.tipoProducto = tipoProducto;
    }

    public BigDecimal getCosto() {
        return costo;
    }

    public void setCosto(BigDecimal costo) {
        this.costo = costo;
    }

    public void setDescuento(BigDecimal descuento) {
        this.descuento = descuento;
    }

    public BigDecimal getDescuento() {
        return descuento;
    }

    public String getCodigoDistribuidor() {
        return codigoDistribuidor;
    }

    public void setCodigoDistribuidor(String codigoDistribuidor) {
        this.codigoDistribuidor = codigoDistribuidor;
    }

    public BigDecimal getPrecioMayorista() {
        return precioMayorista;
    }

    public void setPrecioMayorista(BigDecimal precioMayorista) {
        this.precioMayorista = precioMayorista;
    }

    public BigDecimal getDescuentoMayorista() {
        return descuentoMayorista;
    }

    public void setDescuentoMayorista(BigDecimal descuentoMayorista) {
        this.descuentoMayorista = descuentoMayorista;
    }
    
    

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (codigoProducto != null ? codigoProducto.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof CatalagoProducto)) {
            return false;
        }
        CatalagoProducto other = (CatalagoProducto) object;
        if ((this.codigoProducto == null && other.codigoProducto != null) || (this.codigoProducto != null && !this.codigoProducto.equals(other.codigoProducto))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "ec.com.codesoft.model.CatalagoProducto[ codigoProducto=" + codigoProducto + " ]";
    }
    

}
