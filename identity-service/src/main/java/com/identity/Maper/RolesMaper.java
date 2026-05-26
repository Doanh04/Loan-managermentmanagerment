package com.identity.Maper;

import com.identity.dto.request.RolesRequest;
import com.identity.dto.response.RoleResponse;
import com.identity.entity.Roles;
import org.mapstruct.Builder;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring", builder = @Builder(disableBuilder = true))
public interface RolesMaper {
    Roles toRolesEntity (RolesRequest request);

    RoleResponse toRoleResponse (Roles roleEntity);
}
