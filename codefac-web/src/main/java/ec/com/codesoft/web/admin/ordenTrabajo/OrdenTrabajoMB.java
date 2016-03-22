/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ec.com.codesoft.web.admin.ordenTrabajo;

import ec.com.codesoft.model.CategoriaTrabajo;
import ec.com.codesoft.model.Cliente;
import ec.com.codesoft.model.DetalleOrdenTrabajo;
import ec.com.codesoft.model.OrdenTrabajo;
import ec.com.codesoft.model.Servicios;
import ec.com.codesoft.model.Usuario;
import ec.com.codesoft.modelo.servicios.ClienteServicio;
import ec.com.codesoft.modelo.servicios.OrdenTrabajoServicio;
import ec.com.codesoft.modelo.servicios.SistemaServicio;
import ec.com.codesoft.web.reportes.ordenTrabajo.OrdenTrabajoDetalleReporte;
import ec.com.codesoft.web.reportes.ordenTrabajo.OrdenTrabajoReporte;
import java.io.IOException;
import java.io.Serializable;
import java.math.BigDecimal;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.annotation.PostConstruct;
import javax.ejb.EJB;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;
import javax.faces.event.ValueChangeEvent;
import net.sf.jasperreports.engine.JRException;
import org.primefaces.context.RequestContext;
import org.primefaces.event.SelectEvent;

/**
 *
 * @author carlo
 */
@ViewScoped
@ManagedBean
public class OrdenTrabajoMB implements Serializable {

    /**
     * Orden de trabajo para grabar
     */
    private OrdenTrabajo ordenTrabajo;

    /**
     * Lista de servicios para facturar
     */
    private List<Servicios> servicios;

    private DetalleOrdenTrabajo detalleOrdenTrabajo;

    @EJB
    private ClienteServicio clienteServicio;

    @EJB
    private OrdenTrabajoServicio ordenTrabajoServicio;

    @EJB
    private SistemaServicio sistemaServicio;

    private List<CategoriaTrabajo> categorias;
    /**
     * Listado de los usuario con perfil de empleados
     */
    private List<Usuario> empleados;
    /**
     * Variable para llevar el total de la orden de trabajo
     */
    private BigDecimal total;

    private String nickEmpleadoSeleccionado;
    private String idServicioSeleccionado;
    private String idCategoriaSeleccionado;

    @PostConstruct
    public void postConstruct() {
        ordenTrabajo = new OrdenTrabajo();
        ordenTrabajo.setTotal(new BigDecimal("0.00"));
        ordenTrabajo.setCedulaRuc(new Cliente());
        // ordenTrabajo.setDetalleOrdenTrabajo(new ArrayList<DetalleOrdenTrabajo>());
        ordenTrabajo.setDetalleOrdenTrabajoList(new ArrayList<DetalleOrdenTrabajo>());
        detalleOrdenTrabajo = new DetalleOrdenTrabajo();
        //total=new BigDecimal("0.00").setScale(2,BigDecimal.ROUND_DOWN);    
        empleados = ordenTrabajoServicio.obtenerEmpleados();
        servicios = ordenTrabajoServicio.obtenerServicios();
        categorias = new ArrayList<CategoriaTrabajo>();
    }

    /**
     * Grabar la orden de trabajo
     */
    public void grabarOrdenTrabajo() {
        Usuario empleado = ordenTrabajoServicio.getUsuarioByNick(nickEmpleadoSeleccionado);        

        ordenTrabajo.setUsuEmpleado(empleado);
        ordenTrabajo.setFechaEmision(new Date());

        ordenTrabajoServicio.grabar(ordenTrabajo);
        System.out.println("orden grabado ...");
        generaPdf();
    }

