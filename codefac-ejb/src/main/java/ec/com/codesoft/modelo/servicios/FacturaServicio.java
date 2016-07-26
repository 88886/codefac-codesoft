/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ec.com.codesoft.modelo.servicios;

import ec.com.codesoft.model.AbonoVentaCredito;
import ec.com.codesoft.model.Banco;
import ec.com.codesoft.model.Compra;
import ec.com.codesoft.model.CreditoFactura;
import ec.com.codesoft.model.Declaraciones;
import ec.com.codesoft.model.DetalleOrdenTrabajo;
import ec.com.codesoft.model.DetalleProductoGeneral;
import ec.com.codesoft.model.DetalleProductoIndividual;
import ec.com.codesoft.model.DetalleVentaOrdenTrabajo;
import ec.com.codesoft.model.DetallesServicio;
import ec.com.codesoft.model.Intereses;
import ec.com.codesoft.model.ProductoGeneralVenta;
import ec.com.codesoft.model.ProductoIndividualCompra;
import ec.com.codesoft.model.Venta;
import ec.com.codesoft.modelo.facade.AbonoVentaCreditoFacade;
import ec.com.codesoft.modelo.facade.BancoFacade;
import ec.com.codesoft.modelo.facade.CompraFacade;
import ec.com.codesoft.modelo.facade.CreditoFacturaFacade;
import ec.com.codesoft.modelo.facade.DetalleOrdenTrabajoFacade;
import ec.com.codesoft.modelo.facade.DetalleProductoGeneralFacade;
import ec.com.codesoft.modelo.facade.DetalleProductoIndividualFacade;
import ec.com.codesoft.modelo.facade.DetalleVentaOrdenTrabajoFacade;
import ec.com.codesoft.modelo.facade.DetallesServicioFacade;
import ec.com.codesoft.modelo.facade.InteresesFacade;
import ec.com.codesoft.modelo.facade.ProductoGeneralCompraFacade;
import ec.com.codesoft.modelo.facade.ProductoGeneralVentaFacade;
import ec.com.codesoft.modelo.facade.ProductoIndividualCompraFacade;
import ec.com.codesoft.modelo.facade.VentaFacade;
import java.util.Date;
import java.util.List;
import javax.ejb.EJB;
import javax.ejb.LocalBean;
import javax.ejb.Stateless;
import javax.ejb.TransactionAttribute;
import javax.ejb.TransactionAttributeType;

/**
 *
 * @author Suco
 */
@LocalBean
@Stateless
public class FacturaServicio {

    @EJB
    CompraFacade compraFacade;

    @EJB
    ProductoIndividualCompraFacade productoIndividualFacade;

    @EJB
    ProductoGeneralCompraFacade productoGeneralFacade;

    @EJB
    ProductoGeneralVentaFacade productoGeneralVentaFacade;

    @EJB
    DetalleProductoIndividualFacade detalleIndividualFacade;

    @EJB
    DetalleProductoGeneralFacade detalleGeneralFacade;

    @EJB
    VentaFacade ventaFacade;

    @EJB
    InteresesFacade interesFacade;

    @EJB
    BancoFacade bancofacade;

    @EJB
    DetalleVentaOrdenTrabajoFacade detalleVentaOrdenTrabajoFacade;

    @EJB
    DetalleOrdenTrabajoFacade detalleOrdenFacade;

    @EJB
    CreditoFacturaFacade creditoFacturaFacade;

    @EJB
    AbonoVentaCreditoFacade abonoVentaCreditoFacade;
    
    @EJB
    DetallesServicioFacade detallesServiciosFacade;

    /**
     * Busca los productos con especifico y devuelve la cantidad disponible
     *
     * @param codP
     * @return
     */
    public int devolverStockIndividual(String codP) {
        return (productoIndividualFacade.findStockIndividual(codP).intValue());
    }

    /**
     * Busca los producto con codigo general y devuelve la cantidad disponible
     *
     * @param codP
     * @return
     */
    public ProductoGeneralVenta devolverStockGeneral(String codP) {

        return productoGeneralFacade.findGeneralCodP(codP);
    }

