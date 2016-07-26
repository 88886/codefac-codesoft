/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ec.com.codesoft.modelo.servicios;

import ec.com.codesoft.model.CategoriaTrabajo;
import ec.com.codesoft.model.DetalleOrdenTrabajo;
import ec.com.codesoft.model.OrdenTrabajo;
import ec.com.codesoft.model.Servicios;
import ec.com.codesoft.model.Usuario;
import ec.com.codesoft.modelo.facade.CategoriaTrabajoFacade;
import ec.com.codesoft.modelo.facade.DetalleOrdenTrabajoFacade;
import ec.com.codesoft.modelo.facade.OrdenTrabajoFacade;
import ec.com.codesoft.modelo.facade.ServiciosFacade;
import ec.com.codesoft.modelo.facade.UsuarioFacade;
import java.util.List;
import javax.ejb.EJB;
import javax.ejb.LocalBean;
import javax.ejb.Stateless;

/**
 *
 * @author carlo
 */
@Stateless
@LocalBean
public class OrdenTrabajoServicio {

    @EJB
    private OrdenTrabajoFacade ordenTrabajoFacade;

    @EJB
    private DetalleOrdenTrabajoFacade detalleOrdenTrabajoFacade;

    @EJB
    private UsuarioFacade usuarioFacade;

    @EJB
    private ServiciosFacade servicios;

    @EJB
    private CategoriaTrabajoFacade categoriaTrabajoFacade;
    

    public void grabar(OrdenTrabajo ordenTrabajo) {
        ordenTrabajo.setEstado("revision");

        ordenTrabajoFacade.create(ordenTrabajo);

        Integer id = ordenTrabajo.getIdOrdenTrabajo();

        List<DetalleOrdenTrabajo> lista = ordenTrabajo.getDetalleOrdenTrabajoList();

        for (DetalleOrdenTrabajo detalle : lista) {
            detalle.setIdOrdenTrabajo(ordenTrabajo);
            detalle.setEstado("sinRevisar");
            detalleOrdenTrabajoFacade.edit(detalle);
        }
    }

    /**
     * Obtiene todos los usuarios con el perfil de empleados
     */
    public List<Usuario> obtenerEmpleados() {
        List<Usuario> empleados = usuarioFacade.getUsuariosPorPerfil("empleado");
        return empleados;
    }

    /**
     * Metodo para anular la orden de trabajo
     */
    public void anularOrdenTrabajo(OrdenTrabajo orden) {
        orden.setEstado("anulado");
        ordenTrabajoFacade.edit(orden);
    }

    /**
     * Obtener el usuario por el nick
     *
     * @return
     */
    public Usuario getUsuarioByNick(String nick) {
        return usuarioFacade.find(nick);
    }
    
    public void editar(OrdenTrabajo ordenTrabajo)
    {
        ordenTrabajoFacade.edit(ordenTrabajo);
    }
    
    public Usuario getUsuarioByCodigo(String nick)
    {
        return usuarioFacade.find(nick);
    }
    
    /**
     * Envia un item de la orden de trabajo para editar y cambiar el
     * estado 
     */
    public void reparar(DetalleOrdenTrabajo detalle)
    {
        detalleOrdenTrabajoFacade.edit(detalle);
    }
    
    ////////////////////////////////// GET AND SET //////////////////////

    public List<Servicios> obtenerServicios() {
        return servicios.findAll();
        //return ordenTrabajoFacade.findAllDesc()
    }

    public Servicios obtenerServicioPorCodigo(Integer codigo) {
        return servicios.find(codigo);
    }
    
    public List<CategoriaTrabajo> obtenerCategoriasPorServicio(Integer codServicio){
        return ordenTrabajoFacade.getCategoriaOrdenServicio(codServicio);
    }

    public CategoriaTrabajo obtenerCategoriaPorCodigo(Integer codigo) {
        return categoriaTrabajoFacade.find(codigo);
    }

    public List<OrdenTrabajo> obtenerOrdenesTrabajo() {
        return ordenTrabajoFacade.findAllDesc();
    }
    public List<OrdenTrabajo> obtenerOrdenesTrabajoAll() {
        return ordenTrabajoFacade.findAll();
    }

    /**
     * Obtiene una lista de las ordenes de trabajo ordenadas por fecha de
     * ingreso
     *
     * @return
     */
    public List<OrdenTrabajo> obtenerPorFechaIngreso(String orden,String estado) 
    {
        return ordenTrabajoFacade.getByDateEntry(orden,estado);
    }

    public List<OrdenTrabajo> obtenerPorFechaSalida(String orden,String estado) 
    {
        return ordenTrabajoFacade.getByDateDeparture(orden,estado);
    }

    public List<OrdenTrabajo> obtenerPorPrecio(String orden,String estado) 
    {
        return ordenTrabajoFacade.getByPrice(orden,estado);
    }

    public void actualizarOrdenTrabajo(OrdenTrabajo orden){
        ordenTrabajoFacade.edit(orden);
    }
    
    
    /**
     * Obtener la orden de trabajo por id
     * @param id
     * @return 
     */
    public OrdenTrabajo getOrdenTrabajoById(Integer id)
    {
        return ordenTrabajoFacade.find(id);
    }
}
