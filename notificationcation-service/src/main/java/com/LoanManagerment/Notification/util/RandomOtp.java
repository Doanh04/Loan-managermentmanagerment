package com.LoanManagerment.Notification.util;

import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;

import java.util.Random;


@Component
public class RandomOtp {
    public static String OtpRandomUtil(){
        Random random = new Random();
        int otp = 000000 + random.nextInt(999999);
        return String.valueOf(otp);
    }
}
