/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ec.com.codesoft.web.seguridad;

import ec.com.codesoft.model.Configuracion;
import ec.com.codesoft.modelo.servicios.SistemaServicio;
import java.io.Serializable;
import javax.annotation.PostConstruct;
import javax.ejb.EJB;
import javax.faces.bean.ApplicationScoped;
import javax.faces.bean.ManagedBean;

/**
 *
 * @author carlo
 */
@ApplicationScoped
@ManagedBean
public class SistemaMB implements Serializable
{
    /**
     * Configuracion del sistema
     */
    @EJB
    private SistemaServicio sistemaServicio;
    
    private Configuracion configuracion;
    
    @PostConstruct
    public void init()
    {
        configuracion=sistemaServicio.getConfiguracion();
    }

    public Configuracion getConfiguracion() 
    {
        return sistemaServicio.getConfiguracion();
    }

    public void setConfiguracion(Configuracion configuracion) 
    {
        this.configuracion = configuracion;
    }
    
    
}
