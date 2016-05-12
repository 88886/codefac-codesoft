/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ec.com.codesoft.web.admin.ordenTrabajo;

import ec.com.codesoft.model.Servicios;
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
@ManagedBean
@ViewScoped
public class CatalogoServiciosMB implements Serializable
{
    /**
     * Referencia para editar el servicio
     */
    private Servicios servicioSeleccionado;
    /**
     * Referencia para guardar un nuevo servicio
     */
    private Servicios servicioNuevo;
    /**
     * Lista de servicios para mostrar en la tabla
     */
    private List<Servicios> serviciosList;
    
    @EJB
    private ServiciosServicio servicioServicios;
    

    @PostConstruct
    public void postCostruct()
    {
        serviciosList=servicioServicios.obtenerTodos();
        
    }
    
    /**
     * Abrir nuevo servicio
     */
    public void abrirNuevoServicio()
    {
        System.out.println("abriendo dialogo nuevo servicio");
        servicioNuevo=new Servicios();
        RequestContext.getCurrentInstance().execute("PF('widgetNuevoServicio').show()");
    }   
    
    
    /**
     * 
     */
    public void guardarServicio()
    {
        System.out.println("grabando el servicio nuevo");
        servicioServicios.grabar(servicioNuevo);
        RequestContext.getCurrentInstance().execute("PF('widgetNuevoServicio').hide()"); 
        serviciosList=servicioServicios.obtenerTodos();
        
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
    
    
    
}
