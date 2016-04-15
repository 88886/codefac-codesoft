/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ec.com.codesoft.web.operador;

import ec.com.codesoft.model.Cliente;
import ec.com.codesoft.model.DetalleProductoGeneral;
import ec.com.codesoft.model.DetalleProductoIndividual;
import ec.com.codesoft.model.NotaCreditoDebito;
import ec.com.codesoft.model.ProductoGeneralVenta;
import ec.com.codesoft.model.Venta;
import ec.com.codesoft.modelo.servicios.FacturaServicio;
import ec.com.codesoft.modelo.servicios.NotaCreditoDebitoServicio;
import ec.com.codesoft.modelo.servicios.SistemaServicio;
import ec.com.codesoft.web.reportes.FacturaDetalleModeloReporte;
import ec.com.codesoft.web.reportes.NotaVentaModeloReporte;
import java.io.IOException;
import java.io.Serializable;
import java.math.BigDecimal;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.annotation.PostConstruct;
import javax.ejb.EJB;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;
import net.sf.jasperreports.engine.JRException;
import org.primefaces.context.RequestContext;

/**
 *
 * @author carlo
 */
@ManagedBean
@ViewScoped
public class NotaCreditoMB implements Serializable {

    /**
     * Propiedad para almacenar la referencia a la venta
     */
    private Venta venta;
    /**
     * Variable para buscar por el codigo el documento
     */
    private Integer codigoDocumento;

    /**
     * Lista para cargar el detalle de la venta
     */
    private List<DetallesVenta> detalleVenta;

    /**
     * Valor de la cantidad a modificar
     */
    private BigDecimal valorModificacion;
    private String razonModificacion;

    private BigDecimal subtotal;
    private BigDecimal iva;
    private BigDecimal total;

    @EJB
    private FacturaServicio facturaServicio;

    @EJB
    private NotaCreditoDebitoServicio notasServicios;

    @EJB
    private SistemaServicio sistemaServicio;

    @PostConstruct
    public void postConstruct() {
        venta = new Venta();
        venta.setCedulaRuc(new Cliente());
        venta.getCedulaRuc().setNombre("");
        codigoDocumento = 0;
        detalleVenta = new ArrayList<DetallesVenta>();
    }

    /**
     * Realizar factura por el codigo del documento
     */
    public void buscarFactura() {
        System.out.println("buscando factura ...");
        //System.out.println(codigoDocumento);

        venta = facturaServicio.buscarPorCodigoDocumento(codigoDocumento);
        if (venta == null) {
            venta = new Venta();
            venta.setCedulaRuc(new Cliente());

        } else {
            System.out.println(venta.getCedulaRuc());
            cargarDetallesVenta();
            valorModificacion = venta.getTotal().divide(new BigDecimal("1.12"));
            subtotal = valorModificacion;
            iva = valorModificacion.multiply(new BigDecimal("0.12"));
            total = venta.getTotal();

        }
        //System.out.println(venta);

    }

    private void cargarDetallesVenta() {
        System.out.println("agregando detalles ....");
        detalleVenta.clear();
        List<DetalleProductoGeneral> detalleGeneral = venta.getDetalleProductoGeneralList();
        for (DetalleProductoGeneral detalle : detalleGeneral) {
            //agregando los datos al detalle visual
            detalleVenta.add(new DetallesVenta(
                    detalle.getCantidad(),
                    detalle.getCodigoProducto().getCodigoProducto(),
                    detalle.getCodigoProducto().getNombre(),
                    (detalle.getSubtotal().divide(new BigDecimal(detalle.getCantidad()))),
                    detalle.getSubtotal()));
        }

        List<DetalleProductoIndividual> detalleIndividual = venta.getDetalleProductoIndividualList();
        for (DetalleProductoIndividual detalle : detalleIndividual) {
            //agregando los datos al detalle visual
            detalleVenta.add(new DetallesVenta(
                    1,
                    detalle.getProductoIndividualCompra().getCodigoProducto().getCodigoProducto(),
                    detalle.getProductoIndividualCompra().getCodigoProducto().getNombre(),
                    detalle.getSubtotal(),
                    detalle.getSubtotal()));
        }

    }

