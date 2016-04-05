/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ec.com.codesoft.modelo.servicios;

import ec.com.codesoft.model.Usuario;
import ec.com.codesoft.modelo.facade.UsuarioFacade;
import java.util.List;
import javax.ejb.EJB;
import javax.ejb.LocalBean;
import javax.ejb.Stateless;

/**
 *
 * @author Suco
 */
@Stateless
@LocalBean
public class UsuarioServicio {
    
    @EJB
    private UsuarioFacade usuarioFacade;
        
    public List<Usuario> devolverUsuarios()
    {
        return usuarioFacade.findAll();
    }
    
    public void insertarUsuario(Usuario usuario)
    {
        usuarioFacade.create(usuario);
    }
    public void editar(Usuario usuario)
    {
        usuarioFacade.edit(usuario);
    }
    
    
 }
