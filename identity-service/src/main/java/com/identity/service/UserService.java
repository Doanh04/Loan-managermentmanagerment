package com.identity.service;

import com.event.dto.messageOtpDto;
import com.identity.Constain.RolesEnum;
import com.identity.Constain.UserStatus;
import com.identity.Maper.UserMaper;
import com.identity.Repositoty.RolesRepository;
import com.identity.Repositoty.UserRepository;
import com.identity.Ultil.ConfigTopicKafka;
import com.identity.dto.request.OTPRequest;
import com.identity.dto.request.UserCreationRequest;
import com.identity.dto.response.SendEmailResponse;
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
import org.springframework.data.redis.core.RedisTemplate;
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
    ConfigTopicKafka configTopicKafka;
    RedisTemplate<String, Object> redisTemplate;

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

                configTopicKafka.sendSms(userRequest.getPhoneNumber());

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
        user.setStatus(UserStatus.WAITING_ACTIVE);
        user.setCreate_at(LocalDateTime.now());

        try{
            user = userRepository.save(user);
        }
        catch (DataIntegrityViolationException exception){
            throw new AppException(ErrorCode.USER_EXITED);
        }

        configTopicKafka.sendSms(user.getPhoneNumber());

        return userMaper.toUserResponse(user);
    }

    @Transactional
    public void verifyOTP(OTPRequest request){
        String key = "otp:sms:" + request.getPhoneNumber();
        String catcheOtp =(String) redisTemplate.opsForValue().get(key);

        if(catcheOtp == null){
            throw new AppException(ErrorCode.OTP_EXPIRED);
        }
        if(!catcheOtp.equals(request.getOTP())){
            throw new AppException(ErrorCode.OTP_INVALID);
        }
        User user = userRepository.findByPhoneNumber(request.getPhoneNumber())
                .orElseThrow(() -> new AppException(ErrorCode.USER_NOT_FOUND));

        user.setStatus(UserStatus.WAITING_ACTIVE);
        userRepository.save(user);
        configTopicKafka.sendEmailWelcome(user.getEmailVerified(), user.getUsername());

        redisTemplate.delete(key);
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
