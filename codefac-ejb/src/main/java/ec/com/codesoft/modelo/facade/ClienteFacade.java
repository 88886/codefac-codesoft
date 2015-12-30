/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ec.com.codesoft.modelo.facade;

import ec.com.codesoft.modelo.Cliente;
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
public class ClienteFacade extends AbstractFacade<Cliente>
{
    @PersistenceContext(unitName = "codefacPU")
    private EntityManager em;

    public ClienteFacade() 
    {
        super(Cliente.class);
    }
    
    
    
    @Override
    protected EntityManager getEntityManager() {
        return em;
    }
    
}
