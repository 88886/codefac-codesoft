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
    
    ////////////////////////////////// GET AND SET //////////////////////

    public List<Servicios> obtenerServicios() {
        return servicios.findAll();
    }

    public Servicios obtenerServicioPorCodigo(Integer codigo) {
        return servicios.find(codigo);
    }

    public CategoriaTrabajo obtenerCategoriaPorCodigo(Integer codigo) {
        return categoriaTrabajoFacade.find(codigo);
    }

    public List<OrdenTrabajo> obtenerOrdenesTrabajo() {
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

}
