package com.LoanManagerment.Notification.Repository.httpClient;

import com.LoanManagerment.Notification.dto.request.EmailRequest;
import com.LoanManagerment.Notification.dto.request.SmsRequest;
import com.LoanManagerment.Notification.dto.response.SendEmailResponse;
import com.LoanManagerment.Notification.dto.response.SmsResponse;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;

@FeignClient(name = "sms-client", url = "${notification.sms.brevo-url:https://api.brevo.com/v3}")
public interface SmsClient {
    @PostMapping(value = "/transactionalSMS/send", produces = MediaType.APPLICATION_JSON_VALUE)
    SmsResponse sendOTPSms(@RequestHeader("api-key") String apiKey, @RequestBody SmsRequest smsRequest);

    @PostMapping(value = "/smtp/email", produces = MediaType.APPLICATION_JSON_VALUE)
    SendEmailResponse sendEmail(@RequestHeader("api-key") String apikey, @RequestBody EmailRequest emailRequest);
}
