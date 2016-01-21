/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package ec.com.codesoft.modelo.facade;

import ec.com.codesoft.model.Distribuidor;
import ec.com.codesoft.model.Usuario;
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
public class DistribuidorFacade extends AbstractFacade<Distribuidor> {
    @PersistenceContext(unitName = "codefacPU")
    private EntityManager em;

    @Override
    protected EntityManager getEntityManager() {
        return em;
    }

    public DistribuidorFacade() {
        super(Distribuidor.class);
    }
    
    public Distribuidor findDistribuidor(String ruc){
    
    
         try
        {
            String queryString = "SELECT d FROM Distribuidor d where d.ruc=?1";
            Query query = em.createQuery(queryString);
            query.setParameter(1,ruc);
            Distribuidor distribuidor = (Distribuidor) query.getSingleResult();
            System.out.println(distribuidor);
            return distribuidor;
        }
        catch(NoResultException e)
        {
            return null;
        }
    
    }
}
