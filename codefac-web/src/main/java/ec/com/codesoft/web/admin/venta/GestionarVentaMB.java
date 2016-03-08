/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ec.com.codesoft.web.admin.venta;

import ec.com.codesoft.model.DetalleProductoGeneral;
import ec.com.codesoft.model.DetalleProductoIndividual;
import ec.com.codesoft.model.Venta;
import ec.com.codesoft.modelo.servicios.FacturaServicio;
import ec.com.codesoft.modelo.servicios.SistemaServicio;
import ec.com.codesoft.web.operador.DetallesVenta;
import ec.com.codesoft.web.operador.FacturaMB;
import ec.com.codesoft.web.reportes.FacturaDetalleModeloReporte;
import ec.com.codesoft.web.reportes.FacturaModeloReporte;
import ec.com.codesoft.web.reportes.NotaVentaModeloReporte;
import java.io.IOException;
import java.io.Serializable;
import java.math.BigDecimal;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.annotation.PostConstruct;
import javax.ejb.EJB;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;
import net.sf.jasperreports.engine.JRException;

/**
 *
 * @author carlo
 */
@ManagedBean
@ViewScoped
public class GestionarVentaMB implements Serializable {

    @EJB
    private FacturaServicio facturaServicio;

    @EJB
    private SistemaServicio sistemaServicio;

    /**
     * Lista de las ventas realizados
     */
    private List<Venta> ventas;

    private List<Venta> ventasFiltro;

    private Venta venta;

    private List<DetallesVenta> detallesVenta;

    @PostConstruct
    public void postConstruc() {
        ventas = facturaServicio.obtenerVentas();
    }
    
    public void cargarDetalles()
    {
        detallesVenta = new ArrayList<DetallesVenta>();
        List<DetalleProductoGeneral> listaGeneral = venta.getDetalleProductoGeneralList();

        for (DetalleProductoGeneral detalle : listaGeneral) {
            detallesVenta.add(new DetallesVenta(
                    detalle.getCantidad(),
                    detalle.getCodigoProducto().getCodigoProducto(),
                    detalle.getCodigoProducto().getNombre(),
                    detalle.getCodigoProducto().getPrecio(),
                    detalle.getSubtotal()));
        }

        List<DetalleProductoIndividual> listaIndividual = venta.getDetalleProductoIndividualList();
        for (DetalleProductoIndividual detalle : listaIndividual) {
            detallesVenta.add(new DetallesVenta(
                    1,
                    detalle.getCodigoUnico().getCodigoProducto().getCodigoProducto(),
                    detalle.getCodigoUnico().getCodigoProducto().getNombre(),
                    detalle.getCodigoUnico().getCodigoProducto().getPrecio(),
                    detalle.getSubtotal()));
        }
    }

    public String getColor(Venta venta) {
        System.out.println("Estado" + venta.getEstado());

        if (venta.getEstado().equals("facturado")) {
            return "white";
        } else {
            return "coral";
        }
    }

    /**
     *
     */
    public void anular(Venta venta) {
        System.out.println("anulando venta");
        facturaServicio.anularVenta(venta);
    }

