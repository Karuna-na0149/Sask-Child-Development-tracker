package com.sask.tracker.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import com.sask.tracker.exception.EmailDeliveryException;

import software.amazon.awssdk.services.ses.SesClient;
import software.amazon.awssdk.services.ses.model.*;

@Service
public class EmailService {

    @Value("${aws.sender}")
    private String sender;

    @Autowired
    private SesClient sesClient;

    //  Return boolean to track if email was sent successfully
    public boolean sendNotification(String recipient, String subject, String body) {
        try {
            SendEmailRequest request = SendEmailRequest.builder()
                    .destination(Destination.builder()
                            .toAddresses(recipient)
                            .build())
                    .message(Message.builder()
                            .subject(Content.builder().data(subject).build())
                            .body(Body.builder().text(Content.builder().data(body).build()).build())
                            .build())
                    .source(sender)
                    .build();

            sesClient.sendEmail(request);
            return true; //  Success
        }  catch (SesException e) {
            throw new EmailDeliveryException("Failed to deliver email to: " + recipient, e);
        }
    }
}
