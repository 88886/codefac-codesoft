/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package ec.com.codesoft.model;

import java.io.Serializable;
import java.math.BigDecimal;
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
import javax.persistence.Table;
import javax.validation.constraints.NotNull;
import org.omg.PortableServer.THREAD_POLICY_ID;

/**
 *
 * @author Suco
 */
@Entity
@Table(name = "producto_general_compra")
@NamedQueries({
    @NamedQuery(name = "ProductoGeneralCompra.findAll", query = "SELECT p FROM ProductoGeneralCompra p")})
public class ProductoGeneralCompra implements Serializable {
    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "CODIGO_GENERADO")
    private Integer codigoGenerado;
    
    @Column(name = "CANTIDAD")
    private Integer cantidad;
    // @Max(value=?)  @Min(value=?)//if you know range of your decimal fields consider using these annotations to enforce field validation
    @Column(name = "COSTO_INDIVIDUAL")
    private BigDecimal costoIndividual;
    @Column(name = "CANTIDAD_MAL_ESTADO")
    private Integer cantidadMalEstado;
    @Column(name = "CANTIDAD__CADUCADA")
    private Integer cantidadCaducada;
    
    @JoinColumn(name = "CODIGO_COMPRA", referencedColumnName = "CODIGO_COMPRA")
    @ManyToOne
    private Compra codigoCompra;
    
    @JoinColumn(name = "CODIGO_PRODUCTO", referencedColumnName = "CODIGO_PRODUCTO")
    @ManyToOne
    private CatalagoProducto codigoProducto;

    public ProductoGeneralCompra() {
    }

    public ProductoGeneralCompra(Integer codigoGenerado) {
        this.codigoGenerado = codigoGenerado;
    }

    public Integer getCodigoGenerado() {
        return codigoGenerado;
    }

    public void setCodigoGenerado(Integer codigoGenerado) {
        this.codigoGenerado = codigoGenerado;
    }

    public Integer getCantidad() {
        return cantidad;
    }

    public void setCantidad(Integer cantidad) {
        this.cantidad = cantidad;
    }

    public BigDecimal getCostoIndividual() {
        return costoIndividual;
    }

    public void setCostoIndividual(BigDecimal costoIndividual) {
        this.costoIndividual = costoIndividual;
    }

    public Integer getCantidadMalEstado() {
        return cantidadMalEstado;
    }

    public void setCantidadMalEstado(Integer cantidadMalEstado) {
        this.cantidadMalEstado = cantidadMalEstado;
    }

    public Integer getCantidadCaducada() {
        return cantidadCaducada;
    }

    public void setCantidadCaducada(Integer cantidadCaducada) {
        this.cantidadCaducada = cantidadCaducada;
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
    
    public BigDecimal getSubtotal()
    {
        return this.costoIndividual.multiply(new BigDecimal(this.cantidad));        
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (codigoGenerado != null ? codigoGenerado.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof ProductoGeneralCompra)) {
            return false;
        }
        ProductoGeneralCompra other = (ProductoGeneralCompra) object;
        if ((this.codigoGenerado == null && other.codigoGenerado != null) || (this.codigoGenerado != null && !this.codigoGenerado.equals(other.codigoGenerado))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "ec.com.codesoft.model.ProductoGeneralCompra[ codigoGenerado=" + codigoGenerado + " ]";
    }
    
}
