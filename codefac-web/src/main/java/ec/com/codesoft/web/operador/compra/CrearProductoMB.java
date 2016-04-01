/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ec.com.codesoft.web.operador.compra;

import ec.com.codesoft.model.CatalagoProducto;
import ec.com.codesoft.model.ProductoGeneralVenta;
import ec.com.codesoft.modelo.servicios.CatalogoServicio;
import java.io.Serializable;
import java.math.BigDecimal;
import javax.annotation.PostConstruct;
import javax.ejb.EJB;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;
import javax.faces.context.FacesContext;
import javax.servlet.http.HttpServletRequest;
import org.primefaces.context.RequestContext;

/**
 *
 * @author carlo
 */
@ViewScoped
@ManagedBean
public class CrearProductoMB implements Serializable {

    @EJB
    private CatalogoServicio catologoServicio;

    private CatalagoProducto catalogo;
    private ProductoGeneralVenta productoGeneral;

    /**
     * Variables para saber si el precios incluyen o no incluyen iva
     */
    private String ivaCosto;
    private String ivaMayorista;

    @PostConstruct
    public void postConstruct() {
        this.catalogo = new CatalagoProducto();
        FacesContext context = FacesContext.getCurrentInstance();
        HttpServletRequest request = (HttpServletRequest) context.getExternalContext().getRequest();
        String codigo = request.getParameter("codigo");
        catalogo.setCodigoProducto(codigo);
        
        catalogo.setPrecio(new BigDecimal("0.00"));
        catalogo.setPrecioMayorista(new BigDecimal("0.00"));
        
        catalogo.setCosto(new BigDecimal("0.00"));
        
        catalogo.setDescuento(new BigDecimal("0.00"));
        catalogo.setDescuentoMayorista(new BigDecimal("0.00"));

    }

    public void grabar() {
//        System.out.println("grabando catalogo...");
//        this.catologoServicio.insertar(catalogo);
//        System.out.println(catalogo);
//        RequestContext.getCurrentInstance().closeDialog(catalogo);

        System.out.println("Grabando el catalago ...");

        //verificar si el precio viene con o sin iva
        if (ivaCosto.equals("+")) {
            //System.out.println(catalagoProducto.getPrecio().setScale(3,BigDecimal.ROUND_DOWN));
            //BigDecimal iva=new BigDecimal("1.12");
            BigDecimal valor = catalogo.getPrecio().divide(new BigDecimal("1.12"), 3, BigDecimal.ROUND_DOWN);
            //System.out.println("si:"+valor);

            valor = valor.setScale(3, BigDecimal.ROUND_DOWN);
            catalogo.setPrecio(valor);

        }

        if (ivaMayorista.equals("+")) {
            BigDecimal valor = catalogo.getPrecioMayorista().divide(new BigDecimal("1.12"), 3, BigDecimal.ROUND_DOWN);
            valor = valor.setScale(3, BigDecimal.ROUND_DOWN);
            catalogo.setPrecioMayorista(valor);
            //catalagoProducto.setPrecioMayorista(catalagoProducto.getPrecioMayorista().divide(new BigDecimal("1.12")).setScale(2,BigDecimal.ROUND_UP));
        }

        catologoServicio.insertar(catalogo);
        RequestContext.getCurrentInstance().closeDialog(catalogo);

        //RequestContext.getCurrentInstance().execute("PF('dialogNuevoProducto').hide()");
        //catalogo = new CatalagoProducto();
        //catalagoProductos = catologoServicio.obtenerTodos();
        //dialogoAbierto = false;
        //mostrarMensaje("Producto Grabado ..", "El catalogo fue grabado");

        //catalogo = catologoServicio.buscarCatalogo(catalogo.getCodigoProducto());
    }
    
    public void cancelar()
    {
        RequestContext.getCurrentInstance().closeDialog(null);
    }

    public CatalagoProducto getCatalogo() {
        return catalogo;
    }

    public void setCatalogo(CatalagoProducto catalogo) {
        this.catalogo = catalogo;
    }

    public String getIvaCosto() {
        return ivaCosto;
    }

    public void setIvaCosto(String ivaCosto) {
        this.ivaCosto = ivaCosto;
    }

    public String getIvaMayorista() {
        return ivaMayorista;
    }

    public void setIvaMayorista(String ivaMayorista) {
        this.ivaMayorista = ivaMayorista;
    }

}
