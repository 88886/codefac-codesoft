/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ec.com.codesoft.web.admin.ordenTrabajo;

import ec.com.codesoft.model.OrdenTrabajo;
import ec.com.codesoft.modelo.servicios.OrdenTrabajoServicio;
import java.io.Serializable;
import java.util.List;
import javax.annotation.PostConstruct;
import javax.ejb.EJB;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;

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
    
    
    @PostConstruct
    public void postCostruct()
    {
        ordenTrabajoList=ordenTrabajoServicio.obtenerOrdenesTrabajo();
    }
    
    public void imprimir(OrdenTrabajo ordenTrabajo)
    {
        
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
    
    
    
}
