/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ec.com.codesoft.web;

import ec.com.codesoft.modelo.Cliente;
import ec.com.codesoft.modelo.servicios.ClienteServicio;
import java.io.Serializable;
import java.util.List;
import javax.annotation.PostConstruct;
import javax.ejb.EJB;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.RequestScoped;

/**
 *
 * @author Suco
 */
@ManagedBean
@RequestScoped
public class ClienteBean implements Serializable{
    
    private Cliente cliente;
    private Cliente clienteSeleccionado;
    private List<Cliente> clientes;
    @EJB
    private ClienteServicio clienteServicio;
    
    
    @PostConstruct
    public void postCOnstruct(){
        System.out.println("hola");
        clientes=clienteServicio.obtenerTodos();
    }
    
    public String crearCliente()
    {
     
        cliente=new Cliente();
        System.err.println("hola");
        cliente.setCedulaRuc("1310821481");
        cliente.setNombre("Andres Muentes");
        cliente.setCiudad("Quito");
        clienteServicio.insertar(cliente);
        System.out.println("Guardado");
        return "welcomePrimefaces";
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
    
    
    
}