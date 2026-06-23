package com.LoanManagerment.Notification.service;

import com.LoanManagerment.Notification.Exception.AppException;
import com.LoanManagerment.Notification.Exception.ErrorCode;
import com.LoanManagerment.Notification.Repository.OtpRepository;
import com.LoanManagerment.Notification.Repository.httpClient.SmsClient;
import com.LoanManagerment.Notification.dto.request.SmsRequest;
import com.LoanManagerment.Notification.dto.response.SmsResponse;
import com.LoanManagerment.Notification.util.RandomOtp;
import feign.FeignException;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import lombok.experimental.NonFinal;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Service;

import java.time.Duration;

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
    RandomOtp randomOtp;

    public SmsResponse sendOtp(SmsRequest smsRequest){
        SmsRequest sms = SmsRequest.builder()
                .recipient(smsRequest.getRecipient())
                .sender("D_COST")
                .unicodeEnabled(true)
                .content("Đây là mã dùng một lần để xác thực tài khoản của bạn. Mã xác thực là " + smsRequest.getRecipient()
                + ", nhắc lại. Mã xác thực là " + smsRequest.getRecipient())
                .build();

        try{
            String otp = RandomOtp.OtpRandomUtil();
            String key = smsRequest.getRecipient();
            redistemplate.opsForValue().set(
                    key,
                    otp,
                    Duration.ofMinutes(10)
            );
            return smsClient.sendOTPSms(brevoApiKey, sms);
        }
        catch (FeignException e){
            throw new AppException(ErrorCode.CANNOT_SEND_SMS);
        }
    }

    
    public boolean verifyOtp(String key, String inputOtp){
        String otpInRedis = redistemplate.opsForValue().get(key);

        if(otpInRedis == null) return false;

        if(otpInRedis.equals(inputOtp)){
            redistemplate.delete(key);
            return true;
        }

        return false;
    }
}
