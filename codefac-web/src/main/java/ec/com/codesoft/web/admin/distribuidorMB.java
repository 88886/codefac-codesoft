/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ec.com.codesoft.web.admin;

import ec.com.codesoft.model.Distribuidor;
import ec.com.codesoft.modelo.servicios.DistribuidorServicio;
import java.awt.Event;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;
import javax.annotation.PostConstruct;
import javax.ejb.EJB;
import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;
import javax.faces.context.FacesContext;
import javax.faces.model.SelectItem;
import javax.faces.model.SelectItemGroup;
import org.primefaces.context.RequestContext;
import org.primefaces.event.SelectEvent;

/**
 *
 * @author Suco
 */
@ManagedBean
@ViewScoped
public class distribuidorMB implements Serializable {

    //Distribuidors
    private List<Distribuidor> distribuidores;
    private Distribuidor distribuidorSeleccionado;
    
    private Distribuidor distribuidor;
    //Banderas auxliares
    private Boolean flagBoton1;
    private Boolean enModificar;

    //opciones de las Lista
    private List<SelectItem> tipoPagos;
    private String selection;
    private String pagoDistribuidor;

    @EJB
    private DistribuidorServicio distribuidorServicio;

    @PostConstruct
    public void inicializar() {

        System.err.println("Entrando A Inicializar");
        distribuidores = distribuidorServicio.obtenerTodos();
        System.out.println("Distri:" + distribuidores);
        distribuidor = new Distribuidor();
        flagBoton1 = true;
        enModificar = false;
        pagoDistribuidor="";
        //incializar las opciones de tipos de pagos
        tipoPagos = new ArrayList<SelectItem>();
        SelectItemGroup pagoEfectivo = new SelectItemGroup("Efectivo");
        SelectItemGroup pagoCredito = new SelectItemGroup("Crédito");

        SelectItemGroup Tresmeses = new SelectItemGroup("3 Meses");
        SelectItemGroup Seismeses = new SelectItemGroup("6 Meses");
        SelectItemGroup Nuevemeses = new SelectItemGroup("9 Meses");
        SelectItemGroup DoceMeses = new SelectItemGroup("12 Meses");

        pagoCredito.setSelectItems(new SelectItem[]{Tresmeses, Seismeses, Nuevemeses, DoceMeses});
        tipoPagos.add(pagoEfectivo);
        tipoPagos.add(pagoCredito);

    }

//    public void onRowSelect(SelectEvent event) {
//        flagBoton1 = false;
//        distribuidor = distribuidorSeleccionado;
//        System.out.println(distribuidorSeleccionado.getNombres());
//    }
    public void onRowSelect(SelectEvent event) {
        FacesMessage msg = new FacesMessage("Distribuidor Seleccionado", ((Distribuidor) event.getObject()).getNombre());
        FacesContext.getCurrentInstance().addMessage(null, msg);
        flagBoton1 = false;
        
        //distribuidor = distribuidorSeleccionado;
        System.out.println("->"+distribuidorSeleccionado.getNombre());

    }

    public void onRowUnSelect(SelectEvent event) {
        flagBoton1 = true;
        distribuidorSeleccionado = new Distribuidor();
        System.out.println("Deseleccionado");
        distribuidor = new Distribuidor();
    }

    public void enModificar() 
    {

        System.out.println("Modificando y Crear...");
        enModificar = true;
        //distribuidor = distribuidorSeleccionado;
    }

    public void enCrear() {
        enModificar = false;
    }

    public void registarDistribuidor(Event e) {
        if (enModificar) {
            distribuidor.setTipoPago(pagoDistribuidor);// selection = tipo de pago de la lista
            System.out.println("Tipo de Pago :" + pagoDistribuidor);
            distribuidorServicio.actualizar(distribuidor);
            distribuidor = new Distribuidor();
            distribuidores = distribuidorServicio.obtenerTodos();
            flagBoton1 = true;
        } else {
            distribuidor.setEstado('A');// A: distribuidor Activo
            distribuidor.setTipoPago(pagoDistribuidor);// selection = tipo de pago de la lista
            System.out.println("Tipo de Pago :" + pagoDistribuidor);
            distribuidorServicio.insertar(distribuidor);
            System.out.println("Guardando Distribuidor");
            distribuidor = new Distribuidor();
            distribuidores = distribuidorServicio.obtenerTodos();
            flagBoton1 = true;
        }
    }

    public void cancelar() {
        distribuidor = new Distribuidor();
    }

    public void verificarDistribuidorExiste() {
        if (distribuidorServicio.verificarExistencia(distribuidor.getRuc())) {
            FacesMessage message = new FacesMessage(FacesMessage.SEVERITY_INFO, "El distribuidor ya existe", "!porfavor revise los datos!");
            RequestContext.getCurrentInstance().showMessageInDialog(message);

        }

    }

    public boolean filterByName(Object value, Object filter, Locale locale) {
        String filterText = (filter == null) ? null : filter.toString().trim();
        if (filterText == null || filterText.equals("")) {
            return true;
        }

        if (value == null) {
            return false;
        }

        String carName = value.toString().toUpperCase();
        filterText = filterText.toUpperCase();

        if (carName.contains(filterText)) {
            return true;
        } else {
            return false;
        }
    }

    public List<Distribuidor> getDistribuidores() {
        return distribuidores;
    }

    public void setDistribuidores(List<Distribuidor> distribuidores) {
        this.distribuidores = distribuidores;
    }

    public Distribuidor getDistribuidorSeleccionado() {
        return distribuidorSeleccionado;
    }

    public void setDistribuidorSeleccionado(Distribuidor distribuidorSeleccionado) {
        this.distribuidorSeleccionado = distribuidorSeleccionado;
    }

    public Distribuidor getDistribuidor() {
        return distribuidor;
    }

    public void setDistribuidor(Distribuidor distribuidor) {
        this.distribuidor = distribuidor;
    }

    public Boolean getFlagBoton1() {
        return flagBoton1;
    }

    public void setFlagBoton1(Boolean flagBoton1) {
        this.flagBoton1 = flagBoton1;
    }

    public Boolean getEnModificar() {
        return enModificar;
    }

    public void setEnModificar(Boolean enModificar) {
        this.enModificar = enModificar;
    }

    public String getSelection() {
        return selection;
    }

    public void setSelection(String selection) {
        this.selection = selection;
    }

    public List<SelectItem> getTipoPagos() {
        return tipoPagos;
    }

    public void setTipoPagos(List<SelectItem> tipoPagos) {
        this.tipoPagos = tipoPagos;
    }

    public String getPagoDistribuidor() {
        return pagoDistribuidor;
    }

    public void setPagoDistribuidor(String pagoDistribuidor) {
        this.pagoDistribuidor = pagoDistribuidor;
    }
    
    

}
