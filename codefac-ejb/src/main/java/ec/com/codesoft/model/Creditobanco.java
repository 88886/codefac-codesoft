/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ec.com.codesoft.model;

import java.io.Serializable;
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

/**
 *
 * @author Suco
 */
@Entity
@Table(name = "creditobanco")
@NamedQueries({
    @NamedQuery(name = "Creditobanco.findAll", query = "SELECT c FROM Creditobanco c")})
public class Creditobanco implements Serializable {
    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "CODIGOCREDITO")
    private Integer codigocredito;
    @JoinColumn(name = "CODINTERES", referencedColumnName = "CODINTERES")
    @ManyToOne
    private Intereses codinteres;
    @JoinColumn(name = "CODIGO_FACTURA", referencedColumnName = "CODIGO_FACTURA")
    @ManyToOne
    private Venta codigoFactura;

    public Creditobanco() {
    }

    public Creditobanco(Integer codigocredito) {
        this.codigocredito = codigocredito;
    }

    public Integer getCodigocredito() {
        return codigocredito;
    }

    public void setCodigocredito(Integer codigocredito) {
        this.codigocredito = codigocredito;
    }

    public Intereses getCodinteres() {
        return codinteres;
    }

    public void setCodinteres(Intereses codinteres) {
        this.codinteres = codinteres;
    }

    public Venta getCodigoFactura() {
        return codigoFactura;
    }

    public void setCodigoFactura(Venta codigoFactura) {
        this.codigoFactura = codigoFactura;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (codigocredito != null ? codigocredito.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof Creditobanco)) {
            return false;
        }
        Creditobanco other = (Creditobanco) object;
        if ((this.codigocredito == null && other.codigocredito != null) || (this.codigocredito != null && !this.codigocredito.equals(other.codigocredito))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "MODELOS.Creditobanco[ codigocredito=" + codigocredito + " ]";
    }
    
}
