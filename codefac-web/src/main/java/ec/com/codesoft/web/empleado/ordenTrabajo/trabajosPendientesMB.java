/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ec.com.codesoft.web.empleado.ordenTrabajo;

import ec.com.codesoft.model.OrdenTrabajo;
import ec.com.codesoft.modelo.servicios.OrdenTrabajoServicio;
import ec.com.codesoft.web.util.CorreoMB;
import java.io.Serializable;
import java.util.List;
import javax.annotation.PostConstruct;
import javax.ejb.EJB;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;
import org.primefaces.context.RequestContext;

/**
 *
 * @author carlo
 */
@ManagedBean
@ViewScoped
public class trabajosPendientesMB implements Serializable {

    @EJB
    private OrdenTrabajoServicio ordenTrabajoServicio;

    private List<OrdenTrabajo> ordenTrabajoList;

    /**
     * Variable para saber el tipo de campo por filtrar ejemplo: por fecha
     * ingreso, fecha salida, precios
     */
    private String tipoFiltro;
    /**
     * Indica el tipo de ordenamiento que se va a realiza desendente o ascendete
     */
    private String tipoOrden;

    private OrdenTrabajo ordenTrabajoSeleccionados;

    @PostConstruct
    public void postConstruct() {
        ordenTrabajoList = ordenTrabajoServicio.obtenerPorFechaIngreso("ASC");
        System.out.println(ordenTrabajoList.size());
        tipoFiltro = "ingreso";
        tipoOrden = "ASC";
    }

    /**
     * Realiza el filtro por un campo y realiza el ordenamiento
     */
    public void filtrar() {
        System.out.println("tipo:" + tipoFiltro);
        switch (tipoFiltro) {
            case "entrega":
                ordenTrabajoList = ordenTrabajoServicio.obtenerPorFechaSalida(tipoOrden);
                break;

            case "ingreso":
                ordenTrabajoList = ordenTrabajoServicio.obtenerPorFechaIngreso(tipoOrden);
                break;

            case "precio":
                ordenTrabajoList = ordenTrabajoServicio.obtenerPorPrecio(tipoOrden);
                break;

        }
    }

    /**
     * Contrala la reparacion rapida de la orden de trabajo
     */
    public void reparacionRapida(OrdenTrabajo orden) {
        System.out.println("reparacion rapida..");
        RequestContext.getCurrentInstance().execute("PF('dlgReparacion').show()");
        ordenTrabajoSeleccionados = orden;

    }

    /**
     * Cambiar el estado de la orden de trabajo
     */
    public void grabarOrdenTrabajo() {
        ordenTrabajoServicio.editar(ordenTrabajoSeleccionados);
        System.out.println("enviando correo ...");
        CorreoMB correo = new CorreoMB();
        correo.EnviarCorreoSinArchivoAdjunto(""
                + "carlosmast2302@gmail.com", ""
                        + "Codesoft", ""
                                + "Codefac le informa que su trabajo con orden No: "+ordenTrabajoSeleccionados.getIdOrdenTrabajo()+"("+ordenTrabajoSeleccionados.toStringEquipos()+") se encuentra listo "
                                + "<br> Diagnostico:"+ordenTrabajoSeleccionados.getDiagnostico()+"<br> Costo: "+ordenTrabajoSeleccionados.getTotal());
    }

    //////////////////////////GET AND SET//////////////////////////////
    public List<OrdenTrabajo> getOrdenTrabajoList() {
        return ordenTrabajoList;
    }

    public void setOrdenTrabajoList(List<OrdenTrabajo> ordenTrabajoList) {
        this.ordenTrabajoList = ordenTrabajoList;
    }

    public String getTipoFiltro() {
        return tipoFiltro;
    }

    public void setTipoFiltro(String tipoFiltro) {
        this.tipoFiltro = tipoFiltro;
    }

    public String getTipoOrden() {
        return tipoOrden;
    }

    public void setTipoOrden(String tipoOrden) {
        this.tipoOrden = tipoOrden;
    }

    public OrdenTrabajo getOrdenTrabajoSeleccionados() {
        return ordenTrabajoSeleccionados;
    }

    public void setOrdenTrabajoSeleccionados(OrdenTrabajo ordenTrabajoSeleccionados) {
        this.ordenTrabajoSeleccionados = ordenTrabajoSeleccionados;
    }

}
