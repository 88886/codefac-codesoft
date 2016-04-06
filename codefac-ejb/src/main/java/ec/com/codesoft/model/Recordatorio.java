/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ec.com.codesoft.model;

import java.io.Serializable;
import java.util.Date;
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
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.validation.constraints.Size;
import javax.xml.bind.annotation.XmlRootElement;

/**
 *
 * @author carlo
 */
@Entity
@Table(name = "recordatorio")
@NamedQueries({
    @NamedQuery(name = "Recordatorio.findAll", query = "SELECT r FROM Recordatorio r")})
public class Recordatorio implements Serializable {
    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "ID_RECORDATORIO")
    private Integer idRecordatorio;
    @Size(max = 2048)
    @Column(name = "CONTENIDO")
    private String contenido;
    @Column(name = "FECHA_CREADO")
    @Temporal(TemporalType.TIMESTAMP)
    private Date fechaCreado;
    @Column(name = "FECHA_RECORDAR")
    @Temporal(TemporalType.TIMESTAMP)
    private Date fechaRecordar;
    @Size(max = 12)
    @Column(name = "ESTADO")
    private String estado;
    @Column(name = "INTERVALO_RECORDAR")
    private Integer intervaloRecordar;
    @JoinColumn(name = "NICK", referencedColumnName = "NICK")
    @ManyToOne
    private Usuario nick;

    public Recordatorio() {
    }

    public Recordatorio(Integer idRecordatorio) {
        this.idRecordatorio = idRecordatorio;
    }

    public Integer getIdRecordatorio() {
        return idRecordatorio;
    }

    public void setIdRecordatorio(Integer idRecordatorio) {
        this.idRecordatorio = idRecordatorio;
    }

    public String getContenido() {
        return contenido;
    }

    public void setContenido(String contenido) {
        this.contenido = contenido;
    }

    public Date getFechaCreado() {
        return fechaCreado;
    }

    public void setFechaCreado(Date fechaCreado) {
        this.fechaCreado = fechaCreado;
    }

    public Date getFechaRecordar() {
        return fechaRecordar;
    }

    public void setFechaRecordar(Date fechaRecordar) {
        this.fechaRecordar = fechaRecordar;
    }

    public String getEstado() {
        return estado;
    }

    public void setEstado(String estado) {
        this.estado = estado;
    }

    public Integer getIntervaloRecordar() {
        return intervaloRecordar;
    }

    public void setIntervaloRecordar(Integer intervaloRecordar) {
        this.intervaloRecordar = intervaloRecordar;
    }

    public Usuario getNick() {
        return nick;
    }

    public void setNick(Usuario nick) {
        this.nick = nick;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (idRecordatorio != null ? idRecordatorio.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof Recordatorio)) {
            return false;
        }
        Recordatorio other = (Recordatorio) object;
        if ((this.idRecordatorio == null && other.idRecordatorio != null) || (this.idRecordatorio != null && !this.idRecordatorio.equals(other.idRecordatorio))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "modelo.Recordatorio[ idRecordatorio=" + idRecordatorio + " ]";
    }
    
}
