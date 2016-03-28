/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ec.com.codesoft.web.empleado.ordenTrabajo;

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

    @PostConstruct
    public void postConstruct() {
        ordenTrabajoList = ordenTrabajoServicio.obtenerPorFechaIngreso("ASC");
        System.out.println(ordenTrabajoList.size());
        tipoFiltro="ingreso";
        tipoOrden="ASC";
    }

    /**
     * Realiza el filtro por un campo y realiza el ordenamiento
     */
    public void filtrar() 
    {
        System.out.println("tipo:"+tipoFiltro);
        switch (tipoFiltro) {
            case "entrega":
                ordenTrabajoList=ordenTrabajoServicio.obtenerPorFechaSalida(tipoOrden);
                break;

            case "ingreso":
                ordenTrabajoList=ordenTrabajoServicio.obtenerPorFechaIngreso(tipoOrden);
                break;
                
            case "precio":
                ordenTrabajoList=ordenTrabajoServicio.obtenerPorPrecio(tipoOrden);
                break;

        }
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

}
