/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ec.com.codesoft.modelo.facade;

import ec.com.codesoft.model.Banco;
import ec.com.codesoft.model.Venta;
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
public class BancoFacade extends AbstractFacade<Banco> {

    @PersistenceContext(unitName = "codefacPU")
    private EntityManager em;

    @Override
    protected EntityManager getEntityManager() {
        return em;
    }

    public BancoFacade() {
        super(Banco.class);
    }

    public Banco findInteresesBanco(String nombre) {
        try {
            String queryString = "SELECT b FROM Banco b where b.nombre='" + nombre + "'";
            Query query = em.createQuery(queryString);
            //query.setParameter(1, codP);
            Banco intereses = (Banco) query.getSingleResult();
            //  System.out.println("facade"+intereses);
            return intereses;
        } catch (NoResultException e) {
            return null;
        }

    }

   

}
