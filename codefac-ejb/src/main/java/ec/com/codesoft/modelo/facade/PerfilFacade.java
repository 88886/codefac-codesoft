/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ec.com.codesoft.modelo.facade;

import ec.com.codesoft.model.Perfil;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

/**
 *
 * @author carlo
 */
@Stateless
public class PerfilFacade extends AbstractFacade<Perfil>
{
    @PersistenceContext(unitName = "codefacPU")
    private EntityManager em;

    public PerfilFacade() {
        super(Perfil.class);
    }

    @Override
    protected EntityManager getEntityManager() 
    {
        return em;
    }
    
}
