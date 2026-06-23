package com.identity.ApplicationInit;

import com.identity.Constain.RolesEnum;
import com.identity.Constain.UserStatus;
import com.identity.Repositoty.PerrmissionRepository;
import com.identity.Repositoty.RolesRepository;
import com.identity.Repositoty.UserRepository;
import com.identity.entity.User;
import com.identity.exception.AppException;
import com.identity.exception.ErrorCode;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.ApplicationRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.annotation.Order;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.time.LocalDateTime;
import java.util.Optional;
import java.util.Set;

@Configuration
@Slf4j
public class UserAdminInitApplication {
    @Bean
    @Order(3)
    ApplicationRunner userInitApplication(UserRepository userRepository, RolesRepository rolesRepository, PerrmissionRepository perrmissionRepository, PasswordEncoder passwordEncoder){
        return (args -> {
            var role = rolesRepository.findById(RolesEnum.ADMIN).orElseThrow(() -> new AppException(ErrorCode.PERMISSION_IS_EXITED));
            Optional<User> user = userRepository.findByUsername("Admin");
            if(user.isEmpty()){
                userRepository.save(User.builder()
                        .username("Admin")
                        .password(passwordEncoder.encode("admin"))
                        .status(UserStatus.ACTIVE)
                        .Roles(Set.of(role))
                        .verified(true)
                        .create_at(LocalDateTime.now())
                        .build());
            }
        });
    }
}
