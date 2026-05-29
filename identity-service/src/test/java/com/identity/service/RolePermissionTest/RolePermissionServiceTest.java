package com.identity.service.RolePermissionTest;

import com.identity.Constain.PermissionEnum;
import com.identity.Constain.RolesEnum;
import com.identity.Maper.RolePermissionMaper;
import com.identity.Repositoty.PerrmissionRepository;
import com.identity.Repositoty.RolesRepository;
import com.identity.dto.response.PermissionRes;
import com.identity.dto.response.RolePermissionReponse;
import com.identity.entity.Permission;
import com.identity.entity.Roles;
import com.identity.exception.AppException;
import com.identity.exception.ErrorCode;
import com.identity.service.RolePermissionService;
import lombok.AccessLevel;
import lombok.experimental.FieldDefaults;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.HashSet;
import java.util.Optional;
import java.util.Set;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;

@ExtendWith(MockitoExtension.class)
@Slf4j
@FieldDefaults(level = AccessLevel.PRIVATE)
class RolePermissionServiceTest {

    @Mock RolesRepository roleRepository;
    @Mock PerrmissionRepository permissonRepository;
    @Mock RolePermissionMaper rolePermissionMaper;

    @InjectMocks RolePermissionService rolePermissionService;

    @Test
    void createRolesPermission_Success(){
        Permission permissionEntity = Permission.builder()
                .Permission(PermissionEnum.CUSTOMER_READ)
                .build();

        Roles roleEntity = Roles.builder()
                .role(RolesEnum.ADMIN)
                .permission(new HashSet<>())
                .build();

        RolePermissionReponse rolePermissionResponse = RolePermissionReponse.builder()
                .role("ADMIN")
                .permissionReponse(Set.of(PermissionRes.builder().permission("CUSTOMER_READ").build()))
                .build();

        Mockito.when(roleRepository.findById(any())).thenReturn(Optional.of(roleEntity));
        Mockito.when(permissonRepository.findById(any())).thenReturn(Optional.of(permissionEntity));
        Mockito.when(roleRepository.save(any())).thenReturn(roleEntity);
        Mockito.when(rolePermissionMaper.toRolePermissionReponse(any())).thenReturn(rolePermissionResponse);

        RolePermissionReponse result = rolePermissionService.createRolePermission(RolesEnum.ADMIN, PermissionEnum.CUSTOMER_READ);

        assertNotNull(result);
        assertEquals("ADMIN", result.getRole());
        assertEquals(1, result.getPermissionReponse().size());
    }

    @Test
    void createRolePermision_RoleNotFound(){
        Mockito.when(roleRepository.findById(any())).thenReturn(Optional.empty());

        AppException exception = assertThrows(AppException.class, () -> {
            rolePermissionService.createRolePermission(RolesEnum.ADMIN, PermissionEnum.CUSTOMER_READ);
        });

        assertEquals(ErrorCode.ROLE_NOT_FOUND, exception.getErrorCode());
    }

    @Test
    void createRolePermission_PermissionAlreadyExists(){
        Permission permissionEntity = Permission.builder()
                .Permission(PermissionEnum.CUSTOMER_READ)
                .build();

        Set<Permission> currentPermissions = new HashSet<>();
        currentPermissions.add(permissionEntity);

        Roles roleEntity = Roles.builder()
                .role(RolesEnum.ADMIN)
                .permission(currentPermissions)
                .build();

        Mockito.when(roleRepository.findById(any())).thenReturn(Optional.of(roleEntity));
        Mockito.when(permissonRepository.findById(any())).thenReturn(Optional.of(permissionEntity));

        AppException exception = assertThrows(AppException.class, () -> {
            rolePermissionService.createRolePermission(RolesEnum.ADMIN, PermissionEnum.CUSTOMER_READ);
        });

        assertEquals(ErrorCode.PERMISSION_IS_EXITED, exception.getErrorCode());
    }
}