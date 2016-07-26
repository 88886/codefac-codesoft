/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ec.com.codesoft.web.util;

import ec.com.codesoft.model.CatalagoProducto;
import ec.com.codesoft.model.ProductoGeneralVenta;
import ec.com.codesoft.modelo.servicios.FacturaServicio;
import ec.com.codesoft.web.seguridad.SistemaMB;
import java.io.Serializable;
import java.math.BigDecimal;
import java.math.RoundingMode;
import javax.ejb.EJB;
import javax.faces.bean.ApplicationScoped;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ManagedProperty;
import javax.faces.bean.RequestScoped;

/**
 *
 * @author carlo
 */
@ApplicationScoped
@ManagedBean
public class CalculosMB implements Serializable {

    @ManagedProperty(value = "#{sistemaMB}")
    /**
     * Variable para obtener las variables del sistema
     */
    private SistemaMB sistemaMB;
    @EJB
    FacturaServicio facturaServicio;

    /**
     * Metodo que me permite agregar el precio a un valor
     *
     * @return
     */
    public BigDecimal incluirIva(BigDecimal valor) {
        if (valor != null) {
            BigDecimal iva = sistemaMB.getConfiguracion().getIva().divide(new BigDecimal("100"), RoundingMode.DOWN).add(new BigDecimal("1"));
            BigDecimal respuesta = valor.multiply(iva);
            respuesta = respuesta.setScale(2, BigDecimal.ROUND_UP);
            return respuesta;
        } else {
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

    public SistemaMB getSistemaMB() {
        return sistemaMB;
    }

    public void setSistemaMB(SistemaMB sistemaMB) {
        this.sistemaMB = sistemaMB;
    }

}
