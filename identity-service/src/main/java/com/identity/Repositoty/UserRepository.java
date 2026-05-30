package com.identity.Repositoty;

import com.identity.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface UserRepository extends JpaRepository<User, String> {

    @Query(value = " Select userName from User where Username = :userName", nativeQuery = true)
    boolean existsByUsername(@Param("userName") String userName);

    @Query(value = "select email_verified from User where email_verified = :email_verified", nativeQuery = true)
    boolean existsByEmail(@Param("email_verified") String email_verified);
}
