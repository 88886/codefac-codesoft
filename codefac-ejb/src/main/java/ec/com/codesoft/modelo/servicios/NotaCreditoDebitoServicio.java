/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ec.com.codesoft.modelo.servicios;

import ec.com.codesoft.model.NotaCreditoDebito;
import ec.com.codesoft.modelo.facade.NotaCreditoDebitoFacade;
import javax.ejb.EJB;
import javax.ejb.LocalBean;
import javax.ejb.Stateless;

/**
 *
 * @author carlo
 */
@LocalBean
@Stateless
public class NotaCreditoDebitoServicio 
{
    @EJB
    private NotaCreditoDebitoFacade notaFacade;
    
    public void grabarNotaCredito(NotaCreditoDebito nota)
    {
        notaFacade.create(nota);
    }
}
