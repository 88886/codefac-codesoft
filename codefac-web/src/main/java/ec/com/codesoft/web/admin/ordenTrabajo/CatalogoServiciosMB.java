/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ec.com.codesoft.web.admin.ordenTrabajo;

import ec.com.codesoft.model.CategoriaTrabajo;
import ec.com.codesoft.model.DetalleOrdenTrabajo;
import ec.com.codesoft.model.DetallesServicio;
import ec.com.codesoft.model.Servicios;
import ec.com.codesoft.modelo.servicios.ServiciosServicio;
import ec.com.codesoft.modelo.servicios.SistemaServicio;
import java.awt.Event;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import javax.annotation.PostConstruct;
import javax.ejb.EJB;
import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;
import org.primefaces.context.RequestContext;

/**
 *
 * @author carlo
 */
@ManagedBean
@ViewScoped
public class CatalogoServiciosMB implements Serializable {

    /**
     * Referencia para editar el servicio
     */
    private Servicios servicioSeleccionado;
    /**
     * Referencia para guardar un nuevo servicio
     */
    private Servicios servicioNuevo;
    private List<CategoriaTrabajo> categorias;
    private CategoriaTrabajo categoriaSeleccionada;
    private CategoriaTrabajo categoriaNueva;
    private Boolean guardar;
    private Boolean editar;
    /**
     * Lista de servicios para mostrar en la tabla
     */
    private List<Servicios> serviciosList;

    @EJB
    private ServiciosServicio servicioServicios;

    @PostConstruct
    public void postCostruct() {
        serviciosList = servicioServicios.obtenerTodos();
        categoriaSeleccionada = new CategoriaTrabajo();
        categorias = new ArrayList<>();
        guardar = true;
        editar = true;

    }

    /**
     *
     */
    public void onRowSelect(Event event) {
        guardar = false;
        System.out.println("Onrow");
        categorias = new ArrayList<>();
        categorias = servicioServicios.obtenerCategoriasTrabajo(servicioSeleccionado.getCodigoServicio());
        System.out.println("Categorias " + categorias);
    }

    /**
     *
     */
    public void onRowUnSelectCat(Event event) {
    }

    public void onRowSelectCat(Event event) {
        editar = false;
        System.out.println("CategoriaSeleccionada " + categoriaSeleccionada);
        categoriaNueva = categoriaSeleccionada;
        System.out.println("Cat Nueva " + categoriaNueva);
    }

    /**
     *
     */
    public void onRowUnSelect(Event event) {
    }

    /**
     * Abrir nuevo servicio
     */
    public void abrirNuevoServicio() {
        System.out.println("abriendo dialogo nuevo servicio");
        servicioNuevo = new Servicios();
        RequestContext.getCurrentInstance().execute("PF('widgetNuevoServicio').show()");
    }

    public void abrirNuevaCategoria() {
        System.out.println("abriendo dialogo nueva Categoria");
        categoriaNueva = new CategoriaTrabajo();
        RequestContext.getCurrentInstance().execute("PF('widgetNuevaCategoria').show()");
    }

    public void editarNuevaCategoria() {
        System.out.println("abriendo dialogo nueva Categoria");
        RequestContext.getCurrentInstance().execute("PF('widgetNuevaCategoria').show()");
    }

    public void guardarCategoria() {
        System.out.println("Guardando Categoria");
        if (editar == false) {//false editar esta activo por el disabled del boton
            servicioServicios.editarCategoria(categoriaNueva);
            RequestContext.getCurrentInstance().execute("PF('widgetNuevaCategoria').hide()");
            //RequestContext.getCurrentInstance().execute("PF('widgetMensaje').show()");
            FacesMessage message = new FacesMessage(FacesMessage.SEVERITY_INFO, "Correcto", "Proceso Ejecutado Correctamente");
            RequestContext.getCurrentInstance().showMessageInDialog(message);
            editar = true;
        } else {
            //categoriaNueva.setIdCategoriaTrabajo(0);
            categoriaNueva.setCodigoServicio(servicioSeleccionado);
            categoriaNueva.setDetalleOrdenTrabajoList(new ArrayList<DetalleOrdenTrabajo>());
            categoriaNueva.setDetallesServicioList(new ArrayList<DetallesServicio>());
            servicioServicios.guardarCategoria(categoriaNueva);
            //servicioSeleccionado.getCategoriaTrabajoList().add(categoriaNueva);//setear el objetol
            categorias = servicioServicios.obtenerCategoriasTrabajo(servicioSeleccionado.getCodigoServicio());
            RequestContext.getCurrentInstance().execute("PF('widgetNuevaCategoria').hide()");
            FacesMessage message = new FacesMessage(FacesMessage.SEVERITY_INFO, "Correcto", "Proceso Ejecutado Correctamente");
            RequestContext.getCurrentInstance().showMessageInDialog(message);
            //RequestContext.getCurrentInstance().execute("PF('widgetMensaje').show()");
        }

    }

