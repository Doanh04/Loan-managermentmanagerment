package com.identity.Controler;

import com.identity.dto.request.UserCreationRequest;
import com.identity.dto.response.ApiResponse;
import com.identity.dto.response.UserCreationResponse;
import com.identity.service.UserService;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/controler")
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class UserControler {
    UserService userService;

    @PostMapping("/create-user")
    public ApiResponse<UserCreationResponse> createUser(@RequestBody UserCreationRequest userRequest){
        return ApiResponse.<UserCreationResponse>builder()
                .message("Create user success")
                .result(userService.createUser(userRequest))
                .build();
    }
}
