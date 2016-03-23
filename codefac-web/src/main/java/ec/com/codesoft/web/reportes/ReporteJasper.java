/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ec.com.codesoft.web.reportes;

import ec.com.codesoft.modelo.servicios.SistemaServicio;
import ec.com.codesoft.web.seguridad.SistemaMB;
import ec.com.codesoft.web.test.modelo.ModeloPersona;
import java.io.IOException;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import javax.ejb.EJB;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ManagedProperty;
import javax.faces.bean.NoneScoped;
import javax.faces.context.FacesContext;
import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletResponse;
import net.sf.jasperreports.engine.JRException;
import net.sf.jasperreports.engine.JasperExportManager;
import net.sf.jasperreports.engine.JasperFillManager;
import net.sf.jasperreports.engine.JasperPrint;
import net.sf.jasperreports.engine.JasperRunManager;
import net.sf.jasperreports.engine.data.JRBeanCollectionDataSource;

/**
 *
 * @author carlos
 */
public abstract class ReporteJasper<T> {

    private String raiz;

    public ReporteJasper(String raiz) {
        this.raiz = raiz;
    }

    public abstract List<T> getLista();

    public abstract Map<String, Object> getParametros();

    public abstract String getPath();

    private List<ModeloPersona> lstPersonas = new ArrayList<ModeloPersona>();

    public void exportarPDF2() throws JRException, IOException {

        //String reportePath=FacesContext.getCurrentInstance().getExternalContext().getRealPath(getPath());
        //String reportePath="E:/reportes/"+getPath();
        System.out.println("sistema: " + raiz);
        String reportePath = raiz + getPath();

        JasperPrint jasperPrint = JasperFillManager.fillReport(reportePath, getParametros(), new JRBeanCollectionDataSource(getLista(), false));

        HttpServletResponse response = (HttpServletResponse) FacesContext.getCurrentInstance().getExternalContext().getResponse();
        response.addHeader("Content-disposition", "attachment; filename=jsfReporte.pdf");
        ServletOutputStream stream = response.getOutputStream();

        JasperExportManager.exportReportToPdfStream(jasperPrint, stream);

        stream.flush();
        stream.close();

        FacesContext.getCurrentInstance().responseComplete();
    }

    public void exportarPDF() throws JRException, IOException {

        //String reportePath=FacesContext.getCurrentInstance().getExternalContext().getRealPath(getPath());
        //String reportePath="E:/reportes/"+getPath();
        System.out.println("sistema: " + raiz);
        String reportePath = raiz + getPath();

        JasperPrint jasperPrint = JasperFillManager.fillReport(reportePath, getParametros(), new JRBeanCollectionDataSource(getLista(), false));

        //HttpServletResponse response = (HttpServletResponse) FacesContext.getCurrentInstance().getExternalContext().getResponse();
        //response.addHeader("Content-disposition", "attachment; filename=jsfReporte.pdf");
        //ServletOutputStream stream = response.getOutputStream();

        //JasperExportManager.exportReportToPdfStream(jasperPrint, stream);

        //stream.flush();
        //stream.close();

        FacesContext.getCurrentInstance().responseComplete();

        byte[] bytes = JasperRunManager.runReportToPdf(reportePath,getParametros(), new JRBeanCollectionDataSource(getLista()));
        HttpServletResponse response = (HttpServletResponse) FacesContext.getCurrentInstance().getExternalContext().getResponse();
        response.setContentType("application/pdf");
        response.setContentLength(bytes.length);
        
        
        ServletOutputStream outStream = response.getOutputStream();
        outStream.write(bytes, 0, bytes.length);
        
        outStream.flush();
        outStream.close();

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
