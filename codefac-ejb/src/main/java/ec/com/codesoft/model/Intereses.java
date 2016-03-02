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
import javax.validation.constraints.Size;

/**
 *
 * @author Suco
 */
@Entity
@Table(name = "intereses")
@NamedQueries({
    @NamedQuery(name = "Intereses.findAll", query = "SELECT i FROM Intereses i")})
public class Intereses implements Serializable {
    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "CODINTERES")
    private Integer codinteres;
    @Column(name = "MESES")
    private Integer meses;
    // @Max(value=?)  @Min(value=?)//if you know range of your decimal fields consider using these annotations to enforce field validation
    @Column(name = "VALOR")
    private BigDecimal valor;
    @Size(max = 200)
    @Column(name = "NOTAS")
    private String notas;
    @JoinColumn(name = "CODIGO_BANCO", referencedColumnName = "CODIGO_BANCO")
    @ManyToOne
    private Banco codigoBanco;
    @OneToMany(mappedBy = "codinteres")
    private List<Creditobanco> creditobancoList;

    public Intereses() {
    }

    public Intereses(Integer codinteres) {
        this.codinteres = codinteres;
    }

    public Integer getCodinteres() {
        return codinteres;
    }

    public void setCodinteres(Integer codinteres) {
        this.codinteres = codinteres;
    }

    public Integer getMeses() {
        return meses;
    }

    public void setMeses(Integer meses) {
        this.meses = meses;
    }

    public BigDecimal getValor() {
        return valor;
    }

    public void setValor(BigDecimal valor) {
        this.valor = valor;
    }

    public String getNotas() {
        return notas;
    }

    public void setNotas(String notas) {
        this.notas = notas;
    }

    public Banco getCodigoBanco() {
        return codigoBanco;
    }

    public void setCodigoBanco(Banco codigoBanco) {
        this.codigoBanco = codigoBanco;
    }

    public List<Creditobanco> getCreditobancoList() {
        return creditobancoList;
    }

    public void setCreditobancoList(List<Creditobanco> creditobancoList) {
        this.creditobancoList = creditobancoList;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (codinteres != null ? codinteres.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof Intereses)) {
            return false;
        }
        Intereses other = (Intereses) object;
        if ((this.codinteres == null && other.codinteres != null) || (this.codinteres != null && !this.codinteres.equals(other.codinteres))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "MODELOS.Intereses[ codinteres=" + codinteres + " ]";
    }
    
}
