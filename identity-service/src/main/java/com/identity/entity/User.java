package com.identity.entity;

import com.identity.Constain.UserStatus;
import jakarta.persistence.*;
import lombok.*;
import lombok.experimental.FieldDefaults;

import java.time.Instant;
import java.time.LocalDateTime;
import java.util.HashSet;
import java.util.Set;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
@Entity
@Table(name = "user")
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    @Column(name = "User_Id")
    String user_Id;

    @Column(name = "Username", columnDefinition = "VARCHAR(255)", unique = true)
    String username;

    @Column(name = "Password", nullable = false, columnDefinition = "VARCHAR(255)")
    String password;

    @Column(name = "email_verified" ,columnDefinition = "VARCHAR(255)", unique = true)
    String emailVerified;

    @Column(name = "verified")
    boolean verified;

    @Column(name = "Phone_Number", columnDefinition = "VARCHAR(12)", unique = true, nullable = false)
    String phone_Number;

    @Enumerated(EnumType.STRING)
    @Column(name = "Status", columnDefinition = "VARCHAR(50)")
    UserStatus status;

    @Column(name = "Create_at")
    LocalDateTime create_at;

    @Column(name = "update_at")
    LocalDateTime update_at;

    @ManyToMany(cascade = {CascadeType.PERSIST, CascadeType.MERGE})
    @JoinTable(
            name = "User_role",
            joinColumns = @JoinColumn(name = "User_Id"),
            inverseJoinColumns = @JoinColumn(name = "Role")
    )
    Set<Roles> Roles = new HashSet<>();

}
