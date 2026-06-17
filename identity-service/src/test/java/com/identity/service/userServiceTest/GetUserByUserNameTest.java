package com.identity.service.userServiceTest;

import com.identity.Maper.UserMaper;
import com.identity.Repositoty.UserRepository;
import com.identity.dto.response.PermissionRes;
import com.identity.dto.response.RolePermissionReponse;
import com.identity.dto.response.UserCreationResponse;
import com.identity.entity.User;
import com.identity.exception.AppException;
import com.identity.exception.ErrorCode;
import com.identity.service.UserService;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;


import java.time.LocalDateTime;
import java.util.Optional;
import java.util.Set;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;

@Slf4j
@ExtendWith(MockitoExtension.class)
public class GetUserByUserNameTest {
    @Mock
    UserRepository userRepository;
    @Mock
    UserMaper userMaper;
    @Mock
    User user;

    @InjectMocks
    UserService userService;

    RolePermissionReponse rolePermissionResponse;

    UserCreationResponse userResponse;

    @BeforeEach
    void initData() {
        PermissionRes permissionCreate = PermissionRes.builder()
                .permission("CUSTOMER_CREATE")
                .build();

        PermissionRes permissionRead = PermissionRes.builder()
                .permission("CUSTOMER_READ")
                .build();

        PermissionRes permisisonWrite = PermissionRes.builder()
                .permission("CUSTOMER_WRITE")
                .build();

        rolePermissionResponse = RolePermissionReponse.builder()
                .role("CUSTOMER")
                .permissionReponse(Set.of(permissionCreate, permissionRead, permisisonWrite))
                .build();

        userResponse = UserCreationResponse.builder()
                .userId("User1")
                .userName("doanhpd01")
                .phoneNumber("0865393278")
                .status("ACTIVE")
                .email_verified("dgrunt04@gmail.com")
                .verified(true)
                .rolePermissionResponse(Set.of(rolePermissionResponse))
                .create_at(LocalDateTime.of(2026, 5, 31, 13, 21, 21, 21))
                .build();

        user = User.builder()
                .user_Id("User1")
                .username("doanhpd01")
                .emailVerified("dgrunt04@gmail.com")
                .verified(true)
                .create_at(LocalDateTime.of(2026, 5, 31, 13, 21, 21, 21))
                .build();
    }

        @Test
        void getUserByUserName_Success(){
//            Mockito.when(userRepository.findByUsername(any())).thenReturn(user);
            Mockito.when(userMaper.toUserResponse(any())).thenReturn(userResponse);

            UserCreationResponse result = userService.getByUserName("doanhpd01");

            log.info("result find by username: " + result);
            assertNotNull(result);
            assertEquals(userResponse, result);
        }

        @Test
        void getUserByUserName_False(){
            Mockito.when(userRepository.findByUsername(any())).thenReturn(null);
            AppException exception = assertThrows(AppException.class, ()->{
                userService.getByUserName("doanhpd");
            });

            assertEquals(ErrorCode.USER_NOT_FOUND, exception.getErrorCode());
        }
}
