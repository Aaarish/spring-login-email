package com.example.springloginemail.email;

import jakarta.mail.*;
import jakarta.mail.internet.InternetAddress;
import jakarta.mail.internet.MimeMessage;
import org.springframework.stereotype.Service;

import java.util.Properties;

@Service
public class EmailSender {

    public String sendEMail(String to, String from, String subject, String text) throws MessagingException {
        Properties properties = new Properties();

        properties.put("mail.smtp.auth", true);
        properties.put("mail.smtp.starttls.enable", true);
        properties.put("mail.smtp.host", "smtp.gmail.com");
        properties.put("mail.smtp.port", "587");


        String email = "aarishm767";
        String password = "giegiafrrgbinjtv";

        Session session = Session.getInstance(properties, new Authenticator() {
            @Override
            protected PasswordAuthentication getPasswordAuthentication() {
                return new PasswordAuthentication(email, password);
            }
        });

        Message message = new MimeMessage(session);
        message.setRecipient(Message.RecipientType.TO, new InternetAddress(to));
        message.setFrom(new InternetAddress(from));
        message.setSubject(subject);
        message.setText(text);

        Transport.send(message);

        return "Email sent successfully";
    }
}
