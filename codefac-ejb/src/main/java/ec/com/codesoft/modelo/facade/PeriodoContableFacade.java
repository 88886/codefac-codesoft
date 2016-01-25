/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package ec.com.codesoft.modelo.facade;

import ec.com.codesoft.model.PeriodoContable;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

/**
 *
 * @author Suco
 */
@Stateless
public class PeriodoContableFacade extends AbstractFacade<PeriodoContable> {
    @PersistenceContext(unitName = "codefacPU")
    private EntityManager em;

    @Override
    protected EntityManager getEntityManager() {
        return em;
    }

    public PeriodoContableFacade() {
        super(PeriodoContable.class);
    }
    
//     public PeriodoContable findPeriodoContable(){
//    
//    
//         try
//        {
//            String queryString = "SELECT p FROM Pe d where d.ruc=?1";
//            Query query = em.createQuery(queryString);
//            query.setParameter(1,ruc);
//            Distribuidor distribuidor = (Distribuidor) query.getSingleResult();
//            System.out.println(distribuidor);
//            return distribuidor;
//        }
//        catch(NoResultException e)
//        {
//            return null;
//        }
//    
//    }
    
}
