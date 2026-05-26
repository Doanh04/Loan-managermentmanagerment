package com.identity.dto.request;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.identity.Constain.PermissionEnum;
import com.identity.Constain.PermissionNameEnum;
import com.identity.customAntotaion.ValidateEnum;
import lombok.*;
import lombok.experimental.FieldDefaults;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
@JsonInclude(JsonInclude.Include.NON_NULL)
public class PermissionRequest {
    @ValidateEnum(enumCLASS = PermissionEnum.class, message = "PERMISSION_INVALID")
    String permision;
    @ValidateEnum(enumCLASS = PermissionNameEnum.class, message = "PERMISION_NAME_INVALID")
    String permissionName;
    String description;
}
