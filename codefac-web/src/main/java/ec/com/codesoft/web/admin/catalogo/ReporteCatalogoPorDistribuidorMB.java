/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ec.com.codesoft.web.admin.catalogo;

import ec.com.codesoft.model.CatalagoProducto;
import ec.com.codesoft.modelo.servicios.CatalogoServicio;
import ec.com.codesoft.modelo.servicios.CompraServicio;
import ec.com.codesoft.web.reportes.CatalagoModelo;
import ec.com.codesoft.web.reportes.ModeloCatalagoColumnas;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import javax.annotation.PostConstruct;
import javax.ejb.EJB;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;
import org.omg.PortableInterceptor.SYSTEM_EXCEPTION;

/**
 *
 * @author carlo
 */
@ManagedBean
@ViewScoped
public class ReporteCatalogoPorDistribuidorMB implements Serializable
{
   // @EJB
   // private CompraServicio compraServicio;
    
    @EJB
    private CatalogoServicio catalogoServicio;
    
    
    private List<CatalagoProducto> catalagoProductosList;
    
    /**
     * Lista para mostrar la lista de datos del modelo en 3 columnas
     */
    private List<ModeloCatalagoColumnas> listModeloDatos;
    
    
    @PostConstruct
    public void postConstruct()
    {
        catalagoProductosList=catalogoServicio.obtenerTodos();
        
        
        System.out.println("numero items: "+catalagoProductosList.size());
        
        cargarDatosModelo();
    }
    
    private void cargarDatosModelo()
    {
        listModeloDatos=new ArrayList<ModeloCatalagoColumnas>();
        
        int indice=0;
        ModeloCatalagoColumnas columnaCatalogo=new ModeloCatalagoColumnas();
        
        for (CatalagoProducto catalogo : catalagoProductosList) 
        {
            if(indice==2)
            {
                listModeloDatos.add(columnaCatalogo);
                indice=0;
            }
            else
            {
                CatalagoModelo dato=new CatalagoModelo();                
                dato.setNombre(catalogo.getNombre());
                dato.setCodigo(catalogo.getCodigoProducto());
                columnaCatalogo.getCatalogoModeloList().add(dato);
                indice++;
            }
            
        }
    }
    
    /////////////////////////METODOS GET AND SET ///////////////////////////////

    public List<ModeloCatalagoColumnas> getListModeloDatos() {
        return listModeloDatos;
    }

    public void setListModeloDatos(List<ModeloCatalagoColumnas> listModeloDatos) {
        this.listModeloDatos = listModeloDatos;
    }

    public List<CatalagoProducto> getCatalagoProductosList() {
        return catalagoProductosList;
    }

    public void setCatalagoProductosList(List<CatalagoProducto> catalagoProductosList) {
        this.catalagoProductosList = catalagoProductosList;
    }

    
    
}
