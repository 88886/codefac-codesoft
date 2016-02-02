/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package ec.com.codesoft.model;

import java.io.Serializable;
import java.util.Objects;
import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.OneToOne;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

/**
 *
 * @author Suco
 */
@Entity
@Table(name = "producto_general_venta")
@NamedQueries({
    @NamedQuery(name = "ProductoGeneralVenta.findAll", query = "SELECT p FROM ProductoGeneralVenta p")})
public class ProductoGeneralVenta implements Serializable {
    private static final long serialVersionUID = 1L;
    @Id
    //@NotNull
    @Column(name = "CODIGO_PRODUCTO")
    private String codigoProducto;
    
    @Column(name = "CANTIDAD_DISPONIBLE")
    private Integer cantidadDisponible;
    @Column(name = "CANTIDAD_BAJA")
    private Integer cantidadBaja;
    @Column(name = "CANTIDAD_ROBADO")
    private Integer cantidadRobado;
    @Column(name = "CANTIDAD_VENDIDA")
    private Integer cantidadVendida;
    @Column(name = "CANTIDAD_CADUCADA")
    private Integer cantidadCaducada;
    @Column(name = "LIMITE_MINIMO")
    private Integer limiteMinimo;
    
    @JoinColumn(name = "CODIGO_PRODUCTO", referencedColumnName = "CODIGO_PRODUCTO", insertable = false, updatable = false)
    @OneToOne
    private CatalagoProducto catalagoProducto;
    
    @JoinColumn(name = "CODIGO_RESERVA_PROD_GENERAL", referencedColumnName = "CODIGO_RESERVA_PROD_GENERAL")
    @ManyToOne
    private ReservaProductoGeneral codigoReservaProdGeneral;

    public ProductoGeneralVenta() {
    }

    public void agregarProductos(Integer cantidad)
    {
        this.cantidadDisponible=this.cantidadDisponible+cantidad;
    }
    
    public ProductoGeneralVenta(String codigoProducto) {
        this.codigoProducto = codigoProducto;
    }

    public String getCodigoProducto() {
        return codigoProducto;
    }

    public void setCodigoProducto(String codigoProducto) {
        this.codigoProducto = codigoProducto;
    }

    public Integer getCantidadDisponible() {
        return cantidadDisponible;
    }

    public void setCantidadDisponible(Integer cantidadDisponible) {
        this.cantidadDisponible = cantidadDisponible;
    }

    public Integer getCantidadBaja() {
        return cantidadBaja;
    }

    public void setCantidadBaja(Integer cantidadBaja) {
        this.cantidadBaja = cantidadBaja;
    }

    public Integer getCantidadRobado() {
        return cantidadRobado;
    }

    public void setCantidadRobado(Integer cantidadRobado) {
        this.cantidadRobado = cantidadRobado;
    }

    public Integer getCantidadVendida() {
        return cantidadVendida;
    }

    public void setCantidadVendida(Integer cantidadVendida) {
        this.cantidadVendida = cantidadVendida;
    }

    public Integer getCantidadCaducada() {
        return cantidadCaducada;
    }

    public void setCantidadCaducada(Integer cantidadCaducada) {
        this.cantidadCaducada = cantidadCaducada;
    }

    public Integer getLimiteMinimo() {
        return limiteMinimo;
    }

    public void setLimiteMinimo(Integer limiteMinimo) {
        this.limiteMinimo = limiteMinimo;
    }

    public CatalagoProducto getCatalagoProducto() {
        return catalagoProducto;
    }

    public void setCatalagoProducto(CatalagoProducto catalagoProducto) {
        this.catalagoProducto = catalagoProducto;
    }

    public ReservaProductoGeneral getCodigoReservaProdGeneral() {
        return codigoReservaProdGeneral;
    }

    public void setCodigoReservaProdGeneral(ReservaProductoGeneral codigoReservaProdGeneral) {
        this.codigoReservaProdGeneral = codigoReservaProdGeneral;
    }

    @Override
    public int hashCode() {
        int hash = 7;
        hash = 41 * hash + Objects.hashCode(this.codigoProducto);
        return hash;
    }

    @Override
    public boolean equals(Object obj) {
        if (obj == null) {
            return false;
        }
        if (getClass() != obj.getClass()) {
            return false;
        }
        final ProductoGeneralVenta other = (ProductoGeneralVenta) obj;
        if (!Objects.equals(this.codigoProducto, other.codigoProducto)) {
            return false;
        }
        return true;
    }

    

    @Override
    public String toString() {
        return "ProductoGeneralVenta{" + "codigoProducto=" + codigoProducto + ", cantidadDisponible=" + cantidadDisponible + ", cantidadBaja=" + cantidadBaja + ", cantidadRobado=" + cantidadRobado + ", cantidadVendida=" + cantidadVendida + ", cantidadCaducada=" + cantidadCaducada + ", limiteMinimo=" + limiteMinimo + ", catalagoProducto=" + catalagoProducto + ", codigoReservaProdGeneral=" + codigoReservaProdGeneral + '}';
    }

    
    
}
