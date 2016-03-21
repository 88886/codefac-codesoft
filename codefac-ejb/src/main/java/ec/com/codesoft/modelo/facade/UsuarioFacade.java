/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ec.com.codesoft.modelo.facade;

import ec.com.codesoft.model.Usuario;
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
public class UsuarioFacade extends AbstractFacade<Usuario> {

    @PersistenceContext(unitName = "codefacPU")
    private EntityManager em;

    @Override
    protected EntityManager getEntityManager() {
        return em;
    }

    public UsuarioFacade() {
        super(Usuario.class);
    }

    public Usuario login(String nick, String clave) {
        try {
            String queryString = "SELECT u FROM Usuario u where u.nick=?1 And u.clave=?2";
            Query query = em.createQuery(queryString);
            query.setParameter(1, nick);
            query.setParameter(2, clave);
            Usuario usuario = (Usuario) query.getSingleResult();
            //System.out.println(usuario);
            return usuario;
        } catch (NoResultException e) {
            return null;
        }

    }

    /**
     * Filtra todos los usuarios por su perfil
     *
     * @param perfil
     * @return
     */
    public List<Usuario> getUsuariosPorPerfil(String perfil) {

        //String queryString = "SELECT u FROM Usuario u where u. u.nick=?1 And u.clave=?2";
        try {
            String queryString = "SELECT DISTINCT p.nick FROM Perfil p where p.tipo=?1";

            Query query = em.createQuery(queryString);
            query.setParameter(1, perfil);

            List<Usuario> usuarios = (List<Usuario>) query.getResultList();
            return usuarios;
        } catch (NoResultException e) {
            return null;
        }
    }
    

}
