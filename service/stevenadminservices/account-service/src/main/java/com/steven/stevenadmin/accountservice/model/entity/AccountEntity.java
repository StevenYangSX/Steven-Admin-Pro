package com.steven.stevenadmin.accountservice.model.entity;


import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.CreationTimestamp;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.sql.Timestamp;
import java.time.Instant;
import java.util.*;

@Entity
@Data
@Table(name = "eb_system_admin")
public class AccountEntity implements UserDetails {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "account_id")
    private Integer id;

    @Column(unique = true)
    private String account;

    private String headPic;
    private String password;
    private String realName;


    // many roles set to many users - many-to-many relation
    @ManyToMany(fetch = FetchType.EAGER)
    @JoinTable(
            name = "account_role_relation",
            joinColumns = {@JoinColumn(name = "account_id")},
            inverseJoinColumns = {@JoinColumn(name = "role_id")}
    )
    private Set<RoleEntity> authorities;

    @Column(nullable = false, updatable = false)
    @CreationTimestamp
    @Temporal(TemporalType.TIMESTAMP)
    private Timestamp addTime;

    private Integer lastTime;

    @Column(nullable = false)
    private Integer loginCount = 1;

    @Column(nullable = false)
    private Integer level = 1;

    @Column(nullable = false)
    private Integer status = 1;

    @Column(nullable = false)
    private Integer isDel = 0;


    public void setAddTime(Date addTime) {
        this.addTime = new Timestamp(addTime.getTime());
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return this.authorities;
    }


    @Override
    public String getPassword() {
        return password;
    }

    @Override
    public String getUsername() {
        return account;
    }

    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    @Override
    public boolean isAccountNonLocked() {
        return status == 1;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @Override
    public boolean isEnabled() {
        return status == 1;
    }
}
