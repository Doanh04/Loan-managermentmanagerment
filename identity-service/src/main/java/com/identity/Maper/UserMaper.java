package com.identity.Maper;

import com.identity.dto.request.UserCreationRequest;
import com.identity.dto.response.RolePermissionReponse;
import com.identity.dto.response.UserCreationResponse;
import com.identity.entity.Roles;
import com.identity.entity.User;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface UserMaper {
    @Mapping(source = "userName", target = "username")
    @Mapping(source = "passWord", target = "password")
    @Mapping(source = "phoneNumber", target = "phone_Number")
    @Mapping(source = "email_verified", target = "email_verified")
    User toUserEntity(UserCreationRequest userRequest);

    @Mapping(source = "user_Id", target = "userId")
    @Mapping(source = "username", target = "userName")
    @Mapping(source = "email_verified", target = "email_verified")
    @Mapping(source = "verified", target = "verified")
    @Mapping(source = "roles", target = "rolePermissionResponse")
    @Mapping(source = "phone_Number", target = "phoneNumber")
    @Mapping(source = "create_at", target = "create_at")
    UserCreationResponse toUserResponse(User userEntity);

    @Mapping(source = "role", target = "role")
    @Mapping(source = "permission", target = "permissionReponse")
    RolePermissionReponse toRolePermissionReponse(Roles role);
}
