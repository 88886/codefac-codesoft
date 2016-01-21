/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package ec.com.codesoft.modelo.servicios;

import ec.com.codesoft.model.CatalagoProducto;
import ec.com.codesoft.modelo.facade.CatalagoProductoFacade;
import java.util.List;
import javax.ejb.EJB;
import javax.ejb.LocalBean;
import javax.ejb.Stateless;

/**
 *
 * @author Suco
 */
@LocalBean 
@Stateless  
public class CatalogoServicio {
    @EJB
    private CatalagoProductoFacade catalogoFacade;
    
    public void insertar(CatalagoProducto catalogo){
        this.catalogoFacade.create(catalogo);
    }
    
    public void actualizar(CatalagoProducto catalogo){
        catalogoFacade.edit(catalogo);
    } 
    
    public void eliminar(CatalagoProducto catalogo){
        catalogoFacade.remove(catalogo);
    }
    
    public List<CatalagoProducto> obtenerTodos(){
        
        return catalogoFacade.findAll();
    }
    
    public CatalagoProducto buscarCatalogo(String codigoP) {       
        return catalogoFacade.findCatalogo(codigoP);
       
    }
    
    
}
