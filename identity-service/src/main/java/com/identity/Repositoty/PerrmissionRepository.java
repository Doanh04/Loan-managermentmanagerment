package com.identity.Repositoty;

import com.identity.Constain.PermissionEnum;
import com.identity.entity.Permission;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PerrmissionRepository extends JpaRepository<Permission, PermissionEnum> {
}
