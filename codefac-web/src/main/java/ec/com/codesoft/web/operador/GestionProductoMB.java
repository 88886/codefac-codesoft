/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ec.com.codesoft.web.operador;

import ec.com.codesoft.model.CatalagoProducto;
import ec.com.codesoft.modelo.servicios.CatalogoServicio;
import java.io.Serializable;
import java.util.List;
import java.util.Locale;
import javax.annotation.PostConstruct;
import javax.ejb.EJB;
import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;
import javax.faces.context.FacesContext;
import static org.castor.util.Messages.message;
import org.primefaces.context.RequestContext;

/**
 *
 * @author carlo
 */
@ManagedBean
@ViewScoped
public class GestionProductoMB implements Serializable {

    /**
     * Lista para obtener y mostrar la lista de productos
     */
    private List<CatalagoProducto> catalagoProductos;
    /**
     * Propiedad para seleccionar en la tabla
     */
    private CatalagoProducto catalogoSeleccionado;

    /**
     * Lista de los productos filtrados en la tabla
     */
    private List<CatalagoProducto> catalagoProductosFiltrados;

    /**
     * Variable para poder crear un nuevo catalago
     */
    private CatalagoProducto catalagoProducto;

    /**
     * Variable para saber si el dialogo esta abierto
     */
    private Boolean dialogoAbierto;
    private Boolean dialogoEditAbierto;

    @EJB
    private CatalogoServicio catalogoServicio;

    //@EJB
    //private CatalogoServicio catologoServicio;
    @PostConstruct
    public void postCostruct() {
        catalagoProductos = catalogoServicio.obtenerTodos();
        catalagoProducto = new CatalagoProducto();
        dialogoAbierto = false;
        this.dialogoEditAbierto=false;
    }

    ///////////////////////////METODOS/////////////////////////
    /**
     * Abre el dialogo y nos muestra una nueva venta para agregar un producto
     */
    public void mostrarNuevoProducto() {
        System.out.println("abriendo el dialogo ...");
        catalagoProducto = new CatalagoProducto();
        System.out.println(catalagoProducto);
        RequestContext.getCurrentInstance().execute("PF('dialogNuevoProducto').show()");
        dialogoAbierto = true;
    }

    public void mostrarEditarProducto() {
        //dialogNuevoProductoEdit
        System.out.println("abriendo el dialogo de editar ...");
        //catalagoProducto = new CatalagoProducto();
        System.out.println(catalogoSeleccionado);
        RequestContext.getCurrentInstance().execute("PF('dialogNuevoProductoEdit').show()");
        dialogoEditAbierto = true;
    }

    /**
     * Metodo para cerrar la venta del dialogo
     */
    public void cancelarGrabar() {
        System.out.println("cancelando grabar ...");
        RequestContext.getCurrentInstance().execute("PF('dialogNuevoProducto').hide()");
        catalagoProducto = new CatalagoProducto();
        dialogoAbierto = false;
        
         mostrarMensaje("Cancelado","El proceso de grabar fue cancelado");
    }

    public void cancelarEditar() {
        System.out.println("cancelando editar ...");
        RequestContext.getCurrentInstance().execute("PF('dialogNuevoProductoEdit').hide()");
        dialogoEditAbierto=false;
        mostrarMensaje("Cancelado","El proceso de editar fue cancelado");
    }

    public void grabarCatalogo() {
        System.out.println("Grabando el catalago ...");
        catalogoServicio.insertar(catalagoProducto);
        RequestContext.getCurrentInstance().execute("PF('dialogNuevoProducto').hide()");
        catalagoProducto = new CatalagoProducto();
        catalagoProductos = catalogoServicio.obtenerTodos();
        dialogoAbierto=false;
        mostrarMensaje("Producto Grabado ..","El catalogo fue grabado");
        
        catalagoProducto=catalogoServicio.buscarCatalogo(catalagoProducto.getCodigoProducto());        
//        System.out.println(catalagoProducto.getProductoGeneralVenta().getCantidadDisponible());
        
    }

    public void editarCatalago() {
        System.out.println("Editando el catalogo ...");
        catalogoServicio.actualizar(catalogoSeleccionado);
        RequestContext.getCurrentInstance().execute("PF('dialogNuevoProductoEdit').hide()");
        dialogoEditAbierto=false;
        mostrarMensaje("Producto Editado","El catalogo fue editado");
    }

    /**
     * Verifica al inicio si el dialogo esta abierto o cerrado
     */
    public void verificarDialogo() 
    {
        System.out.println("Verificando si el dialogo se encuentra abierto ...");
        if (dialogoAbierto) {
            RequestContext.getCurrentInstance().execute("PF('dialogNuevoProducto').show()");
        }
        
        if(dialogoEditAbierto)
        {
            RequestContext.getCurrentInstance().execute("PF('dialogNuevoProductoEdit').show()");
        }
    }

    public void mostrarMensaje(String titulo,String mensaje)
    {
        FacesContext context = FacesContext.getCurrentInstance();
        context.addMessage(null, new FacesMessage(titulo,mensaje));
    }
    
    public void verificarExisteCatalogoProducto()
    {
        boolean noexiste=catalogoServicio.verificarExisteProducto(catalagoProducto.getCodigoProducto());
        if(noexiste)
        {
            FacesMessage message = new FacesMessage(FacesMessage.SEVERITY_INFO, "El producto ya existe", "!porfavor revise los datos!");
            RequestContext.getCurrentInstance().showMessageInDialog(message);
        }
        
    }
    
    public void eliminarDetalle(CatalagoProducto catalogo) 
    {
        catalagoProductos.remove(catalogo);
        
        catalogoServicio.eliminar(catalogo);
    }
    
    public boolean filterByName(Object value, Object filter, Locale locale) 
    {
        String filterText = (filter == null) ? null : filter.toString().trim();
        if (filterText == null || filterText.equals("")) {
            return true;
        }

        if (value == null) {
            return false;
        }

        String carName = value.toString().toUpperCase();
        filterText = filterText.toUpperCase();

        if (carName.contains(filterText)) 
        {
            return true;
        } else 
        {
            return false;
        }
    }

    //////////////////////METODOS GET Y SET ///////////////////
    public List<CatalagoProducto> getCatalagoProductos() {
        return catalagoProductos;
    }

    public void setCatalagoProductos(List<CatalagoProducto> catalagoProductos) {
        this.catalagoProductos = catalagoProductos;
    }

    public CatalagoProducto getCatalogoSeleccionado() {
        return catalogoSeleccionado;
    }

    public void setCatalogoSeleccionado(CatalagoProducto catalogoSeleccionado) {
        this.catalogoSeleccionado = catalogoSeleccionado;
    }

    public List<CatalagoProducto> getCatalagoProductosFiltrados() {
        return catalagoProductosFiltrados;
    }

    public void setCatalagoProductosFiltrados(List<CatalagoProducto> catalagoProductosFiltrados) {
        this.catalagoProductosFiltrados = catalagoProductosFiltrados;
    }

    public CatalagoProducto getCatalagoProducto() {
        return catalagoProducto;
    }

    public void setCatalagoProducto(CatalagoProducto catalagoProducto) {
        this.catalagoProducto = catalagoProducto;
    }

}
