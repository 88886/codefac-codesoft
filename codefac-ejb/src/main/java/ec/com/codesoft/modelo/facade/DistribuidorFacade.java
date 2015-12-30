/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ec.com.codesoft.modelo.facade;

import ec.com.codesoft.modelo.Distribuidor;
import javax.ejb.LocalBean;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

/**
 *
 * @author Carlos
 */
//@LocalBean 
@Stateless 
public class DistribuidorFacade extends AbstractFacade<Distribuidor>
{

    @PersistenceContext(unitName = "codefacPU")
    private EntityManager em;


    public DistribuidorFacade() 
    {
        super(Distribuidor.class);
    }

    @Override
    protected EntityManager getEntityManager() 
    {
        return em;
    }
    
}
