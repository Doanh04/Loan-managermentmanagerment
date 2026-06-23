package com.LoanManagerment.Notification.entity;

import lombok.*;
import org.springframework.data.annotation.Id;
import org.springframework.data.redis.core.RedisHash;

@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
@RedisHash("otp:sms")
public class OtpRedis {
    @Id
    private String phoneNumber;

    private String code;
}
