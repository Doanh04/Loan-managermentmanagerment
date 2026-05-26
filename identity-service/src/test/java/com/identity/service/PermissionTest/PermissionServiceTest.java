package com.identity.service.PermissionTest;

import com.identity.Constain.PermissionNameEnum;
import com.identity.Maper.PermissionMaper;
import com.identity.Repositoty.PerrmissionRepository;
import com.identity.dto.request.PermissionRequest;
import com.identity.dto.response.PermissionReponse;
import com.identity.entity.Permission;
import com.identity.exception.AppException;
import com.identity.exception.ErrorCode;
import com.identity.service.PermissionService;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import static org.mockito.ArgumentMatchers.any;
import static org.junit.jupiter.api.Assertions.*;

@ExtendWith(MockitoExtension.class)
@Slf4j
class PermissionServiceTest {
    @Mock
    private PerrmissionRepository permissionRepository;
    @Mock
    private PermissionMaper permissionMaper;

    @InjectMocks
    private PermissionService permissionService;

    private PermissionRequest permissionRequest;
    private PermissionReponse permissionResponse;
    private Permission permission;

    @BeforeEach
    void initData(){
        // Hàm này chạy trước TỪNG test case để chuẩn bị dữ liệu mẫu sạch
        permissionRequest = PermissionRequest.builder()
                .permision("CUSTOMER_CREATE")
                .permissionName("NAME_CUSTOMER_CREATE")
                .description("Allow to create customer")
                .build();

        permission = Permission.builder()
                .Permission(com.identity.Constain.PermissionEnum.CUSTOMER_CREATE)
                .Permission_name(PermissionNameEnum.NAME_CUSTOMER_CREATE)
                .Desciption("Allow to create customer")
                .build();

        permissionResponse = PermissionReponse.builder()
                .permission("CUSTOMER_CREATE")
                .permissionName("NAME_CUSTOMER_CREATE")
                .description("Allow to create customer")
                .build();
    }

    @Test
    void createPermission_valid_success() {
        //ARRANGE (Giả lập hành vi của các Mock)

        Mockito.when(permissionMaper.toPermissionEntity(any())).thenReturn(permission);
        Mockito.when(permissionRepository.save(any())).thenReturn(permission);
        Mockito.when(permissionMaper.toPermissionReponse(any())).thenReturn(permissionResponse);

       // ACT (Chạy hàm thực tế)
        PermissionReponse result = permissionService.createPermission(permissionRequest);

        // ASSERT (Kiểm tra kết quả)
        assertNotNull(result); // Đảm bảo kết quả không null
        assertEquals("CUSTOMER_CREATE", result.getPermission()); // Đảm bảo dữ liệu map đúng
        assertEquals("NAME_CUSTOMER_CREATE", result.getPermissionName());

        // Kiểm tra xem hàm save của repository có thực sự được gọi 1 lần hay không
        Mockito.verify(permissionRepository, Mockito.times(1)).save(any());
    }

    @Test
    void createPermission_validRequest_throwException(){
        Mockito.when(permissionRepository.existsById(any())).thenReturn(true);
        Mockito.when(permissionMaper.toPermissionEntity(any()))
                .thenReturn(permission);

        AppException exception = assertThrows(AppException.class, ()->{
            permissionService.createPermission(permissionRequest);
        });

        assertEquals(ErrorCode.PERMISSION_IS_EXITED, exception.getErrorCode());
    }

    @Test
    void createPermision_validRequest_notBlank(){
        Mockito.when(permissionMaper.toPermissionEntity(any())).thenReturn(permission);

        AppException exception = assertThrows(AppException.class, ()->{
            permissionService.createPermission(permissionRequest);
        });

        assertEquals(ErrorCode.PERMISSION_NOT_BLANK, exception.getErrorCode());
    }
}