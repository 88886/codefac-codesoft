/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ec.com.codesoft.web.util;

import java.io.Serializable;
import java.math.BigDecimal;
import java.math.RoundingMode;
import javax.faces.bean.ApplicationScoped;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.RequestScoped;

/**
 *
 * @author carlo
 */
@ApplicationScoped
@ManagedBean
public class CalculosMB implements Serializable {

    /**
     * Metodo que me permite agregar el precio a un valor
     *
     * @return
     */
    public BigDecimal incluirIva(BigDecimal valor) {
        if (valor != null) {
            BigDecimal respuesta = valor.multiply(new BigDecimal("1.12"));
            respuesta = respuesta.setScale(2, BigDecimal.ROUND_UP);
            return respuesta;
        }
        else
        {
            return null;
        }
        
        
    }

    /**
     *
     * @param valor
     * @return
     */
    public BigDecimal redondeoSuperior(BigDecimal valor) {
        if (valor != null) {
            BigDecimal respuesta = valor.setScale(2, RoundingMode.UP);
            return respuesta;
        }
        return new BigDecimal(0);
    }

    public BigDecimal redondeoInferior(BigDecimal valor) {
        if (valor != null) {
            BigDecimal respuesta = valor.setScale(4, RoundingMode.DOWN);
            return respuesta;
        }
        return new BigDecimal(0);
    }
    
     public BigDecimal redondeoInferiorMostar(BigDecimal valor) {
        if (valor != null) {
            BigDecimal respuesta = valor.setScale(2, RoundingMode.DOWN);
            return respuesta;
        }
        return new BigDecimal(0);
    }

}
