/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ec.com.codesoft.modelo.facade;

import ec.com.codesoft.model.ProductoGeneralVenta;
import ec.com.codesoft.model.Venta;
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
public class ProductoGeneralVentaFacade extends AbstractFacade<ProductoGeneralVenta> {

    @PersistenceContext(unitName = "codefacPU")
    private EntityManager em;

    @Override
    protected EntityManager getEntityManager() {
        return em;
    }

    public ProductoGeneralVentaFacade() {
        super(ProductoGeneralVenta.class);
    }

    public ProductoGeneralVenta findByCodigoProducto(String codigo) {
        //ProductoGeneralVenta p;
        //p.getCatalagoProducto().getCodigoProducto();
        try {
            //String queryString = "SELECT p FROM ProductoGeneralVenta p WHERE p.catalagoProducto.codigoProducto=?1";
            String queryString = "SELECT p FROM CatalagoProducto c inner join c.productoGeneralVenta p  WHERE c.codigoProducto=?1";
           // String queryString = "SELECT p FROM CatalagoProducto c inner join c.productoGeneralVenta p  WHERE c.codigoProducto='123'";
            System.out.println(queryString);
            Query query = em.createQuery(queryString);
            query.setParameter(1,codigo);
            return (ProductoGeneralVenta) query.getSingleResult();
        } catch (NoResultException e) {
            return null;
        }

    }

}
