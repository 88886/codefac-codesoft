/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ec.com.codesoft.web.admin.distribuidor;

import ec.com.codesoft.model.CatalagoProducto;
import ec.com.codesoft.model.Cliente;
import ec.com.codesoft.model.Distribuidor;
import ec.com.codesoft.modelo.servicios.DistribuidorServicio;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
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
 * @author carlo
 */
@ManagedBean
@ViewScoped
public class DistribuidorGestionMB implements Serializable {

    @EJB
    private DistribuidorServicio distribuidorServicio;

    private Distribuidor distribuidor;

    private Distribuidor distribuidorSeleccionado;

    private List<Distribuidor> listaDistribuidor;

    //opciones de las Lista
    private List<SelectItem> tipoPagos;

    private boolean dialogoNuevoAbierto;
    private boolean dialogoEditarAbierto;

    @PostConstruct
    public void postConstruct() {
        System.out.println("iniciando ...");
        this.listaDistribuidor = distribuidorServicio.obtenerTodos();
        distribuidor = new Distribuidor();

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

        this.dialogoNuevoAbierto = false;
        this.dialogoEditarAbierto = false;

    }

    public void verificarDistribuidorExiste() {
        if (distribuidorServicio.verificarExistencia(distribuidor.getRuc())) {
            FacesMessage message = new FacesMessage(FacesMessage.SEVERITY_INFO, "El distribuidor ya existe", "!porfavor revise los datos!");
            RequestContext.getCurrentInstance().showMessageInDialog(message);
        }

    }

    public void grabar() {
        System.out.println("grabando ...");
        distribuidorServicio.insertar(distribuidor);
        RequestContext.getCurrentInstance().execute("PF('nuevoDistribuidor').hide()");
        distribuidor = new Distribuidor();
        listaDistribuidor = distribuidorServicio.obtenerTodos();
        this.dialogoNuevoAbierto = false;

    }

    public void editar() {
        System.out.println("editando ...");
        distribuidorServicio.actualizar(distribuidorSeleccionado);
        RequestContext.getCurrentInstance().execute("PF('editarDistribuidor').hide()");
        listaDistribuidor = distribuidorServicio.obtenerTodos();
        this.dialogoEditarAbierto = false;
    }

    public void eliminar(Distribuidor distribuidor) {
        distribuidorServicio.eliminar(distribuidor);
        listaDistribuidor = distribuidorServicio.obtenerTodos();
    }

    public void cancelar() {
        System.out.println("cancelando ...");
        RequestContext.getCurrentInstance().execute("PF('nuevoDistribuidor').hide()");
        distribuidor = new Distribuidor();
        distribuidor.setNombre("carlos");
        System.out.println(distribuidor.getNombre() + "<n--");
        System.out.println(distribuidor.getDireccion() + "<d--");
        mostrarMensaje("Cancelado", "El proceso de grabar fue cancelado");
        dialogoNuevoAbierto = false;

    }
    
    public void cancelarEditar()
    {
        System.out.println("cancelando ...");
        RequestContext.getCurrentInstance().execute("PF('editarDistribuidor').hide()");
        mostrarMensaje("Cancelado", "El proceso de editar fue cancelado");
        dialogoEditarAbierto = false;
    }

    public void mostrarNuevoDistribuidor() {
        System.out.println("abriendo el dialogo distribuidor ...");
        distribuidor = new Distribuidor();
        //System.out.println(catalagoProducto);
        RequestContext.getCurrentInstance().execute("PF('nuevoDistribuidor').show()");
        dialogoNuevoAbierto = true;

        //dialogoAbierto = true;
    }

    public void mostrarEditarDistribuidor() {
        System.out.println("abriendo el dialogo para editar distribuidor ...");

        RequestContext.getCurrentInstance().execute("PF('editarDistribuidor').show()");
        dialogoEditarAbierto = true;
    }

    public void mostrarMensaje(String titulo, String mensaje) {
        FacesContext context = FacesContext.getCurrentInstance();
        context.addMessage(null, new FacesMessage(titulo, mensaje));
    }

    /**
     * Verificar el dialogo para mostrar u ocultar los dialogos
     */
    public void verificarDialogo() {
        System.out.println("init ...");
        ///verificar que el dialogo para crear este abierto
        if (dialogoNuevoAbierto) {
            RequestContext.getCurrentInstance().execute("PF('nuevoDistribuidor').show()");
        }
        
        if (dialogoEditarAbierto) {
            RequestContext.getCurrentInstance().execute("PF('editarDistribuidor').show()");
        }

    }

    public void onRowSelect(SelectEvent event) {
        FacesMessage msg = new FacesMessage("Distribuidor Seleccionado", ((Distribuidor) event.getObject()).getNombre());
        this.distribuidorSeleccionado=(Distribuidor) event.getObject();
        FacesContext.getCurrentInstance().addMessage(null, msg);
        System.out.println("fila seleccionada ..");

    }

    /////////////// GET AND SET /////////////////
    public Distribuidor getDistribuidor() {
        return distribuidor;
    }

    public void setDistribuidor(Distribuidor distribuidor) {
        this.distribuidor = distribuidor;
    }

    public Distribuidor getDistribuidorSeleccionado() {
        return distribuidorSeleccionado;
    }

    public void setDistribuidorSeleccionado(Distribuidor distribuidorSeleccionado) {
        this.distribuidorSeleccionado = distribuidorSeleccionado;
    }

    public List<Distribuidor> getListaDistribuidor() {
        return listaDistribuidor;
    }

    public void setListaDistribuidor(List<Distribuidor> listaDistribuidor) {
        this.listaDistribuidor = listaDistribuidor;
    }

    public List<SelectItem> getTipoPagos() {
        return tipoPagos;
    }

    public void setTipoPagos(List<SelectItem> tipoPagos) {
        this.tipoPagos = tipoPagos;
    }

}
