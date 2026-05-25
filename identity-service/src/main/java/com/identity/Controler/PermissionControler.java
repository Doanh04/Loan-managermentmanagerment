package com.identity.Controler;

import com.identity.dto.request.PermissionRequest;
import com.identity.dto.response.ApiResponse;
import com.identity.dto.response.PermissionReponse;
import com.identity.service.PermissionService;
import jakarta.validation.Valid;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/permission")
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class PermissionControler {
    PermissionService permissionService;

    @PostMapping("/create-permission")
    ApiResponse<PermissionReponse> createPermission(@Valid @RequestBody PermissionRequest permissionRequest){
        return ApiResponse.<PermissionReponse>builder()
                .message("Create Permission Success")
                .result(permissionService.createPermission(permissionRequest))
                .build();
    }
}
