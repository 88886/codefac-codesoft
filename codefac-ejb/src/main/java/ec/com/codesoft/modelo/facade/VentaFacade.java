/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ec.com.codesoft.modelo.facade;

import ec.com.codesoft.model.DetalleOrdenTrabajo;
import ec.com.codesoft.model.DetalleProductoGeneral;
import ec.com.codesoft.model.DetalleProductoIndividual;
import ec.com.codesoft.model.DetalleVentaOrdenTrabajo;
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
public class VentaFacade extends AbstractFacade<Venta> {

    @PersistenceContext(unitName = "codefacPU")
    private EntityManager em;

    @Override
    protected EntityManager getEntityManager() {
        return em;
    }

    public VentaFacade() {
        super(Venta.class);
    }

    /**
     * Obtiene el valor correspondiente al codigo de la ultima factura segun la
     * secuencia de ingreso de las documentos
     *
     * @return
     */
    public Integer getCodigoUltimaFactura() {
        try {
            String queryString = "SELECT max(v.codigoDocumento) FROM Venta v WHERE v.tipoDocumento='Factura' ";
            Query query = em.createQuery(queryString);
            
            Integer numero = Integer.parseInt(query.getSingleResult().toString());
            //System.out.println("num: "+numero);
            return numero;
        } catch (NoResultException e) {
            return null;
        } catch (NullPointerException ex) {
            return 0;
        }

    }

    /**
     * Obtiene el numero de documento de la ultima nota de entrega vendidad
     *
     * @return
     */

    public Integer getCodigoUltimaNota() {
        try {
            String queryString = "SELECT max(v.codigoDocumento) FROM Venta v WHERE v.tipoDocumento='Nota' ";
            Query query = em.createQuery(queryString);
            Integer numero = Integer.parseInt(query.getSingleResult().toString());
            return numero;
        } catch (NoResultException e) {
            return null;
        } catch (NullPointerException ex) {
            return 0;
        }

    }

    public Venta findCodigoDocumento(Integer codigo) {
        try {
            String queryString = "SELECT v FROM Venta v WHERE v.codigoDocumento=" + codigo.toString();
            System.out.println(queryString);
            Query query = em.createQuery(queryString);
            return (Venta) query.getSingleResult();
        } catch (NoResultException e) {
            return null;
        }
    }

    /**
     * Obtiene todas las ventas
     *
     * @return
     */
    public List<Venta> getVentas() {
        try {
            String queryString = "SELECT v FROM Venta v order by v.codigoFactura desc ";
            System.out.println(queryString);
            Query query = em.createQuery(queryString);
            List<Venta> ventas = (List<Venta>) query.getResultList();
            return ventas;
        } catch (NoResultException e) {
            return null;
        }
    }

    public Venta findFacturaVentasDiariasFecha(String fecha) {
        try {
            String queryString = "SELECT v FROM Venta v where v.fecha like '%" + fecha + "%' and v.estado='Diaria'";
            Query query = em.createQuery(queryString);
            //query.setParameter(1, codP);
            Venta intereses = (Venta) query.getSingleResult();
            //  System.out.println("facade"+intereses);
            return intereses;
        } catch (NoResultException e) {
            return null;
        }

    }

    public List<DetalleProductoGeneral> findFDetalleGeneralVentasDiariasCod(int cod) {
        try {
            String queryString = "SELECT g FROM DetalleProductoGeneral g where g.codigoFactura.codigoFactura='" + cod + "'";
            Query query = em.createQuery(queryString);
            //query.setParameter(1, codP);
            List<DetalleProductoGeneral> detalles = (List<DetalleProductoGeneral>) query.getResultList();
            //  System.out.println("facade"+intereses);
            return detalles;
        } catch (NoResultException e) {
            return null;
        }

    }

    public List<DetalleProductoIndividual> findFDetalleIndividualVentasDiariasCod(int cod) {
        try {
            String queryString = "SELECT g FROM DetalleProductoIndividual g where g.codigoFactura.codigoFactura='" + cod + "'";
            Query query = em.createQuery(queryString);
            //query.setParameter(1, codP);
            List<DetalleProductoIndividual> detalles = (List<DetalleProductoIndividual>) query.getResultList();
            //  System.out.println("facade"+intereses);
            return detalles;
        } catch (NoResultException e) {
            return null;
        }

    }
    
     public List<DetalleVentaOrdenTrabajo> findFDetalleOrdenTrabajoCod(int cod) {
        try {
            String queryString = "SELECT g FROM DetalleVentaOrdenTrabajo g where g.codigoFactura.codigoFactura='" + cod + "'";
            Query query = em.createQuery(queryString);
            //query.setParameter(1, codP);
            List<DetalleVentaOrdenTrabajo> detalles = (List<DetalleVentaOrdenTrabajo>) query.getResultList();
             System.out.println("facadeVenta"+detalles);
            return detalles;
        } catch (NoResultException e) {
            return null;
        }

    }

    public List<Venta> findVentasIntervalo(String fecha1, String fecha2) {

        try {
            String queryString = "SELECT  v FROM Venta v WHERE v.fecha like '%" + fecha1 + "%'";
            Query query = em.createQuery(queryString);
            //query.setParameter(1, codP);
            List<Venta> ventas = (List<Venta>) query.getResultList();
            //  System.out.println("facade"+intereses);
            return ventas;
        } catch (NoResultException e) {
            return null;
        }
    }

}
