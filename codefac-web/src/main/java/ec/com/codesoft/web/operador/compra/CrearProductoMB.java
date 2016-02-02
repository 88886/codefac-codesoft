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
public class CrearProductoMB implements Serializable
{
    @EJB
    private CatalogoServicio catologoServicio;
    
    
    private CatalagoProducto catalogo; 
    private ProductoGeneralVenta productoGeneral;
    
    @PostConstruct
    public void postConstruct()
    {
        this.catalogo=new CatalagoProducto();
        FacesContext context = FacesContext.getCurrentInstance();
        HttpServletRequest request = (HttpServletRequest) context.getExternalContext().getRequest();
        String codigo=request.getParameter("codigo");
        catalogo.setCodigoProducto(codigo);
        
    }
    
    public void grabar()
    {
        System.out.println("grabando catalogo...");
        this.catologoServicio.insertar(catalogo);
        System.out.println(catalogo);
        RequestContext.getCurrentInstance().closeDialog(catalogo); 
    }   

    public CatalagoProducto getCatalogo() {
        return catalogo;
    }

    public void setCatalogo(CatalagoProducto catalogo) {
        this.catalogo = catalogo;
    }
    
    
    
}
