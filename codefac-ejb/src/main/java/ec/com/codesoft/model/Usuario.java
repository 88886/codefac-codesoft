/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ec.com.codesoft.model;

import java.io.Serializable;
import java.util.List;
import javax.persistence.Basic;
import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlTransient;

/**
 *
 * @author carlo
 */
@Entity
@Table(name = "usuario")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "Usuario.findAll", query = "SELECT u FROM Usuario u"),
    @NamedQuery(name = "Usuario.findByNick", query = "SELECT u FROM Usuario u WHERE u.nick = :nick"),
    @NamedQuery(name = "Usuario.findByClave", query = "SELECT u FROM Usuario u WHERE u.clave = :clave"),
    @NamedQuery(name = "Usuario.findByCargo", query = "SELECT u FROM Usuario u WHERE u.cargo = :cargo"),
    @NamedQuery(name = "Usuario.findByNombres", query = "SELECT u FROM Usuario u WHERE u.nombres = :nombres"),
    @NamedQuery(name = "Usuario.findByDireccion", query = "SELECT u FROM Usuario u WHERE u.direccion = :direccion"),
    @NamedQuery(name = "Usuario.findByTelefono", query = "SELECT u FROM Usuario u WHERE u.telefono = :telefono"),
    @NamedQuery(name = "Usuario.findByCelular", query = "SELECT u FROM Usuario u WHERE u.celular = :celular"),
    @NamedQuery(name = "Usuario.findByEmail", query = "SELECT u FROM Usuario u WHERE u.email = :email"),
    @NamedQuery(name = "Usuario.findByEstado", query = "SELECT u FROM Usuario u WHERE u.estado = :estado")})
