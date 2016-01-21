/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ec.com.codesoft.web;

import ec.com.codesoft.model.Cliente;
import ec.com.codesoft.modelo.servicios.ClienteServicio;
import java.io.Serializable;
import java.util.List;
import javax.annotation.PostConstruct;
import javax.ejb.EJB;
import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.RequestScoped;
import javax.faces.context.FacesContext;
import org.primefaces.event.SelectEvent;

/**
 *
 * @author Suco
 */
@ManagedBean
@RequestScoped
public class pruebaBean implements Serializable {

    private List<Cliente> listaClientes;
    private Cliente clienteSeleccionado;
    private Cliente cliente;
    private String tituloFormulario;
    private boolean enNueva;
    private boolean enModificar;
    private boolean enDetalles;
    @EJB
    private ClienteServicio clienteServicio;

    @PostConstruct
    public void postConstruct() {
        listaClientes = clienteServicio.obtenerTodos();
        this.cliente = new Cliente();
    }

    public void nuevaCliente() {
        this.cliente = new Cliente();
        System.out.println("nuevoFuncion");
        this.tituloFormulario = "Creación del Cliente";
    }

    public void onRowSelect(SelectEvent event) {
        FacesMessage msg = new FacesMessage("Cliente Seleccionado", ((Cliente) event.getObject()).getNombre());
        FacesContext.getCurrentInstance().addMessage(null, msg);
    }

    public List<Cliente> getListaClientes() {
        return listaClientes;
    }

    public Cliente getClienteSeleccionado() {
        return clienteSeleccionado;
    }

    public void setClienteSeleccionado(Cliente clienteSeleccionado) {
        this.clienteSeleccionado = clienteSeleccionado;
    }

    public Cliente getCliente() {
        return cliente;
    }

    public void setCliente(Cliente cliente) {
        this.cliente = cliente;
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
    
    

}