    public void grabarNotaCredito() 
    {
        System.out.println("Grabar  nota de credito ...");
        NotaCreditoDebito nota = new NotaCreditoDebito();
        nota.setCantidad(valorModificacion);
        nota.setCodigoDebitoCredito(0);
        nota.setCodigoFactura(venta);
        nota.setDescripcion(razonModificacion);
        nota.setFecha(new Date());
        nota.setTipoNota("credito");

        notasServicios.grabarNotaCredito(nota);
        RequestContext.getCurrentInstance().execute("PF('confirmarGuardado').show()");

    }

    public void imprimir() {

//        System.out.println("imprimiendo ...");
//        NotaVentaModeloReporte notaVenta = new NotaVentaModeloReporte(sistemaServicio.getConfiguracion().getPathReportes());
//        notaVenta.setDireccion(venta.getCedulaRuc().getDireccion());
//        SimpleDateFormat sdf = new SimpleDateFormat("yyyy/MM/dd");
//        notaVenta.setFechaFactura(sdf.format(venta.getFecha()));
//        notaVenta.setFechaaFactura(sdf.format(venta.getFecha()));
//        notaVenta.setFormaPago(" ");
//        notaVenta.setNombreCliente(venta.getCedulaRuc().getNombre());
//        notaVenta.setTelefono(venta.getCedulaRuc().getTelefono());
//        notaVenta.setTotal(total);
//
//        for (DetallesVenta detalle : detalleVenta) {
//            FacturaDetalleModeloReporte detallesFactura = new FacturaDetalleModeloReporte();
//            detallesFactura.setCantidad(detalle.getCantidad() + "");
//            detallesFactura.setCodigo(detalle.getCodigo());
//            detallesFactura.setDescripcion(detalle.getNombre());
//            detallesFactura.setDescuento(" ");
//            detallesFactura.setPrecioUnitario(detalle.getCosto().toString());
//            detallesFactura.setTotal(detalle.getTotal().toString());
//            if (notaVenta != null) {
//                notaVenta.agregarDetalle(detallesFactura);
//            }
//        }
//
//        try {
//            notaVenta.exportarPDF();
//            System.out.println("generando pdf ...");
//        } catch (JRException ex) {
//            Logger.getLogger(NotaCreditoMB.class.getName()).log(Level.SEVERE, null, ex);
//        } catch (IOException ex) {
//            Logger.getLogger(NotaCreditoMB.class.getName()).log(Level.SEVERE, null, ex);
//        }

    }

    public void cambiarValor() {
        subtotal = valorModificacion;
        iva = valorModificacion.multiply(new BigDecimal("0.12"));
        total = subtotal.multiply(new BigDecimal("1.12"));
    }

    public void eliminarDetalle(DetallesVenta detalle) {
        System.out.println("Eliminando Detalle ...");
        detalleVenta.remove(detalle);
        System.out.println(detalle.getTotal());
        //subtotal=subtotal.subtract(detalle.getTotal());
        valorModificacion = new BigDecimal(0);
        cambiarValor();
        System.out.println(subtotal);
    }

    public Integer getCodigoDocumento() {
        return codigoDocumento;
    }

    public void setCodigoDocumento(Integer codigoDocumento) {
        this.codigoDocumento = codigoDocumento;
    }

    public Venta getVenta() {
        return venta;
    }

    public void setVenta(Venta venta) {
        this.venta = venta;
    }

    public List<DetallesVenta> getDetalleVenta() {
        return detalleVenta;
    }

    public void setDetalleVenta(List<DetallesVenta> detalleVenta) {
        this.detalleVenta = detalleVenta;
    }

    public BigDecimal getValorModificacion() {
        return valorModificacion;
    }

    public void setValorModificacion(BigDecimal valorModificacion) {
        this.valorModificacion = valorModificacion;
    }

    public BigDecimal getSubtotal() {
        return subtotal;
    }

    public void setSubtotal(BigDecimal subtotal) {
        this.subtotal = subtotal;
    }

    public BigDecimal getIva() {
        return iva;
    }

    public void setIva(BigDecimal iva) {
        this.iva = iva;
    }

    public BigDecimal getTotal() {
        return total;
    }

    public void setTotal(BigDecimal total) {
        this.total = total;
    }

    public String getRazonModificacion() {
        return razonModificacion;
    }

    public void setRazonModificacion(String razonModificacion) {
        this.razonModificacion = razonModificacion;
    }

}
