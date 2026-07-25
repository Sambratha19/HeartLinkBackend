package com.example.heart_link_backend.Service;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.*;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

@Service
public class EmailService {

    @Value("${brevo.api.key}")
    private String apiKey;

    private final RestTemplate restTemplate = new RestTemplate();


    public void sendVerificationEmail(String email, String token) {

        String link =
                "https://heartlinkbackend.onrender.com/auth/verify?token=" + token;


        String htmlContent =
                """
                <h2>Welcome to Heart Link ❤️</h2>
                
                <p>Please verify your email by clicking the link below:</p>
                
                <a href="%s">
                    Verify Email
                </a>
                
                <br><br>
                
                <p>Thank you,<br>
                Heart Link Team</p>
                """.formatted(link);


        sendEmail(
                email,
                "Heart Link ❤️ Email Verification",
                htmlContent
        );
    }



    public void sendInviteEmail(
            String toEmail,
            String senderEmail,
            String senderName,
            Long inviteId
    ) {

        String baseUrl =
                "https://heartlinkbackend.onrender.com";


        String acceptLink =
                baseUrl + "/invite/accept/" + inviteId;

        String rejectLink =
                baseUrl + "/invite/reject/" + inviteId;


        String htmlContent =
                """
                <h2>❤️ HeartLink Invitation</h2>

                <p>
                <b>%s</b> (%s) invited you.
                </p>

                <br>

                <a href="%s">
                    Accept Invitation
                </a>

                <br><br>

                <a href="%s">
                    Reject Invitation
                </a>

                <br><br>

                <p>
                Thank you,<br>
                HeartLink Team
                </p>
                """
                        .formatted(
                                senderName,
                                senderEmail,
                                acceptLink,
                                rejectLink
                        );


        sendEmail(
                toEmail,
                "HeartLink Invitation",
                htmlContent
        );
    }



    private void sendEmail(
            String receiver,
            String subject,
            String htmlContent
    ) {

        String url =
                "https://api.brevo.com/v3/smtp/email";


        HttpHeaders headers = new HttpHeaders();

        headers.setContentType(MediaType.APPLICATION_JSON);
        headers.set("api-key", apiKey);


        String body =
                """
                {
                  "sender": {
                    "name": "Heart Link",
                    "email": "sithukitu1616@gmail.com"
                  },
                  "to": [
                    {
                      "email": "%s"
                    }
                  ],
                  "subject": "%s",
                  "htmlContent": "%s"
                }
                """
                        .formatted(
                                receiver,
                                subject,
                                htmlContent
                                        .replace("\"", "\\\"")
                                        .replace("\n", "")
                        );


        HttpEntity<String> request =
                new HttpEntity<>(body, headers);


        try {

            ResponseEntity<String> response =
                    restTemplate.postForEntity(
                            url,
                            request,
                            String.class
                    );


            System.out.println(
                    "Brevo response: "
                            + response.getBody()
            );

            System.out.println(
                    "Email sent successfully to: "
                            + receiver
            );

        }
        catch(Exception e){

            System.out.println(
                    "Email sending failed: "
                            + e.getMessage()
            );
        }
    }
}