package com.LoanManagerment.Notification.dto.request;

import lombok.*;

import java.util.List;

@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class EmailRequest {
    SenderRequest sender;
    List<RecipientRequest> to;

    @Builder.Default
    int templateId = 1;
}
