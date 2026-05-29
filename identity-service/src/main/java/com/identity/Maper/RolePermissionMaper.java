package com.identity.Maper;

import com.identity.dto.response.PermissionReponse;
import com.identity.dto.response.RolePermissionReponse;
import com.identity.entity.Permission;
import com.identity.entity.Roles;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface RolePermissionMaper {
//    @Mapping(source = "Permission", target = "permission")
//    @Mapping(source = "Permission_name", target = "permissionName")
//    @Mapping(source = "Desciption", target = "description")
//    PermissionReponse toPermissionResponse(Permission permission);

    @Mapping(source = "role", target = "role")
    @Mapping(source = "permission", target = "permissionReponse")
    RolePermissionReponse toRolePermissionReponse(Roles role);
}
