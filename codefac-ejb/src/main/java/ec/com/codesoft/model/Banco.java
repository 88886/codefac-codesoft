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
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.validation.constraints.Size;

/**
 *
 * @author Suco
 */
@Entity
@Table(name = "banco")
@NamedQueries({
    @NamedQuery(name = "Banco.findAll", query = "SELECT b FROM Banco b")})
public class Banco implements Serializable {
    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "CODIGO_BANCO")
    private Integer codigoBanco;
    @Size(max = 128)
    @Column(name = "NOMBRE")
    private String nombre;
    @Size(max = 512)
    @Column(name = "DESCRIPCION")
    private String descripcion;
    // @Max(value=?)  @Min(value=?)//if you know range of your decimal fields consider using these annotations to enforce field validation
    @Column(name = "RECARGO")
    private BigDecimal recargo;
    @OneToMany(mappedBy = "codigoBanco" ,cascade = CascadeType.ALL)
    private List<Intereses> interesesList;

    public Banco() {
    }

    public Banco(Integer codigoBanco) {
        this.codigoBanco = codigoBanco;
    }

    public Integer getCodigoBanco() {
        return codigoBanco;
    }

    public void setCodigoBanco(Integer codigoBanco) {
        this.codigoBanco = codigoBanco;
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

    public BigDecimal getRecargo() {
        return recargo;
    }

    public void setRecargo(BigDecimal recargo) {
        this.recargo = recargo;
    }

    public List<Intereses> getInteresesList() {
        return interesesList;
    }

    public void setInteresesList(List<Intereses> interesesList) {
        this.interesesList = interesesList;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (codigoBanco != null ? codigoBanco.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof Banco)) {
            return false;
        }
        Banco other = (Banco) object;
        if ((this.codigoBanco == null && other.codigoBanco != null) || (this.codigoBanco != null && !this.codigoBanco.equals(other.codigoBanco))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "MODELOS.Banco[ codigoBanco=" + codigoBanco + " ]";
    }
    
}
