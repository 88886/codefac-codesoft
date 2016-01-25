/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ec.com.codesoft.modelo.servicios;

import ec.com.codesoft.model.ProductoGeneralCompra;
import ec.com.codesoft.modelo.facade.ProductoGeneralCompraFacade;
import ec.com.codesoft.modelo.facade.ProductoIndividualCompraFacade;
import javax.ejb.EJB;
import javax.ejb.LocalBean;
import javax.ejb.Stateless;

/**
 *
 * @author Suco
 */
@LocalBean
@Stateless
public class FacturaServicio {

    @EJB
    ProductoIndividualCompraFacade productoIndividualFacade;
    
     @EJB
    ProductoGeneralCompraFacade productoGeneralFacade;

    public int devolverStockIndividual(String codP) {
        return (productoIndividualFacade.findStockIndividual(codP).intValue());
    }
    
    public ProductoGeneralCompra devolverStockGeneral(String codP){
        
        return productoGeneralFacade.findGeneralCodP(codP);
    }

}
