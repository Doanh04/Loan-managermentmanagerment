package com.identity.service;

import com.identity.Constain.RolesEnum;
import com.identity.Repositoty.PerrmissionRepository;
import com.identity.Repositoty.RolesRepository;
import com.identity.dto.request.RolePeRequest;
import com.identity.dto.response.RolePeResponse;
import com.identity.entity.Roles;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
@Slf4j
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class RolePermissionService {
    RolesRepository roleRepository;
    PerrmissionRepository permissonRepository;

//    public RolePeResponse createRolePermission(RolePeRequest request){
//        Optional<Roles> role = roleRepository.findById(RolesEnum.valueOf(request.getRole()));
//    }

}
