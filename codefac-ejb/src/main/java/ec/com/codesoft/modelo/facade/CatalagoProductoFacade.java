/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package ec.com.codesoft.modelo.facade;

import ec.com.codesoft.model.CatalagoProducto;
import ec.com.codesoft.model.Distribuidor;
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
public class CatalagoProductoFacade extends AbstractFacade<CatalagoProducto> {
    @PersistenceContext(unitName = "codefacPU")
    private EntityManager em;

    @Override
    protected EntityManager getEntityManager() {
        return em;
    }

    public CatalagoProductoFacade() {
        super(CatalagoProducto.class);
    }
    
    
     public CatalagoProducto findCatalogo(String codP){
    
    
         try
        {
            String queryString = "SELECT c FROM CatalagoProducto c where c.codigoProducto=?1";
            Query query = em.createQuery(queryString);
            query.setParameter(1,codP);
            CatalagoProducto catalago = (CatalagoProducto) query.getSingleResult();
            System.out.println(catalago);
            return catalago;
        }
        catch(NoResultException e)
        {
            return null;
        }
    
    }
    
}
