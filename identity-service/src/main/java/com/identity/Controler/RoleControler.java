package com.identity.Controler;

import com.identity.dto.request.RolesRequest;
import com.identity.dto.response.ApiResponse;
import com.identity.dto.response.RoleResponse;
import com.identity.service.RoleService;
import jakarta.validation.Valid;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/roles")
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class RoleControler {
    RoleService roleService;

    @PostMapping("create-role")
    ApiResponse<RoleResponse> createRoles(@Valid @RequestBody RolesRequest request){
        return ApiResponse.<RoleResponse>builder()
                .message("Create roles success")
                .result(roleService.createRole(request))
                .build();
    }

    @DeleteMapping("delete-role/{}")
    ApiResponse deleteRoles(@PathVariable String role){
        roleService.deleteRole(role);
        return ApiResponse.builder()
                .message("Roles deleted success")
                .build();
    }
    @GetMapping("get-all-role")
    ApiResponse<List<RoleResponse>> getAllRole(){
        return ApiResponse.<List<RoleResponse>>builder()
                .message("List roles find all complete")
                .result(roleService.getAllRoles())
                .build();
    }
}
