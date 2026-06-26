package com.identity.service;

import com.event.dto.messageOtpDto;
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
import jakarta.transaction.Transactional;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import lombok.extern.slf4j.Slf4j;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.*;

@Service
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
@Slf4j
public class UserService {
    UserRepository userRepository;
    UserMaper userMaper;
    PasswordEncoder passwordEncoder;
    RolesRepository roleRepository;
    KafkaTemplate<String, Object> kafkaTemplate;

    @Transactional
    public UserCreationResponse createUser(UserCreationRequest userRequest){
        Optional<User> exitingUser = userRepository.findByUsername(userRequest.getUserName());
        if (exitingUser.isPresent()) {
            User exiting = exitingUser.get();

            if(exiting.getStatus() == UserStatus.WAITING_ACTIVE || exiting.getStatus() == UserStatus.ACTIVE || exiting.getStatus() == UserStatus.BANNED
                || exiting.getStatus() == UserStatus.LOCKED) {
                throw new AppException(ErrorCode.USER_EXITED);
            };
            if(exiting.getStatus() == UserStatus.IN_ACTIVE){
                exiting.setPassword(passwordEncoder.encode(userRequest.getPassWord()));

                userRepository.save(exiting);

                messageOtpDto otpDto = messageOtpDto.builder()
                        .chanel("SMS")
                        .repicient(exiting.getPhone_Number())
                        .subject("Welcome")
                        .body("....")
                        .build();
                kafkaTemplate.send("notification-sms-v3", otpDto);

                return userMaper.toUserResponse(exiting);
            }
        }

        boolean email = userRepository.existsByEmailVerified(userRequest.getEmail_verified());
        if(email) throw new AppException(ErrorCode.EMAIL_VERIFIED_EXITED);

        User user =userMaper.toUserEntity(userRequest);

        user.setPassword(passwordEncoder.encode(userRequest.getPassWord()));

        Set<Roles> roles = new HashSet<>();

        roleRepository.findById(RolesEnum.CUSTOMER).ifPresent(roles::add);

        user.setRoles(roles);
        user.setVerified(false);
        user.setStatus(UserStatus.IN_ACTIVE);
        user.setCreate_at(LocalDateTime.now());

        try{
            user = userRepository.save(user);
        }
        catch (DataIntegrityViolationException exception){
            throw new AppException(ErrorCode.USER_EXITED);
        }

        messageOtpDto otpDto = messageOtpDto.builder()
                .chanel("SMS")
                .repicient(user.getPhone_Number())
                .subject("Welcome")
                .body("....")
                .build();
        kafkaTemplate.send("notification-sms-v3", otpDto);

        return userMaper.toUserResponse(user);
    }

    @PreAuthorize("hasAnyAuthority('ROLE_ADMIN') and hasAnyAuthority('PERMISSION_SYSTEM_CONFIG')")
    public List<UserCreationResponse> getAllUser(){
        var allUser = userRepository.findAll();
        return allUser.stream().map(userMaper::toUserResponse).toList();
    }

    public UserCreationResponse getByUserName(String userName){
        User result = userRepository.findByUsername(userName)
                .orElseThrow(() -> new AppException(ErrorCode.USER_NOT_FOUND));

        return userMaper.toUserResponse(result);
    }

    @Transactional
    @PreAuthorize("hasAnyAuthority('ROLE_ADMIN') and hasAnyAuthority('PERMISSION_SYSTEM_CONFIG')")
    public void deleteByUserName(String userName) {
        if (userName == null) {
            throw new AppException(ErrorCode.USER_NOT_FOUND);
        }

        User user = userRepository.findByUsername(userName)
                .orElseThrow(() -> new AppException(ErrorCode.USER_NOT_FOUND));

        if (UserStatus.ACTIVE.equals(user.getStatus())) {
            userRepository.deleteUserByUsername(userName, LocalDateTime.now());
        } else {
            throw new AppException(ErrorCode.UKNOWN_ERROR);
        }
    }

}
