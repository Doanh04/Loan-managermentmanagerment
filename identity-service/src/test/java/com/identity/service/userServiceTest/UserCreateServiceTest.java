package com.identity.service.userServiceTest;

import com.identity.Constain.*;
import com.identity.Maper.UserMaper;
import com.identity.Repositoty.RolesRepository;
import com.identity.Repositoty.UserRepository;
import com.identity.dto.request.UserCreationRequest;
import com.identity.dto.response.*;
import com.identity.entity.Permission;
import com.identity.entity.Roles;
import com.identity.entity.User;
import com.identity.exception.AppException;
import com.identity.exception.ErrorCode;
import com.identity.service.UserService;
import lombok.AccessLevel;
import lombok.experimental.FieldDefaults;
import lombok.experimental.NonFinal;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.security.crypto.password.PasswordEncoder;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;

import java.time.LocalDateTime;

import java.util.Optional;
import java.util.Set;

@ExtendWith(MockitoExtension.class)
@Slf4j
@FieldDefaults(level = AccessLevel.PRIVATE)
class UserCreateServiceTest {
    @Mock
    @NonFinal
    UserRepository userRepository;
    @Mock
    @NonFinal
    UserMaper userMaper;
    @Mock
    @NonFinal
    RolesRepository roleRepository;
    @Mock
    @NonFinal
    PasswordEncoder passwordEncoder;

    User user;
    UserCreationRequest userRequest;
    UserCreationResponse userResponse;
    Roles role;



    @InjectMocks
    UserService userService;

    @BeforeEach
    void initData(){
//----------------------------------------------------------
        Permission permisionCreate = Permission.builder()
                .Permission(PermissionEnum.CUSTOMER_CREATE)
                .Permission_name(PermissionNameEnum.NAME_CUSTOMER_CREATE)
                .build();
        Permission permissionWrite = Permission.builder()
                .Permission(PermissionEnum.CUSTOMER_WRITE)
                .Permission_name(PermissionNameEnum.NAME_CUSTOMER_WRITE)
                .build();
        Permission permissionRead =Permission.builder()
                .Permission(PermissionEnum.CUSTOMER_READ)
                .Permission_name(PermissionNameEnum.NAME_CUSTOMER_READ)
                .build();

//        Roles role = new Roles();
        role = Roles.builder()
                .role(RolesEnum.CUSTOMER)
                .Name_role(NameRoleEnum.ROLE_CUSTOMER)
                .permission(Set.of(permissionRead, permissionWrite, permisionCreate))
                .build();
//------------------------------------------------------------

        PermissionRes permissionReadRes = PermissionRes.builder()
                .permission("CUSTOMER_READ")
                .build();

        PermissionRes permissionWriteRes = PermissionRes.builder()
                .permission("CUSTOMER_WRITE")
                .build();
        PermissionRes permissionCreateRes= PermissionRes.builder()
                .permission("CUSTOMER_CREATE")
                .build();


        RolePermissionReponse roleRermissionReponse = RolePermissionReponse.builder()
                .role("CUSTOMER")
                .permissionReponse(Set.of(permissionReadRes, permissionWriteRes, permissionCreateRes))
                .build();


//-------------------------------------------------------------
        userRequest = UserCreationRequest.builder()
                .userName("0865393278")
                .passWord("Doanh@123")
                .phoneNumber("0865393278")
                .email_verified("dgrunt04@gmail.com")
                .build();


        user = User.builder()
                .user_Id("abcxyz")
                .username("0865393278")
                .password("hashedPasword")
                .emailVerified("dgrunt04@gmail.com")
                .verified(false)
                .phone_Number("0865393278")
                .status(UserStatus.WAITING_ACTIVE)
                .create_at(LocalDateTime.of(2026, 5, 31, 13, 21, 21, 21))
                .update_at(null)
                .Roles(Set.of(role))
                .build();



        userResponse = UserCreationResponse.builder()
                .userId("abcxyz")
                .userName("0865393278")
                .create_at(LocalDateTime.of(2026, 5, 31, 13, 21, 21, 21))
                .phoneNumber("0865393278")
                .status("CUSTOMER")
                .email_verified("dgrunt04@gmail.com")
                .verified(false)
                .rolePermissionResponse(Set.of(roleRermissionReponse))
                .build();
    }

//    Create success
    @Test
    void createUserSuccess(){
        Mockito.when(userRepository.existsByUsername(any())).thenReturn(false);
        Mockito.when(userRepository.existsByEmailVerified(any())).thenReturn(false);
        Mockito.when(userMaper.toUserEntity(any())).thenReturn(user);
        Mockito.when(passwordEncoder.encode("Doanh@123")).thenReturn("hashedPasword");
        Mockito.when(roleRepository.findById(any())).thenReturn(Optional.of(role));
        Mockito.when(userRepository.save(any())).thenReturn(user);
        Mockito.when(userMaper.toUserResponse(any())).thenReturn(userResponse);

        UserCreationResponse result = userService.createUser(userRequest);

        assertNotNull(result);
        assertEquals(userResponse, result);
    }

    @Test
    void createUser_false_ExitsByUserName(){
//        Mockito.when(userMaper.toUserEntity(any())).thenReturn(user);
        Mockito.when(userRepository.existsByUsername(any())).thenReturn(true);

        AppException exception = assertThrows(AppException.class, () ->{
            userService.createUser(userRequest);
        });

        assertEquals(ErrorCode.USERNAME_IS_EXITED, exception.getErrorCode());
    }

    @Test
    void create_false_ExitsByEmail(){
        Mockito.when(userRepository.existsByUsername(any())).thenReturn(false);
        Mockito.when(userRepository.existsByEmailVerified(any())).thenReturn(true);

        AppException exception = assertThrows(AppException.class, ()->{
            userService.createUser(userRequest);
        });

        assertEquals(ErrorCode.EMAIL_VERIFIED_EXITED, exception.getErrorCode());
    }
}

