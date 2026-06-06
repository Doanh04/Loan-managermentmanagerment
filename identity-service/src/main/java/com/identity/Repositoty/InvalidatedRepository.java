package com.identity.Repositoty;

import com.identity.entity.InvaldatedToken;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface InvalidatedRepository extends JpaRepository<InvaldatedToken, String> {
}
