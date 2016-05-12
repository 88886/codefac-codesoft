/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ec.com.codesoft.web.seguridad;

import ec.com.codesoft.model.Configuracion;
import ec.com.codesoft.model.Empresa;
import ec.com.codesoft.modelo.servicios.SistemaServicio;
import java.io.Serializable;
import javax.annotation.PostConstruct;
import javax.ejb.EJB;
import javax.faces.application.FacesMessage;
import javax.faces.bean.ApplicationScoped;
import javax.faces.bean.ManagedBean;
import org.primefaces.context.RequestContext;

/**
 *
 * @author carlo
 */
@ApplicationScoped
@ManagedBean
public class SistemaMB implements Serializable {

    /**
     * Configuracion del sistema
     */
    @EJB
    private SistemaServicio sistemaServicio;

    private Configuracion configuracion;

    private Empresa empresa;

    @PostConstruct
    public void init() {
        configuracion = sistemaServicio.getConfiguracion();
        empresa = sistemaServicio.getEmpresa();
    }

    public void guardarEmpresa() {
        sistemaServicio.editarEmpresa(empresa);
        FacesMessage message = new FacesMessage(FacesMessage.SEVERITY_INFO, "Información", "Datos guardados correctamente");
        RequestContext.getCurrentInstance().showMessageInDialog(message);
    }

    public void guardarConfiguracion() {
        sistemaServicio.editarConfiguracion(configuracion);
        FacesMessage message = new FacesMessage(FacesMessage.SEVERITY_INFO, "Información", "Datos guardados correctamente");
        RequestContext.getCurrentInstance().showMessageInDialog(message);
    }

    public Configuracion getConfiguracion() {
        return sistemaServicio.getConfiguracion();
    }

    public void setConfiguracion(Configuracion configuracion) {
        this.configuracion = configuracion;
    }

    public Empresa getEmpresa() {
        return empresa;
    }

    public void setEmpresa(Empresa empresa) {
        this.empresa = empresa;
    }

}
