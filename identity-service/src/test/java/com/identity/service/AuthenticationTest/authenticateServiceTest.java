package com.identity.service.AuthenticationTest;

import com.identity.Constain.PermissionEnum;
import com.identity.Constain.PermissionNameEnum;
import com.identity.Constain.RolesEnum;
import com.identity.Repositoty.UserRepository;
import com.identity.dto.request.AuthenticationRequest;
import com.identity.dto.response.AuthenticationResponse;
import com.identity.entity.Permission;
import com.identity.entity.Roles;
import com.identity.entity.User;
import com.identity.service.AuthenticationService;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.util.CollectionUtils;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.ArgumentMatchers.any;
import java.util.Set;
import java.util.StringJoiner;

@Slf4j
@ExtendWith(MockitoExtension.class)
public class authenticateServiceTest {
    @Mock
    UserRepository userRepository;

    @Mock
    StringJoiner stringJoiner;
    @Mock
    CollectionUtils collectionUltil;
    @Mock
    PasswordEncoder encoder;

    Roles roles;
    User user;
    AuthenticationRequest authenticationRequest;
    AuthenticationResponse authenticationResponse;

    @InjectMocks
    AuthenticationService authenticationService;

    @BeforeEach
    void initData(){
        Permission permissionRead = Permission.builder()
                .Permission(PermissionEnum.valueOf("USER_READ"))
                .Permission_name(PermissionNameEnum.valueOf("NAME_USER_READ"))
                .build();
        Permission permissionWrite = Permission.builder()
                .Permission(PermissionEnum.valueOf("USER_WRITE"))
                .Permission_name(PermissionNameEnum.valueOf("NAME_USER_WRITE"))
                .build();

        roles = Roles.builder()
                .role(RolesEnum.valueOf("ADMIN"))
                .permission(Set.of(permissionRead, permissionWrite))
                .build();

        user = User.builder()
                .user_Id("user_test")
                .username("doanhpd")
                .emailVerified("dgrunt04@gmail.com")
                .phone_Number("0865393178")
                .password("doanh123pd")
                .Roles(Set.of(roles))
                .build();

        authenticationRequest = AuthenticationRequest.builder()
                .userName("doanhpd")
                .password("doanh123pd")
                .build();
        authenticationResponse = AuthenticationResponse.builder()
                .token("abcxyz")
                .build();

//        authenticationService.SIGNER_KEY = "abcdefghijklmnopqrstuvwxyzABCDEFGHIJKLMNOPQRSTUVWXYZ12345678901234567890";
    }

    @Test
    void test_generateToken_success(){
//        Mockito.when(userRepository.findByUsername(any())).thenReturn(user);
        Mockito.when(encoder.matches("doanh123pd", "doanh123pd")).thenReturn(true);

        AuthenticationResponse authenticationResult = authenticationService.authenticate(authenticationRequest);

        log.info(String.valueOf(authenticationResult));

        assertNotNull(authenticationResult);

        assertEquals("null", authenticationResult);
    }
}
