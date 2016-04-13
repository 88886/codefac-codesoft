/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ec.com.codesoft.web.operador.compra;

import ec.com.codesoft.model.Distribuidor;
import ec.com.codesoft.modelo.servicios.DistribuidorServicio;
import ec.com.codesoft.web.operador.comprarMB;
import java.io.Serializable;
import java.util.List;
import java.util.Map;
import javax.annotation.PostConstruct;
import javax.ejb.EJB;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ManagedProperty;
import javax.faces.bean.ViewScoped;
import javax.faces.context.FacesContext;
import javax.servlet.http.HttpServletRequest;
import org.primefaces.context.RequestContext;

/**
 *
 * @author carlo
 */
@ManagedBean
@ViewScoped
public class crearDistribuidorMB implements Serializable {

    private Distribuidor distribuidor;

    @EJB
    private DistribuidorServicio distribuidorServicio;

    @ManagedProperty(value = "#{comprarMB}")
    private comprarMB comprarMB;

    @PostConstruct
    public void postConstruc() {
        distribuidor = new Distribuidor();
        FacesContext context = FacesContext.getCurrentInstance();
        HttpServletRequest request = (HttpServletRequest) context.getExternalContext().getRequest();
        String ruc = request.getParameter("ruc");
        distribuidor.setRuc(ruc);
    }

    public void grabar() {
        System.out.println("grabando distribuidor ...");
        //System.out.println(distribuidor);
        distribuidorServicio.insertar(distribuidor);
        RequestContext.getCurrentInstance().closeDialog(distribuidor);
    }

    public void cancelar() {
        System.out.println("cancelando grabar distribuidor ...");
        //System.out.println(distribuidor);
        
        RequestContext.getCurrentInstance().closeDialog(null);
    }

    public Distribuidor getDistribuidor() {
        return distribuidor;
    }

    public void setDistribuidor(Distribuidor distribuidor) {
        this.distribuidor = distribuidor;
    }

    public comprarMB getComprarMB() {
        return comprarMB;
    }

    public void setComprarMB(comprarMB comprarMB) {
        this.comprarMB = comprarMB;
    }

}
