/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ec.com.codesoft.modelo;

import java.io.Serializable;
import java.util.Date;
import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

/**
 *
 * @author Suco
 */
@Entity
@Table(name = "cliente")

public class Cliente implements Serializable {
    private static final long serialVersionUID = 1L;
    @Id
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 13)
    @Column(name = "CEDULA_RUC")
    private String cedulaRuc;
    @Size(max = 50)
    @Column(name = "NOMBRES")
    private String nombres;
    @Size(max = 50)
    @Column(name = "APELLIDOS")
    private String apellidos;
    @Size(max = 10)
    @Column(name = "TELEFONO")
    private String telefono;
    @Size(max = 50)
    @Column(name = "CORREO")
    private String correo;
    @Size(max = 100)
    @Column(name = "DIRECCION")
    private String direccion;
    @Size(max = 11)
    @Column(name = "CELULAR")
    private String celular;
    @Column(name = "FECHA_INGRESO")
    @Temporal(TemporalType.TIMESTAMP)
    private Date fechaIngreso;
    @Column(name = "ESTADO")
    private Character estado;
    @Size(max = 15)
    @Column(name = "TIPO")
    private String tipo;
    @Size(max = 25)
    @Column(name = "CIUDAD")
    private String ciudad;
    @Size(max = 100)
    @Column(name = "NOMBRE")
    private String nombre;
    @Column(name = "ULTIMO_MOV")
    @Temporal(TemporalType.TIMESTAMP)
    private Date ultimoMov;
    @Size(max = 200)
    @Column(name = "NOTAS")
    private String notas;

    public Cliente() {
    }

    public Cliente(String cedulaRuc) {
        this.cedulaRuc = cedulaRuc;
    }

    public String getCedulaRuc() {
        return cedulaRuc;
    }

    public void setCedulaRuc(String cedulaRuc) {
        this.cedulaRuc = cedulaRuc;
    }

    public String getNombres() {
        return nombres;
    }

    public void setNombres(String nombres) {
        this.nombres = nombres;
    }

    public String getApellidos() {
        return apellidos;
    }

    public void setApellidos(String apellidos) {
        this.apellidos = apellidos;
    }

    public String getTelefono() {
        return telefono;
    }

    public void setTelefono(String telefono) {
        this.telefono = telefono;
    }

    public String getCorreo() {
        return correo;
    }

    public void setCorreo(String correo) {
        this.correo = correo;
    }

    public String getDireccion() {
        return direccion;
    }

    public void setDireccion(String direccion) {
        this.direccion = direccion;
    }

    public String getCelular() {
        return celular;
    }

    public void setCelular(String celular) {
        this.celular = celular;
    }

    public Date getFechaIngreso() {
        return fechaIngreso;
    }

    public void setFechaIngreso(Date fechaIngreso) {
        this.fechaIngreso = fechaIngreso;
    }

    public Character getEstado() {
        return estado;
    }

    public void setEstado(Character estado) {
        this.estado = estado;
    }

    public String getTipo() {
        return tipo;
    }

    public void setTipo(String tipo) {
        this.tipo = tipo;
    }

    public String getCiudad() {
        return ciudad;
    }

    public void setCiudad(String ciudad) {
        this.ciudad = ciudad;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public Date getUltimoMov() {
        return ultimoMov;
    }

    public void setUltimoMov(Date ultimoMov) {
        this.ultimoMov = ultimoMov;
    }

    public String getNotas() {
        return notas;
    }

    public void setNotas(String notas) {
        this.notas = notas;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (cedulaRuc != null ? cedulaRuc.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof Cliente)) {
            return false;
        }
        Cliente other = (Cliente) object;
        if ((this.cedulaRuc == null && other.cedulaRuc != null) || (this.cedulaRuc != null && !this.cedulaRuc.equals(other.cedulaRuc))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "ec.edu.espe.codesoft.modelo.Cliente[ cedulaRuc=" + cedulaRuc + " ]";
    }
    
}