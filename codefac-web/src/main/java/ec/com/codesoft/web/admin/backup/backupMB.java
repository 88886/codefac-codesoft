/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ec.com.codesoft.web.admin.backup;

import ec.com.codesoft.modelo.servicios.SistemaServicio;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.InputStream;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.annotation.PostConstruct;
import javax.ejb.EJB;
import javax.faces.bean.ManagedBean;
import javax.faces.context.FacesContext;
import javax.servlet.ServletContext;
import org.primefaces.model.DefaultStreamedContent;
import org.primefaces.model.StreamedContent;

/**
 *
 * @author Suco
 */
@ManagedBean
public class backupMB {

    ServletContext ctx;
    String basePath;
    // private String txtPath = "G:\\New Folder\\";
    private String txtPath;
    private String lblMessage;
    private StreamedContent file;

    @EJB
    private SistemaServicio sistemaServicio;

    @PostConstruct
    public void inicializar() {
        ctx = (ServletContext) FacesContext.getCurrentInstance().getExternalContext().getContext();
        basePath = ctx.getRealPath("/");
        txtPath = sistemaServicio.getConfiguracion().getPathreportes() + "backup/"; //ruta para guardar el respaldo
        System.out.println("Path " + txtPath);

    }

    public void descargar() {
        System.out.println("Ruta "+ backup() + ".sql");
        InputStream stream;
        try {
            stream = new FileInputStream(txtPath);
            file = new DefaultStreamedContent(stream, "db/sql",  backup() + ".sql");
        } catch (FileNotFoundException ex) {
            Logger.getLogger(backupMB.class.getName()).log(Level.SEVERE, null, ex);
        }
        
    }

    public String backup() {
        if (txtPath.equals("")) {
            lblMessage = ("Please choose path to save!");
        } else {
            Date now = new Date();
            SimpleDateFormat dateFormat = new SimpleDateFormat("dd-MM-yyyy_HH-mm-ss");
            //File file = new File(dateFormat.format(now));
            String strFilename = dateFormat.format(now);

            long nowLong = now.getTime();
            //String strFilename;
            //strFilename = nowLong.toString();
            //strFilename = String.valueOf(nowLong);
            System.out.println(strFilename);
            //String command = "C://xampp//mysql//bin/mysqldump -u(db user name) -p(db password) --add-drop-database -B (db name) -r " + "\"" + txtPath.getText().toString() + "\\" + strFilename + ".sql\"";
            String command = "C://xampp//mysql//bin/mysqldump --user=" + sistemaServicio.getConfiguracion().getDbUser() + " --password=" + sistemaServicio.getConfiguracion().getDbPassword() + " --add-drop-database -B codefac -r " + "\"" + txtPath.toString() + "\\" + strFilename + ".sql\"";
            System.out.println(command);
            Process p = null;
            try {
                Runtime runtime = Runtime.getRuntime();
                p = runtime.exec(command);

                int processComplete = p.waitFor();

                if (processComplete == 0) {

                    // System.out.println("<html><font color='green'>Backup created successfully!</font></html>");
                    lblMessage = "Respaldo Creado Existosamente en :  " + txtPath.toString() + "\\" + strFilename + ".sql";
                    return strFilename;
                } else {
                    lblMessage = "No se puede crear el respaldo";
                    return null;
                }

            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        return null;
    }

    public String getLblMessage() {
        return lblMessage;
    }

    public void setLblMessage(String lblMessage) {
        this.lblMessage = lblMessage;
    }

    public StreamedContent getFile() {
        return file;
    }

}