    private void generaPdf() {
        System.out.println("generando pdf..");
        OrdenTrabajoReporte orden = new OrdenTrabajoReporte(sistemaServicio.getConfiguracion().getPathReportes());
        orden.setAbono(ordenTrabajo.getAdelanto().toString());
        orden.setCedula(ordenTrabajo.getCedulaRuc().getCedulaRuc());
        SimpleDateFormat formateador = new SimpleDateFormat("EEEE d MMMM HH:mm:ss");
        orden.setFechaRecepcion(formateador.format(ordenTrabajo.getFechaEntrega()).toString());
        orden.setMonto(ordenTrabajo.getTotal().toString());
        orden.setNombre(ordenTrabajo.getCedulaRuc().getNombre());
        orden.setObservacion(ordenTrabajo.getObservacion().toString());
        orden.setOrdenTrabajo(ordenTrabajo.getIdOrdenTrabajo().toString());
        orden.setSaldo(ordenTrabajo.getTotal().subtract(ordenTrabajo.getAdelanto()).toString());
        orden.setTelefono(ordenTrabajo.getCedulaRuc().getTelefono());

        List<DetalleOrdenTrabajo> lista = ordenTrabajo.getDetalleOrdenTrabajoList();
        for (DetalleOrdenTrabajo detalle : lista) {
            OrdenTrabajoDetalleReporte detalleReporte = new OrdenTrabajoDetalleReporte();
            detalleReporte.setDescripcion(detalle.getDescripcion());
            detalleReporte.setNombre(detalle.getEquipo());
            detalleReporte.setPrecio(detalle.getPrecio().toString());
            detalleReporte.setProblema(detalle.getProblema());
            detalleReporte.setTrabajoRealizar(detalle.getTrabajoRealizar());
            orden.getDetalles().add(detalleReporte);
        }

        try {
            orden.exportarPDF();
            System.out.println("pdf generado");
        } catch (JRException ex) {
            Logger.getLogger(OrdenTrabajoMB.class.getName()).log(Level.SEVERE, null, ex);
        } catch (IOException ex) {
            Logger.getLogger(OrdenTrabajoMB.class.getName()).log(Level.SEVERE, null, ex);
        }

    }

    public void abrirEquipoDialog() {
        detalleOrdenTrabajo = new DetalleOrdenTrabajo();
        RequestContext.getCurrentInstance().execute("PF('dlgDetalle').show()");
        System.out.println("Abrir dialogo de agregar el equipo");
    }

    /**
     * Busca un cliente abriendo el dialogo correspondiente
     */
    public void buscarClienteDlg() {
        System.out.println("Buscando el cliente ..");
        Map<String, Object> options = new HashMap<String, Object>();
        options.put("modal", true);
        options.put("height", 300);
        RequestContext.getCurrentInstance().openDialog("buscarClientesDlg", options, null);
    }

    /**
     * Me permite agregar un detalle a la hoja de trabajo
     */
    public void agregarDetalle() {
        System.out.println("agregando detalle ...");
//        System.out.println(detalleOrdenTrabajo.getEquipo());
//        System.out.println(ordenTrabajo.getTotal());

        ordenTrabajo.getDetalleOrdenTrabajoList().add(detalleOrdenTrabajo);
        
        CategoriaTrabajo categoria=ordenTrabajoServicio.obtenerCategoriaPorCodigo(Integer.parseInt(idCategoriaSeleccionado));
        detalleOrdenTrabajo.setIdCategoriaTrabajo(categoria);
        
        detalleOrdenTrabajo.getIdCategoriaTrabajo();
        System.out.println(detalleOrdenTrabajo.getPrecio());

        ordenTrabajo.setTotal(ordenTrabajo.getTotal().add(detalleOrdenTrabajo.getPrecio()));
        ordenTrabajo.setDescuento(new BigDecimal("12.00"));

        System.out.println(ordenTrabajo.getTotal());
        RequestContext.getCurrentInstance().execute("PF('dlgDetalle').hide()");

        detalleOrdenTrabajo = new DetalleOrdenTrabajo();
    }

