
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import javax.annotation.PostConstruct;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

/**
 *
 * @author Suco
 */
@ManagedBean
@SessionScoped
public class pruebaVariable implements Serializable{
    
    private List<String> lista;
    private  int cont;
    
    @PostConstruct
    public void construct(){
        lista=new ArrayList<String>();
    }
    
    public void guardar(){
        
        lista.add("Item"+cont);
        cont+=1;
        System.out.println("Datos "+ lista.size());
    }
    
    public void verificar(){
        System.out.println("Verificar "+ lista.size());
    }
}
