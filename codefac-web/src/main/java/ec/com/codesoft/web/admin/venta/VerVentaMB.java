/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ec.com.codesoft.web.admin.venta;

import ec.com.codesoft.model.DetalleProductoGeneral;
import ec.com.codesoft.model.DetalleProductoIndividual;
import ec.com.codesoft.model.DetalleVentaOrdenTrabajo;
import ec.com.codesoft.model.Venta;
import ec.com.codesoft.modelo.servicios.FacturaServicio;
import ec.com.codesoft.web.operador.DetallesVenta;
import java.io.Serializable;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import javax.annotation.PostConstruct;
import javax.ejb.EJB;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;
import javax.faces.context.FacesContext;

/**
 *
 * @author carlo
 */
@ManagedBean
@ViewScoped
public class VerVentaMB implements Serializable {

    @EJB
    private FacturaServicio factura;

    private Venta venta;

    private List<DetallesVenta> detalles;

    @PostConstruct
    public void postConstruct() {

    }

    public void preRender() {
        System.out.println("Prerender ...");
        Map params = FacesContext.getCurrentInstance().getExternalContext().getRequestParameterMap();

        String id = (String) params.get("id");

        Integer codigo = Integer.parseInt(id);
        venta = factura.buscarVentaById(codigo);

        detalles = new ArrayList<DetallesVenta>();
        List<DetalleProductoGeneral> listaGeneral = venta.getDetalleProductoGeneralList();

        for (DetalleProductoGeneral detalle : listaGeneral) {
            detalles.add(new DetallesVenta(
                    detalle.getCantidad(),
                    detalle.getCodigoProducto().getCodigoProducto(),
                    detalle.getCodigoProducto().getNombre(),
                    detalle.getPrecioIndividual(),
                    detalle.getSubtotal(),
                    detalle.getDescuento()));
            
            //System.out.println("d-:"+detalle.getDescuento());
        }
        
        for (DetalleProductoGeneral detalle : listaGeneral) {
            System.out.println("d->"+detalle.getDescuento());
        }

        List<DetalleProductoIndividual> listaIndividual = venta.getDetalleProductoIndividualList();
        for (DetalleProductoIndividual detalle : listaIndividual) {
            detalles.add(new DetallesVenta(
                    1,
                    detalle.getProductoIndividualCompra().getCodigoProducto().getCodigoProducto(),
                    detalle.getProductoIndividualCompra().getCodigoProducto().getNombre(),
                    detalle.getPrecioIndividual(),
                    detalle.getSubtotal(),
                    detalle.getDescuento()));
        }
        
         List<DetalleVentaOrdenTrabajo> listaDetallesOrden =venta.getDetalleVentaOrdenTrabajoList();
        for (DetalleVentaOrdenTrabajo detalle : listaDetallesOrden) {
            System.out.println("DetalleOrden"+ detalle.getIdDetalleOrdenTrabajo().getEquipo());
            detalles.add(new DetallesVenta(
                    1,
                    detalle.getIdDetalleOrdenTrabajo().getIdOrdenTrabajo().getIdOrdenTrabajo().toString(),
                    detalle.getIdDetalleOrdenTrabajo().getEquipo()+" -> "+detalle.getIdDetalleOrdenTrabajo().getEstado(),
                    detalle.getIdDetalleOrdenTrabajo().getPrecio(),
                    detalle.getIdDetalleOrdenTrabajo().getPrecio()));
        }

    }

    public Venta getVenta() {
        return venta;
    }

    public void setVenta(Venta venta) {
        this.venta = venta;
    }

    public List<DetallesVenta> getDetalles() {
        return detalles;
    }

    public void setDetalles(List<DetallesVenta> detalles) {
        this.detalles = detalles;
    }

}
