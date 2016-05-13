/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ec.com.codesoft.web.admin.backup;

import java.sql.Connection;
import java.text.SimpleDateFormat;
import java.util.Date;
import javax.annotation.PostConstruct;
import javax.faces.bean.ManagedBean;
import javax.faces.context.FacesContext;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import javax.servlet.ServletContext;

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
    private Connection conection;

    private EntityManagerFactory emf;

    @PostConstruct
    public void inicializar() {
//        emf = Persistence.createEntityManagerFactory("myDbFile.odb");
//        System.out.println("Entidades "+emf.getMetamodel().getEntities());

        ctx = (ServletContext) FacesContext.getCurrentInstance().getExternalContext().getContext();
        basePath = ctx.getRealPath("/");
        txtPath = "F://reportes//" + "backup//";
        System.out.println("Path " + txtPath);
    }

    public void backup() {
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
            String command = "C://xampp//mysql//bin/mysqldump --user=root --password=root --add-drop-database -B codefac -r " + "\"" + txtPath.toString() + "\\" + strFilename + ".sql\"";
            System.out.println(command);
            Process p = null;
            try {
                Runtime runtime = Runtime.getRuntime();
                p = runtime.exec(command);

                int processComplete = p.waitFor();

                if (processComplete == 0) {

                    // System.out.println("<html><font color='green'>Backup created successfully!</font></html>");
                    lblMessage = "Backup created successfully to " + txtPath.toString() + "\\" + strFilename + ".sql";

                } else {
                    lblMessage = "Could not create the backup";
                }

            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    public String getLblMessage() {
        return lblMessage;
    }

    public void setLblMessage(String lblMessage) {
        this.lblMessage = lblMessage;
    }

}
