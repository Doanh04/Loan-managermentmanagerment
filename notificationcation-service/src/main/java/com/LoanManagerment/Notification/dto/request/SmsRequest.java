package com.LoanManagerment.Notification.dto.request;

import lombok.*;

@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class SmsRequest {
    private String recipient;
    private String sender;
    private boolean unicodeEnabled;
    private String content;
}
