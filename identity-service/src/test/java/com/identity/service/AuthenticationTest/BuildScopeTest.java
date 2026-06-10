package com.identity.service.AuthenticationTest;

import com.identity.Constain.PermissionEnum;
import com.identity.Constain.PermissionNameEnum;
import com.identity.Constain.RolesEnum;
import com.identity.Repositoty.UserRepository;
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
import org.springframework.test.util.AssertionErrors;
import org.springframework.util.CollectionUtils;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;


import java.util.Set;
import java.util.StringJoiner;

@ExtendWith(MockitoExtension.class)
@Slf4j
public class BuildScopeTest {
    @Mock
    UserRepository userRepository;

    @Mock
    StringJoiner stringJoiner;
    @Mock
    CollectionUtils collectionUltil;

    Roles roles;
    User user;

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
                .Roles(Set.of(roles))
                .build();
    }

    @Test
    void test_buildScope_Success(){
        String scope = authenticationService.buildScope(user);

        String expectedScope = "ROLE_ADMIN PERMISSION_USER_READ PERMISSION_USER_WRITE";
        log.info("result built scope after"+ scope);

        assertNotNull(scope);
        assertEquals(expectedScope, scope);
    }


}
