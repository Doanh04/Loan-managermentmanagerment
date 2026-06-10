package com.identity.entity;

import com.identity.Constain.PermissionEnum;
import com.identity.Constain.PermissionNameEnum;
import jakarta.persistence.*;
import lombok.*;
import lombok.experimental.FieldDefaults;

import java.util.HashSet;
import java.util.Set;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
@Entity
@Table(name = "permission")
public class Permission {
    @Id
    @Enumerated(EnumType.STRING)
    @Column(name = "Permission", columnDefinition = "VARCHAR(100)")
    PermissionEnum Permission;

    @Enumerated(EnumType.STRING)
    @Column(name = "Permission_name",unique = true ,columnDefinition = "VARCHAR(100)")
    PermissionNameEnum Permission_name;

    @Column(name = "Desciption", columnDefinition = "NVARCHAR(500)")
    String Desciption;

    @ManyToMany(mappedBy = "permission")
    Set<Roles> roles = new HashSet<>();
}
