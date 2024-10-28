package com.labotec.traccar.app.utils.mail;

import org.springframework.stereotype.Service;

import javax.mail.*;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;
import java.util.Properties;

@Service
public class EmailSender {
    public static void main(String[] args) {
        // Configura las propiedades del correo
        Properties props = new Properties();
        props.put("mail.smtp.auth", "true");
        props.put("mail.smtp.starttls.enable", "true");
        props.put("mail.smtp.host", "smtp.gmail.com");
        props.put("mail.smtp.port", "587");

        // Autenticación
        final String username = "contact.alert.page.web@gmail.com"; // tu correo
        final String password = "asna pydw hmvh qxxo"; // tu contraseña

        Session session = Session.getInstance(props, new Authenticator() {
            protected PasswordAuthentication getPasswordAuthentication() {
                return new PasswordAuthentication(username, password);
            }
        });

        try {
            // Crea un objeto MimeMessage
            Message message = new MimeMessage(session);
            message.setFrom(new InternetAddress("contact.alert.page.web@gmail.com"));
            message.setRecipients(Message.RecipientType.TO, InternetAddress.parse("cerroteberes@gmail.com"));
            message.setSubject("Asunto del correo");
            message.setText("Cuerpo del correo");

            // Envía el correo
            Transport.send(message);
            System.out.println("Correo enviado exitosamente");

        } catch (MessagingException e) {
            System.out.println(e.getMessage());
        }
    }
}
