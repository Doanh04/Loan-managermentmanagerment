package com.LoanManagerment.event.dto;

import lombok.*;

import java.util.Map;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class messageOtpDto {
    String chanel;
    String repicient;
    String templateCode;
    Map<String, Object> param;
    String subject;
    String body;
}
