package com.identity.entity;

import com.identity.Constain.NameRoleEnum;
import jakarta.persistence.*;
import lombok.*;
import lombok.experimental.FieldDefaults;

import java.util.HashSet;
import java.util.Set;
import com.identity.Constain.RolesEnum;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
@Entity
public class Roles {
    @Id
    @Column(name = "Role", columnDefinition = "VARCHAR(100)")
    @Enumerated(EnumType.STRING)
    RolesEnum role;

    @Enumerated(EnumType.STRING)
    @Column(name = "Name_role", unique = true, columnDefinition = "VARCHAR(100)")
    NameRoleEnum Name_role;

    @Column(name = "Description", columnDefinition = "VARCHAR(500)")
    String Description;

    @ManyToMany(mappedBy = "Roles")
    Set<User> users = new HashSet<>();

    @ManyToMany(cascade = {CascadeType.PERSIST, CascadeType.MERGE})
    @JoinTable(
            name = "Role_Permission",
            joinColumns = @JoinColumn(name = "Role"),
            inverseJoinColumns = @JoinColumn(name = "Permission")
    )
    Set<Permission> permission = new HashSet<>();
}
