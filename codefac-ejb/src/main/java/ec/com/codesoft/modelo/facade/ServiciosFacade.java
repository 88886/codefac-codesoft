/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ec.com.codesoft.modelo.facade;

import ec.com.codesoft.model.CategoriaTrabajo;
import ec.com.codesoft.model.Servicios;
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
public class ServiciosFacade extends AbstractFacade<Servicios> {

    @PersistenceContext(unitName = "codefacPU")
    private EntityManager em;

    @Override
    protected EntityManager getEntityManager() {
        return em;
    }

    public ServiciosFacade() {
        super(Servicios.class);
    }

    public List<CategoriaTrabajo> getCatTrabajo(Integer codServicio) {
        try {
            String queryString = "SELECT c FROM CategoriaTrabajo c where c.codigoServicio.codigoServicio=?1";
            Query query = em.createQuery(queryString);
            query.setParameter(1, codServicio);

            List<CategoriaTrabajo> categorias = (List<CategoriaTrabajo>) query.getResultList();
            //System.out.println("num: "+numero);
            return categorias;
        } catch (NoResultException e) {
            return null;
        } catch (NullPointerException ex) {
            return null;
        }

    }

}
