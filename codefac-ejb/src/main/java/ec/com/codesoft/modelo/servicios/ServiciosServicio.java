/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ec.com.codesoft.modelo.servicios;

import ec.com.codesoft.model.CategoriaTrabajo;
import ec.com.codesoft.model.Servicios;
import ec.com.codesoft.modelo.facade.CategoriaTrabajoFacade;
import ec.com.codesoft.modelo.facade.ServiciosFacade;
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
public class ServiciosServicio 
{
    @EJB
    private ServiciosFacade servicioFacade;
    
    @EJB
    private CategoriaTrabajoFacade categoriaTrabajoFacade;
    
    /***
     * Obtiene todos los servicios
     */
    public List<Servicios> obtenerTodos()
    {
        return servicioFacade.findAll();
    }
    
    /**
     * Graba los servicios
     */
    public void grabar(Servicios servicios)
    {
        servicioFacade.create(servicios);
    }
    
    public void editar(Servicios servicio)
    {
        servicioFacade.edit(servicio);
    }
    
    public void eliminar(Servicios servicio)
    {
        servicioFacade.remove(servicio);
    }
    
    public Servicios buscarPorID(Integer codigo)
    {
        return servicioFacade.find(codigo);
    }
    
    public List<CategoriaTrabajo> obtenerCategoriasTrabajo(Integer codServicio){
        return servicioFacade.getCatTrabajo(codServicio);
    }
    
    public void guardarCategoria(CategoriaTrabajo categoria){
        categoriaTrabajoFacade.create(categoria);
    }
    public void editarCategoria(CategoriaTrabajo categoria){
        
        categoriaTrabajoFacade.edit(categoria);
    }
    public void eliminarCategoria(CategoriaTrabajo categoria){
        categoriaTrabajoFacade.remove(categoria);
    }
}
