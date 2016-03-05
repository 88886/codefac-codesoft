/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ec.com.codesoft.web.test;

import ec.com.codesoft.web.reportes.FacturaDetalleModeloReporte;
import ec.com.codesoft.web.reportes.FacturaModeloReporte;
import ec.com.codesoft.web.test.modelo.ModeloPersona;
import java.io.IOException;
import java.io.Serializable;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.RequestScoped;
import javax.faces.bean.ViewScoped;
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
@ManagedBean
@ViewScoped
public class GenerarPdf implements Serializable {

    private List<ModeloPersona> lstPersonas = new ArrayList<ModeloPersona>();
    
    public void exportarPDF2() throws JRException, IOException {
        Map<String, Object> parametros = new HashMap<String, Object>();
        parametros.put("txtusuario", "MitoCode");
        
        //String reportPath2 = FacesContext.getCurrentInstance().getExternalContext().getRealPath("reporteCliente.jasper");
        //File jasper = new File(FacesContext.getCurrentInstance().getExternalContext().getRealPath("rpJSF.jasper"));
        String reportePath=FacesContext.getCurrentInstance().getExternalContext().getRealPath("reportes/ejemplo.jasper");
        
        //jasperPrint2 = JasperFillManager.fillReport(reportPath2, new HashMap(), beanCollectionDataSource2);
        JasperPrint jasperPrint = JasperFillManager.fillReport(reportePath,parametros, new JRBeanCollectionDataSource(getLstPersonas(),false));

        HttpServletResponse response = (HttpServletResponse) FacesContext.getCurrentInstance().getExternalContext().getResponse();
        response.addHeader("Content-disposition", "attachment; filename=jsfReporte.pdf");
        ServletOutputStream stream = response.getOutputStream();

        JasperExportManager.exportReportToPdfStream(jasperPrint, stream);

        stream.flush();
        stream.close();
        FacesContext.getCurrentInstance().responseComplete();
    }
    
    
    public void abrirPDF() {
        //FacturaModeloReporte factura = new FacturaModeloReporte();
//                "001",
//                "Juan Perez",
//                "Sangolqui",
//                "040000123",
//                "02223123",
//                "1990-01-01",
//                "efectivo",
//                "ninguna nota",
//                new BigDecimal(100),
//                new BigDecimal(300),
//                new BigDecimal(500));
//
//        FacturaDetalleModeloReporte detalle = new FacturaDetalleModeloReporte(
//                "2",
//                "123123",
//                "esfero",
//                new BigDecimal(10),
//                new BigDecimal(20),
//                new BigDecimal(30));
//
//        factura.agregarDetalle(detalle);
//        detalle = new FacturaDetalleModeloReporte(
//                "2",
//                "123123",
//                "esfero",
//                new BigDecimal(10),
//                new BigDecimal(20),
//                new BigDecimal(30));
//
//        factura.agregarDetalle(detalle);
//        detalle = new FacturaDetalleModeloReporte(
//                "1",
//                "12343",
//                "laptop",
//                new BigDecimal(100),
//                new BigDecimal(200),
//                new BigDecimal(300));
//
//        factura.agregarDetalle(detalle);
//
//        detalle = new FacturaDetalleModeloReporte(
//                "1",
//                "123",
//                "mouse",
//                new BigDecimal(1),
//                new BigDecimal(2),
//                new BigDecimal(3));
//
//        factura.agregarDetalle(detalle);
        
//        try {
//            
//            //factura.exportarPDF();
//        } catch (JRException ex) {
//            Logger.getLogger(GenerarPdf.class.getName()).log(Level.SEVERE, null, ex);
//        } catch (IOException ex) {
//            Logger.getLogger(GenerarPdf.class.getName()).log(Level.SEVERE, null, ex);
//        }
    }

    public void exportarPDF() throws JRException, IOException {
        Map<String, Object> parametros = new HashMap<String, Object>();
        parametros.put("txtusuario", "MitoCode");

        //String reportPath2 = FacesContext.getCurrentInstance().getExternalContext().getRealPath("reporteCliente.jasper");
        //File jasper = new File(FacesContext.getCurrentInstance().getExternalContext().getRealPath("rpJSF.jasper"));
        String reportePath = FacesContext.getCurrentInstance().getExternalContext().getRealPath("report3.jasper");

        //jasperPrint2 = JasperFillManager.fillReport(reportPath2, new HashMap(), beanCollectionDataSource2);
        JasperPrint jasperPrint = JasperFillManager.fillReport(reportePath, parametros, new JRBeanCollectionDataSource(getLstPersonas()));

        HttpServletResponse response = (HttpServletResponse) FacesContext.getCurrentInstance().getExternalContext().getResponse();
        response.addHeader("Content-disposition", "attachment; filename=jsfReporte.pdf");
        ServletOutputStream stream = response.getOutputStream();

        JasperExportManager.exportReportToPdfStream(jasperPrint, stream);

        stream.flush();
        stream.close();
        FacesContext.getCurrentInstance().responseComplete();
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
