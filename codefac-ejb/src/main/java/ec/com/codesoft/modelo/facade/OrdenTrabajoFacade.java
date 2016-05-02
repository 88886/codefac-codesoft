/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ec.com.codesoft.modelo.facade;

import ec.com.codesoft.model.Distribuidor;
import ec.com.codesoft.model.OrdenTrabajo;
import java.util.List;
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
public class OrdenTrabajoFacade extends AbstractFacade<OrdenTrabajo> {

    @PersistenceContext(unitName = "codefacPU")
    private EntityManager em;

    public OrdenTrabajoFacade() {
        super(OrdenTrabajo.class);
    }

    @Override
    protected EntityManager getEntityManager() {
        return em;
    }

    public List<OrdenTrabajo> getByDateEntry(String orden,String estado) {
        try {
            String queryString = "SELECT o FROM OrdenTrabajo o where o.estado=?1 order by o.fechaEmision "+orden;
            Query query = em.createQuery(queryString);
            query.setParameter(1,estado);
           
            List<OrdenTrabajo> ordenTrabajoList = (List<OrdenTrabajo>) query.getResultList();
            //System.out.println(orden);
            return ordenTrabajoList;
        } catch (NoResultException e) {
            return null;
        }

    }

    /**
     * Obtiene una lista de las ordenes de trabajo por la fecha de entrega
     *
     * @return
     */
    public List<OrdenTrabajo> getByDateDeparture(String orden,String estado) {
        try {
            String queryString = "SELECT o FROM OrdenTrabajo o where o.estado=?1 order by o.fechaEntrega "+orden;
            Query query = em.createQuery(queryString);
            query.setParameter(1,estado);
            
            System.out.println(query);

            List<OrdenTrabajo> ordenTrabajoList = (List<OrdenTrabajo>) query.getResultList();

            return ordenTrabajoList;
        } catch (NoResultException e) {
            return null;
        }

    }

    public List<OrdenTrabajo> getByPrice(String orden,String estado) {
        try {
            String queryString = "SELECT o FROM OrdenTrabajo o where o.estado=?1 order by o.total "+orden;
            Query query = em.createQuery(queryString);
            query.setParameter(1,estado);
            
            System.out.println(query);

            List<OrdenTrabajo> ordenTrabajoList = (List<OrdenTrabajo>) query.getResultList();

            return ordenTrabajoList;
        } catch (NoResultException e) 
        {
            return null;
        }

    }
    
    public List<OrdenTrabajo> findAllDesc()
    {
         try {
                String queryString = "SELECT o FROM OrdenTrabajo o where o.estado='reparado' order by o.idOrdenTrabajo DESC ";
            Query query = em.createQuery(queryString);
            //query.setParameter(1,estado);
           
            List<OrdenTrabajo> ordenTrabajoList = (List<OrdenTrabajo>) query.getResultList();
            //System.out.println(orden);
            return ordenTrabajoList;
        } catch (NoResultException e) {
            return null;
        }
    }

    //getByPrice
}
