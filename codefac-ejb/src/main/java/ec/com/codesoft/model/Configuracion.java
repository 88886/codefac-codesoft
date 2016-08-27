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
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.Table;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;
import javax.xml.bind.annotation.XmlRootElement;

/**
 *
 * @author carlo
 */
@Entity
@Table(name = "configuracion")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "Configuracion.findAll", query = "SELECT c FROM Configuracion c"),
    @NamedQuery(name = "Configuracion.findByIdConfiguracion", query = "SELECT c FROM Configuracion c WHERE c.idConfiguracion = :idConfiguracion"),
    @NamedQuery(name = "Configuracion.findByIva", query = "SELECT c FROM Configuracion c WHERE c.iva = :iva"),
    @NamedQuery(name = "Configuracion.findByPathreportes", query = "SELECT c FROM Configuracion c WHERE c.pathreportes = :pathreportes"),
    @NamedQuery(name = "Configuracion.findByMaxItemFactura", query = "SELECT c FROM Configuracion c WHERE c.maxItemFactura = :maxItemFactura"),
    @NamedQuery(name = "Configuracion.findByMaxItemNota", query = "SELECT c FROM Configuracion c WHERE c.maxItemNota = :maxItemNota"),
    @NamedQuery(name = "Configuracion.findByMaxItemOrdenTrabajo", query = "SELECT c FROM Configuracion c WHERE c.maxItemOrdenTrabajo = :maxItemOrdenTrabajo")})
public class Configuracion implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "ID_CONFIGURACION")
    private Integer idConfiguracion;
    // @Max(value=?)  @Min(value=?)//if you know range of your decimal fields consider using these annotations to enforce field validation
    @Column(name = "IVA")
    private BigDecimal iva;
    @Size(max = 512)
    @Column(name = "PATHREPORTES")
    private String pathreportes;
    @Column(name = "MAX_ITEM_FACTURA")
    private Integer maxItemFactura;
    @Column(name = "MAX_ITEM_NOTA")
    private Integer maxItemNota;
    @Column(name = "MAX_ITEM_ORDEN_TRABAJO")
    private Integer maxItemOrdenTrabajo;
    
    
    @Column(name = "EMAIL_SERVICIO_TECNICO")
    @Pattern(regexp="^[_a-z0-9-]+(.[_a-z0-9-]+)*@[a-z0-9-]+(.[a-z0-9-]+)*(.[a-z]{2,4})$")
    private String emailServicioTecnico;

    @Column(name = "CLAVE_EMAIL_SERVICIO_TECNICO")
    private String claveEmailServicioTecnico;
    
    @Column(name = "DB_USER")
    private String dbUser;
   
    @Column(name = "DB_PASSWORD")
    private String dbPassword;
    
    @Column(name = "STOCK_MINIMO")
    private Integer stockMinimo;
    
    @Column(name = "NOTAS_ORDEN")
    private String notasOrden;
    
    
    
    public Configuracion() {
    }

    public Configuracion(Integer idConfiguracion) {
        this.idConfiguracion = idConfiguracion;
    }

    public Integer getIdConfiguracion() {
        return idConfiguracion;
    }

    public void setIdConfiguracion(Integer idConfiguracion) {
        this.idConfiguracion = idConfiguracion;
    }

    public BigDecimal getIva() {
        return iva;
    }

    public void setIva(BigDecimal iva) {
        this.iva = iva;
    }

    public String getPathreportes() {
        return pathreportes;
    }

    public void setPathreportes(String pathreportes) {
        this.pathreportes = pathreportes;
    }

    public Integer getMaxItemFactura() {
        return maxItemFactura;
    }

    public void setMaxItemFactura(Integer maxItemFactura) {
        this.maxItemFactura = maxItemFactura;
    }

    public Integer getMaxItemNota() {
        return maxItemNota;
    }

    public void setMaxItemNota(Integer maxItemNota) {
        this.maxItemNota = maxItemNota;
    }

    public Integer getMaxItemOrdenTrabajo() {
        return maxItemOrdenTrabajo;
    }

    public void setMaxItemOrdenTrabajo(Integer maxItemOrdenTrabajo) {
        this.maxItemOrdenTrabajo = maxItemOrdenTrabajo;
    }

    public String getEmailServicioTecnico() {
        return emailServicioTecnico;
    }

    public void setEmailServicioTecnico(String emailServicioTecnico) {
        this.emailServicioTecnico = emailServicioTecnico;
    }

    public String getClaveEmailServicioTecnico() {
        return claveEmailServicioTecnico;
    }

    public void setClaveEmailServicioTecnico(String claveEmailServicioTecnico) {
        this.claveEmailServicioTecnico = claveEmailServicioTecnico;
    }

    public String getDbUser() {
        return dbUser;
    }

    public void setDbUser(String dbUser) {
        this.dbUser = dbUser;
    }

    public String getDbPassword() {
        return dbPassword;
    }

    public void setDbPassword(String dbPassword) {
        this.dbPassword = dbPassword;
    }

    public Integer getStockMinimo() {
        return stockMinimo;
    }

    public void setStockMinimo(Integer stockMinimo) {
        this.stockMinimo = stockMinimo;
    }

    public String getNotasOrden() {
        return notasOrden;
    }

    public void setNotasOrden(String notasOrden) {
        this.notasOrden = notasOrden;
    }
    
    

    
    
    

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (idConfiguracion != null ? idConfiguracion.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof Configuracion)) {
            return false;
        }
        Configuracion other = (Configuracion) object;
        if ((this.idConfiguracion == null && other.idConfiguracion != null) || (this.idConfiguracion != null && !this.idConfiguracion.equals(other.idConfiguracion))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "modelo.Configuracion[ idConfiguracion=" + idConfiguracion + " ]";
    }

}
