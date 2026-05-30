package com.identity.service;

import com.identity.Maper.UserMaper;
import com.identity.Repositoty.UserRepository;
import com.identity.dto.request.UserCreationRequest;
import com.identity.dto.response.UserCreationResponse;
import com.identity.entity.User;
import com.identity.exception.AppException;
import com.identity.exception.ErrorCode;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
@Slf4j
public class UserService {
    UserRepository userRepository;
    UserMaper userMaper;
//    PasswordEncoder passwordEncoder;
//
//    public UserCreationResponse createUser(UserCreationRequest userRequest){
//        boolean userName = userRepository.existsByUsername(userRequest.getUserName());
//        if(userName) throw new AppException(ErrorCode.USERNAME_IS_EXITED);
//
//        boolean email = userRepository.existsByEmail(userRequest.getEmail_verified());
//        if(email) throw new AppException(ErrorCode.EMAIL_VERIFIED_EXITED);
//
//        User user =userMaper.toUserEntity(userRequest);
//
//        user.setPassword();
//    }
}
