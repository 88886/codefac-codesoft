/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ec.com.codesoft.web.operador.util;

import ec.com.codesoft.web.admin.util.*;
import ec.com.codesoft.web.widget.CommonWidGet;
import ec.com.codesoft.web.widget.VentasDiariasMB;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import javax.annotation.PostConstruct;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ManagedProperty;
import javax.faces.bean.SessionScoped;
import javax.faces.bean.ViewScoped;
import org.primefaces.context.RequestContext;

/**
 *
 * @author carlo
 */
@ManagedBean
@SessionScoped
public class menuRapidoOperadorMB  implements Serializable{
    
    private List<CommonWidGet> listaWidGet;

    @ManagedProperty(value = "#{calcuUtilMB}")
    private CalcuUtilMB calcuUtilMB;

    @ManagedProperty(value = "#{notaUtilMB}")
    private NotaUtilMB notaUtilMB;
    
    @ManagedProperty(value = "#{ventasDiariasMB}")
    private VentasDiariasMB ventasDiariasMB;

    @PostConstruct
    public void postConstruct() {
        listaWidGet = new ArrayList<CommonWidGet>();
       
        listaWidGet.add(notaUtilMB);
        listaWidGet.add(calcuUtilMB);
        listaWidGet.add(ventasDiariasMB);
       
       
    }

    /**
     * Me permite abrir los WidGet que se encuentren abiertos
     */
    public void iniciarWidget() {
         ventasDiariasMB.inicializar();
        for (CommonWidGet widGet : listaWidGet) {
            widGet.initDialogo();
        }
    }

    //abre los widgets
    public void abrirWidget(String textWidGet) {
        for (CommonWidGet widGet : listaWidGet) {
            if (widGet.getNameVar().equals(textWidGet)) {
                listaWidGet.remove(widGet);
                listaWidGet.add(widGet);
                widGet.abrirDialogo();
            }
        }
    }
    
    
    
    
    
    ///////////////////////METODOS GET AND SET/////////////////////

    public CalcuUtilMB getCalcuUtilMB() {
        return calcuUtilMB;
    }

    public void setCalcuUtilMB(CalcuUtilMB calcuUtilMB) {
        this.calcuUtilMB = calcuUtilMB;
    }

    public NotaUtilMB getNotaUtilMB() {
        return notaUtilMB;
    }

    public void setNotaUtilMB(NotaUtilMB notaUtilMB) {
        this.notaUtilMB = notaUtilMB;
    }

    public VentasDiariasMB getVentasDiariasMB() {
        return ventasDiariasMB;
    }

    public void setVentasDiariasMB(VentasDiariasMB ventasDiariasMB) {
        this.ventasDiariasMB = ventasDiariasMB;
    }
    
}