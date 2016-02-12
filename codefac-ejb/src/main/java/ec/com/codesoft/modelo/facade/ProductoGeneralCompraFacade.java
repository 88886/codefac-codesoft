/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package ec.com.codesoft.modelo.facade;

import ec.com.codesoft.model.ProductoGeneralCompra;
import ec.com.codesoft.model.ProductoGeneralVenta;
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
    
    public ProductoGeneralVenta findGeneralCodP(String codP) {

        try {
            //String queryString = "SELECT p FROM ProductoGeneralVenta p where p.codigoProducto='"+codP+"'";
            String queryString = "SELECT p FROM CatalagoProducto c inner join c.productoGeneralVenta p  WHERE c.codigoProducto=?1";
            Query query = em.createQuery(queryString);
//            System.out.println(queryString);
            query.setParameter(1, codP);
            ProductoGeneralVenta producto= (ProductoGeneralVenta) query.getSingleResult();
            return producto;
        } catch (NoResultException e) {
            return null;
        }

    }
    
}
