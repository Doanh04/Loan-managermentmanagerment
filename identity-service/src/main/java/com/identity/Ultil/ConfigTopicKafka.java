package com.identity.Ultil;

import com.event.dto.SendEmailDTO;
import com.event.dto.messageOtpDto;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class ConfigTopicKafka {
    private final KafkaTemplate<String, Object> kafkaTemplate;

    public void sendSms(String phoneNumber){
        messageOtpDto Otp = messageOtpDto.builder()
                .chanel("SMS")
                .repicient(phoneNumber)
                .subject("Welcome")
                .body("......")
                .build();

        kafkaTemplate.send("notification-sms-v3", Otp);
    }

    public void sendEmailWelcome(String email, String name){
        SendEmailDTO sendEmail = SendEmailDTO.builder()
                .chanel("EMAIL")
                .repicient(email)
                .name(name)
                .subject("Welcome")
                .body("..............")
                .build();

        kafkaTemplate.send("notification-email", sendEmail);
    }
}
