/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ec.com.codesoft.web.admin;

import ec.com.codesoft.model.Cliente;
import ec.com.codesoft.model.Usuario;
import ec.com.codesoft.modelo.servicios.UsuarioServicio;
import java.io.Serializable;
import javax.annotation.PostConstruct;
import javax.ejb.EJB;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;
import javax.faces.context.FacesContext;
import javax.servlet.http.HttpServletRequest;
import org.primefaces.context.RequestContext;

/**
 *
 * @author Suco
 */
@ViewScoped
@ManagedBean
public class crearUsuario implements Serializable {

    private Usuario usuario;

    @EJB
    private UsuarioServicio usuarioServicio;

    @PostConstruct
    public void postConstruct() {
        this.usuario = new Usuario();
        FacesContext context = FacesContext.getCurrentInstance();
        HttpServletRequest request = (HttpServletRequest) context.getExternalContext().getRequest();
        String nick = request.getParameter("nick");
        usuario.setNick(nick);
    }

    public void cancelar() {
        System.out.println("Cancelando");
        RequestContext.getCurrentInstance().closeDialog(null);
    }

    public void guardar() {
        usuarioServicio.insertarUsuario(usuario);
        RequestContext.getCurrentInstance().closeDialog(usuario);
    }

    public Usuario getUsuario() {
        return usuario;
    }

    public void setUsuario(Usuario usuario) {
        this.usuario = usuario;
    }

}
