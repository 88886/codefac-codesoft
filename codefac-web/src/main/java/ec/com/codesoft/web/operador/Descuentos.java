/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ec.com.codesoft.web.operador;

import java.math.BigDecimal;

/**
 *
 * @author Suco
 */
public class Descuentos {
    private String nombre;
    private BigDecimal valor;

    public Descuentos(){}
    public Descuentos(String nombre, BigDecimal valor) {
        this.nombre = nombre;
        this.valor = valor;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public BigDecimal getValor() {
        return valor;
    }

    public void setValor(BigDecimal valor) {
        this.valor = valor;
    }
    
    
    
    
    
    
    
    
}
