package com.identity.dto.response;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.identity.Constain.UserStatus;
import com.identity.customAntotaion.ValidateEnum;
import lombok.*;
import lombok.experimental.FieldDefaults;

import java.time.Instant;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
@JsonInclude(JsonInclude.Include.NON_NULL)
public class UserCreationResponse {
    String userName;
    String passWord;
    Instant createAt;
    String phoneNumber;
    String status;
    String email_verified;
}
