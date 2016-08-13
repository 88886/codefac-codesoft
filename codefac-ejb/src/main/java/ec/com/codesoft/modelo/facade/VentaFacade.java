/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ec.com.codesoft.modelo.facade;

import ec.com.codesoft.model.AbonoVentaCredito;
import ec.com.codesoft.model.Compra;
import ec.com.codesoft.model.CreditoFactura;
import ec.com.codesoft.model.Declaraciones;
import ec.com.codesoft.model.DetalleProductoGeneral;
import ec.com.codesoft.model.DetalleProductoIndividual;
import ec.com.codesoft.model.DetalleVentaOrdenTrabajo;
import ec.com.codesoft.model.DetallesServicio;
import ec.com.codesoft.model.Venta;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
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
           
            
            
            List<Venta> intereses = (List<Venta>) query.getResultList();
            //  System.out.println("facade"+intereses);
            if(intereses.size()==0){
                //Venta venta=new Venta();
                return null;
            }else{
            return intereses.get(0);
            }
            
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
    
    public List<DetallesServicio> findServicioVentasDiariasCod(int cod) {
        try {
            String queryString = "SELECT g FROM DetallesServicio g where g.codigoFactura.codigoFactura='" + cod + "'";
            Query query = em.createQuery(queryString);
            //query.setParameter(1, codP);
            List<DetallesServicio> detalles = (List<DetallesServicio>) query.getResultList();
            //  System.out.println("facade"+intereses);
            return detalles;
        } catch (NoResultException e) {
            return null;
        }

    }
    
    public List<DetalleVentaOrdenTrabajo> findDetallesOrdenTranajoVentasDiariasCod(int cod) {
        try {
            String queryString = "SELECT g FROM DetalleVentaOrdenTrabajo g where g.codigoFactura.codigoFactura='" + cod + "'";
            Query query = em.createQuery(queryString);
            //query.setParameter(1, codP);
            List<DetalleVentaOrdenTrabajo> detalles = (List<DetalleVentaOrdenTrabajo>) query.getResultList();
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
            System.out.println("facadeVenta" + detalles);
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

    public List<Venta> findVentaTipo(String cedula, String tipoPago) {
        try {
            String queryString = "SELECT v FROM Venta v WHERE v.tipoPago=?1 and v.cedulaRuc.cedulaRuc=?2";
            Query query = em.createQuery(queryString);
            query.setParameter(1, tipoPago);
            query.setParameter(2, cedula);

            List<Venta> ventas = (List<Venta>) query.getResultList();
            //  System.out.println("facade"+intereses);
            return ventas;
        } catch (NoResultException e) {
            return null;
        }
    }

    public CreditoFactura findCreditoFactura(Integer codFactura, String estado) {
        try {
            String queryString = "SELECT c FROM CreditoFactura c WHERE c.codigoFactura.codigoFactura=?1 and c.estado=?2";
            Query query = em.createQuery(queryString);
            //System.out.println("Query "+query);
            query.setParameter(1, codFactura);
            query.setParameter(2, estado);
            CreditoFactura credito = (CreditoFactura) query.getSingleResult();
            return credito;
        } catch (NoResultException e) {
            return null;
        }
    }

    public List<AbonoVentaCredito> findAbonoCredito(Integer codcredito) {
        try {
            String queryString = "SELECT c FROM AbonoVentaCredito c WHERE c.codigoFacturaCredito.codigoFacturaCredito=?1";
            Query query = em.createQuery(queryString);
            query.setParameter(1, codcredito);
            List<AbonoVentaCredito> credito = (List<AbonoVentaCredito>) query.getResultList();
            return credito;
        } catch (NoResultException e) {
            return null;
        }

    }

    public List<Venta> findVentasSubtotales(Date fechaF) {
        try {
            String queryStringSub = "SELECT  d from Declaraciones d order by d.idDeclaracion desc";
            Query querySub = em.createQuery(queryStringSub);
            Declaraciones declaracion = (Declaraciones) querySub.setMaxResults(1).getSingleResult();
            SimpleDateFormat formateador = new SimpleDateFormat("yyyy-MM-dd");
            System.out.println("Fecha " + formateador.format(declaracion.getFechaFinal()));

            String fechaI = "";
            fechaI = formateador.format(declaracion.getFechaFinal());

            //suamar 1 dia a la fecha para q obtega <=
            Calendar calendar = Calendar.getInstance();
            calendar.setTime(fechaF);
            calendar.add(Calendar.DAY_OF_YEAR, 1);
            fechaF = calendar.getTime();

            String fechaFinal = "";
            fechaFinal = formateador.format(fechaF);
            System.out.println("Fecha Final" + fechaFinal);

            String queryString = "SELECT v FROM Venta v where v.fecha between '" + fechaI + "' and '" + fechaFinal + "' and v.codigoDocumento > '" + declaracion.getFacturaFinal() + "'  ";
            Query query = em.createQuery(queryString);
//            query.setParameter(1, fechaI);
//            query.setParameter(2, fechaFinal);
            List<Venta> ventas = (List<Venta>) query.getResultList();
            return ventas;
        } catch (NoResultException e) {
            return null;
        }

    }

    public List<Venta> findNotasTotales(Date fechaF) {
        try {
            String queryStringSub = "SELECT  d from Declaraciones d order by d.idDeclaracion desc";
            Query querySub = em.createQuery(queryStringSub);
            Declaraciones declaracion = (Declaraciones) querySub.setMaxResults(1).getSingleResult();
            SimpleDateFormat formateador = new SimpleDateFormat("yyyy-MM-dd");
            System.out.println("Fecha " + formateador.format(declaracion.getFechaFinal()));

            String fechaI = "";
            fechaI = formateador.format(declaracion.getFechaFinal());

            //suamar 1 dia a la fecha para q obtega <=
            Calendar calendar = Calendar.getInstance();
            calendar.setTime(fechaF);
            calendar.add(Calendar.DAY_OF_YEAR, 1);
            fechaF = calendar.getTime();

            String fechaFinal = "";
            fechaFinal = formateador.format(fechaF);
            System.out.println("Fecha Final" + fechaFinal);

            String queryString = "SELECT v FROM Venta v where v.fecha between "
                    + "'" + fechaI + "' and '" + fechaFinal + "' "
                    + "and v.codigoDocumento > '" + declaracion.getNotaFinal() + "' and v.tipoDocumento='Nota' ";
            Query query = em.createQuery(queryString);
//            query.setParameter(1, fechaI);
//            query.setParameter(2, fechaFinal);
            List<Venta> ventas = (List<Venta>) query.getResultList();
            return ventas;
        } catch (NoResultException e) {
            return null;
        }

    }

    public Declaraciones findUltimaDeclaracion() {
        try {
            String queryStringSub = "SELECT  d from Declaraciones d order by d.idDeclaracion desc";
            Query querySub = em.createQuery(queryStringSub);
            Declaraciones declaracion = (Declaraciones) querySub.setMaxResults(1).getSingleResult();
            return declaracion;
        } catch (NoResultException e) {
            return null;
        }
    }

    public List<Compra> findComprasSubtotales(Date fechaF) {
        try {
            String queryStringSub = "SELECT  d from Declaraciones d order by d.idDeclaracion desc";
            Query querySub = em.createQuery(queryStringSub);
            Declaraciones declaracion = (Declaraciones) querySub.setMaxResults(1).getSingleResult();
            SimpleDateFormat formateador = new SimpleDateFormat("yyyy-MM-dd");
            System.out.println("Fecha " + formateador.format(declaracion.getFechaFinal()));

            String fechaI = "";
            fechaI = formateador.format(declaracion.getFechaFinal());

            //suamar 1 dia a la fecha para q obtega <=
            Calendar calendar = Calendar.getInstance();
            calendar.setTime(fechaF);
            calendar.add(Calendar.DAY_OF_YEAR, 1);
            fechaF = calendar.getTime();

            String fechaFinal = "";
            fechaFinal = formateador.format(fechaF);
            System.out.println("Fecha Final" + fechaFinal);

            String queryString = "SELECT v FROM Compra v where v.fecha between '" + fechaI + "' and '" + fechaFinal + "'";
            Query query = em.createQuery(queryString);
//            query.setParameter(1, fechaI);
//            query.setParameter(2, fechaFinal);
            List<Compra> compras = (List<Compra>) query.getResultList();
            return compras;
        } catch (NoResultException e) {
            return null;
        }

    }
}
