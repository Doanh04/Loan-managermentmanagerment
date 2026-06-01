package com.identity.service;

import com.identity.Constain.RolesEnum;
import com.identity.Constain.UserStatus;
import com.identity.Maper.UserMaper;
import com.identity.Repositoty.RolesRepository;
import com.identity.Repositoty.UserRepository;
import com.identity.dto.request.UserCreationRequest;
import com.identity.dto.response.UserCreationResponse;
import com.identity.entity.Roles;
import com.identity.entity.User;
import com.identity.exception.AppException;
import com.identity.exception.ErrorCode;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import lombok.extern.slf4j.Slf4j;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.HashSet;
import java.util.Set;

@Service
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
@Slf4j
public class UserService {
    UserRepository userRepository;
    UserMaper userMaper;
    PasswordEncoder passwordEncoder;
    RolesRepository roleRepository;

    public UserCreationResponse createUser(UserCreationRequest userRequest){
        boolean userName = userRepository.existsByUsername(userRequest.getUserName());
        if(userName) throw new AppException(ErrorCode.USERNAME_IS_EXITED);

        boolean email = userRepository.existsByEmailVerified(userRequest.getEmail_verified());
        if(email) throw new AppException(ErrorCode.EMAIL_VERIFIED_EXITED);

        User user =userMaper.toUserEntity(userRequest);

        user.setPassword(passwordEncoder.encode(userRequest.getPassWord()));

        Set<Roles> roles = new HashSet<>();

        roleRepository.findById(RolesEnum.CUSTOMER).ifPresent(roles::add);

        user.setRoles(roles);
        user.setVerified(false);
        user.setStatus(UserStatus.WAITING_ACTIVE);
        user.setCreate_at(LocalDateTime.now());

        try{
            user = userRepository.save(user);
        }
        catch (DataIntegrityViolationException exception){
            throw new AppException(ErrorCode.USER_EXITED);
        }

        return userMaper.toUserResponse(user);
    }


}
