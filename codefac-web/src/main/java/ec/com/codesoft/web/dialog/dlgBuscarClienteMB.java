/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ec.com.codesoft.web.dialog;

import ec.com.codesoft.model.Cliente;
import ec.com.codesoft.modelo.servicios.ClienteServicio;
import java.io.Serializable;
import java.util.List;
import javax.annotation.PostConstruct;
import javax.ejb.EJB;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;
import org.primefaces.context.RequestContext;
import org.primefaces.event.SelectEvent;

/**
 *
 * @author carlo
 */
@ViewScoped
@ManagedBean
public class dlgBuscarClienteMB implements Serializable
{
    private List<Cliente> clientesLista;
    private Cliente clienteSeleccionado;
    
    @EJB
    private ClienteServicio clienteServicio;
        
    
    @PostConstruct
    public void postConstruct()
    {
        clientesLista=clienteServicio.obtenerTodos();
    }
    
    
    public void onRowSelectCliente(SelectEvent event) {
        System.out.println("En sleccion");
        //clienteEncontrado = clienteSeleccionado;
        Cliente cliente=(Cliente) event.getObject();
        RequestContext.getCurrentInstance().closeDialog(cliente);
        System.out.println(cliente);
        //clientesLista = clienteServicio.obtenerTodos();
    }

    public void onRowUnSelectCliente(SelectEvent event) {
        System.out.println("deseleccionando ...");
    }
    
    /////////////////METODOS GET AND SET ////////////////////

    public List<Cliente> getClientesLista() {
        return clientesLista;
    }

    public void setClientesLista(List<Cliente> clientesLista) {
        this.clientesLista = clientesLista;
    }

    public ClienteServicio getClienteServicio() {
        return clienteServicio;
    }

    public void setClienteServicio(ClienteServicio clienteServicio) {
        this.clienteServicio = clienteServicio;
    }
    
    
    public Cliente getClienteSeleccionado() {
        return clienteSeleccionado;
    }

    public void setClienteSeleccionado(Cliente clienteSeleccionado) {
        this.clienteSeleccionado = clienteSeleccionado;
    }
    
    
}
