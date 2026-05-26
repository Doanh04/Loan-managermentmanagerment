package com.identity.dto.request;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.identity.Constain.NameRoleEnum;
import com.identity.Constain.RolesEnum;
import com.identity.customAntotaion.ValidateEnum;
import lombok.*;
import lombok.experimental.FieldDefaults;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
@JsonInclude(JsonInclude.Include.NON_NULL)
public class RolesRequest {
    @ValidateEnum(enumCLASS = RolesEnum.class, message = "ROLE_INVALID")
    String role;
    @ValidateEnum(enumCLASS = NameRoleEnum.class, message = "ROLE_NAME_INVALID")
    String nameRole;
    String description;
}
