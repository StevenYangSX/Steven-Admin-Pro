package com.steven.stevenadmin.accountservice.model.entity;


import jakarta.persistence.*;
import lombok.Data;
import org.springframework.security.core.GrantedAuthority;

@Entity
@Data
@Table(name = "eb_system_role")
public class RoleEntity implements GrantedAuthority {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "role_id")
    private Integer id;

    @Column(nullable = false,unique = true)
    private String roleName;

    private String rules;

    @Column(nullable = false)
    private Integer status = 1;

    @Column(nullable = false)
    private Integer level = 1;

    @Override
    public String getAuthority() {
        return roleName;
    }
}
