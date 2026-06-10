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
import org.springframework.security.access.prepost.PreAuthorize;
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
    @PreAuthorize("hasAnyAuthority('ROLE_ADMIN') and hasAnyAuthority('PERMISSION_SYSTEM_CONFIG')")
    public RolePermissionReponse createRolePermission(RolesEnum RoleRequest, PermissionEnum permissionEnum){
        Roles role = roleRepository.findById(RoleRequest)
                .orElseThrow(() -> new AppException(ErrorCode.ROLE_NOT_FOUND));

        Permission permission = permissonRepository.findById(permissionEnum)
                .orElseThrow(() -> new AppException(ErrorCode.PERMISSION_NOT_FOUND));

        Set<Permission> allPermisison = role.getPermission();
        if(allPermisison.contains(permission)) throw new AppException(ErrorCode.PERMISSION_IS_EXITED);

        allPermisison.add(permission);

        roleRepository.save(role);

        return rolePermissionMapper.toRolePermissionReponse(role);
    }

    @PreAuthorize("hasAnyAuthority('ROLE_ADMIN') and hasAnyAuthority('PERMISSION_SYSTEM_CONFIG')")
    public List<RolePermissionReponse> getAllRolePermission(){
        List<Roles> rolePermission = roleRepository.findAll();

        return rolePermission.stream().map(rolePermissionMapper::toRolePermissionReponse).toList();
    }

    @PreAuthorize("hasAnyAuthority('ROLE_ADMIN') and hasAnyAuthority('PERMISSION_SYSTEM_CONFIG')")
    public void removePermissionFromRole(RolesEnum roles, PermissionEnum permissions){
        Roles role = roleRepository.findById(roles)
                .orElseThrow(()-> new AppException(ErrorCode.ROLE_NOT_FOUND));

        Permission permission = permissonRepository.findById(permissions)
                .orElseThrow(()->new AppException(ErrorCode.PERMISSION_NOT_FOUND));

        role.getPermission().remove(permission);

        roleRepository.save(role);
    }

    @PreAuthorize("hasAnyAuthority('ROLE_ADMIN') and hasAnyAuthority('PERMISSION_SYSTEM_CONFIG')")
    public void clearAllRolePermission(RolesEnum roles){
        Roles role = roleRepository.findById(roles)
                .orElseThrow(() -> new AppException(ErrorCode.ROLE_NOT_FOUND));

        role.getPermission().clear();

        roleRepository.save(role);
    }
}
