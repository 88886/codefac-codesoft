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
import javax.validation.constraints.NotNull;
import javax.xml.bind.annotation.XmlRootElement;
import org.omg.PortableServer.THREAD_POLICY_ID;

@Entity
@Table(name = "detalle_combo_producto")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "DetalleComboProducto.findAll", query = "SELECT d FROM DetalleComboProducto d"),
    @NamedQuery(name = "DetalleComboProducto.findByIdDetalleCombo", query = "SELECT d FROM DetalleComboProducto d WHERE d.idDetalleCombo = :idDetalleCombo"),
    @NamedQuery(name = "DetalleComboProducto.findByCantidad", query = "SELECT d FROM DetalleComboProducto d WHERE d.cantidad = :cantidad")})
public class DetalleComboProducto implements Serializable {
    private static final long serialVersionUID = 1L;
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "ID_DETALLE_COMBO")
    private Integer idDetalleCombo;
    @Column(name = "CANTIDAD")
    private Integer cantidad;
    
    @JoinColumn(name = "ID_COMBO_PRODUCTO", referencedColumnName = "ID_COMBO_PRODUCTO")
    @ManyToOne
    private ComboProducto idComboProducto;
    
    @JoinColumn(name = "CODIGO_PRODUCTO", referencedColumnName = "CODIGO_PRODUCTO")
    @ManyToOne
    private CatalagoProducto codigoProducto;

    public DetalleComboProducto() {
    }

    public DetalleComboProducto(Integer idDetalleCombo) {
        this.idDetalleCombo = idDetalleCombo;
    }

    public Integer getIdDetalleCombo() {
        return idDetalleCombo;
    }

    public void setIdDetalleCombo(Integer idDetalleCombo) {
        this.idDetalleCombo = idDetalleCombo;
    }

    public Integer getCantidad() {
        return cantidad;
    }

    public void setCantidad(Integer cantidad) {
        this.cantidad = cantidad;
    }

    public ComboProducto getIdComboProducto() {
        return idComboProducto;
    }

    public void setIdComboProducto(ComboProducto idComboProducto) {
        this.idComboProducto = idComboProducto;
    }

    public CatalagoProducto getCodigoProducto() {
        return codigoProducto;
    }

    public void setCodigoProducto(CatalagoProducto codigoProducto) {
        this.codigoProducto = codigoProducto;
    }
    
    public String getSubtotal()
    {
        
        System.out.println(this.codigoProducto.getPrecio().multiply(new BigDecimal(cantidad)));
        return this.codigoProducto.getPrecio().multiply(new BigDecimal(cantidad)).toString();
        
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (idDetalleCombo != null ? idDetalleCombo.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof DetalleComboProducto)) {
            return false;
        }
        DetalleComboProducto other = (DetalleComboProducto) object;
        if ((this.idDetalleCombo == null && other.idDetalleCombo != null) || (this.idDetalleCombo != null && !this.idDetalleCombo.equals(other.idDetalleCombo))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "modelo.DetalleComboProducto[ idDetalleCombo=" + idDetalleCombo + " ]";
    }
    
}
