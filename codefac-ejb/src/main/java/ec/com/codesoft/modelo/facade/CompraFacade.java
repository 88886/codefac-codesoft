/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package ec.com.codesoft.modelo.facade;

import ec.com.codesoft.model.Compra;
import java.util.List;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.NoResultException;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;

/**
 *
 * @author Suco
 */
@Stateless
public class CompraFacade extends AbstractFacade<Compra> {
    @PersistenceContext(unitName = "codefacPU")
    private EntityManager em;

    @Override
    protected EntityManager getEntityManager() {
        return em;
    }

    public CompraFacade() {
        super(Compra.class);
    }
    
    
    /**
     * Obtiene las compras en orden desecendetes segun la fecha de compra
     * @return 
     */
    public List<Compra> findOrderDesc()
    {
        
        try {
            String queryString = "SELECT c FROM Compra c order by c.fecha desc";

            Query query = em.createQuery(queryString);

            List<Compra> compras = (List<Compra>) query.getResultList();
            return compras;
        } catch (NoResultException e) {
            return null;
        }

        
    }
    
}