    public void eliminarCategoria(CategoriaTrabajo categoria) {
        servicioServicios.eliminarCategoria(categoria);
        categorias = servicioServicios.obtenerCategoriasTrabajo(servicioSeleccionado.getCodigoServicio());
        FacesMessage message = new FacesMessage(FacesMessage.SEVERITY_INFO, "Correcto", "Proceso Ejecutado Correctamente");
        RequestContext.getCurrentInstance().showMessageInDialog(message);
        // RequestContext.getCurrentInstance().execute("PF('widgetMensaje').show()");
    }

    /**
     *
     */
    public void guardarServicio() {

        System.out.println("grabando el servicio nuevo");
        servicioServicios.grabar(servicioNuevo);
        serviciosList = servicioServicios.obtenerTodos();
        RequestContext.getCurrentInstance().execute("PF('widgetNuevoServicio').hide()");
        //RequestContext.getCurrentInstance().execute("PF('widgetMensaje').show()");
        FacesMessage message = new FacesMessage(FacesMessage.SEVERITY_INFO, "Correcto", "Proceso Ejecutado Correctamente");
        RequestContext.getCurrentInstance().showMessageInDialog(message);

    }

    /**
     * Abrir Editar Servicio
     */
    public void abrirEditarServicio() {
        System.out.println("editando el widget el servicio");
        RequestContext.getCurrentInstance().execute("PF('widgetEditarServicio').show()");

    }

    public void editarServicio() {
        servicioServicios.editar(servicioSeleccionado);
        RequestContext.getCurrentInstance().execute("PF('widgetEditarServicio').hide()");
        //RequestContext.getCurrentInstance().execute("PF('widgetMensaje').show()");
        FacesMessage message = new FacesMessage(FacesMessage.SEVERITY_INFO, "Correcto", "Proceso Ejecutado Correctamente");
        RequestContext.getCurrentInstance().showMessageInDialog(message);

    }

    /**
     * Eliminar el servicio
     */
    public void eliminar(Servicios servicio) {
        System.out.println("eliminando el servicio");
        servicioServicios.eliminar(servicio);
        serviciosList = servicioServicios.obtenerTodos();
        //RequestContext.getCurrentInstance().execute("PF('widgetMensaje').show()");
        FacesMessage message = new FacesMessage(FacesMessage.SEVERITY_INFO, "Correcto", "Proceso Ejecutado Correctamente");
        RequestContext.getCurrentInstance().showMessageInDialog(message);
    }

    //////////////////////METODOS GET AND SET ////////////////
    public List<Servicios> getServiciosList() {
        return serviciosList;
    }

    public void setServiciosList(List<Servicios> serviciosList) {
        this.serviciosList = serviciosList;
    }

    public Servicios getServicioNuevo() {
        return servicioNuevo;
    }

    public void setServicioNuevo(Servicios servicioNuevo) {
        this.servicioNuevo = servicioNuevo;
    }

    public Servicios getServicioSeleccionado() {
        return servicioSeleccionado;
    }

    public void setServicioSeleccionado(Servicios servicioSeleccionado) {
        this.servicioSeleccionado = servicioSeleccionado;
    }

    public List<CategoriaTrabajo> getCategorias() {
        return categorias;
    }

    public void setCategorias(List<CategoriaTrabajo> categorias) {
        this.categorias = categorias;
    }

    public CategoriaTrabajo getCategoriaSeleccionada() {
        return categoriaSeleccionada;
    }

    public void setCategoriaSeleccionada(CategoriaTrabajo categoriaSeleccionada) {
        this.categoriaSeleccionada = categoriaSeleccionada;
    }

    public CategoriaTrabajo getCategoriaNueva() {
        return categoriaNueva;
    }

    public void setCategoriaNueva(CategoriaTrabajo categoriaNueva) {
        this.categoriaNueva = categoriaNueva;
    }

    public Boolean getGuardar() {
        return guardar;
    }

    public void setGuardar(Boolean guardar) {
        this.guardar = guardar;
    }

    public Boolean getEditar() {
        return editar;
    }

    public void setEditar(Boolean editar) {
        this.editar = editar;
    }

}
