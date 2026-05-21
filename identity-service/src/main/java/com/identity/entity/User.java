package com.identity.entity;

import com.identity.Constain.UserStatus;
import jakarta.persistence.*;
import lombok.*;
import lombok.experimental.FieldDefaults;

import java.time.Instant;
import java.util.HashSet;
import java.util.Set;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
@Entity
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    @Column(name = "User_Id")
    String User_Id;

    @Column(name = "Username", columnDefinition = "VARCHAR(255)")
    String Username;

    @Column(name = "Password")
    String Password;

    @Column(name = "email_verified", columnDefinition = "VARCHAR(255)")
    String email_verified;

    @Column(name = "Phone_Number", columnDefinition = "VARCHAR(12)")
    String Phone_Number;

    @Enumerated(EnumType.STRING)
    @Column(name = "Status", columnDefinition = "TIMESTAMP(6)")
    UserStatus Status;

    @Column(name = "Create_at")
    Instant Create_at;

    @ManyToMany(cascade = {CascadeType.PERSIST, CascadeType.MERGE})
    @JoinTable(
            name = "User_role",
            joinColumns = @JoinColumn(name = "User_Id"),
            inverseJoinColumns = @JoinColumn(name = "Role")
    )
    Set<Roles> Roles = new HashSet<>();
}
