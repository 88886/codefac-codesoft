/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ec.com.codesoft.web.operador;

import ec.com.codesoft.model.Cliente;
import ec.com.codesoft.modelo.servicios.ClienteServicio;
import java.io.Serializable;
import javax.annotation.PostConstruct;
import javax.ejb.EJB;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;
import javax.faces.context.FacesContext;
import javax.servlet.http.HttpServletRequest;
import org.primefaces.context.RequestContext;

/**
 *
 * @author carlo
 */
@ViewScoped
@ManagedBean
public class CrearCliente implements Serializable{
    
    private Cliente cliente;
    
    @EJB
    private ClienteServicio clienteServicio;
    
    @PostConstruct
    public void postCostruct()
    {
        this.cliente=new Cliente();
        FacesContext context = FacesContext.getCurrentInstance();
        HttpServletRequest request = (HttpServletRequest) context.getExternalContext().getRequest();
        String cedula=request.getParameter("cedula");
        cliente.setCedulaRuc(cedula);
    }
    
    public void cancelar(){
    
    RequestContext.getCurrentInstance().closeDialog(null); 
    }
    public void guardar()
    {
        clienteServicio.insertar(cliente);
        RequestContext.getCurrentInstance().closeDialog(cliente);        
    }

    public Cliente getCliente() {
        return cliente;
    }

    public void setCliente(Cliente cliente) {
        this.cliente = cliente;
    }
        
    
            
}
