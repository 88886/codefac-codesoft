
import ec.com.codesoft.model.CreditoFactura;
import ec.com.codesoft.model.Venta;
import ec.com.codesoft.modelo.servicios.FacturaServicio;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import javax.annotation.PostConstruct;
import javax.ejb.EJB;
import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;
import javax.faces.context.FacesContext;

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
@ViewScoped

public class gestionarAbonoMB implements Serializable {

    private String campoBuscar;
    private String codigoBuscar;
    private String placeHolder;
    private List<CreditoFactura> creditoFacturaObtenidos;
    private List<Venta> ventasTipoPago;

    @EJB
    private FacturaServicio facturaServicio;

    @PostConstruct
    public void inicializar() {
        campoBuscar = "Factura";
        placeHolder = "Ingrese el N de Factura";
        creditoFacturaObtenidos=new ArrayList<CreditoFactura>();
    }

    public void escojerFiltro() {
        System.out.println(campoBuscar);
        if (campoBuscar.equals("Cliente")) {
            placeHolder = "Ingrese la Cédula o RUC";
        } else {
            placeHolder = "Ingrese el N de Factura";
        }
    }

    public void buscarDatos() {
        if (campoBuscar.equals("Cliente")) {
            ventasTipoPago = facturaServicio.obtenerVentaTipo(codigoBuscar, "Credito");
            if (ventasTipoPago != null) {
                creditoFacturaObtenidos = new ArrayList<CreditoFactura>();
                for (int i = 0; i < ventasTipoPago.size(); i++) {
                    CreditoFactura creditoTemporal = new CreditoFactura();
                    creditoTemporal = facturaServicio.obtenerCreditoFactura(ventasTipoPago.get(i).getCodigoFactura(), "Proceso");
                    //System.out.println("Credito"+creditoTemporal);
                    if (creditoTemporal != null) {
                        creditoFacturaObtenidos.add(creditoTemporal);
                    }
                }
            } else {
                FacesMessage msg = new FacesMessage("No se Encontraron Datos");
                FacesContext.getCurrentInstance().addMessage(null, msg);
            }

        }else{
            
        }
    }

    public String getCampoBuscar() {
        return campoBuscar;
    }

    public void setCampoBuscar(String campoBuscar) {
        this.campoBuscar = campoBuscar;
    }

    public String getCodigoBuscar() {
        return codigoBuscar;
    }

    public void setCodigoBuscar(String codigoBuscar) {
        this.codigoBuscar = codigoBuscar;
    }

    public String getPlaceHolder() {
        return placeHolder;
    }

    public void setPlaceHolder(String placeHolder) {
        this.placeHolder = placeHolder;
    }

    public List<CreditoFactura> getCreditoFacturaObtenidos() {
        return creditoFacturaObtenidos;
    }

    public void setCreditoFacturaObtenidos(List<CreditoFactura> creditoFacturaObtenidos) {
        this.creditoFacturaObtenidos = creditoFacturaObtenidos;
    }

    public List<Venta> getVentasTipoPago() {
        return ventasTipoPago;
    }

    public void setVentasTipoPago(List<Venta> ventasTipoPago) {
        this.ventasTipoPago = ventasTipoPago;
    }
    
    
    
}
