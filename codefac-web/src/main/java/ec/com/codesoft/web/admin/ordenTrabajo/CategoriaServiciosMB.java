/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ec.com.codesoft.web.admin.ordenTrabajo;

import ec.com.codesoft.model.CategoriaTrabajo;
import ec.com.codesoft.model.Servicios;
import ec.com.codesoft.modelo.servicios.CategoriaServicioServicios;
import ec.com.codesoft.modelo.servicios.ServiciosServicio;
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
@ViewScoped
@ManagedBean
public class CategoriaServiciosMB implements Serializable {

    @EJB
    private ServiciosServicio serviciosServicio;

    @EJB
    private CategoriaServicioServicios categoriaServicio;

    /**
     * Lista para mostrar en la tabla
     */
    private List<CategoriaTrabajo> categoriaTrabajoList;

    private List<Servicios> serviciosList;

    /**
     * Servicio Seleccionado
     */
    private Servicios servicioSeleccionado;

    /**
     * *
     * Categoria para seleccionar en la tabla
     */
    private CategoriaTrabajo categoriaSeleccionada;

    /**
     * Variable para crear l nueva categoria
     */
    private CategoriaTrabajo categoriaNuevo;

    /**
     * id del servicio para crear y editar
     */
    private Integer idServicioSeleccionado;
    

    private Servicios servicioCrear;
            
    @PostConstruct
    public void postCostruct() {
        categoriaTrabajoList = categoriaServicio.obtenerTodos();
    }

    public void abrirNuevoCatalogo() {
        System.out.println("abriendo dialogo nuevo catalago");
        categoriaNuevo = new CategoriaTrabajo();
        serviciosList = serviciosServicio.obtenerTodos();
        RequestContext.getCurrentInstance().execute("PF('widgetNuevoCatalogo').show()");
    }

    public void guardarCatalogo() {
        System.out.println("guardar el catalogo");
        Servicios servicio = serviciosServicio.buscarPorID(idServicioSeleccionado);
        categoriaNuevo.setCodigoServicio(servicio);
        categoriaServicio.grabar(categoriaNuevo);
        RequestContext.getCurrentInstance().execute("PF('widgetNuevoCatalogo').hide()");
        categoriaTrabajoList = categoriaServicio.obtenerTodos();

    }

    public void abrirEditarCatalogo() {
        System.out.println("abriendo dialogo editar servicio");
        idServicioSeleccionado=categoriaSeleccionada.getCodigoServicio().getCodigoServicio();
        categoriaTrabajoList=categoriaServicio.obtenerTodos();
                
        RequestContext.getCurrentInstance().execute("PF('widgetEditarServicio').show()");
    }
    
    public void abrirNuevoServicio()
    {
        System.out.println("dialogo new servicio");
        servicioCrear=new Servicios();
        RequestContext.getCurrentInstance().execute("PF('widgetNuevoServicio').show()");
    }
    
    public void mensaje()
    {
        System.out.println("imprimiendo mensaje...");
    }
    
    public void editarCategoria()
    {
        System.out.println("editando la categoria");
        categoriaServicio.editar(categoriaSeleccionada);
        categoriaTrabajoList=categoriaServicio.obtenerTodos();
        RequestContext.getCurrentInstance().execute("PF('widgetEditarServicio').hide()");
        
    }
    
    public void eliminar(CategoriaTrabajo categoria)
    {
        System.out.println("eliminando categoria");
        categoriaServicio.eliminar(categoria);
        categoriaTrabajoList=categoriaServicio.obtenerTodos();
    }
    
    public void guardarServicio()
    {
        System.out.println("grabando nuevo servicio ...");
        serviciosServicio.grabar(servicioCrear);
        serviciosList=serviciosServicio.obtenerTodos();
        RequestContext.getCurrentInstance().execute("PF('widgetNuevoServicio').hide()");
        
    }

    //////////////////METODOS GET AND SET/////////////////////
    public List<CategoriaTrabajo> getCategoriaTrabajoList() {
        return categoriaTrabajoList;
    }

    public void setCategoriaTrabajoList(List<CategoriaTrabajo> categoriaTrabajoList) {
        this.categoriaTrabajoList = categoriaTrabajoList;
    }

    public CategoriaTrabajo getCategoriaSeleccionada() {
        return categoriaSeleccionada;
    }

    public void setCategoriaSeleccionada(CategoriaTrabajo categoriaSeleccionada) {
        this.categoriaSeleccionada = categoriaSeleccionada;
    }

    public CategoriaTrabajo getCategoriaNuevo() {
        return categoriaNuevo;
    }

    public void setCategoriaNuevo(CategoriaTrabajo categoriaNuevo) {
        this.categoriaNuevo = categoriaNuevo;
    }

    public List<Servicios> getServiciosList() {
        return serviciosList;
    }

    public void setServiciosList(List<Servicios> serviciosList) {
        this.serviciosList = serviciosList;
    }

    public Servicios getServicioSeleccionado() {
        return servicioSeleccionado;
    }

    public void setServicioSeleccionado(Servicios servicioSeleccionado) {
        this.servicioSeleccionado = servicioSeleccionado;
    }

    public Integer getIdServicioSeleccionado() {
        return idServicioSeleccionado;
    }

    public void setIdServicioSeleccionado(Integer idServicioSeleccionado) {
        this.idServicioSeleccionado = idServicioSeleccionado;
    }
    public Servicios getServicioCrear() {
        return servicioCrear;
    }

    public void setServicioCrear(Servicios servicioCrear) {
        this.servicioCrear = servicioCrear;
    }

    
    
}
