/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ec.com.codesoft.modelo.facade;

import ec.com.codesoft.model.CatalagoProducto;
import ec.com.codesoft.model.ProductoGeneralCompra;
import ec.com.codesoft.model.ProductoGeneralVenta;
import java.math.BigDecimal;
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
            List<ProductoGeneralVenta> producto = (List<ProductoGeneralVenta>) query.getResultList();
            //return producto.get(0);//cambiar solo x la Julie
            if (producto.size() == 0) {
                return null;
            }
            return producto.get(0);
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
        } catch (NoResultException e) {
            return null;
        }
    }

    public List<CatalagoProducto> obtenerProductosDistribuidor(String codigo) {
        try {
            //String queryString = "SELECT p FROM ProductoGeneralVenta p where p.codigoProducto='"+codP+"'";
            String queryString = "SELECT c FROM CatalagoProducto c WHERE c.codigoDistribuidor=?1";
            Query query = em.createQuery(queryString);
//            System.out.println(queryString);
            query.setParameter(1, codigo);
            List<CatalagoProducto> catalogo = (List<CatalagoProducto>) query.getResultList();
            return catalogo;
        } catch (NoResultException e) {
            return null;
        }
    }

    public List<String> obtenerProductos(String codigoDistribuidor) {
        try {
            //String queryString = "SELECT p FROM ProductoGeneralVenta p where p.codigoProducto='"+codP+"'";
            String queryString = "SELECT DISTINCT(c.codigoProducto.codigoProducto) FROM ProductoGeneralCompra c WHERE c.codigoCompra.ruc.ruc=?1";
            Query query = em.createQuery(queryString);
//            System.out.println(queryString);
            query.setParameter(1, codigoDistribuidor);
            List<String> codproductos = (List<String>) query.getResultList();
            return codproductos;
        } catch (NoResultException e) {
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
            String queryString2 = "SELECT p2 FROM ProductoGeneralCompra p2 WHERE p2codigoCompra.fecha.=(" + queryString + ")";

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

    /**
     * Devuelve el ultimo costo de los producto generales segun el distribuidor
     *
     * @return
     */
    public BigDecimal getUltimoCostoProductoByDistribuidor(String idProducto, String rucDistribuidor) {

        try {
            String queryString2 = "SELECT MAX(p.codigoCompra.fecha) FROM ProductoGeneralCompra p where p.codigoCompra.ruc.ruc=?2 AND p.codigoProducto.codigoProducto=?1  ";
            String queryString = "SELECT p2 FROM ProductoGeneralCompra p2 where p2.codigoCompra.ruc.ruc=?2 AND p2.codigoProducto.codigoProducto=?1 AND p2.codigoCompra.fecha=(" + queryString2 + ")";
            //String queryString = "SELECT p.costoIndividual FROM ProductoGeneralCompra p WHERE p.codigoCompra.ruc=?1 AND p.codigoProducto.codigoProducto=?2";

            Query query = em.createQuery(queryString);
            query.setParameter(2, rucDistribuidor);
            query.setParameter(1, idProducto);

            // BigDecimal costo = (BigDecimal) query.getSingleResult();
            //System.out.println("costo: "+costo);
            // System.out.println("fecha: "+query.getSingleResult());
            //           List<Object> lista=query.getResultList();
//            for (Object obj : lista) {
//                System.out.println("fecha->"+obj);
//            }
            List<ProductoGeneralCompra> lista = (List<ProductoGeneralCompra>) query.getResultList();
            //for (ProductoGeneralCompra item : lista) 
            // {
            //    System.out.println(item.getCodigoProducto().getNombre()+","+item.getCostoIndividual()+","+item.getCodigoCompra().getFecha());
            //}
            if (lista.size() == 0) {
                return null;
            }
            return lista.get(0).getCostoIndividual();

        } catch (NoResultException e) {
            return null;
        } catch (NullPointerException ex) {
            return null;
        }

    }

    //obtner Ultimoproducto
    public ProductoGeneralCompra getUltimoProductoByDistribuidor(String idProducto, String rucDistribuidor) {

        try {
            String queryString2 = "SELECT MAX(p.codigoCompra.fecha) FROM ProductoGeneralCompra p where p.codigoCompra.ruc.ruc=?2 AND p.codigoProducto.codigoProducto=?1  ";
            String queryString = "SELECT p2 FROM ProductoGeneralCompra p2 where p2.codigoCompra.ruc.ruc=?2 AND p2.codigoProducto.codigoProducto=?1 AND p2.codigoCompra.fecha=(" + queryString2 + ")";
            //String queryString = "SELECT p.costoIndividual FROM ProductoGeneralCompra p WHERE p.codigoCompra.ruc=?1 AND p.codigoProducto.codigoProducto=?2";

            Query query = em.createQuery(queryString);
            query.setParameter(2, rucDistribuidor);
            query.setParameter(1, idProducto);

            List<ProductoGeneralCompra> lista = (List<ProductoGeneralCompra>) query.getResultList();

            if (lista.size() == 0) {
                return null;
            }
            return lista.get(0);

        } catch (NoResultException e) {
            return null;
        } catch (NullPointerException ex) {
            return null;
        }

    }

    /**
     * Obtiene el costo del ultimo producto especifico segun el distribuidor
     *
     * @param idProducto
     * @param rucDistribuidor
     * @return
     */
    public BigDecimal getUltimoCostoProductoEspecifico(String idProducto, String rucDistribuidor) {
        try {
            String queryString2 = "SELECT MAX(p.codigoCompra.fecha) FROM ProductoIndividualCompra p where p.codigoCompra.ruc.ruc=?2 AND p.codigoProducto.codigoProducto=?1  ";
            String queryString = "SELECT p2 FROM ProductoIndividualCompra p2 where p2.codigoCompra.ruc.ruc=?2 AND p2.codigoProducto.codigoProducto=?1 AND p2.codigoCompra.fecha=(" + queryString2 + ")";

            Query query = em.createQuery(queryString);
            query.setParameter(2, rucDistribuidor);
            query.setParameter(1, idProducto);

            List<ProductoGeneralCompra> lista = (List<ProductoGeneralCompra>) query.getResultList();

            if (lista.size() == 0) {
                return null;
            }

            return lista.get(0).getCostoIndividual();

        } catch (NoResultException e) {
            return null;
        } catch (NullPointerException ex) {
            return null;
        }

    }

}
