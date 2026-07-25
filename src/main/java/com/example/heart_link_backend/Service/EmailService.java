package com.example.heart_link_backend.Service;

import jakarta.mail.internet.MimeMessage;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;

@Service
public class EmailService {

    @Autowired
    private JavaMailSender mailSender;


    public  void sendVerificationEmail(String email, String token){
        String link = "https://heartlinkbackend.onrender.com/auth/verify?token=" + token;

        String message = """
                Welcome to Heart Link ❤️
                
                Please verify your email by clicking the link below:
                """ + link;

        SimpleMailMessage messages = new SimpleMailMessage();
        messages.setTo(email);
        messages.setSubject("Heart Link ❤️ Email Verification");
        messages.setText("Welcome to Heart Link!\n\nClick below to verify your email:\n" + link);

        mailSender.send(messages);

        System.out.println("Email sent successfully to: " + email);
        // TODO: integrate SMTP (Gmail / SendGrid)
        System.out.println("Sending email to: " + email);
        System.out.println("Message: " + message);
    }

    public void sendInviteEmail(String toEmail, String senderEmail, String senderName, Long inviteId) {

        String baseUrl = "https://heartlinkbackend.onrender.com"; // change to deployed URL later

        String acceptLink = baseUrl + "/invite/accept/" + inviteId;
        String rejectLink = baseUrl + "/invite/reject/" + inviteId;

        String message =
                "❤️ HeartLink Invitation\n\n" +
                        senderName + " (" + senderEmail + ") invited you.\n\n" +
                        "Click below:\n\n" +
                        "Accept: " + acceptLink + "\n" +
                        "Reject: " + rejectLink + "\n\n" +
                        "Thank you,\nHeartLink Team";

        SimpleMailMessage mail = new SimpleMailMessage();
        mail.setTo(toEmail);
        mail.setSubject("HeartLink Invitation");
        mail.setText(message);

        mailSender.send(mail);
    }

}
