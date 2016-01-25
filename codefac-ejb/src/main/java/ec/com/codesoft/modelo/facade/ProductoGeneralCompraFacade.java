/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package ec.com.codesoft.modelo.facade;

import ec.com.codesoft.model.ProductoGeneralCompra;
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
public class ProductoGeneralCompraFacade extends AbstractFacade<ProductoGeneralCompra> {
    @PersistenceContext(unitName = "codefacPU")
    private EntityManager em;

    @Override
    protected EntityManager getEntityManager() {
        return em;
    }

    public ProductoGeneralCompraFacade() {
        super(ProductoGeneralCompra.class);
    }
    
    public ProductoGeneralCompra findGeneralCodP(String codP) {

        try {
            String queryString = "SELECT p FROM ProductoGeneralCompra p where p.codigoProducto.codigoProducto='"+codP+"'";
            Query query = em.createQuery(queryString);
            //query.setParameter(1, codP);
            ProductoGeneralCompra producto= (ProductoGeneralCompra) query.getSingleResult();
            return producto;
        } catch (NoResultException e) {
            return null;
        }

    }
    
}
