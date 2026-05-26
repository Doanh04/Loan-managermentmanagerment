package com.identity.service;

import com.identity.Constain.PermissionEnum;
import com.identity.Constain.PermissionNameEnum;
import com.identity.Maper.PermissionMaper;
import com.identity.Repositoty.PerrmissionRepository;
import com.identity.dto.request.PermissionRequest;
import com.identity.dto.response.PermissionReponse;
import com.identity.entity.Permission;
import com.identity.exception.AppException;
import com.identity.exception.ErrorCode;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.Arrays;
import java.util.List;

@Service
@RequiredArgsConstructor
@Slf4j
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class PermissionService {
    PerrmissionRepository permissionRepository;
    PermissionMaper permissionMaper;

    public PermissionReponse createPermission(PermissionRequest request){

        Permission permission = permissionMaper.toPermissionEntity(request);
        if(permission.getPermission() == null || permission.getPermission_name() == null){
            throw new AppException(ErrorCode.PERMISSION_NOT_BLANK);
        }
        boolean exitedById = permissionRepository.existsById(PermissionEnum.valueOf(request.getPermision()));
        if(exitedById == true) throw new AppException(ErrorCode.PERMISSION_IS_EXITED);

        permission.setPermission(PermissionEnum.valueOf(request.getPermision()));
        permission.setPermission_name(PermissionNameEnum.valueOf(request.getPermissionName()));
        permission.setDesciption(request.getDescription());
        permissionRepository.save(permission);

        return permissionMaper.toPermissionReponse(permission);
    }

    public void delete(String permission){
        if(permission==null) throw new AppException(ErrorCode.PERMISSION_INVALID);
        boolean exits = Arrays.stream(PermissionEnum.values())
                        .map(Enum::name)
                                .anyMatch(name -> name.equals(permission));
        if(!exits) throw new AppException(ErrorCode.PERMISSION_NOT_BLANK);


        permissionRepository.deleteById(PermissionEnum.valueOf(permission));
    }

    public List<PermissionReponse> getAllPermission(){

        var result = permissionRepository.findAll();

        return result.stream()
                .map(permissionMaper::toPermissionReponse).toList();
    }
}
