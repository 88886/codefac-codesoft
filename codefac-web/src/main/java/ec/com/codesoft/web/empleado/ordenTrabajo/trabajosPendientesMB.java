/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ec.com.codesoft.web.empleado.ordenTrabajo;

import ec.com.codesoft.model.DetalleOrdenTrabajo;
import ec.com.codesoft.model.OrdenTrabajo;
import ec.com.codesoft.modelo.servicios.OrdenTrabajoServicio;
import ec.com.codesoft.modelo.servicios.SistemaServicio;
import ec.com.codesoft.web.seguridad.SistemaMB;
import ec.com.codesoft.web.util.CorreoMB;
import java.io.Serializable;
import java.util.List;
import javax.annotation.PostConstruct;
import javax.ejb.EJB;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ManagedProperty;
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

    @ManagedProperty(value = "#{sistemaMB}")
    private SistemaMB sistemaMB;

    @EJB
    private SistemaServicio sistemaServicio;

    /**
     * Variable para saber el tipo de campo por filtrar ejemplo: por fecha
     * ingreso, fecha salida, precios
     */
    private String tipoFiltro;
    /**
     * Indica el tipo de ordenamiento que se va a realiza desendente o ascendete
     */
    private String tipoOrden;

    /**
     * Variable para saber el estado de la compra
     */
    private String tipoEstado;

    /**
     * Variable para confirmar el envio de correo
     */
    private boolean confirmarCorreo;

    /**
     * Variable que me permite saber la tarea que estoy reparando o editando
     */
    private DetalleOrdenTrabajo repararTarea;

    /**
     * *
     * Variable que me permite registrar el asunto del correo para enviar
     */
    private String asuntoCorreo;
    /**
     * Variable que representa el contenido del correo para enviar
     */
    private String contenidoCorreo;

    private OrdenTrabajo ordenTrabajoSeleccionados;
    
    /**
     * Orden de trabajo que me sirve para enviar el correo
     */
    private OrdenTrabajo ordenTrabajoCorreo;
    
    /**
     * Correo 
     */
    private String correoEnviar;

    @PostConstruct
    public void postConstruct() {
        tipoEstado = "revision";
        ordenTrabajoList = ordenTrabajoServicio.obtenerPorFechaIngreso("ASC", tipoEstado);
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
                ordenTrabajoList = ordenTrabajoServicio.obtenerPorFechaSalida(tipoOrden, tipoEstado);
                break;

            case "ingreso":
                ordenTrabajoList = ordenTrabajoServicio.obtenerPorFechaIngreso(tipoOrden, tipoEstado);
                break;

            case "precio":
                ordenTrabajoList = ordenTrabajoServicio.obtenerPorPrecio(tipoOrden, tipoEstado);
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
        ordenTrabajoSeleccionados.setEstado("reparado");
        ordenTrabajoServicio.editar(ordenTrabajoSeleccionados);

        if (confirmarCorreo) {
            System.out.println("enviando correo ...");
            System.out.println(sistemaMB.getConfiguracion());
            CorreoMB correo = new CorreoMB(sistemaMB.getConfiguracion().getEmailServicioTecnico(), sistemaMB.getConfiguracion().getClaveEmailServicioTecnico());
            correo.EnviarCorreoSinArchivoAdjunto(""
                    + ordenTrabajoSeleccionados.getCedulaRuc().getCorreo(), ""
                    + "Codesoft", ""
                    + "Codefac le informa que su trabajo con orden No: " + ordenTrabajoSeleccionados.getIdOrdenTrabajo() + "(" + ordenTrabajoSeleccionados.toStringEquipos() + ") se encuentra listo "
                    + "<br> Diagnostico:" + ordenTrabajoSeleccionados.getDiagnostico() + "<br> Costo: " + ordenTrabajoSeleccionados.getTotal());
        }
        RequestContext.getCurrentInstance().execute("PF('dlgReparacion').hide()");
        //dlgReparacion
    }

    /**
     * Metodo para abrir el dialogo para reparar
     */
    public void abrirRepararTarea(DetalleOrdenTrabajo detalle) {
        RequestContext.getCurrentInstance().execute("PF('widgetReparItem').show()");
        this.repararTarea = detalle;

    }

    /**
     * Metodo que me permite cerrar el dialogo para reparar la tarea
     */
    public void cerrarRepararTarea() {
        System.out.println("cerrar el dialogo para editar los items");
        RequestContext.getCurrentInstance().execute("PF('widgetReparItem').hide()");
    }

    /**
     * Revisa y cambia el estado segun el arreglo del tecnico
     */
    public void revisarTarea() {
        //ordenTrabajoSeleccionados.setEstado("reparado");
        repararTarea.setEstado("reparado");
        ordenTrabajoServicio.reparar(repararTarea);

        if (confirmarCorreo) {
            System.out.println("enviando correo ...");
            System.out.println(sistemaMB.getConfiguracion());
            CorreoMB correo = new CorreoMB(sistemaMB.getConfiguracion().getEmailServicioTecnico(), sistemaMB.getConfiguracion().getClaveEmailServicioTecnico());
            correo.EnviarCorreoSinArchivoAdjunto(""
                    + repararTarea.getIdOrdenTrabajo().getCedulaRuc().getCorreo(), ""
                    + "Codesoft", ""
                    + "Codefac le informa que su trabajo con orden No: " + repararTarea.getIdOrdenTrabajo().getIdOrdenTrabajo() + "(" + repararTarea.getEquipo() + ") se encuentra listo "
                    + "<br> Diagnostico:" + repararTarea.getDiagnostico() + "<br> Costo: " + repararTarea.getIdOrdenTrabajo().getTotal());
        }
        RequestContext.getCurrentInstance().execute("PF('widgetReparItem').hide()");
    }

    /**
     * Abrir el dialogo para enviar un correo rapido
     */
    public void abrirDialogoCorreo(OrdenTrabajo orden) 
    {
        System.out.println("abriendo dialogo -> "+orden);
        RequestContext.getCurrentInstance().execute("PF('widgetEnviarCorreo').show()");
        this.ordenTrabajoCorreo = orden;
        
    }

    /**
     * Metodo que me enviar cerrar el dialogo de los correoss
     */
    public void cerrarEnviarCorreo() {
        RequestContext.getCurrentInstance().execute("PF('widgetEnviarCorreo').hide()");
        System.out.println(this.ordenTrabajoCorreo);
    }

    /**
     * Metodo que me permite enviar el correo al destinatario
     */
    public void enviarCorreo() {
        System.out.println("Orden-> "+ordenTrabajoCorreo);
        System.out.println("enviando correo de informacion ...");
        System.out.println(sistemaMB.getConfiguracion());
        CorreoMB correo = new CorreoMB(sistemaMB.getConfiguracion().getEmailServicioTecnico(), sistemaMB.getConfiguracion().getClaveEmailServicioTecnico());
        correo.EnviarCorreoSinArchivoAdjunto(""
                + ordenTrabajoCorreo.getCedulaRuc().getCorreo(), ""
                + sistemaServicio.getEmpresa().getNombre()+" "+asuntoCorreo, ""
                +contenidoCorreo);

        RequestContext.getCurrentInstance().execute("PF('widgetEnviarCorreo').hide()");
        
    }

    //////////////////////////GET AND SET//////////////////////////////
    public SistemaMB getSistemaMB() {
        return sistemaMB;
    }

    public void setSistemaMB(SistemaMB sistemaMB) {
        this.sistemaMB = sistemaMB;
    }

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

    public boolean isConfirmarCorreo() {
        return confirmarCorreo;
    }

    public void setConfirmarCorreo(boolean confirmarCorreo) {
        this.confirmarCorreo = confirmarCorreo;
    }

    public String getTipoEstado() {
        return tipoEstado;
    }

    public void setTipoEstado(String tipoEstado) {
        this.tipoEstado = tipoEstado;
    }

    public DetalleOrdenTrabajo getRepararTarea() {
        return repararTarea;
    }

    public void setRepararTarea(DetalleOrdenTrabajo repararTarea) {
        this.repararTarea = repararTarea;
    }

    public String getAsuntoCorreo() {
        return asuntoCorreo;
    }

    public void setAsuntoCorreo(String asuntoCorreo) {
        this.asuntoCorreo = asuntoCorreo;
    }

    public String getContenidoCorreo() {
        return contenidoCorreo;
    }

    public void setContenidoCorreo(String contenidoCorreo) {
        this.contenidoCorreo = contenidoCorreo;
    }

    public OrdenTrabajo getOrdenTrabajoCorreo() {
        return ordenTrabajoCorreo;
    }

    public void setOrdenTrabajoCorreo(OrdenTrabajo ordenTrabajoCorreo) {
        this.ordenTrabajoCorreo = ordenTrabajoCorreo;
    }
    
    

}
