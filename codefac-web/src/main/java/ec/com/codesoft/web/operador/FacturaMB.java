/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ec.com.codesoft.web.operador;

import ec.com.codesoft.model.Cliente;
import ec.com.codesoft.modelo.servicios.ClienteServicio;
import javax.annotation.PostConstruct;
import javax.ejb.EJB;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;
import org.primefaces.context.RequestContext;

/**
 *
 * @author Suco
 */
@ManagedBean
@ViewScoped

public class FacturaMB {

    private boolean estadoDialogo;
    private boolean estadoDialogoEspecifico;
    private String cedCliente;
    private String msjCliente;
    private Cliente clienteEncontrado;
    private boolean mostrarPanel;
    
    
    @EJB
    ClienteServicio clienteServicio;

    @PostConstruct
    public void inicializar() {
        estadoDialogo = false;
        estadoDialogoEspecifico = false;
        msjCliente="";
        clienteEncontrado=new Cliente();
        mostrarPanel=false;
    }

    public void verificarDialogo() {
        if (estadoDialogo) {
            RequestContext.getCurrentInstance().execute("PF('nuevoCatalogo').show()");
        }
    }
    
    
     public void buscarCliente() {

        clienteEncontrado = clienteServicio.buscarCliente(cedCliente);
        if (clienteEncontrado == null) {
            System.out.println("NNEncontrado");
            msjCliente = "Distribuidor No Encontrado";
            mostrarPanel = false;

        } else {

            System.out.println("Encontrado");
            msjCliente = "Distribuidor Encontrado";
            //mostrarCompra = true;
            mostrarPanel = true;
            //tabCompra = true;
        }
    }

    public boolean isEstadoDialogo() {
        return estadoDialogo;
    }

    public void setEstadoDialogo(boolean estadoDialogo) {
        this.estadoDialogo = estadoDialogo;
    }

    public boolean isEstadoDialogoEspecifico() {
        return estadoDialogoEspecifico;
    }

    public void setEstadoDialogoEspecifico(boolean estadoDialogoEspecifico) {
        this.estadoDialogoEspecifico = estadoDialogoEspecifico;
    }

    public String getCedCliente() {
        return cedCliente;
    }

    public void setCedCliente(String cedCliente) {
        this.cedCliente = cedCliente;
    }

    public String getMsjCliente() {
        return msjCliente;
    }

    public void setMsjCliente(String msjCliente) {
        this.msjCliente = msjCliente;
    }

    public Cliente getClienteEncontrado() {
        return clienteEncontrado;
    }

    public void setClienteEncontrado(Cliente clienteEncontrado) {
        this.clienteEncontrado = clienteEncontrado;
    }

    public boolean isMostrarPanel() {
        return mostrarPanel;
    }

    public void setMostrarPanel(boolean mostrarPanel) {
        this.mostrarPanel = mostrarPanel;
    }
    
    
    
    
    
    
    
}
