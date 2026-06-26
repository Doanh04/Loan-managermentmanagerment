package com.LoanManagerment.Notification.Controler;

import com.LoanManagerment.Notification.dto.request.SmsRequest;
import com.LoanManagerment.Notification.service.NotificationService;
import com.LoanManagerment.event.dto.messageOtpDto;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import lombok.extern.slf4j.Slf4j;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RestController;

@Controller
@RestController("/send")
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

}
