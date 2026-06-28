package com.LoanManagerment.Notification.service;

import com.LoanManagerment.Notification.Exception.AppException;
import com.LoanManagerment.Notification.Exception.ErrorCode;
import com.LoanManagerment.Notification.Repository.OtpRepository;
import com.LoanManagerment.Notification.Repository.httpClient.SmsClient;
import com.LoanManagerment.Notification.dto.request.EmailRequest;
import com.LoanManagerment.Notification.dto.request.RecipientRequest;
import com.LoanManagerment.Notification.dto.request.SenderRequest;
import com.LoanManagerment.Notification.dto.request.SmsRequest;
import com.LoanManagerment.Notification.dto.response.SendEmailResponse;
import com.LoanManagerment.Notification.dto.response.SmsResponse;
import com.LoanManagerment.Notification.util.RandomOtp;
import feign.FeignException;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import lombok.experimental.NonFinal;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Service;

import java.time.Duration;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collector;
import java.util.stream.Collectors;

@Slf4j
@Service
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
@RequiredArgsConstructor
public class NotificationService {
    @Value("${notification.sms.brevo-apikey}")
    @NonFinal
    String brevoApiKey;

    OtpRepository otpRepository;
    SmsClient smsClient;
    StringRedisTemplate redistemplate;

    public SmsResponse sendOtp(SmsRequest smsRequest){

        String otp = RandomOtp.OtpRandomUtil();
        String key = "otp:sms:" + smsRequest.getRecipient();
        redistemplate.opsForValue().set(
                key,
                otp,
                Duration.ofMinutes(10)
        );
        log.info("Key và OTP là {}",key + otp);
        SmsRequest sms = SmsRequest.builder()
                .recipient(smsRequest.getRecipient())
                .sender("DCOST")
                .unicodeEnabled(true)
                .content("DCOST: Vui lòng không chia sẻ mã OTP này, Đây là mã dùng một lần để xác thực tài khoản của bạn, mã xác nhận có thời gian 10 phút. Mã xác thực là " + smsRequest.getRecipient()
                + ", nhắc lại. Mã xác thực là " + otp + "nhắc lại OTP là: " + otp)
                .build();

        try{
            return smsClient.sendOTPSms(brevoApiKey, sms);
        }
        catch (FeignException e){
            log.error("Lỗi khi gọi Brevo API: {}", e.contentUTF8());
            throw new AppException(ErrorCode.CANNOT_SEND_SMS);
        }
    }
    public SendEmailResponse sendEmail(EmailRequest request){
        EmailRequest emailRequest = EmailRequest.builder()
                .to(request.getTo().stream().map(r -> RecipientRequest.builder()
                        .email(r.getEmail())
                        .name(r.getName())
                        .build()).collect(Collectors.toList()))
                .sender(SenderRequest.builder()
                        .email(request.getSender().getEmail())
                        .name(request.getSender().getName())
                        .build())
                .build();

        try{
            SendEmailResponse response = smsClient.sendEmail(brevoApiKey, emailRequest);
            log.info("Brevo API phản hồi: {}", response);
            return response;
        }
        catch (FeignException e){
            log.error("Lỗi khi gọi Brevo API Email: {}", e.contentUTF8());
            throw new AppException(ErrorCode.CANNOT_SEND_SMS);
        }
    }
}
