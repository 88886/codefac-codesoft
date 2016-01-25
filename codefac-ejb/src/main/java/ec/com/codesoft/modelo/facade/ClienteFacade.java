/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ec.com.codesoft.modelo.facade;

import ec.com.codesoft.model.Cliente;
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
public class ClienteFacade extends AbstractFacade<Cliente> {

    @PersistenceContext(unitName = "codefacPU")
    private EntityManager em;

    @Override
    protected EntityManager getEntityManager() {
        return em;
    }

    public ClienteFacade() {
        super(Cliente.class);
    }

    public Cliente findCliente(String cedula) {

        try {
            String queryString = "SELECT c FROM cliente d where c.cedula_ruc=?1";
            Query query = em.createQuery(queryString);
            query.setParameter(1, cedula);
            Cliente cliente = (Cliente) query.getSingleResult();
            System.out.println(cliente);
            return cliente;
        } catch (NoResultException e) {
            return null;
        }
    }

}
