/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ec.com.codesoft.modelo.servicios;

import ec.com.codesoft.model.ComboProducto;
import ec.com.codesoft.model.DetalleComboProducto;
import ec.com.codesoft.modelo.facade.ComboProductoFacade;
import ec.com.codesoft.modelo.facade.DetalleComboProductoFacade;
import java.io.Serializable;
import java.util.List;
import javax.ejb.EJB;
import javax.ejb.LocalBean;
import javax.ejb.Stateless;

/**
 *
 * @author carlo
 */
@LocalBean
@Stateless
public class ComboProductoServicio
{
    @EJB
    private ComboProductoFacade comboProductoFacade;
    
    @EJB
    private DetalleComboProductoFacade detalleComboProductoFacade;
    
    /**
     * Obtiene todos los combos
     */
    public List<ComboProducto> obtenerTodos()
    {
        return comboProductoFacade.findAll();
    }
    
    /**
     * Me permite grabar un combo de productos nuevo
     */
    public void grabar(ComboProducto comboProducto)
    {
        comboProductoFacade.create(comboProducto);
        Integer combo=comboProducto.getIdComboProducto();
        List<DetalleComboProducto> lista= comboProducto.getDetalleComboProductoCollection();
        for (DetalleComboProducto detalle : lista) 
        {
            detalle.setIdComboProducto(comboProducto);
            detalleComboProductoFacade.edit(detalle);
        }
    }
    
}
