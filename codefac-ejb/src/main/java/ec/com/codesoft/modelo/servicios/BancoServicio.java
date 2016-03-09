/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ec.com.codesoft.modelo.servicios;

import ec.com.codesoft.model.Creditobanco;
import ec.com.codesoft.modelo.facade.CreditobancoFacade;
import javax.ejb.EJB;
import javax.ejb.LocalBean;
import javax.ejb.Stateless;

/**
 *
 * @author Suco
 */
@LocalBean
@Stateless
public class BancoServicio {
    @EJB
    CreditobancoFacade creditoBancoFacade;
    
    
    public void guardarCreditoBanco(Creditobanco credito){
        creditoBancoFacade.create(credito);
    }
}
