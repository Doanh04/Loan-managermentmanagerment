package com.identity.dto.request;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.identity.Constain.UserStatus;
import com.identity.customAntotaion.ValidateEnum;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.*;
import lombok.experimental.FieldDefaults;

import java.time.Instant;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
@JsonInclude(JsonInclude.Include.NON_NULL)
public class UserCreationRequest {
    String userName;
    @Size(min = 8, message = "INVALID_PASSWORD")
    String passWord;
    String phoneNumber;
    @Email(message = "INVALID_EMAIL")
    @NotBlank(message = "EMAIL_IS_REQUIRED")
    String email_verified;
}
