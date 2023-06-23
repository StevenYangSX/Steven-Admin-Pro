package com.steven.stevenadmin.accountservice.model.repository;

import com.steven.stevenadmin.accountservice.model.entity.AccountEntity;
import com.steven.stevenadmin.accountservice.model.entity.RoleEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;


@Repository
public interface RoleRepository extends JpaRepository<RoleEntity, Integer> {
    Optional<RoleEntity> findByRoleName(String roleName);
}
