package com.demo.demo.Service;

import com.demo.demo.Model.Users;
import org.springframework.stereotype.Service;
import javax.mail.*;
import javax.mail.internet.*;
import java.io.IOException;
import java.util.Date;
import java.util.Properties;

@Service
public class EmailSender {
    public String sendmail(String userEmail, String userPassword) throws AddressException, MessagingException, IOException {

        Properties prop = new Properties();
        prop.put("mail.smtp.auth", "true");
        prop.put("mail.smtp.starttls.enable", "true");
        prop.put("mail.smtp.host", "smtp.gmail.com");
        prop.put("mail.smtp.port", "587");
        Session session = Session.getInstance(prop, new javax.mail.Authenticator() {
            protected PasswordAuthentication getPasswordAuthentication() {
                return new PasswordAuthentication("testspring800@gmail.com", "test8080");
            }

        });

        Message message = new MimeMessage(session);
        message.setFrom(new InternetAddress(userEmail, false));

        message.setRecipients(Message.RecipientType.TO, InternetAddress.parse(userEmail));
        message.setSubject("You are successfully registered");
        message.setContent("Welcome to Spring boot", "text/html");
        message.setSentDate(new Date());

        MimeBodyPart messageBodyPart = new MimeBodyPart();
        messageBodyPart.setContent("Welcome to Spring boot", "text/html");
        Multipart multipart = new MimeMultipart();
        multipart.addBodyPart(messageBodyPart);

        message.setContent(multipart);
        Transport.send(message);
        return "Email sent Successfully";
    }
}
