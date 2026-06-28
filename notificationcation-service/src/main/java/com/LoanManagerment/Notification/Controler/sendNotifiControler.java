package com.LoanManagerment.Notification.Controler;

import com.LoanManagerment.Notification.dto.request.EmailRequest;
import com.LoanManagerment.Notification.dto.request.RecipientRequest;
import com.LoanManagerment.Notification.dto.request.SenderRequest;
import com.LoanManagerment.Notification.dto.request.SmsRequest;
import com.LoanManagerment.Notification.dto.response.SmsResponse;
import com.LoanManagerment.Notification.service.NotificationService;
import com.LoanManagerment.event.dto.SendEmailDTO;
import com.LoanManagerment.event.dto.messageOtpDto;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import lombok.extern.slf4j.Slf4j;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/send")
@RequiredArgsConstructor
@Slf4j
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class sendNotifiControler {
    NotificationService notificationService;

    @KafkaListener(topics = "notification-sms-v3")
    public void listionNotificationSms(messageOtpDto sms){
        log.info("Message sms:{} ", sms );
        notificationService.sendOtp(SmsRequest.builder()
                        .recipient(sms.getRepicient())
                .build());
    }

    @KafkaListener(topics = "notification-email")
    public void listionNotificationEmail(SendEmailDTO sendEmailDTO){
        log.info("Message sms:{} ", sendEmailDTO );
        notificationService.sendEmail(EmailRequest.builder()
                        .sender(SenderRequest.builder()
                                .email("luonghamduc123@gmail.com")
                                .name("DCOST")
                                .build())
                        .to(List.of(RecipientRequest.builder()
                                        .email(sendEmailDTO.getRepicient())
                                        .name(sendEmailDTO.getName())
                                .build()))
                .build());
    }

    @PostMapping("/email")
    public SmsResponse smsResponse (@RequestBody EmailRequest emailRequest){
        notificationService.sendEmail(emailRequest);
        return SmsResponse.builder()

                .build();
    }
}
