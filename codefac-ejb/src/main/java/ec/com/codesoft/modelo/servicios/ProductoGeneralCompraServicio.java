/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package ec.com.codesoft.modelo.servicios;

import ec.com.codesoft.model.ProductoGeneralCompra;
import ec.com.codesoft.modelo.facade.ProductoGeneralCompraFacade;
import javax.ejb.EJB;
import javax.ejb.LocalBean;
import javax.ejb.Stateless;

/**
 *
 * @author Suco
 */
@LocalBean
@Stateless
public class ProductoGeneralCompraServicio {
    
     @EJB
     ProductoGeneralCompraFacade productoFacade;
     
     
     public void insertar(ProductoGeneralCompra distribuidor) {
        this.productoFacade.create(distribuidor);
    }

    public void actualizar(ProductoGeneralCompra distribuidor) {
        productoFacade.edit(distribuidor);
    }

    public void eliminar(ProductoGeneralCompra distribuidor) {
        productoFacade.remove(distribuidor);
    }


    
}
