/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ec.com.codesoft.modelo.servicios;

import ec.com.codesoft.model.Distribuidor;
import ec.com.codesoft.modelo.facade.DistribuidorFacade;
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
public class DistribuidorServicio {

    @EJB
    DistribuidorFacade distribuidorFacade;

    public void insertar(Distribuidor distribuidor) {
        this.distribuidorFacade.create(distribuidor);
    }

    public void actualizar(Distribuidor distribuidor) {
        distribuidorFacade.edit(distribuidor);
    }

    public void eliminar(Distribuidor distribuidor) {
        distribuidorFacade.remove(distribuidor);
    }

    public List<Distribuidor> obtenerTodos() {

        return distribuidorFacade.findAll();
    }

    public Distribuidor buscarDistribuidor(String codigo) {
       return distribuidorFacade.findDistribuidor(codigo);
//        
    }
    
    
    //verifica la existencia de los distribuidores
    public boolean verificarExistencia(String cedula)
    {
        if(distribuidorFacade.find(cedula)==null)
        {
            return false;
        }
        else
        {
            return true;
        }
    }
}
