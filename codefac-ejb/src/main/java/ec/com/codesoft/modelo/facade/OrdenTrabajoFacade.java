/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ec.com.codesoft.modelo.facade;

import ec.com.codesoft.model.OrdenTrabajo;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

/**
 *
 * @author carlo
 */
@Stateless
public class OrdenTrabajoFacade extends AbstractFacade<OrdenTrabajo> {

    @PersistenceContext(unitName = "codefacPU")
    private EntityManager em;

    public OrdenTrabajoFacade() {
        super(OrdenTrabajo.class);
    }

    @Override
    protected EntityManager getEntityManager() {
        return em;
    }

}
