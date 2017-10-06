
package com.mycompany.sms;

import java.util.*;
import javax.mail.*;
import javax.mail.internet.*;


public class SendEmail  {
    String msg = "NO2";
    public SendEmail( String from2, String password2,String to2,String sub2,String msg2) {  
  
        final String username = from2;
        final String password = password2;

        Properties props = new Properties();
        props.put("mail.smtp.auth", "true");
        props.put("mail.smtp.starttls.enable", "true");
        props.put("mail.smtp.host", "smtp.gmail.com");
        props.put("mail.smtp.port", "587");
        
        Session session = Session.getInstance(props,
          new javax.mail.Authenticator() {
            protected PasswordAuthentication getPasswordAuthentication() {
                return new PasswordAuthentication(username, password);
            }
          });
        try {
            Message message = new MimeMessage(session);
            message.setFrom(new InternetAddress(from2));
            message.setRecipients(Message.RecipientType.TO,
                InternetAddress.parse(to2));
            message.setSubject(sub2);
            message.setText("Hello Dear,"
                + "\n\n if you forget your password and want to change it, please copy this code and put it in the form to change your password! \n\n"+msg2);
            
            Transport.send(message);
            
            msg="Done";

        } catch (MessagingException e) {
            msg = e.toString();
            throw new RuntimeException(e);
        }
    }
}
