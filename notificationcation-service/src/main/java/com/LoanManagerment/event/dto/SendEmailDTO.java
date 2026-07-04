package com.LoanManagerment.event.dto;

import lombok.*;

import java.util.Map;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class SendEmailDTO {
    String chanel;
    String repicient;
    String name;
    String templateCode;
    Map<String, Object> param;
    String subject;
    String body;
}
