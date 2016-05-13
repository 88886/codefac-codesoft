/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ec.com.codesoft.modelo.servicios;

import ec.com.codesoft.model.CategoriaTrabajo;
import ec.com.codesoft.modelo.facade.CategoriaTrabajoFacade;
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
public class CategoriaServicioServicios {
    
    @EJB
    private CategoriaTrabajoFacade categoriaTrabajoFacade;
    
    public void grabar(CategoriaTrabajo categoria)
    {
        categoriaTrabajoFacade.create(categoria);
    }
    
    public void editar(CategoriaTrabajo categoria)
    {
        categoriaTrabajoFacade.edit(categoria);
    }
    
    public void eliminar(CategoriaTrabajo categoria)
    {
        categoriaTrabajoFacade.remove(categoria);
    }
    
    public List<CategoriaTrabajo> obtenerTodos()
    {
        return this.categoriaTrabajoFacade.findAll();
    }
    
}
