package com.identity.Repositoty;

import com.identity.Constain.PermissionEnum;
import com.identity.entity.Permission;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface PerrmissionRepository extends JpaRepository<Permission, PermissionEnum> {}
