package com.identity.service.userServiceTest;

import com.identity.Constain.UserStatus;
import com.identity.Maper.UserMaper;
import com.identity.Repositoty.RolesRepository;
import com.identity.Repositoty.UserRepository;
import com.identity.dto.request.UserCreationRequest;
import com.identity.dto.response.*;
import com.identity.entity.Roles;
import com.identity.entity.User;
import com.identity.service.UserService;
import lombok.experimental.NonFinal;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.data.domain.Example;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Set;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.ArgumentMatchers.any;

@Slf4j
@ExtendWith(MockitoExtension.class)
public class UserGetAllService {
    @Mock
    @NonFinal
    UserRepository userRepository;
    @Mock
    @NonFinal
    UserMaper userMaper;

    @InjectMocks
    UserService userService;

    User user;
    List<UserCreationResponse> userResponse = new ArrayList<>();
    RoleResponse roleResponse;
    RolePermissionReponse rolePermissionReponse;

    @BeforeEach
    void initData(){
        PermissionRes permissionCreate = PermissionRes.builder()
                .permission("CUSTOMER_CREATE")
                .build();

        PermissionRes permissionRead = PermissionRes.builder()
                .permission("CUSTOMER_READ")
                .build();

        PermissionRes permisisonWrite = PermissionRes.builder()
                .permission("CUSTOMER_WRITE")
                .build();


        rolePermissionReponse = RolePermissionReponse.builder()
                .role("CUSTOMER")
                .permissionReponse(Set.of(permissionCreate, permissionRead, permisisonWrite))
                .build();

        userResponse = List.of(
                UserCreationResponse.builder()
                        .userId("User1")
                        .userName("doanhpd01")
                        .phoneNumber("0865393278")
                        .status("ACTIVE")
                        .email_verified("dgrunt04@gmail.com")
                        .verified(true)
                        .rolePermissionResponse(Set.of(rolePermissionReponse))
                        .create_at(LocalDateTime.of(2026, 5, 31, 13, 21, 21, 21))
                        .build()
        );

        user = User.builder()
                .user_Id("User1")
                .username("doanhpd01")
                .build();
    }

    @Test
    void createUser_Success(){
        Mockito.when(userRepository.findAll()).thenReturn(Collections.singletonList(user));

        Mockito.when(userMaper.toUserResponse(any())).thenReturn(userResponse.get(0));

        List<UserCreationResponse> result = userService.getAllUser();

        log.info(result.toString());
        assertNotNull(result);
        assertEquals(userResponse, result);
    }
}
