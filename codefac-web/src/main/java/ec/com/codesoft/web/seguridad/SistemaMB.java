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
import javax.validation.constraints.Pattern;
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
    private Configuracion configuracionEnviar;
    

    private Empresa empresa;
    
    
    
//    private  BigDecimal iva;
//    private String path;
//    private int maxItemsF;
//    private int maxItemsN;
//    private int maxItemsO;
//    private String mail;
//    private String clave;
    

    @PostConstruct
    public void init() {
        configuracion = sistemaServicio.getConfiguracion();
        empresa = sistemaServicio.getEmpresa();
        configuracionEnviar=new Configuracion();
        configuracionEnviar=configuracion;
//        iva=configuracion.getIva();
//        path=configuracion.getPathreportes();
//        maxItemsF=configuracion.getMaxItemFactura();
//        maxItemsN=configuracion.getMaxItemNota();
//        maxItemsO=configuracion.getMaxItemNota();
//        mail=configuracion.getEmailServicioTecnico();
//        clave=configuracion.getClaveEmailServicioTecnico();
    }

    public void guardarEmpresa() {
        sistemaServicio.editarEmpresa(empresa);
        FacesMessage message = new FacesMessage(FacesMessage.SEVERITY_INFO, "Información", "Datos guardados correctamente");
        RequestContext.getCurrentInstance().showMessageInDialog(message);
    }

    public void guardarConfiguracion() {
      
        sistemaServicio.editarConfiguracion(configuracionEnviar);        
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

    public Configuracion getConfiguracionEnviar() {
        return configuracionEnviar;
    }

    public void setConfiguracionEnviar(Configuracion configuracionEnviar) {
        this.configuracionEnviar = configuracionEnviar;
    }

    
    

}
