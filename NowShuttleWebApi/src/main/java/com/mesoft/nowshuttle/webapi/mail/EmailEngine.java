package com.mesoft.nowshuttle.webapi.mail;

import java.util.Properties;

import javax.mail.Message;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;

public class EmailEngine {
    
    public static void sendMail(String email, String password) throws Exception {
        String host = "smtp.gmail.com";
        String from = "mehmetersinbitirgen";
        String pass = "sivrihisar";
        Properties props = System.getProperties();
        props.put("mail.smtp.starttls.enable", "true"); // added this line
        props.put("mail.smtp.host", host);
        props.put("mail.smtp.user", from);
        props.put("mail.smtp.password", pass);
        props.put("mail.smtp.port", "587");
        props.put("mail.smtp.auth", "true");
        
        String[] to = { email }; // added this line
        
        Session session = Session.getDefaultInstance(props, null);
        MimeMessage message = new MimeMessage(session);
        message.setFrom(new InternetAddress(from));
        
        InternetAddress[] toAddress = new InternetAddress[to.length];
        
        // To get the array of addresses
        for (int i = 0; i < to.length; i++) { // changed from a while loop
            toAddress[i] = new InternetAddress(to[i]);
        }
        System.out.println(Message.RecipientType.TO);
        
        for (int i = 0; i < toAddress.length; i++) { // changed from a while loop
            message.addRecipient(Message.RecipientType.TO, toAddress[i]);
        }
        message.setSubject("Registration Confirmation - NowShuttle");
        message.setText("Thank you for registration to NowShuttle application. Your Registered email :" + email + "Your Password :" + password);
        Transport transport = session.getTransport("smtp");
        transport.connect(host, from, pass);
        transport.sendMessage(message, message.getAllRecipients());
        transport.close();
        
    }
}