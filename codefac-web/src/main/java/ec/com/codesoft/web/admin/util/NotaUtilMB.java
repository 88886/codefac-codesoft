/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ec.com.codesoft.web.admin.util;

import ec.com.codesoft.modelo.servicios.UsuarioServicio;
import ec.com.codesoft.web.seguridad.SessionMB;
import ec.com.codesoft.web.widget.CommonWidGet;
import java.io.Serializable;
import java.util.List;
import java.util.concurrent.CopyOnWriteArrayList;
import javax.annotation.PostConstruct;
import javax.ejb.EJB;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ManagedProperty;
import javax.faces.bean.SessionScoped;
import javax.faces.event.ActionEvent;
import javax.faces.event.AjaxBehaviorEvent;
import org.primefaces.context.RequestContext;

/**
 *
 * @author carlo
 */
@ManagedBean
@SessionScoped
public class NotaUtilMB extends CommonWidGet implements Serializable {

    /**
     * Texto que se carga en las notas
     */
    private String mensaje;

    @EJB
    private UsuarioServicio usuarioServicio;

    @ManagedProperty(value = "#{sessionMB}")
    private SessionMB sesion;
//
//    @ManagedProperty(value = "#{menuRapidoAdminMB}")
//    private MenuRapidoAdminMB menuMB;

    @PostConstruct
    public void postConstuct() {
        setX(8);
        setY(120);
        
        setNameVar("dlgNotas");
    }

//    ////////////////////// METODOS IMPLEMENTADOS////////////
//    @Override
//    protected CopyOnWriteArrayList<CommonWidGet> getListHandle() {
//        return menuMB.getListaActualizar();
//    }

//    @Override
//    public void abrirDialogo() {
//        super.abrirDialogo(); //To change body of generated methods, choose Tools | Templates.
//        menuMB.setExpandido(false);
//    }
    /**
     * Graba la nota en el disco
     */
    public void grabarNota() 
    {
        System.out.println("editando la nota");
        usuarioServicio.editar(sesion.getUsuarioLogin());
    }

    public SessionMB getSesion() {
        return sesion;
    }

    public void setSesion(SessionMB sesion) {
        this.sesion = sesion;
    }

//    public MenuRapidoAdminMB getMenuMB() {
//        return menuMB;
//    }
//
//    public void setMenuMB(MenuRapidoAdminMB menuMB) {
//        this.menuMB = menuMB;
//    }
//    
}
