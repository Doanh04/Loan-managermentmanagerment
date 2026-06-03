package com.identity.Repositoty;

import com.identity.dto.response.UserCreationResponse;
import com.identity.entity.User;
import jakarta.transaction.Transactional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;

@Repository
public interface UserRepository extends JpaRepository<User, String> {

    boolean existsByUsername( String username);

    boolean existsByEmailVerified( String emailVerified);

    User findByUsername(String username);

    @Transactional
    @Modifying
    @Query(value = "update user set status = 'BANNED', update_at = :updateAt where username = :username", nativeQuery = true)
    void deleteUserByUsername(@Param("username") String username, @Param("updateAt") LocalDateTime updateAt);


}
