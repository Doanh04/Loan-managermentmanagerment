package com.identity.Repositoty;

import com.identity.Constain.RolesEnum;
import com.identity.entity.Roles;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface RolesRepository extends JpaRepository<Roles, RolesEnum> {
}
