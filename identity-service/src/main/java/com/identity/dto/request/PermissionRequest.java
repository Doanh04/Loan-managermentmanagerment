package com.identity.dto.request;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.identity.Constain.PermissionEnum;
import com.identity.Constain.PermissionNameEnum;
import com.identity.customAntotaion.ValidateEnumPermission;
import jakarta.validation.constraints.NotBlank;
import lombok.*;
import lombok.experimental.FieldDefaults;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
@JsonInclude(JsonInclude.Include.NON_NULL)
public class PermissionRequest {
    @ValidateEnumPermission(enumCLASS = PermissionEnum.class, message = "PERMISSION_INVALID")
    String permision;
    @ValidateEnumPermission(enumCLASS = PermissionNameEnum.class, message = "PERMISION_NAME_INVALID")
    String permissionName;
    String description;
}
