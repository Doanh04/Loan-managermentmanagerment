package com.identity.Controler;

import com.identity.dto.request.AuthenticationRequest;
import com.identity.dto.request.LogoutRequest;
import com.identity.dto.request.RefreshRequest;
import com.identity.dto.response.ApiResponse;
import com.identity.dto.response.AuthenticationResponse;
import com.identity.service.AuthenticationService;
import com.nimbusds.jose.JOSEException;
import jakarta.servlet.http.HttpServletResponse;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseCookie;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.text.ParseException;

@RestController
@RequestMapping("/auth")
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class AuthenticationControler {
    AuthenticationService authenticationService;

    @PostMapping("/login")
    public ApiResponse<AuthenticationResponse> authentication(@RequestBody AuthenticationRequest request){
        return ApiResponse.<AuthenticationResponse>builder()
                .message("Log in success")
                .result(authenticationService.authenticate(request))
                .build();
    }

    @PostMapping("/logout")
    public ApiResponse<Void>logout(@RequestBody LogoutRequest request)throws ParseException, JOSEException  {
        authenticationService.logout(request);

        return ApiResponse.<Void>builder().build();
    }

    @PostMapping("/refresh")
    ApiResponse<AuthenticationResponse> authenticate(@RequestBody RefreshRequest request)
            throws ParseException, JOSEException {
        var result = authenticationService.refresh(request);
        return ApiResponse.<AuthenticationResponse>builder().result(result).build();
    }

//    HTTP COOKIE ONLY

//    public ApiResponse<AuthenticationResponse> authentication(@RequestBody AuthenticationRequest request, HttpServletResponse response){
//        AuthenticationResponse authResponse = authenticationService.authenticate(request);
//        String token = authResponse.getToken();
//
//        ResponseCookie cookie = ResponseCookie.from("access_token", token)
//                .httpOnly(true)
//                .secure(false) // đổi thành true khi chạy trên https
//                .path("/") // áp dùng toàn bộ api đường dẫn sau đó
//                .maxAge(24 * 60 * 60) // time sống cookie
//                .sameSite("Lax") //Hỗ trợ trống tấn công csrf
//                .build();
//// Đính cookie vào Header của Response trả về cho trình duyệt
//        response.addHeader(HttpHeaders.SET_COOKIE, cookie.toString());
//// Ẩn token khỏi Body trước khi trả về cho Frontend (Tùy chọn bảo mật)
//        authResponse.setToken(null);
//        return ApiResponse.<AuthenticationResponse>builder()
//                .message("Login success")
//                .result(authResponse)
//                .build();
//    }
}
