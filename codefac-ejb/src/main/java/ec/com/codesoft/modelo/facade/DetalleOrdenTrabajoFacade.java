/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ec.com.codesoft.modelo.facade;

import ec.com.codesoft.model.DetalleComboProducto;
import ec.com.codesoft.model.DetalleOrdenTrabajo;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

/**
 *
 * @author carlo
 */
@Stateless
public class DetalleOrdenTrabajoFacade extends AbstractFacade<DetalleOrdenTrabajo> {

    @PersistenceContext(unitName = "codefacPU")
    private EntityManager em;

    public DetalleOrdenTrabajoFacade() {
        super(DetalleOrdenTrabajo.class);
    }

    @Override
    protected EntityManager getEntityManager() {
        return em;
    }

}