public class Usuario implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 64)
    @Column(name = "NICK")
    private String nick;
    @Size(max = 128)
    @Column(name = "CLAVE")
    private String clave;
    @Size(max = 32)
    @Column(name = "CARGO")
    private String cargo;
    @Size(max = 32)
    @Column(name = "CEDULA")
    private String cedula;
    @Size(max = 128)
    @Column(name = "NOMBRES")
    private String nombres;
    @Size(max = 128)
    @Column(name = "DIRECCION")
    private String direccion;
    @Size(max = 12)
    @Column(name = "TELEFONO")
    private String telefono;
    @Size(max = 12)
    @Column(name = "CELULAR")
    private String celular;
    // @Pattern(regexp="[a-z0-9!#$%&'*+/=?^_`{|}~-]+(?:\\.[a-z0-9!#$%&'*+/=?^_`{|}~-]+)*@(?:[a-z0-9](?:[a-z0-9-]*[a-z0-9])?\\.)+[a-z0-9](?:[a-z0-9-]*[a-z0-9])?", message="Invalid email")//if the field contains email address consider using this annotation to enforce field validation
    @Size(max = 64)
    @Column(name = "EMAIL")
    private String email;
    @Size(max = 16)
    @Column(name = "ESTADO")
    private String estado;
    @Column(name = "NOTAS")
    private String notas;

    @OneToMany(mappedBy = "nick")
    private List<Compra> compraList;
    @OneToMany(mappedBy = "nick")
    private List<DetalleProductoIndividual> detalleProductoIndividualList;
    @OneToMany(mappedBy = "nick")
    private List<OrdenTrabajo> ordenTrabajoList;
    @OneToMany(mappedBy = "usuEmpleado")
    private List<DetalleOrdenTrabajo> detalleOrdenTrabajoList;
    @OneToMany(mappedBy = "nick")
    private List<Venta> ventaList;
    @OneToMany(mappedBy = "nick")
    private List<DetalleProductoGeneral> detalleProductoGeneralList;
    @OneToMany(mappedBy = "nick")
    private List<DetalleVentaOrdenTrabajo> detalleVentaOrdenTrabajoList;
    @OneToMany(mappedBy = "nick", cascade = CascadeType.ALL)
    private List<Perfil> perfilList;
    @OneToMany(mappedBy = "nick")
    private List<DetallesServicio> detallesServicioList;
    @OneToMany(mappedBy = "usuarioPermiso")
    private List<Venta> ventaList1;
    @OneToMany(mappedBy = "nick")
    private List<Recordatorio> recordatorioList;

    public Usuario() {
    }

    public Usuario(String nick) {
        this.nick = nick;
    }

    public Perfil buscarPerfil(String nombre) {
        for (Perfil perfil : perfilList) {
            if (perfil.getTipo().equals(nombre)) {
                return perfil;
            }
        }
        return null;

    }

    /////////////////////////////METODOS GET AND SET///////////////////////
    public String getNick() {
        return nick;
    }

    public void setNick(String nick) {
        this.nick = nick;
    }

    public String getClave() {
        return clave;
    }

    public void setClave(String clave) {
        this.clave = clave;
    }

    public String getCargo() {
        return cargo;
    }

    public void setCargo(String cargo) {
        this.cargo = cargo;
    }

    public String getNombres() {
        return nombres;
    }

    public void setNombres(String nombres) {
        this.nombres = nombres;
    }

    public String getDireccion() {
        return direccion;
    }

    public void setDireccion(String direccion) {
        this.direccion = direccion;
    }

    public String getTelefono() {
        return telefono;
    }

    public void setTelefono(String telefono) {
        this.telefono = telefono;
    }

    public String getCelular() {
        return celular;
    }

    public void setCelular(String celular) {
        this.celular = celular;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getEstado() {
        return estado;
    }

    public void setEstado(String estado) {
        this.estado = estado;
    }

    public String getCedula() {
        return cedula;
    }

    public void setCedula(String cedula) {
        this.cedula = cedula;
    }

    @XmlTransient
    public List<Compra> getCompraList() {
        return compraList;
    }

    public void setCompraList(List<Compra> compraList) {
        this.compraList = compraList;
    }

    @XmlTransient
    public List<DetalleProductoIndividual> getDetalleProductoIndividualList() {
        return detalleProductoIndividualList;
    }

    public void setDetalleProductoIndividualList(List<DetalleProductoIndividual> detalleProductoIndividualList) {
        this.detalleProductoIndividualList = detalleProductoIndividualList;
    }

    public List<DetalleOrdenTrabajo> getDetalleOrdenTrabajoList() {
        return detalleOrdenTrabajoList;
    }

    public void setDetalleOrdenTrabajoList(List<DetalleOrdenTrabajo> detalleOrdenTrabajoList) {
        this.detalleOrdenTrabajoList = detalleOrdenTrabajoList;
    }
    
    

    @XmlTransient
    public List<OrdenTrabajo> getOrdenTrabajoList() {
        return ordenTrabajoList;
    }

    public void setOrdenTrabajoList(List<OrdenTrabajo> ordenTrabajoList) {
        this.ordenTrabajoList = ordenTrabajoList;
    }

    

    @XmlTransient
    public List<Venta> getVentaList() {
        return ventaList;
    }

    public void setVentaList(List<Venta> ventaList) {
        this.ventaList = ventaList;
    }

    @XmlTransient
    public List<DetalleProductoGeneral> getDetalleProductoGeneralList() {
        return detalleProductoGeneralList;
    }

    public void setDetalleProductoGeneralList(List<DetalleProductoGeneral> detalleProductoGeneralList) {
        this.detalleProductoGeneralList = detalleProductoGeneralList;
    }

    @XmlTransient
    public List<DetalleVentaOrdenTrabajo> getDetalleVentaOrdenTrabajoList() {
        return detalleVentaOrdenTrabajoList;
    }

    public void setDetalleVentaOrdenTrabajoList(List<DetalleVentaOrdenTrabajo> detalleVentaOrdenTrabajoList) {
        this.detalleVentaOrdenTrabajoList = detalleVentaOrdenTrabajoList;
    }

    @XmlTransient
    public List<Perfil> getPerfilList() {
        return perfilList;
    }

    public void setPerfilList(List<Perfil> perfilList) {
        this.perfilList = perfilList;
    }

    @XmlTransient
    public List<DetallesServicio> getDetallesServicioList() {
        return detallesServicioList;
    }

    public void setDetallesServicioList(List<DetallesServicio> detallesServicioList) {
        this.detallesServicioList = detallesServicioList;
    }

    public List<Venta> getVentaList1() {
        return ventaList1;
    }

    public void setVentaList1(List<Venta> ventaList1) {
        this.ventaList1 = ventaList1;
    }

    public String getNotas() {
        return notas;
    }

    public void setNotas(String notas) {
        this.notas = notas;
    }

    public List<Recordatorio> getRecordatorioList() {
        return recordatorioList;
    }

    public void setRecordatorioList(List<Recordatorio> recordatorioList) {
        this.recordatorioList = recordatorioList;
    }
    
    

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (nick != null ? nick.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof Usuario)) {
            return false;
        }
        Usuario other = (Usuario) object;
        if ((this.nick == null && other.nick != null) || (this.nick != null && !this.nick.equals(other.nick))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "modelo.Usuario[ nick=" + nick + " ]";
    }

    //obtener Perfiles
    public String toStringPerfiles() {
        String texto = "";
        for (Perfil perfil : perfilList) {
            texto += perfil.getTipo() + ",";
        }
        return texto;
    }

}
