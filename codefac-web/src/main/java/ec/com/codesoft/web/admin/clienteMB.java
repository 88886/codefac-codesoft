/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package ec.com.codesoft.web.admin;

import ec.com.codesoft.modelo.Cliente;
import ec.com.codesoft.modelo.servicios.ClienteServicio;
import java.awt.Event;
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
public class clienteMB implements Serializable{
 
    //Clientes
    private List<Cliente> clientes;
    private Cliente clienteSeleccionado;
    private Cliente cliente;
    //Banderas auxliares
    private Boolean flagBoton1;
    private Boolean enModificar;
    
    @EJB
    private ClienteServicio clienteServicio;

    @PostConstruct
    public void inicializar() {
        
        clientes=clienteServicio.obtenerTodos();
        cliente = new Cliente();
        flagBoton1 = true;
        enModificar = false;
    }

//    public void onRowSelect(SelectEvent event) {
//        flagBoton1 = false;
//        cliente = clienteSeleccionado;
//        System.out.println(clienteSeleccionado.getNombres());
//    }
    
    public void onRowSelect(SelectEvent event) {
        FacesMessage msg = new FacesMessage("Cliente Seleccionado", ((Cliente) event.getObject()).getNombre());
        FacesContext.getCurrentInstance().addMessage(null, msg);
        flagBoton1 = false;
        System.out.println(clienteSeleccionado.getNombre());
        cliente = clienteSeleccionado;
        
    }

    public void onRowUnSelect(SelectEvent event) {
        flagBoton1 = true;
        clienteSeleccionado = new Cliente();
        System.out.println("Deseleccionado");
        cliente = new Cliente();
    }

    public void enModificar() {
        System.out.println(clienteSeleccionado.getNombres());
        enModificar = true;
        cliente = clienteSeleccionado;
    }

    public void enCrear() {
        enModificar = false;
    }

    public void registarCliente(Event e) {
        if (enModificar) {
            clienteServicio.actualizar(cliente);
            cliente = new Cliente();
            clientes = clienteServicio.obtenerTodos();
            flagBoton1 = true;
        } else {
            cliente.setEstado('A');// A: cliente Activo
            clienteServicio.insertar(cliente);
            cliente = new Cliente();
            clientes = clienteServicio.obtenerTodos();
            flagBoton1 = true;
        }
    }

    public void cancelar() {
        cliente = new Cliente();
    }

    public List<Cliente> getClientes() {
        return clientes;
    }

    public void setClientes(List<Cliente> clientes) {
        this.clientes = clientes;
    }

    public Cliente getClienteSeleccionado() {
        return clienteSeleccionado;
    }

    public void setClienteSeleccionado(Cliente clienteSeleccionado) {
        this.clienteSeleccionado = clienteSeleccionado;
    }

    public Boolean getFlagBoton1() {
        return flagBoton1;
    }

    public void setFlagBoton1(Boolean flagBoton1) {
        this.flagBoton1 = flagBoton1;
    }

    public Cliente getCliente() {
        return cliente;
    }

    public void setCliente(Cliente cliente) {
        this.cliente = cliente;
    }  
}
