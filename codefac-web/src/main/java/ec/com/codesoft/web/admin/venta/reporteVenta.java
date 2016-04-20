/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ec.com.codesoft.web.admin.venta;

import ec.com.codesoft.model.Venta;
import ec.com.codesoft.modelo.servicios.FacturaServicio;
import java.io.Serializable;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import javax.annotation.PostConstruct;
import javax.ejb.EJB;
import javax.faces.bean.ManagedBean;
import org.primefaces.model.chart.Axis;
import org.primefaces.model.chart.AxisType;
import org.primefaces.model.chart.ChartSeries;
import org.primefaces.model.chart.LineChartModel;
import org.primefaces.model.chart.LineChartSeries;

/**
 *
 * @author Suco
 */
@ManagedBean
public class reporteVenta implements Serializable {

    /**
     * Bean de reporte de Ventas
     */
    private List<Venta> ventas;
  

    //grafico lineas
    private LineChartModel lineModel;

    @EJB
    FacturaServicio facturaServicio;

    @PostConstruct
    public void inicializar() {

        ventas = facturaServicio.devolverVentasIntervalo(devolverFechaActual(), "2016-03-23");
        createLineModels();
        for (Venta venta : ventas) {
             SimpleDateFormat formateador = new SimpleDateFormat("hh:mm");
        
            System.out.println(formateador.format(venta.getFecha()) + " --- " + venta.getTotal());
        }
        

    }
    
    public String devolverFechaActual() {
        Date ahora = new Date();
        SimpleDateFormat formateador = new SimpleDateFormat("yyyy-MM-dd");
        return formateador.format(ahora);
    }

    private void createLineModels() {
        lineModel = initLinearModel();
        lineModel.setTitle("Ventas");
        lineModel.setLegendPosition("e");
        Axis yAxis = lineModel.getAxis(AxisType.Y);
    }

    private LineChartModel initLinearModel() {
        LineChartModel model = new LineChartModel();

        ChartSeries venta = new ChartSeries();
        venta.setLabel("Total");
        SimpleDateFormat formateador = new SimpleDateFormat("hh:mm");
        for (int i = 0; i < ventas.size(); i++) {
            venta.set(i+1,ventas.get(i).getTotal());

        }
        model.addSeries(venta);
        return model;
    }

    public List<Venta> getVentas() {
        return ventas;
    }

    public void setVentas(List<Venta> ventas) {
        this.ventas = ventas;
    }

    public LineChartModel getLineModel() {
        return lineModel;
    }

    public void setLineModel(LineChartModel lineModel) {
        this.lineModel = lineModel;
    }

}
