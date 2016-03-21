/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ec.com.codesoft.modelo.servicios;

import ec.com.codesoft.model.DetalleOrdenTrabajo;
import ec.com.codesoft.model.OrdenTrabajo;
import ec.com.codesoft.model.Usuario;
import ec.com.codesoft.modelo.facade.DetalleOrdenTrabajoFacade;
import ec.com.codesoft.modelo.facade.OrdenTrabajoFacade;
import ec.com.codesoft.modelo.facade.UsuarioFacade;
import java.util.List;
import javax.ejb.EJB;
import javax.ejb.LocalBean;
import javax.ejb.Stateless;

/**
 *
 * @author carlo
 */
@Stateless
@LocalBean
public class OrdenTrabajoServicio 
{
    @EJB
    private OrdenTrabajoFacade  ordenTrabajoFacade;
    
    @EJB
    private DetalleOrdenTrabajoFacade detalleOrdenTrabajoFacade;
    
    @EJB
    private UsuarioFacade usuarioFacade;
    
    
    
    public void grabar(OrdenTrabajo ordenTrabajo)
    {
        ordenTrabajoFacade.create(ordenTrabajo);
        
        Integer id=ordenTrabajo.getIdOrdenTrabajo();
        
        List<DetalleOrdenTrabajo> lista=ordenTrabajo.getDetalleOrdenTrabajoList();
        
        for (DetalleOrdenTrabajo detalle : lista) 
        {
            detalle.setIdOrdenTrabajo(ordenTrabajo);
            detalleOrdenTrabajoFacade.edit(detalle);
        }
    }
    
    /**
     * Obtiene todos los usuarios con el perfil de empleados
     */
    public List<Usuario> obtenerEmpleados()
    {
        List<Usuario> empleados=usuarioFacade.getUsuariosPorPerfil("empleado");
        return empleados;
    }
}
