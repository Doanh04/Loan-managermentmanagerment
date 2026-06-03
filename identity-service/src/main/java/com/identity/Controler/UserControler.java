package com.identity.Controler;

import com.identity.dto.request.UserCreationRequest;
import com.identity.dto.response.ApiResponse;
import com.identity.dto.response.UserCreationResponse;
import com.identity.service.UserService;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/user")
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

    @GetMapping("/get-user")
    public ApiResponse<List<UserCreationResponse>> getAllUser(){
        return ApiResponse.<List<UserCreationResponse>>builder()
                .message("Get all user success")
                .result(userService.getAllUser())
                .build();
    }

    @GetMapping("/{username}")
    public ApiResponse<UserCreationResponse> getUserByUserName(@PathVariable String username){
        return ApiResponse.<UserCreationResponse>builder()
                .message("Get user by user name success")
                .result(userService.getByUserName(username))
                .build();
    }

    @DeleteMapping("/{userName}")
    public ApiResponse deleteByUserName(@PathVariable String userName){
        userService.deleteByUserName(userName);
        return ApiResponse.builder()
                .message("Delete user success")
                .build();
    }
}
