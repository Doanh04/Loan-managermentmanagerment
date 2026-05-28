package com.identity.service.RolePermissionTest;

import com.identity.Constain.PermissionEnum;
import com.identity.Constain.RolesEnum;
import com.identity.Maper.RolePermissionMaper;
import com.identity.Repositoty.PerrmissionRepository;
import com.identity.Repositoty.RolesRepository;
import com.identity.dto.response.PermissionReponse;
import com.identity.dto.response.PermissionRes;
import com.identity.dto.response.RolePermissionReponse;
import com.identity.entity.Permission;
import com.identity.entity.Roles;
import com.identity.service.RolePermissionService;
import lombok.AccessLevel;
import lombok.experimental.FieldDefaults;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Collections;
import java.util.HashSet;
import java.util.Optional;
import java.util.Set;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.ArgumentMatchers.any;

@ExtendWith(MockitoExtension.class)
@Slf4j
@FieldDefaults(level = AccessLevel.PRIVATE)
class RolePermissionServiceTest {
    @Mock
    RolesRepository roleRepository;
    @Mock
    PerrmissionRepository permissonRepository;
    @Mock
    RolePermissionMaper rolePermissionMaper;

    @InjectMocks
    RolePermissionService rolePermissionService;

    Roles roleEntity;
    Permission permissionEntity;
    RolePermissionReponse rolePermissionResponse;

    @BeforeEach
    void initData(){
        permissionEntity = Permission.builder()
                .Permission(PermissionEnum.CUSTOMER_CREATE)
                .build();

        // SỬA LỖI 2: Gán thẳng vào biến `roleEntity` của Class, không tạo biến local mới nữa
        roleEntity = Roles.builder()
                .role(RolesEnum.ADMIN)
                .permission(Set.of(permissionEntity))
                .build();

        PermissionRes mockPermissionResponseCreate = PermissionRes.builder()
                .permission("CUSTOMER_CREATE")
                .build();

        // Chuẩn bị sẵn Response DTO để tí nữa đối sánh kết quả kết xuất
        rolePermissionResponse = RolePermissionReponse.builder()
                .role("ADMIN")
                .permissionReponse(Set.of(mockPermissionResponseCreate))
                .build();
    }

//    Function test successfully
    @Test
    void createRolesPermission_Success(){
        Mockito.when(roleRepository.findById(any())).thenReturn(Optional.ofNullable(roleEntity));
        Mockito.when(permissonRepository.findById(any())).thenReturn(Optional.ofNullable(permissionEntity));
        Mockito.when(roleRepository.save(any())).thenReturn(roleEntity);
        Mockito.when(rolePermissionMaper.toRolePermissionReponse(any())).thenReturn(rolePermissionResponse);

        RolePermissionReponse result = rolePermissionService.createRolePermission(RolesEnum.ADMIN, PermissionEnum.CUSTOMER_READ);

        Set<PermissionRes> expectedSet = Set.of(
                PermissionRes.builder().permission("CUSTOMER_CREATE").build()
        );
        assertNotNull(result);
        assertEquals("ADMIN", result.getRole());
        assertEquals(expectedSet, result.getPermissionReponse());

    }
}