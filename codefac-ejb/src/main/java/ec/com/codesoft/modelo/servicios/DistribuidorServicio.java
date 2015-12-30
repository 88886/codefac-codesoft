/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ec.com.codesoft.modelo.servicios;

import ec.com.codesoft.modelo.Distribuidor;
import ec.com.codesoft.modelo.facade.DistribuidorFacade;
import javax.ejb.LocalBean;
import javax.ejb.Stateless;
/**
 *
 * @author Suco
 */
@LocalBean 
@Stateless  
public class DistribuidorServicio {
    
    DistribuidorFacade distribuidorFacade;
    
    public void insertar(Distribuidor distribuidor){
        this.distribuidorFacade.create(distribuidor);
    }
    
    public void actualizar(Distribuidor distribuidor){
        distribuidorFacade.edit(distribuidor);
    } 
    
    public void eliminar(Distribuidor distribuidor){
        distribuidorFacade.remove(distribuidor);
    }
}