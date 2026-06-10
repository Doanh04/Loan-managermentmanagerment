package com.identity.Maper;

import com.identity.dto.request.PermissionRequest;
import com.identity.dto.response.PermissionReponse;
import com.identity.entity.Permission;
import org.mapstruct.Builder;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring", builder = @Builder(disableBuilder = true))
public interface PermissionMaper {
    @Mapping(source = "permision", target = "permission")
    @Mapping(source = "permissionName", target = "permission_name")
    @Mapping(source = "description", target = "desciption")
    Permission toPermissionEntity (PermissionRequest permissionRequest);

    @Mapping(source = "permission", target = "permission")
    @Mapping(source = "permission_name", target = "permissionName")
    @Mapping(source = "desciption", target = "description")
    PermissionReponse toPermissionReponse(Permission permissionResponse);
}
