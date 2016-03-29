/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ec.com.codesoft.modelo.facade;

import ec.com.codesoft.model.ProductoGeneralCompra;
import ec.com.codesoft.model.ProductoGeneralVenta;
import java.util.Date;
import java.util.Iterator;
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
            ProductoGeneralVenta producto = (ProductoGeneralVenta) query.getSingleResult();
            return producto;
        } catch (NoResultException e) {
            return null;
        }

    }

    public List<ProductoGeneralCompra> listaCostosProductoGeneral(String codigoProducto) {

        try {
            String queryString2 = "SELECT MAX(p.codigoCompra.fecha) FROM ProductoGeneralCompra p where p.codigoCompra.ruc=p2.codigoCompra.ruc GROUP BY p.codigoCompra.ruc  ";
            String queryString = "SELECT p2 FROM ProductoGeneralCompra p2 where p2.codigoProducto.codigoProducto=?1 and p2.codigoCompra.fecha= (" + queryString2 + ")";
            System.out.println(queryString);

            Query query = em.createQuery(queryString);
            query.setParameter(1, codigoProducto);

            List<ProductoGeneralCompra> lista = (List<ProductoGeneralCompra>) query.getResultList();
            for (ProductoGeneralCompra obj : lista) {
                System.out.println(obj.getCodigoProducto().getNombre());
                System.out.println(obj.getCostoIndividual());
                System.out.println(obj.getCodigoCompra().getRuc().getNombre());
                System.out.println("---------------------------------------");
            }

            return lista;
        } 
        catch (NoResultException e) {
            return null;
        }
    }

    /**
     * Obtiene la lista de los productos comprados con los ultimos precios
     *
     * @return
     */
    public List<ProductoGeneralCompra> listaUltimosCostosProductoGeneral() {
        try {
            //String queryString = "SELECT p FROM ProductoGeneralVenta p where p.codigoProducto='"+codP+"'";
            //String queryString = "SELECT p , MAX(p.codigoCompra.fecha),c.nombre FROM ProductoGeneralCompra p inner join p.codigoProducto c  group by c";
            //String queryString = "SELECT p, MAX(compra.fecha),c.nombre,compra.fecha FROM ProductoGeneralCompra p inner join p.codigoProducto c inner join p.codigoCompra compra group by c ";

            //String queryString = "SELECT MAX(p.codigoCompra.fecha) FROM ProductoGeneralCompra p WHERE p.codigoProducto.codigoProducto='002' ";
            // String queryString2="SELECT p FROM ProductoGeneralCompra p WHERE p.codigoProducto.codigoProducto='002' AND p.codigoCompra.fecha=("+queryString+")";
            String queryString = "SELECT MAX(p.codigoCompra.fecha) FROM ProductoGeneralCompra p WHERE p.codigoProducto.codigoProducto=p2.codigoProducto.codigoProducto ";
            String queryString2 = "SELECT p2 FROM ProductoGeneralCompra p2 WHERE p2.codigoCompra.fecha=(" + queryString + ")";

            //String queryString2 = "SELECT p, MAX(p.codigoCompra.fecha) FROM ProductoGeneralCompra p JOIN v.vehicle ve GROUP BY ve ORDER BY MAX(v.validTill)";
            Query query = em.createQuery(queryString2);

            //System.out.println(query.getSingleResult());
////            System.out.println(queryString);
//      //      query.setParameter(1, codP);
//            Iterator it=query.getResultList().iterator();
//            while(it.hasNext())
//            {
//                Object[] par=(Object[])it.next();
//                ProductoGeneralCompra p=(ProductoGeneralCompra) par[0];    
//                Object fecha=(Object) par[1];
//
//                
//                System.out.println(p.getCodigoProducto().getNombre());
//                System.out.println(p.getCostoIndividual());
//                System.out.println("f:"+p.getCodigoCompra().getFecha());
//                System.out.println(fecha);
//                System.out.println("c:"+p.getCodigoGenerado());
//
//                
//                
//            }
//            
            List<ProductoGeneralCompra> lista = (List<ProductoGeneralCompra>) query.getResultList();
            for (ProductoGeneralCompra obj : lista) {
                System.out.println(obj.getCostoIndividual());
            }
//            
            //List<ProductoGeneralCompra> productos = ( List<ProductoGeneralCompra>) query.getResultList();
            //List<ProductoGeneralCompra> productos = ( List<ProductoGeneralCompra>)lista.get(0);
            return null;
        } catch (NoResultException e) {
            return null;
        }

    }

}
