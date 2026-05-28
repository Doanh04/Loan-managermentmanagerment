package com.identity.service;

import com.identity.Constain.PermissionEnum;
import com.identity.Constain.RolesEnum;
import com.identity.Maper.RolePermissionMaper;
import com.identity.Repositoty.PerrmissionRepository;
import com.identity.Repositoty.RolesRepository;
import com.identity.dto.response.RolePermissionReponse;
import com.identity.entity.Permission;
import com.identity.entity.Roles;
import com.identity.exception.AppException;
import com.identity.exception.ErrorCode;
import jakarta.transaction.Transactional;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.Collections;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Service
@RequiredArgsConstructor
@Slf4j
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class RolePermissionService {
    RolesRepository roleRepository;
    PerrmissionRepository permissonRepository;
    RolePermissionMaper rolePermissionMapper;

//    Service  Role_Permission
    @Transactional
    public RolePermissionReponse createRolePermission(RolesEnum RoleRequest, PermissionEnum permissionEnum){
        Roles role = roleRepository.findById(RoleRequest)
                .orElseThrow(() -> new AppException(ErrorCode.ROLE_INVALID));

        Permission permission = permissonRepository.findById(permissionEnum)
                .orElseThrow(() -> new AppException(ErrorCode.PERMISSION_IS_EXITED));


        Set<Permission> permissionSet = new HashSet<>();
        permissionSet.add(permission);

        role.setPermission(permissionSet);

        roleRepository.save(role);

        return rolePermissionMapper.toRolePermissionReponse(role);
    }


}
