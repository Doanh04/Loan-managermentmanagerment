package com.identity.Controler;

import com.identity.Constain.PermissionEnum;
import com.identity.Constain.RolesEnum;
import com.identity.dto.response.ApiResponse;
import com.identity.dto.response.RolePermissionReponse;
import com.identity.service.RolePermissionService;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/role-permisison")
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class RolePermissionControler {

    RolePermissionService rolePermission;

//    Controler Create Role_PermisisonPermisison
    @PostMapping("/create-role-permission")
    ApiResponse<RolePermissionReponse> createRolePermisison(@RequestParam RolesEnum role,
                                                            @RequestParam PermissionEnum permision){

        return ApiResponse.<RolePermissionReponse>builder()
                .message("Create Role_Permission success")
                .result(rolePermission.createRolePermission(role, permision))
                .build();

    }

    @GetMapping("/get-role-permission")
    ApiResponse<List<RolePermissionReponse>> getAllRolePermission(){
        return ApiResponse.<List<RolePermissionReponse>>builder()
                .message("Get All Permission SuccessSuccess")
                .result(rolePermission.getAllRolePermission())
                .build();
    }

    @DeleteMapping("/delete-permission-role")
    ApiResponse removePermissinOnRole(@RequestParam RolesEnum role,
                                      @RequestParam PermissionEnum permission){

        rolePermission.removePermissionFromRole(role, permission);

        return ApiResponse.builder()
                .message("Remove permission success")
                .build();
    }

    @DeleteMapping("/clear-role-permission/{role}")
    ApiResponse clearRoleResponse(@PathVariable RolesEnum role){
        rolePermission.clearAllRolePermission(role);

        return ApiResponse.builder()
                .message("Remove permission success")
                .build();
    }
}
