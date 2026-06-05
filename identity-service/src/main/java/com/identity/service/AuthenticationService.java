package com.identity.service;

import com.identity.Maper.UserMaper;
import com.identity.Repositoty.UserRepository;
import com.identity.dto.request.AuthenticationRequest;
import com.identity.dto.response.AuthenticationResponse;
import com.identity.entity.User;
import com.identity.exception.AppException;
import com.identity.exception.ErrorCode;
import com.nimbusds.jose.*;
import com.nimbusds.jose.crypto.MACSigner;
import com.nimbusds.jwt.JWTClaimsSet;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import lombok.experimental.NonFinal;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;
import org.springframework.beans.factory.annotation.Value;

import java.time.Instant;
import java.time.temporal.ChronoUnit;
import java.util.Date;
import java.util.StringJoiner;
import java.util.UUID;

@Service
@RequiredArgsConstructor
@Slf4j
public class AuthenticationService {
    private final UserRepository userRepository;
    private final UserMaper userMaper;
    private final PasswordEncoder encoder;

    @NonFinal
    @Value("${jwt.valid-duration}")
    protected long VALID_DURATION;

    @NonFinal
    @Value("${jwt.signerKey}")
    protected  String SIGNER_KEY;

    public AuthenticationResponse authenticate(AuthenticationRequest request) {
//        PasswordEncoder encoder = new BCryptPasswordEncoder(10);

        var userName = userRepository.findByUsername(request.getUserName());
        if (userName == null) throw new AppException(ErrorCode.USER_NOT_FOUND);

        boolean authenticated = encoder.matches(request.getPassword(), userName.getPassword());

        if(!authenticated) throw new AppException(ErrorCode.UNAUTHORIZED);

        var token = generateToken(userName);

        return AuthenticationResponse.builder()
                .token(token)
                .build();
    }
    public String generateToken(User user){
        JWSHeader header = new JWSHeader(JWSAlgorithm.HS512);

        JWTClaimsSet jwtclaimSet = new JWTClaimsSet.Builder()
                .subject(user.getUser_Id())
                .issuer("PhamDucDoanh")
                .issueTime(new Date())
                .expirationTime(new Date(
                        Instant.now().plus(VALID_DURATION, ChronoUnit.SECONDS).toEpochMilli()
                ))
                .jwtID(UUID.randomUUID().toString())
                .claim("scope", buildScope(user))
                .claim("User Name", user.getUsername())
                .claim("email", user.getEmailVerified())
                .claim("phone", user.getPhone_Number())
                .build();

        Payload payload = new Payload(jwtclaimSet.toJSONObject());

        JWSObject jwsObject = new JWSObject(header, payload);

        try{
            jwsObject.sign(new MACSigner(SIGNER_KEY.getBytes()));
            return jwsObject.serialize();
        }
        catch (JOSEException e){
            log.error("Cannot create token");
            throw new RuntimeException(e);
        }
    }

    public String buildScope(User user){
        StringJoiner stringJoiner = new StringJoiner(" ");

        if(!CollectionUtils.isEmpty(user.getRoles()))
            user.getRoles().forEach(roles -> {
                stringJoiner.add("ROLE_" + roles.getRole());
                if(!CollectionUtils.isEmpty(roles.getPermission()))
                    roles.getPermission().forEach(
                            permissions -> {stringJoiner.add("PERMISSION_" + permissions.getPermission().name());
                            }
                    );
            });

        return stringJoiner.toString();
    }
}