    public void insertarDetalleProductoIndividual(List<DetalleProductoIndividual> detalles) {

        for (int i = 0; i < detalles.size(); i++) {
            detalleIndividualFacade.create(detalles.get(i));
        }
    }

    /**
     * Insertar detalleIndividual Venta Diaria
     */
    public void insertarDetalleProductoIndividual(DetalleProductoIndividual detalle) {

        detalleIndividualFacade.create(detalle);

    }

    public void insertarDetallesFacturaProductoGeneral(List<DetalleProductoGeneral> detalles) {

        for (int i = 0; i < detalles.size(); i++) {
            detalleGeneralFacade.create(detalles.get(i));
        }
    }

    /**
     * Insertar detalleIndividual Venta Diaria
     */
    public void insertarDetalleFacturaProductoGeneral(DetalleProductoGeneral detalle) {

        detalleGeneralFacade.create(detalle);

    }
     public void insertarDetalleServicio(DetallesServicio detalleServicio) {

        detallesServiciosFacade.create(detalleServicio);

    }

    public void editarVentaDiaria(Venta venta) {
        ventaFacade.edit(venta);
    }

    public void insertarDetallesVentaOrdenTrabajo(List<DetalleVentaOrdenTrabajo> detalles) {

        for (int i = 0; i < detalles.size(); i++) {
            detalleVentaOrdenTrabajoFacade.create(detalles.get(i));
        }
    }

    @TransactionAttribute(TransactionAttributeType.REQUIRED)
    public void anularVenta(Venta venta) {
        venta.setEstado("anulado");
        ventaFacade.edit(venta);

        List<DetalleProductoGeneral> listaGeneral = venta.getDetalleProductoGeneralList();
        for (DetalleProductoGeneral detalle : listaGeneral) {
            ProductoGeneralVenta productoGeneralVenta = detalle.getCodigoProducto().getProductoGeneralVenta();
            productoGeneralVenta.addStock(detalle.getCantidad());
            productoGeneralVentaFacade.edit(productoGeneralVenta);

        }

        List<DetalleProductoIndividual> listaEspecifico = venta.getDetalleProductoIndividualList();

        for (DetalleProductoIndividual detalle : listaEspecifico) {
            ProductoIndividualCompra productoIndividual = detalle.getProductoIndividualCompra();
            productoIndividualFacade.edit(productoIndividual);

        }

    }

    /**
     * Busca la factura por el codigo principal
     */
    public Venta buscarVentaById(Integer id) {
        return ventaFacade.find(id);
    }

    ///////////////////////METODOS GET AND SET
    //  public int obtenerProductoIndivudualCantidad(String codP) {
    //  return detalleIndividualFacade.findProductosIndividualCantidad(cantidad, codP);
    // }
    public ProductoIndividualCompra devolverIndividualCod(String cod, String codCat) {
        return productoIndividualFacade.findProdIndividual(cod, codCat);
    }

    public void guardarFactura(Venta venta) {

        ventaFacade.create(venta);
    }

    public void actulizarStockGeneral(ProductoGeneralVenta productoGeneral) {
        productoGeneralVentaFacade.edit(productoGeneral);
    }

    public void actulizarStocIndividual(ProductoIndividualCompra prodIndividual) {

        productoIndividualFacade.edit(prodIndividual);
    }

    public ProductoIndividualCompra devolverProductoIndividual(String codUnico) {

        return productoIndividualFacade.findProdIndividualCodUnico(codUnico);
    }

    public void editarCredito(CreditoFactura credito) {
        creditoFacturaFacade.edit(credito);
    }

    /**
     * Obtiene todas las ventas realizadas
     *
     * @return
     */
    public List<Venta> obtenerVentas() {
        return ventaFacade.getVentas();
        //return ventaFacade.findAll();
    }

