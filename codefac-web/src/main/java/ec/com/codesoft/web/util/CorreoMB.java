/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ec.com.codesoft.web.util;

import java.util.Properties;
import javax.activation.DataHandler;
import javax.activation.FileDataSource;
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
    private String Username;

    public CorreoMB() 
    {

        Username = "carlosmast2302@gmail.com";
    }

    public void EnviarCorreoSinArchivoAdjunto(String To, String Subject, String TextoCorreo) {
        //Ejemplo de envio
        //To: "aledennis.93@gmail.com"
        //Subject: "Titulo correo"
        //TextoCorreo: "Esto es una prueba"

        //Si se necesita que tenga formato el texto
        //Cambiar message.setText(TextoCorreo);
        //por
        //message.setContent(TextoCorreo,"text/html" );
        //el texto debe venir en html: "<h1>El mensaje de nuestro primer correo HTML</h1>"
        Properties props = new Properties();
        props.put("mail.smtp.auth", "true");
        props.put("mail.smtp.starttls.enable", "true");
        props.put("mail.smtp.host", "smtp.gmail.com");
        props.put("mail.smtp.ssl.trust", "smtp.gmail.com");
        props.put("mail.smtp.port", "587");

        SmtpAuthenticator authentication = new SmtpAuthenticator();
        Session session = Session.getInstance(props, authentication);

        try {

            Message message = new MimeMessage(session);
            message.setFrom(new InternetAddress(Username));
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

        SmtpAuthenticator authentication = new SmtpAuthenticator();
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
            message.setFrom(new InternetAddress(Username));
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
}
