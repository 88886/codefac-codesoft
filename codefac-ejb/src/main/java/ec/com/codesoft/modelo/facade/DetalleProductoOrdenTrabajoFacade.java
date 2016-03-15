/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ec.com.codesoft.modelo.facade;

import ec.com.codesoft.model.DetalleProductoIndividual;
import ec.com.codesoft.model.DetalleProductoOrdenTrabajo;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

/**
 *
 * @author carlo
 */
@Stateless
public class DetalleProductoOrdenTrabajoFacade extends AbstractFacade<DetalleProductoOrdenTrabajo> {
    @PersistenceContext(unitName = "codefacPU")
    private EntityManager em;
    
    public DetalleProductoOrdenTrabajoFacade() 
    {
        super(DetalleProductoOrdenTrabajo.class);
    }

    @Override
    protected EntityManager getEntityManager() {
        return em;
    }
    
}
