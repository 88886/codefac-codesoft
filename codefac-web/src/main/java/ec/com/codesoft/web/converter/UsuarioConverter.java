/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ec.com.codesoft.web.converter;

import ec.com.codesoft.model.Usuario;
import javax.ejb.SessionBean;
import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.convert.Converter;
import javax.faces.convert.FacesConverter;

/**
 *
 * @author carlo
 */
@FacesConverter(value="personaConverter")
public class UsuarioConverter implements Converter{
    
    private SessionBean sessionBean;

    @Override
    public Object getAsObject(FacesContext context, UIComponent component, String value) {
         if(value==null||value.equals("")){
            return null;
        }
       // sessionBean=(SessionBean)context.getELContext().getELResolver().getValue(context.getELContext(), null, "sessionBean");
       // Usuario u=(Usuario)sessionBean
        //Persona p=(Persona)sessionBean.getHm().get(value);
        return null;
    }

    @Override
    public String getAsString(FacesContext context, UIComponent component, Object value) {
        if(value==null){
            return "";
        }
        Usuario p=(Usuario)value;
        return p.getNombres();
    }
    
}
