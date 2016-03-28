/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ec.com.codesoft.web.admin.usuarios;

import ec.com.codesoft.model.Usuario;
import ec.com.codesoft.modelo.servicios.UsuarioServicio;
import java.io.Serializable;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import javax.annotation.PostConstruct;
import javax.ejb.EJB;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;
import org.primefaces.context.RequestContext;

/**
 *
 * @author Suco
 */
@ViewScoped
@ManagedBean
public class usuarioMB implements Serializable {
    
    private List<Usuario> usuarios;
    private Usuario usuarioSeleccionado;
    
    //ejb
    @EJB
    private UsuarioServicio usuarioServicio;
    
    
    @PostConstruct
    public void inicializar(){
        
       usuarios=usuarioServicio.devolverUsuarios();
    }
    
    
    public void crearUsuario(){
    
        Map<String, Object> options = new HashMap<String, Object>();
        options.put("modal", true);
        //options.put("height", 300);
        RequestContext.getCurrentInstance().openDialog("crearUsuario", options, null);
    }
    
    //metodos get y set

    public List<Usuario> getUsuarios() {
        return usuarios;
    }

    public void setUsuarios(List<Usuario> usuarios) {
        this.usuarios = usuarios;
    }

    public Usuario getUsuarioSeleccionado() {
        return usuarioSeleccionado;
    }

    public void setUsuarioSeleccionado(Usuario usuarioSeleccionado) {
        this.usuarioSeleccionado = usuarioSeleccionado;
    }
    
    
    
    
}
