/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ec.com.codesoft.modelo.facade;

import ec.com.codesoft.model.Usuario;
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
            String queryString = "SELECT max(v.codigoDocumento) FROM Venta v ";
            Query query = em.createQuery(queryString);
            Integer numero = (Integer) query.getSingleResult();
            return numero;
        } catch (NoResultException e) {
            return null;
        }

    }

    public Venta findCodigoDocumento(Integer codigo) {
        try {
            String queryString = "SELECT v FROM Venta v WHERE v.codigoDocumento=" + codigo.toString();
            System.out.println(queryString);
            Query query = em.createQuery(queryString);
            return (Venta)query.getSingleResult();
        } catch (NoResultException e) {
            return null;
        }
    }

}
