/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ec.com.codesoft.web.admin.util;

import ec.com.codesoft.web.widget.CommonWidGet;
import java.io.Serializable;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.annotation.PostConstruct;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;
import javax.script.ScriptEngine;
import javax.script.ScriptEngineManager;
import javax.script.ScriptException;

/**
 *
 * @author carlo
 */
@ManagedBean
@SessionScoped
public class CalcuUtilMB extends CommonWidGet implements Serializable {

    private String textoCalculadora;
    
    private Object resultado;

//    @ManagedProperty(value = "#{menuRapidoAdminMB}")
//    private MenuRapidoAdminMB menuMB;
    @PostConstruct
    public void postConstuct() {
        setX(8);
        setY(120);

        setNameVar("dlgCalculadora");

    }

    /**
     * Funcion que me permite evluar la expresion para la calcluladora
     */
    public void evaluar() {
        ScriptEngineManager manager = new ScriptEngineManager();
        ScriptEngine engine = manager.getEngineByName("js");
        
        try {
            Object resultado=engine.eval(textoCalculadora);
            this.resultado=resultado;
            
        } catch (ScriptException ex) {
            Logger.getLogger(CalcuUtilMB.class.getName()).log(Level.SEVERE, null, ex);
        }
        
        
        
    }
    
    /**
     * Agrega un caracter a la cadena de evalucacion por cada tecla
     */
    public void presionarTecla(String tecla)
    {
        this.textoCalculadora+=tecla;
    }

    public String getTextoCalculadora() {
        return textoCalculadora;
    }

    public void setTextoCalculadora(String textoCalculadora) {
        this.textoCalculadora = textoCalculadora;
    }
    
    

//    public MenuRapidoAdminMB getMenuMB() {
//        return menuMB;
//    }
//
//    public void setMenuMB(MenuRapidoAdminMB menuMB) {
//        this.menuMB = menuMB;
//    }

    public Object getResultado() {
        return resultado;
    }

    public void setResultado(Object resultado) {
        this.resultado = resultado;
    }
}
