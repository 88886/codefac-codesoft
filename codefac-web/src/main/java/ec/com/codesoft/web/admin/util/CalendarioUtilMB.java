/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ec.com.codesoft.web.admin.util;

import ec.com.codesoft.web.widget.CommonWidGet;
import java.io.Serializable;
import javax.annotation.PostConstruct;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;

/**
 *
 * @author carlo
 */
@ManagedBean
@SessionScoped
public class CalendarioUtilMB extends CommonWidGet implements Serializable 
{
    @PostConstruct
    public void postConstuct() {
        setX(8);
        setY(220);        
        setNameVar("dlgCalendario");
    }
    
}
