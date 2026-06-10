package com.identity.dto.response;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.identity.Constain.UserStatus;
import com.identity.customAntotaion.ValidateEnum;
import lombok.*;
import lombok.experimental.FieldDefaults;

import java.time.Instant;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Set;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
@JsonInclude(JsonInclude.Include.NON_NULL)
public class UserCreationResponse {
    String userId;
    String userName;
    String phoneNumber;
    String status;
    String email_verified;
    boolean verified;
    Set<RolePermissionReponse> rolePermissionResponse;
    LocalDateTime create_at;
}
