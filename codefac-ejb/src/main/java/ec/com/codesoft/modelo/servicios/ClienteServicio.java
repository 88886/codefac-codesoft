/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ec.com.codesoft.modelo.servicios;


import ec.com.codesoft.model.Cliente;
import ec.com.codesoft.modelo.facade.ClienteFacade;
import java.util.List;
import javax.ejb.EJB;
import javax.ejb.LocalBean;
import javax.ejb.Stateless;

/**
 *
 * @author Suco
 */
@LocalBean 
@Stateless  
public class ClienteServicio {
    
    @EJB
    private ClienteFacade clienteFacade;
    
    public void insertar(Cliente cliente){
        this.clienteFacade.create(cliente);
    }
    
    public void actualizar(Cliente cliente){
        clienteFacade.edit(cliente);
    } 
    
    public void eliminar(Cliente cliente){
        clienteFacade.remove(cliente);
    }
    
    public List<Cliente> obtenerTodos(){
        
        return clienteFacade.findAll();
    }
    
    public Cliente buscarCliente(String cedula){
        
        return clienteFacade.find(cedula);
    }
    
    /**
     * Verifica si el cliente no existe en la base de datos
     * @param cedula
     * @return 
     */
    public boolean verificarExistencia(String cedula)
    {
        if(clienteFacade.find(cedula)==null)
        {
            return false;
        }
        else
        {
            return true;
        }
    }
}