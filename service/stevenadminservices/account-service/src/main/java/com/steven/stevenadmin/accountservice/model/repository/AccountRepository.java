package com.steven.stevenadmin.accountservice.model.repository;
import com.steven.stevenadmin.accountservice.model.entity.AccountEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface AccountRepository extends JpaRepository<AccountEntity,Integer> {
    Optional<AccountEntity> findByAccount(String account);

}
