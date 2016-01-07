/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ec.com.codesoft.web;

import ec.com.codesoft.modelo.Cliente;
import ec.com.codesoft.modelo.servicios.ClienteServicio;
import java.io.Serializable;
import java.util.Date;
import java.util.List;
import javax.annotation.PostConstruct;
import javax.ejb.EJB;
import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.RequestScoped;
import javax.faces.context.FacesContext;

/**
 *
 * @author Suco
 */
@ManagedBean
@RequestScoped
public class ClienteBean implements Serializable {

    private Cliente cliente;
    private Cliente clienteSeleccionado;
    private List<Cliente> clientes;
    private String tituloFormulario;
    private boolean enNueva;
    private boolean enModificar;
    private boolean enDetalles;
    @EJB
    private ClienteServicio clienteServicio;

    @PostConstruct
    public void postCOnstruct() {
        //System.out.println("hola");
        clientes = clienteServicio.obtenerTodos();
    }

    public void nuevaCliente() {
        this.cliente = new Cliente();
        this.enNueva =true;
        this.tituloFormulario = "Creación del Cliente";
    }
    public void modificarCliente() {
        if (this.clienteSeleccionado!=null) {
            this.tituloFormulario = "Modificación de Cliente";
            this.enModificar = true;
        }
    }
    public void guardarCliente() {
          System.out.println("insertando");
        if (this.enNueva) {
            try {
                System.out.println("insertando");
                this.cliente.setEstado('A');
                this.cliente.setFechaIngreso(new Date());
                System.out.println("insertado");
                this.clienteServicio.insertar(this.cliente);
                this.enNueva = false;
                this.clientes = this.clienteServicio.obtenerTodos();
                FacesMessage msg = new FacesMessage(FacesMessage.SEVERITY_INFO, "Cliente Creado.", "Se ha creado el Cliente con cedula "+ this.cliente.getCedulaRuc());
                FacesContext.getCurrentInstance().addMessage(null, msg);
            } catch (Exception e) {
                FacesMessage msg = new FacesMessage(FacesMessage.SEVERITY_ERROR, "Error al crear cliente.", e.getMessage());
                FacesContext.getCurrentInstance().addMessage(null, msg);
            }
        } else {
            try {
                //this.clienteService.editarCliente(this.cliente);
                this.enModificar = false;
                //this.clientes = this.clienteService.obtenerClientes();
                FacesMessage msg = new FacesMessage(FacesMessage.SEVERITY_INFO, "Cliente Actualizado.", "Se ha actualizado el "+this.cliente);
                FacesContext.getCurrentInstance().addMessage(null, msg);
            } catch (Exception e) {
                FacesMessage msg = new FacesMessage(FacesMessage.SEVERITY_ERROR, "Error al actualizar cliente.", e.getMessage());
                FacesContext.getCurrentInstance().addMessage(null, msg);
            }
        }
    }
    
    public void eliminarCliente() {
        if (this.clienteSeleccionado != null) {
            try {
                //this.copiarClienteSeleccionada();
                //this.clienteService.eliminarCliente(this.cliente.getCedula());
               // this.clientes = this.clienteService.obtenerClientes();
                FacesMessage msg = new FacesMessage(FacesMessage.SEVERITY_INFO, "Cliente Eliminada.", "Se ha eliminado la Cliente " + this.cliente);
                FacesContext.getCurrentInstance().addMessage(null, msg);
            } catch (Exception e) {
                FacesMessage msg = new FacesMessage(FacesMessage.SEVERITY_ERROR, "Error al eliminar Cliente. ", e.getMessage());
                FacesContext.getCurrentInstance().addMessage(null, msg);
            }

        }
    }
     public void cancelar() {
        this.enNueva=false;
        this.enModificar = false;
        this.enDetalles = false;
    }

    public List<Cliente> getClientes() {
        return clientes;
    }

    public Cliente getClienteSeleccionado() {
        return clienteSeleccionado;
    }

    public void setClienteSeleccionado(Cliente clienteSeleccionado) {
        this.clienteSeleccionado = clienteSeleccionado;
    }

    public String getTituloFormulario() {
        return tituloFormulario;
    }

    public void setTituloFormulario(String tituloFormulario) {
        this.tituloFormulario = tituloFormulario;
    }

    public boolean isEnNueva() {
        return enNueva;
    }

    public void setEnNueva(boolean enNueva) {
        this.enNueva = enNueva;
    }

    public boolean isEnModificar() {
        return enModificar;
    }

    public void setEnModificar(boolean enModificar) {
        this.enModificar = enModificar;
    }

    public boolean isEnDetalles() {
        return enDetalles;
    }

    public void setEnDetalles(boolean enDetalles) {
        this.enDetalles = enDetalles;
    }

    public Cliente getCliente() {
        return cliente;
    }

    public void setCliente(Cliente cliente) {
        this.cliente = cliente;
    }
    

}
