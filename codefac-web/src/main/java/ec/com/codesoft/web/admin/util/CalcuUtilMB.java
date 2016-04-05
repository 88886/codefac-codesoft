/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ec.com.codesoft.web.admin.util;

import ec.com.codesoft.web.widget.CommonWidGet;
import java.io.Serializable;
import java.util.List;
import java.util.concurrent.CopyOnWriteArrayList;
import javax.annotation.PostConstruct;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ManagedProperty;
import javax.faces.bean.SessionScoped;

/**
 *
 * @author carlo
 */
@ManagedBean
@SessionScoped
public class CalcuUtilMB extends CommonWidGet implements Serializable {

    private String textoCalculadora;

//    @ManagedProperty(value = "#{menuRapidoAdminMB}")
//    private MenuRapidoAdminMB menuMB;

    @PostConstruct
    public void postConstuct() {
        setX(8);
        setY(120);
        setNameVar("dlgCalculadora");

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

    


}
