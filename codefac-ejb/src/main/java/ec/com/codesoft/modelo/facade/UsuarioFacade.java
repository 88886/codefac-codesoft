/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ec.com.codesoft.modelo.facade;

import ec.com.codesoft.modelo.Usuario;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.NoResultException;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;

/**
 *
 * @author carlo
 */
@Stateless
public class UsuarioFacade extends AbstractFacade<Usuario>
{
    @PersistenceContext(unitName = "codefacPU")
    private EntityManager em;

    public UsuarioFacade() 
    {
        super(Usuario.class);
    }
    
    @Override
    protected EntityManager getEntityManager() 
    {
        return em;
    }
    
        /**
     * Verifica si los datos del usuario son correctos
     *
     * @param nick nombre de usuario
     * @param clave clave de la cuenta
     * @return
     */
    public Usuario login(String nick, String clave) {
        try
        {
            String queryString = "SELECT u FROM Usuario u where u.nick=?1 And u.clave=?2";
            Query query = em.createQuery(queryString);
            query.setParameter(1,nick);
            query.setParameter(2,clave);
            Usuario usuario = (Usuario) query.getSingleResult();
            //System.out.println(usuario);
            return usuario;
        }
        catch(NoResultException e)
        {
            return null;
        }

    }
    
}
