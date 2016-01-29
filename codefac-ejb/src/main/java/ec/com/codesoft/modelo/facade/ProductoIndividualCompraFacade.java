/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ec.com.codesoft.modelo.facade;

import ec.com.codesoft.model.Distribuidor;
import ec.com.codesoft.model.ProductoIndividualCompra;
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
public class ProductoIndividualCompraFacade extends AbstractFacade<ProductoIndividualCompra> {

    @PersistenceContext(unitName = "codefacPU")
    private EntityManager em;

    @Override
    protected EntityManager getEntityManager() {
        return em;
    }

    public ProductoIndividualCompraFacade() {
        super(ProductoIndividualCompra.class);
    }

    public Long findStockIndividual(String codP) {

        try {
            String queryString = "SELECT count(i.codigoUnico) FROM ProductoIndividualCompra i where i.codigoProducto.codigoProducto='" + codP + "' and i.estadoProceso<> 'vendido'";
            Query query = em.createQuery(queryString);
            //query.setParameter(1, codP);
            Long stock = (Long) query.getSingleResult();
            return stock;
        } catch (NoResultException e) {
            return null;
        }
    }

    public ProductoIndividualCompra findProdIndividual(String codP, String codCat) {
        try {
            String queryString = "SELECT i FROM ProductoIndividualCompra i where i.codigoProducto.codigoProducto='" + codCat + "' and i.codigoUnico='" + codP + "'  ";
            Query query = em.createQuery(queryString);
            //query.setParameter(1, codP);
            ProductoIndividualCompra producto = (ProductoIndividualCompra) query.getSingleResult();
            return producto;
        } catch (NoResultException e) {
            return null;
        }
    }

    public ProductoIndividualCompra findProdIndividualCodUnico(String codP) {
        try {
            String queryString = "SELECT i FROM ProductoIndividualCompra i where i.codigoUnico='" + codP + "'  ";
            Query query = em.createQuery(queryString);
            //query.setParameter(1, codP);
            ProductoIndividualCompra producto = (ProductoIndividualCompra) query.getSingleResult();
            return producto;
        } catch (NoResultException e) {
            return null;
        }
    }

}