    /**
     * Obtiene el codigo de la siguiente factura
     *
     * @return
     */
    public Integer getCodigoFactura(String tipo) {
        if (tipo.equals("Factura")) {
            Integer codigo = ventaFacade.getCodigoUltimaFactura();
            if (codigo == null) {
                return 1;
            } else {
                return codigo + 1;
            }

        }

        if (tipo.equals("Nota")) {
            Integer codigo = ventaFacade.getCodigoUltimaNota();
            if (codigo == null) {
                return 1;
            } else {
                return codigo + 1;
            }

        }

        return 0;

    }

    /**
     * Busca una factura segun el numero del documento de la venta
     *
     * @return
     */
    public Venta buscarPorCodigoDocumento(Integer codigo) {
        return ventaFacade.findCodigoDocumento(codigo);
    }

    public List<Intereses> devolverIntereses() {
        return interesFacade.findAll();
    }

    public List<Banco> devolverBancos() {
        return bancofacade.findAll();
    }

    public Banco devolverInteresBanco(String nombre) {
        return bancofacade.findInteresesBanco(nombre);
    }

    public Venta devolverDetallesVentasDiarias(String fecha) {

        return ventaFacade.findFacturaVentasDiariasFecha(fecha);
    }

    public List<DetalleProductoGeneral> devolverVentaDiariaDetallesGeneral(int cod) {
        return ventaFacade.findFDetalleGeneralVentasDiariasCod(cod);
    }
    
    public List<DetallesServicio> devolverVentaDiariaDetallesServicio(int cod) {
        return ventaFacade.findServicioVentasDiariasCod(cod);
    }
    public List<DetalleVentaOrdenTrabajo> devolverVentaDiariaDetallesOrden(int cod) {
        return ventaFacade.findDetallesOrdenTranajoVentasDiariasCod(cod);
    }

    public List<DetalleProductoIndividual> devolverVentaDiariaDetallesIndividual(int cod) {
        return ventaFacade.findFDetalleIndividualVentasDiariasCod(cod);
    }

    public List<DetalleVentaOrdenTrabajo> devolverVentaDiariaDetallesOrdenTrabajo(int cod) {
        return ventaFacade.findFDetalleOrdenTrabajoCod(cod);
    }

    public List<Venta> devolverVentasIntervalo(String fecha1, String fecha2) {

        return ventaFacade.findVentasIntervalo(fecha1, fecha2);
    }

    public void actualizarDetalleOrden(DetalleOrdenTrabajo detalle) {
        detalleOrdenFacade.edit(detalle);
    }

    public void guardarCreditoFactura(CreditoFactura credito) {
        creditoFacturaFacade.create(credito);
    }

    public void guardarAbonos(AbonoVentaCredito abono) {
        abonoVentaCreditoFacade.create(abono);
    }

    public List<Venta> obtenerVentaTipo(String cedula, String tipoPago) {
        return ventaFacade.findVentaTipo(cedula, tipoPago);
    }

    public CreditoFactura obtenerCreditoFactura(Integer codFactura, String estado) {
        return ventaFacade.findCreditoFactura(codFactura, estado);
    }

    public void guardarAbono(AbonoVentaCredito abono) {
        abonoVentaCreditoFacade.create(abono);
    }

    public List<AbonoVentaCredito> obtenerAbonosCredito(Integer codigo) {
        return ventaFacade.findAbonoCredito(codigo);
    }

    public void editarAbono(AbonoVentaCredito abono) {
        abonoVentaCreditoFacade.edit(abono);
    }

    public void eliminarAbono(AbonoVentaCredito abono) {
        abonoVentaCreditoFacade.remove(abono);
    }

    public List<Venta> obtnerSubtotalesVentas(Date fechaF) {
        return ventaFacade.findVentasSubtotales(fechaF);
    }
    
    public List<Compra> obtnerSubtotalesCompras(Date fechaF) {
        return ventaFacade.findComprasSubtotales(fechaF);
    }
    
    public List<Venta> obtnerTotalesNotas(Date fechaF) {
        return ventaFacade.findNotasTotales(fechaF);
    }
    
    public Declaraciones obtenerUltimaDeclaracion(){
        return ventaFacade.findUltimaDeclaracion();
    }
    
    

}
