package com.LoanManagerment.Notification.dto.request;

import lombok.*;

@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class SenderRequest {
    String email;
    String name;
}