    public void validarCliente() {
        Map<String, Object> options = new HashMap<String, Object>();
        options.put("modal", true);
        options.put("height", 300);

        Map<String, List<String>> params = new HashMap<String, List<String>>();
        List<String> values = new ArrayList<String>();

        values.add(ordenTrabajo.getCedulaRuc().getCedulaRuc());
        params.put("cedula", values);

        RequestContext.getCurrentInstance().openDialog("crearCliente", options, params);
    }

    public void onClientChosen(SelectEvent event) {
        Cliente cliente = (Cliente) event.getObject();
        ordenTrabajo.setCedulaRuc(cliente);

        System.out.println("Cliente llego " + cliente);

    }

    public void cargarCategoriasServicios() {
        Servicios servicioSeleccionado = ordenTrabajoServicio.obtenerServicioPorCodigo(Integer.parseInt(idServicioSeleccionado));
        categorias = servicioSeleccionado.getCategoriaTrabajoList();
        System.out.println("cargando categorias ...");
    }

    public void cargarDatosCategoria() 
    {
        Servicios servicioSeleccionado = ordenTrabajoServicio.obtenerServicioPorCodigo(Integer.parseInt(idServicioSeleccionado));
        CategoriaTrabajo categoria = ordenTrabajoServicio.obtenerCategoriaPorCodigo(Integer.parseInt(idCategoriaSeleccionado));
        detalleOrdenTrabajo.setPrecio(categoria.getPrecio());
        detalleOrdenTrabajo.setProblema(categoria.getDescripcion());
        detalleOrdenTrabajo.setTrabajoRealizar(categoria.getTrabajoRealizar());
        System.out.println("cargando categorias ..."+categoria.getPrecio());
    }

    public void seleccionarEmpleado(Usuario usuario) {
        System.out.println("se llamo al usuario");
    }

    public void seleccionarEmple(ValueChangeEvent event) {
        System.out.println("se llamo al usuario");

    }

    public void subjectSelectionChanged(ValueChangeEvent event) {
        System.out.println("se llamo al usuario desde el nuevo evento");
    }

    //////////////////METODOS GET AND SET ///////////////////
    public OrdenTrabajo getOrdenTrabajo() {
        return ordenTrabajo;
    }

    public void setOrdenTrabajo(OrdenTrabajo ordenTrabajo) {
        this.ordenTrabajo = ordenTrabajo;
    }

    public DetalleOrdenTrabajo getDetalleOrdenTrabajo() {
        return detalleOrdenTrabajo;
    }

    public void setDetalleOrdenTrabajo(DetalleOrdenTrabajo detalleOrdenTrabajo) {
        this.detalleOrdenTrabajo = detalleOrdenTrabajo;
    }

    public List<Usuario> getEmpleados() {
        return empleados;
    }

    public void setEmpleados(List<Usuario> empleados) {
        this.empleados = empleados;
    }

    public String getNickEmpleadoSeleccionado() {
        return nickEmpleadoSeleccionado;
    }

    public void setNickEmpleadoSeleccionado(String nickEmpleadoSeleccionado) {
        this.nickEmpleadoSeleccionado = nickEmpleadoSeleccionado;
    }

    public List<Servicios> getServicios() {
        return servicios;
    }

    public void setServicios(List<Servicios> servicios) {
        this.servicios = servicios;
    }

    public String getIdServicioSeleccionado() {
        return idServicioSeleccionado;
    }

    public void setIdServicioSeleccionado(String idServicioSeleccionado) {
        this.idServicioSeleccionado = idServicioSeleccionado;
    }

    public List<CategoriaTrabajo> getCategorias() {
        return categorias;
    }

    public void setCategorias(List<CategoriaTrabajo> categorias) {
        this.categorias = categorias;
    }

    public String getIdCategoriaSeleccionado() {
        return idCategoriaSeleccionado;
    }

    public void setIdCategoriaSeleccionado(String idCategoriaSeleccionado) {
        this.idCategoriaSeleccionado = idCategoriaSeleccionado;
    }

}
