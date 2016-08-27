/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ec.com.codesoft.web.admin.ordenTrabajo;

import ec.com.codesoft.model.DetalleOrdenTrabajo;
import ec.com.codesoft.model.OrdenTrabajo;
import ec.com.codesoft.modelo.servicios.OrdenTrabajoServicio;
import ec.com.codesoft.modelo.servicios.SistemaServicio;
import ec.com.codesoft.web.reportes.ordenTrabajo.OrdenTrabajoDetalleReporte;
import ec.com.codesoft.web.reportes.ordenTrabajo.OrdenTrabajoReporte;
import java.io.IOException;
import java.io.Serializable;
import java.text.SimpleDateFormat;
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
public class GestionarOrdenTrabajoMB implements Serializable
{
    @EJB
    private OrdenTrabajoServicio ordenTrabajoServicio;
    
    private List<OrdenTrabajo> ordenTrabajoList;
    private List<OrdenTrabajo> ordenTrabajoFiltro;
    
    
    private List<DetalleOrdenTrabajo> detalleOrdenTrabajo;
    
    private OrdenTrabajo ordenTrabajo;
    
    @EJB
    private SistemaServicio sistemaServicio;
    
    @PostConstruct
    public void postCostruct()
    {
        ordenTrabajoList=ordenTrabajoServicio.obtenerOrdenesTrabajoAll();
        for (OrdenTrabajo ordenTrabajoList1 : ordenTrabajoList) {
               System.out.println("Ordenes "+ ordenTrabajoList1.getEstado()); 
        }
        
    }
    
    public void devolverDetalleOrdenTrabajo(OrdenTrabajo ordenEnviada){
        System.out.println("DEtalle");
        detalleOrdenTrabajo=ordenEnviada.getDetalleOrdenTrabajoList();
        RequestContext.getCurrentInstance().execute("PF('dlgDetallesOrdenTrabajo').show()");
    }
    
    
    
    public void imprimir(OrdenTrabajo ordenTrabajo)
    {
        this.ordenTrabajo=ordenTrabajo;
        generaPdf();
    }
    
    public void generaPdf() {
        System.out.println("generando pdf..");
        OrdenTrabajoReporte orden = new OrdenTrabajoReporte(sistemaServicio.getConfiguracion().getPathreportes());
        orden.setEmpresa(sistemaServicio.getEmpresa());
        orden.setAbono(ordenTrabajo.getAdelanto().toString());
        orden.setCedula(ordenTrabajo.getCedulaRuc().getCedulaRuc());
        SimpleDateFormat formateador = new SimpleDateFormat("EEEE d MMMM HH:mm:ss");
        orden.setFechaRecepcion(formateador.format(ordenTrabajo.getFechaEntrega()).toString());
        orden.setMonto(ordenTrabajo.getTotal().toString());
        orden.setNombre(ordenTrabajo.getCedulaRuc().getNombre());
        orden.setObservacion(ordenTrabajo.getObservacion().toString());
        orden.setOrdenTrabajo(ordenTrabajo.getIdOrdenTrabajo().toString());
        orden.setSaldo(ordenTrabajo.getTotal().subtract(ordenTrabajo.getAdelanto()).toString());
        orden.setTelefono(ordenTrabajo.getCedulaRuc().getTelefono());
        orden.setNotasOrden(sistemaServicio.getConfiguracion().getNotasOrden());

        List<DetalleOrdenTrabajo> lista = ordenTrabajo.getDetalleOrdenTrabajoList();
        for (DetalleOrdenTrabajo detalle : lista) {
            OrdenTrabajoDetalleReporte detalleReporte = new OrdenTrabajoDetalleReporte();
            detalleReporte.setDescripcion(detalle.getDescripcion());
            detalleReporte.setNombre(detalle.getEquipo());
            detalleReporte.setPrecio(detalle.getPrecio().toString());
            detalleReporte.setProblema(detalle.getProblema());
            detalleReporte.setTrabajoRealizar(detalle.getTrabajoRealizar());
            orden.getDetalles().add(detalleReporte);
        }

        try {
            orden.exportarPDF();
            System.out.println("pdf generado");
        } catch (JRException ex) {
            Logger.getLogger(OrdenTrabajoMB.class.getName()).log(Level.SEVERE, null, ex);
        } catch (IOException ex) {
            Logger.getLogger(OrdenTrabajoMB.class.getName()).log(Level.SEVERE, null, ex);
        }

    }
    
    public void anular(OrdenTrabajo orden)
    {
        ordenTrabajoServicio.anularOrdenTrabajo(orden);
    }

    public List<OrdenTrabajo> getOrdenTrabajoList() {
        return ordenTrabajoList;
    }

    public void setOrdenTrabajoList(List<OrdenTrabajo> ordenTrabajoList) {
        this.ordenTrabajoList = ordenTrabajoList;
    }

    public List<OrdenTrabajo> getOrdenTrabajoFiltro() {
        return ordenTrabajoFiltro;
    }

    public void setOrdenTrabajoFiltro(List<OrdenTrabajo> ordenTrabajoFiltro) {
        this.ordenTrabajoFiltro = ordenTrabajoFiltro;
    }

    public OrdenTrabajo getOrdenTrabajo() {
        return ordenTrabajo;
    }

    public void setOrdenTrabajo(OrdenTrabajo ordenTrabajo) {
        this.ordenTrabajo = ordenTrabajo;
    }

    public List<DetalleOrdenTrabajo> getDetalleOrdenTrabajo() {
        return detalleOrdenTrabajo;
    }

    public void setDetalleOrdenTrabajo(List<DetalleOrdenTrabajo> detalleOrdenTrabajo) {
        this.detalleOrdenTrabajo = detalleOrdenTrabajo;
    }
    
    
    
    
}
