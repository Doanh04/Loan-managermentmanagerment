package com.identity.service;

import com.identity.Constain.NameRoleEnum;
import com.identity.Constain.RolesEnum;
import com.identity.Maper.RolesMaper;
import com.identity.Repositoty.RolesRepository;
import com.identity.dto.request.RolesRequest;
import com.identity.dto.response.RoleResponse;
import com.identity.entity.Roles;
import com.identity.exception.AppException;
import com.identity.exception.ErrorCode;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Service;

import java.util.Arrays;
import java.util.List;

@Service
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class RoleService {
    RolesRepository rolesRepository;
    RolesMaper roleMaper;

    @PreAuthorize("hasAnyAuthority('ROLE_ADMIN') and hasAnyAuthority('PERMISSION_SYSTEM_CONFIG')")
    public RoleResponse createRole(RolesRequest request){
        Roles roles = roleMaper.toRolesEntity(request);

        if(request.getRole()==null || request.getNameRole()==null) throw new AppException(ErrorCode.ROLE_INVALID);
        boolean exitedRole = rolesRepository.existsById(RolesEnum.valueOf(request.getRole()));
        if(exitedRole) throw new AppException(ErrorCode.ROLE_IS_EXITED);

        roles.setRole(RolesEnum.valueOf(request.getRole()));
        roles.setName_role(NameRoleEnum.valueOf(request.getNameRole()));
        roles.setDescription(request.getDescription());
        rolesRepository.save(roles);

        return roleMaper.toRoleResponse(roles);
    }

    @PreAuthorize("hasAnyAuthority('ROLE_ADMIN') and hasAnyAuthority('PERMISSION_SYSTEM_CONFIG')")
    public void deleteRole(String roles){
        if(roles == null) throw new AppException(ErrorCode.ROLE_INVALID);
        boolean exited = Arrays.stream(RolesEnum.values())
                .map(Enum::name)
                .anyMatch(name -> name.equals(roles));

        if(!exited) throw new AppException(ErrorCode.ROLE_NOT_BLANK);

        rolesRepository.deleteById(RolesEnum.valueOf(roles));
    }

    @PreAuthorize("hasAnyAuthority('ROLE_ADMIN') and hasAnyAuthority('PERMISSION_SYSTEM_CONFIG')")
    public List<RoleResponse> getAllRoles(){
        var result = rolesRepository.findAll();

        return result.stream().map(roleMaper::toRoleResponse).toList();
    }
}
