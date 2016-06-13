/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ec.com.codesoft.web.admin.util;

import ec.com.codesoft.modelo.servicios.CatalogoServicio;
import ec.com.codesoft.web.widget.CommonWidGet;
import ec.com.codesoft.web.widget.VentasDiariasMB;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.CopyOnWriteArrayList;
import javax.annotation.PostConstruct;
import javax.ejb.EJB;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ManagedProperty;
import javax.faces.bean.SessionScoped;

/**
 *
 * @author carlo
 */
@ManagedBean
@SessionScoped
public class StackAdminMB implements Serializable {

    private CopyOnWriteArrayList<CommonWidGet> listaWidGet;

    @ManagedProperty(value = "#{calcuUtilMB}")
    private CalcuUtilMB calcuUtilMB;

    @ManagedProperty(value = "#{notaUtilMB}")
    private NotaUtilMB notaUtilMB;

    @ManagedProperty(value = "#{calendarioUtilMB}")
    private CalendarioUtilMB calendarioUtilMB;
    
    @ManagedProperty(value = "#{ventasDiariasMB}")
    private VentasDiariasMB ventasDiariasMB;
    
    @EJB
    private CatalogoServicio catalogoServicio;

    @PostConstruct
    public void postConstruct() {
        ventasDiariasMB.setCatalogosLista(catalogoServicio.obtenerTodos());
        listaWidGet = new CopyOnWriteArrayList<CommonWidGet>();
        listaWidGet.add(notaUtilMB);
        listaWidGet.add(calcuUtilMB);
        listaWidGet.add(calendarioUtilMB);
        listaWidGet.add(ventasDiariasMB);
    }

    /**
     * Me permite abrir los WidGet que se encuentren abiertos
     */
    public void iniciarWidget() {
        for (CommonWidGet widGet : listaWidGet) {
            widGet.initDialogo();
        }
    }

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

    public CalendarioUtilMB getCalendarioUtilMB() {
        return calendarioUtilMB;
    }

    public void setCalendarioUtilMB(CalendarioUtilMB calendarioUtilMB) {
        this.calendarioUtilMB = calendarioUtilMB;
    }

    public VentasDiariasMB getVentasDiariasMB() {
        return ventasDiariasMB;
    }

    public void setVentasDiariasMB(VentasDiariasMB ventasDiariasMB) {
        this.ventasDiariasMB = ventasDiariasMB;
    }
    
    
}
