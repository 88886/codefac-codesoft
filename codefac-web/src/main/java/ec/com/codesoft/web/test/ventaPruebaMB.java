/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ec.com.codesoft.web.test;

import ec.com.codesoft.model.CatalagoProducto;
import ec.com.codesoft.model.DetalleProductoGeneral;
import ec.com.codesoft.model.Venta;
import ec.com.codesoft.modelo.servicios.FacturaServicio;
import java.io.Serializable;
import java.math.BigDecimal;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import javax.annotation.PostConstruct;
import javax.ejb.EJB;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;

/**
 *
 * @author Suco
 */
@ManagedBean
@SessionScoped
public class ventaPruebaMB implements Serializable {

    private Venta ventaDiaria;
    private List<DetalleProductoGeneral> detallesGeneral;

    @EJB
    FacturaServicio facturaServicio;

    @PostConstruct
    public void postConstruct() {
        ventaDiaria = facturaServicio.devolverDetallesVentasDiarias(getFechaActual());
        detallesGeneral=ventaDiaria.getDetalleProductoGeneralList();
//       ventaDiaria=new Venta();
//       ventaDiaria.setCodigoFactura(0);
//       detallesGeneral=new ArrayList<DetalleProductoGeneral>();
//       ventaDiaria.setDetalleProductoGeneralList(detallesGeneral);
       
        

    }
    
    public Venta devolverVenta(){
    
        return facturaServicio.devolverDetallesVentasDiarias(getFechaActual());
    }

    public void guardarDetalle() {
        CatalagoProducto catalogo = new CatalagoProducto();
        catalogo.setCodigoProducto("0152502084");

        DetalleProductoGeneral detalle = new DetalleProductoGeneral();
        detalle.setCantidad(1);
        detalle.setCodigoProducto(catalogo);
        detalle.setSubtotal(new BigDecimal("0.0"));
        detalle.setCodigoDetallGeneral(0);
        detalle.setPrecioIndividual(new BigDecimal("0.0"));
        detalle.setCodigoFactura(ventaDiaria);
        
        List<DetalleProductoGeneral> detalleTemporal =new ArrayList<DetalleProductoGeneral>();
        detalleTemporal.add(detalle);
        //ventaDiaria.getDetalleProductoGeneralList().add(detalle);
        detallesGeneral.add(detalle);
        ventaDiaria.setDetalleProductoGeneralList(detalleTemporal);
        facturaServicio.editarVentaDiaria(ventaDiaria);
        //facturaServicio.insertarDetalleFacturaProductoGeneral(detalle);
        System.out.println("Guardado Detalle General");
        System.out.println("Items " + detallesGeneral.size());
        
        System.out.println("Items Detalle General"+detallesGeneral.size());
        System.out.println("Items DevolverVenta"+devolverVenta().getDetalleProductoGeneralList().size());
        detallesGeneral=devolverVenta().getDetalleProductoGeneralList();
        
        
    }

    public void verificar() {
        System.out.println("Verificar Ventas Dirias Detalles " + ventaDiaria.getDetalleProductoGeneralList().size());
    }

    public String getFechaActual() {
        Date ahora = new Date();
        SimpleDateFormat formateador = new SimpleDateFormat("yyyy-MM-dd");
        return formateador.format(ahora);
    }

}