    /**
     * Imprimir la factura
     */
    public void imprimir(Venta ventaAux) 
    {        
        System.out.println("generado el pdf ...");
        
        this.venta=ventaAux;
        cargarDetalles();
        
        String tipoVenta = venta.getTipoDocumento();


        //genera el pdf segun si es factura o nota de venta
        if (tipoVenta.equals("Nota")) {
            
                      
            NotaVentaModeloReporte notaVenta = new NotaVentaModeloReporte(sistemaServicio.getConfiguracion().getPathReportes());
            notaVenta.setDireccion(venta.getCedulaRuc().getDireccion());
            SimpleDateFormat sdf = new SimpleDateFormat("yyyy/MM/dd");
            notaVenta.setFechaFactura(sdf.format(venta.getFecha()));
            notaVenta.setFechaaFactura(sdf.format(venta.getFecha()));
            notaVenta.setFormaPago(venta.getTipoPago());
            notaVenta.setNombreCliente(venta.getCedulaRuc().getNombre());
            notaVenta.setTelefono(venta.getCedulaRuc().getTelefono());
            notaVenta.setTotal(venta.getTotal());

            for (DetallesVenta detalle : detallesVenta) {
                FacturaDetalleModeloReporte detallesFactura = new FacturaDetalleModeloReporte();
            
                detallesFactura.setCantidad(detalle.getCantidad() + "");
                detallesFactura.setCodigo(detalle.getCodigo());
                detallesFactura.setDescripcion(detalle.getNombre());
                detallesFactura.setDescuento(" ");
                detallesFactura.setPrecioUnitario(detalle.getCosto().toString());
                detallesFactura.setTotal(detalle.getTotal().toString());
                if (notaVenta != null) {
                    notaVenta.agregarDetalle(detallesFactura);
                }
            }

            try {
                notaVenta.exportarPDF();
                //detallesFactura.setCantidad(1);
                //factura.agregarDetalle(detallesFactura);
                //factura.exportarPDF();
            } catch (JRException ex) {
                Logger.getLogger(FacturaMB.class.getName()).log(Level.SEVERE, null, ex);
            } catch (IOException ex) {
                Logger.getLogger(FacturaMB.class.getName()).log(Level.SEVERE, null, ex);
            }

        } else {
            FacturaModeloReporte facturaReporte = new FacturaModeloReporte(sistemaServicio.getConfiguracion().getPathReportes());
            facturaReporte.setCodigoFactura(venta.getCodigoFactura() + "");
            facturaReporte.setDireccion(venta.getCedulaRuc().getDireccion());
            SimpleDateFormat sdf = new SimpleDateFormat("yyyy/MM/dd");
            facturaReporte.setTelefono(venta.getCedulaRuc().getTelefono());

            facturaReporte.setRuc(venta.getCedulaRuc().getCedulaRuc());
            facturaReporte.setFechaaFactura(sdf.format(venta.getFecha()));
            facturaReporte.setFormaPago("Efectivo");
            facturaReporte.setIvaTotal(venta.getTotal());
            facturaReporte.setNombreCliente(venta.getCedulaRuc().getNombre());
            facturaReporte.setNota(" ");
            BigDecimal subtotal=venta.getTotal().divide(new BigDecimal("1.12"));
            subtotal.setScale(2,BigDecimal.ROUND_UP);
            facturaReporte.setSubtotal(subtotal);

            facturaReporte.setTotal(venta.getTotal());
            
            for (DetallesVenta detalle : detallesVenta) {
                FacturaDetalleModeloReporte detallesFactura = new FacturaDetalleModeloReporte();
                detallesFactura.setCantidad(detalle.getCantidad() + "");
                detallesFactura.setCodigo(detalle.getCodigo());
                detallesFactura.setDescripcion(detalle.getNombre());
                detallesFactura.setDescuento(" ");
                detallesFactura.setPrecioUnitario(detalle.getCosto().toString());
                detallesFactura.setTotal(detalle.getTotal().toString());
                if (facturaReporte != null) {
                    facturaReporte.agregarDetalle(detallesFactura);
                }
            }

            try {
                facturaReporte.exportarPDF();

            } catch (JRException ex) {
                Logger.getLogger(FacturaMB.class.getName()).log(Level.SEVERE, null, ex);
            } catch (IOException ex) {
                Logger.getLogger(FacturaMB.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
    }

    public FacturaServicio getFacturaServicio() {
        return facturaServicio;
    }

    public void setFacturaServicio(FacturaServicio facturaServicio) {
        this.facturaServicio = facturaServicio;
    }

    public List<Venta> getVenta() {
        return ventas;
    }

    public void setVenta(List<Venta> venta) {
        this.ventas = venta;
    }

    public List<Venta> getVentasFiltro() {
        return ventasFiltro;
    }

    public void setVentasFiltro(List<Venta> ventasFiltro) {
        this.ventasFiltro = ventasFiltro;
    }

}