/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ec.com.codesoft.web.reportes;

import java.util.ArrayList;
import java.util.List;

/**
 *Modelo para generar el catalogo por columnas
 * @author carlo
 */
public class ModeloCatalagoColumnas 
{
    private List<CatalagoModelo> catalogoModeloList;

    public ModeloCatalagoColumnas() 
    {
        this.catalogoModeloList = new ArrayList<CatalagoModelo>();
    }
    
    ///////////////////////GET AND SET////////////////////////

    public List<CatalagoModelo> getCatalogoModeloList() {
        return catalogoModeloList;
    }

    public void setCatalogoModeloList(List<CatalagoModelo> catalogoModeloList) {
        this.catalogoModeloList = catalogoModeloList;
    }
    
    
    
    
    
}
