package com.identity.Controler;

import com.identity.dto.request.PermissionRequest;
import com.identity.dto.response.ApiResponse;
import com.identity.dto.response.PermissionReponse;
import com.identity.service.PermissionService;
import jakarta.validation.Valid;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.web.bind.annotation.*;

import java.util.List;

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

    @DeleteMapping("/delete-permission/{permission}")
    ApiResponse deletePermission(@PathVariable String permission){
        permissionService.delete(permission);

        return ApiResponse.builder()
                .message("Delete is complete.")
                .build();
    }

    @GetMapping("get-all-permission")
    ApiResponse<List<PermissionReponse>> getAllPermission(){
        return ApiResponse.<List<PermissionReponse>>builder()
                .message("This is full data permission")
                .result(permissionService.getAllPermission())
                .build();
    };
}
