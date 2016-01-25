/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ec.com.codesoft.web.reportes;

import ec.com.codesoft.web.test.modelo.ModeloPersona;
import java.io.IOException;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import javax.faces.context.FacesContext;
import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletResponse;
import net.sf.jasperreports.engine.JRException;
import net.sf.jasperreports.engine.JasperExportManager;
import net.sf.jasperreports.engine.JasperFillManager;
import net.sf.jasperreports.engine.JasperPrint;
import net.sf.jasperreports.engine.data.JRBeanCollectionDataSource;

/**
 *
 * @author carlo
 */
public abstract class ReporteJasper<T> {

    public abstract List<T> getLista();

    public abstract Map<String, Object> getParametros();

    public abstract String getPath();
    
    private List<ModeloPersona> lstPersonas = new ArrayList<ModeloPersona>();

    public void exportarPDF() throws JRException, IOException {
        //Map<String, Object> parametros = getParametros();
        //parametros.put("codigoFactura", "1236123");
        //String reportPath2 = FacesContext.getCurrentInstance().getExternalContext().getRealPath("reporteCliente.jasper");
        //File jasper = new File(FacesContext.getCurrentInstance().getExternalContext().getRealPath("rpJSF.jasper"));
        List<T> lista=new ArrayList<T>(); 
        FacturaDetalleModeloReporte detalle = new FacturaDetalleModeloReporte(
                2,
                "123123",
                "esfero",
                new BigDecimal(10),
                new BigDecimal(20),
                new BigDecimal(30));

       // factura.agregarDetalle(detalle);
        lista.add((T) detalle);
        detalle = new FacturaDetalleModeloReporte(
                2,
                "123123",
                "esfero",
                new BigDecimal(10),
                new BigDecimal(20),
                new BigDecimal(30));
        lista.add((T) detalle);
        
        
        
        String reportePath=FacesContext.getCurrentInstance().getExternalContext().getRealPath(getPath());
        
        //jasperPrint2 = JasperFillManager.fillReport(reportPath2, new HashMap(), beanCollectionDataSource2);
        JasperPrint jasperPrint = JasperFillManager.fillReport(reportePath,getParametros(), new JRBeanCollectionDataSource(getLista(),false));

        HttpServletResponse response = (HttpServletResponse) FacesContext.getCurrentInstance().getExternalContext().getResponse();
        response.addHeader("Content-disposition", "attachment; filename=jsfReporte.pdf");
        ServletOutputStream stream = response.getOutputStream();

        JasperExportManager.exportReportToPdfStream(jasperPrint, stream);

        stream.flush();
        stream.close();
        FacesContext.getCurrentInstance().responseComplete();

//        Map<String, Object> parametros = new HashMap<String, Object>();
//        parametros.put("codigoFactura", "1236123");
//        parametros.put("nombre", "Carlos Sanchez");
//
//        //String reportPath2 = FacesContext.getCurrentInstance().getExternalContext().getRealPath("reporteCliente.jasper");
//        //File jasper = new File(FacesContext.getCurrentInstance().getExternalContext().getRealPath("rpJSF.jasper"));
//        String reportePath = FacesContext.getCurrentInstance().getExternalContext().getRealPath("reportes/reporteFactura.jasper");
//
//        //jasperPrint2 = JasperFillManager.fillReport(reportPath2, new HashMap(), beanCollectionDataSource2);
//        JasperPrint jasperPrint = JasperFillManager.fillReport(reportePath, parametros, new JRBeanCollectionDataSource(getLstPersonas(), false));
//
//        HttpServletResponse response = (HttpServletResponse) FacesContext.getCurrentInstance().getExternalContext().getResponse();
//        response.addHeader("Content-disposition", "attachment; filename=jsfReporte.pdf");
//        ServletOutputStream stream = response.getOutputStream();
//
//        JasperExportManager.exportReportToPdfStream(jasperPrint, stream);
//
//        stream.flush();
//        stream.close();
//        FacesContext.getCurrentInstance().responseComplete();
    }
    
    public List<ModeloPersona> getLstPersonas() {
        
        ModeloPersona per = new ModeloPersona();
        per.setNombres("Mito");
        per.setApellidos("Code");

        Calendar cal = Calendar.getInstance();
        cal.set(1991, 1, 21);
        per.setFechaNacimiento(cal.getTime());

        lstPersonas.add(per);

        per = new ModeloPersona();
        per.setNombres("Jaime");
        per.setApellidos("MD");

        cal = Calendar.getInstance();
        cal.set(1990, 3, 28);
        per.setFechaNacimiento(cal.getTime());

        lstPersonas.add(per);

        per = new ModeloPersona();

        per.setNombres("Carlos");
        per.setApellidos("Cs");

        cal = Calendar.getInstance();
        cal.set(1990, 3, 28);
        per.setFechaNacimiento(cal.getTime());

        lstPersonas.add(per);

        return lstPersonas;
    }
}
