/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ec.com.codesoft.web.util;

import java.util.Properties;
import javax.activation.DataHandler;
import javax.activation.FileDataSource;
import javax.ejb.Asynchronous;
import javax.mail.BodyPart;
import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeBodyPart;
import javax.mail.internet.MimeMessage;
import javax.mail.internet.MimeMultipart;

/**
 *
 * @author carlo
 */
public class CorreoMB
{
    private String username;
    private String clave;
    
    /**
     * Hilo que me permite controlar el envio del correo
     */
    //private Thread hilo;

    public CorreoMB(String nick,String clave) 
    {        
        this.username = nick;
        this.clave=clave;
        System.out.println(username+" "+this.clave);
        //this.hilo=new Thread(this);
    }

    @Asynchronous
    public void EnviarCorreoSinArchivoAdjunto(String To, String Subject, String TextoCorreo) {

        Properties props = new Properties();
        props.put("mail.smtp.auth", "true");
        props.put("mail.smtp.starttls.enable", "true");
        props.put("mail.smtp.host", "smtp.gmail.com");
        props.put("mail.smtp.ssl.trust", "smtp.gmail.com");
        props.put("mail.smtp.port", "587");

        SmtpAuthenticator authentication = new SmtpAuthenticator(username,clave);
        Session session = Session.getInstance(props, authentication);

        try {

            Message message = new MimeMessage(session);
            message.setFrom(new InternetAddress(username));
            message.setRecipients(Message.RecipientType.TO,
                    InternetAddress.parse(To));
            message.setSubject(Subject);
            //message.setText(TextoCorreo);
            message.setContent(TextoCorreo,"text/html; charset=utf-8");

            Transport.send(message);
            System.out.println("Su mensaje ha sido enviado");

        } catch (MessagingException e) {
            throw new RuntimeException(e);
        }
    }

    public void EnviarCorreoConArchivoAdjunto(String To, String Subject, String TextoCorreo,
            String PathArchivo, String NombreArchivo) {
        //Ejemplo de envio
        //To: "aledennis.93@gmail.com"
        //Subject: "Titulo correo"
        //TextoCorreo: "Esto es una prueba"
        //PathArchivo: "d:/ISO38500.pdf"
        //NombreArchivo: "ISO38500.pdf"
        Properties props = new Properties();
        props.put("mail.smtp.auth", "true");
        props.put("mail.smtp.starttls.enable", "true");
        props.put("mail.smtp.host", "smtp.gmail.com");
        props.put("mail.smtp.ssl.trust", "smtp.gmail.com");
        props.put("mail.smtp.port", "587");

        SmtpAuthenticator authentication = new SmtpAuthenticator(username,clave);
        Session session = Session.getInstance(props, authentication);

        try {
            // Se compone la parte del texto
            BodyPart texto = new MimeBodyPart();
            texto.setText(TextoCorreo);

            // Se compone el adjunto con la imagen
            BodyPart adjunto = new MimeBodyPart();
            adjunto.setDataHandler(
                    new DataHandler(new FileDataSource(PathArchivo)));
            adjunto.setFileName(NombreArchivo);

            // Una MultiParte para agrupar texto e imagen.
            MimeMultipart multiParte = new MimeMultipart();
            multiParte.addBodyPart(texto);
            multiParte.addBodyPart(adjunto);
            
            

            // Se compone el correo, dando to, from, subject y el
            // contenido.
            MimeMessage message = new MimeMessage(session);
            message.setFrom(new InternetAddress(username));
            message.setRecipients(Message.RecipientType.TO,
                    InternetAddress.parse(To));
            message.setSubject(Subject);
            message.setContent(multiParte);

            // Se envia el correo.
            Transport.send(message);
            System.out.println("Su mensaje ha sido enviado");

        } catch (MessagingException e) {
            throw new RuntimeException(e);
        }
    }

    //@Override
    //public void run() 
    //{
        
    //}
}
