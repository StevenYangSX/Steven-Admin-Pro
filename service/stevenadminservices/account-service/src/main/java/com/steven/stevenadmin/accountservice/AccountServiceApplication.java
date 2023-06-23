package com.steven.stevenadmin.accountservice;

import com.steven.stevenadmin.accountservice.model.entity.AccountEntity;
import com.steven.stevenadmin.accountservice.model.entity.RoleEntity;
import com.steven.stevenadmin.accountservice.model.repository.AccountRepository;
import com.steven.stevenadmin.accountservice.model.repository.RoleRepository;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.util.HashSet;
import java.util.Set;

@SpringBootApplication
public class AccountServiceApplication {

    public static void main(String[] args) {
        SpringApplication.run(AccountServiceApplication.class, args);
    }


    @Bean
    CommandLineRunner runner(RoleRepository roleRepository, AccountRepository accountRepository, PasswordEncoder passwordEncoder) {
        return args -> {
            if (roleRepository.  findByRoleName("ADMIN").isPresent()) return;

            RoleEntity roleEntity = new RoleEntity();
            roleEntity.setRoleName("ADMIN");

            roleEntity.setStatus(1);
            roleEntity.setLevel(0);
            roleRepository.save(roleEntity);

            RoleEntity roleEntity2 = new RoleEntity();
            roleEntity2.setRoleName("SALES");
            roleEntity2.setStatus(1);
            roleEntity2.setLevel(0);
            roleRepository.save(roleEntity2);

            if (accountRepository.findByAccount("admin").isPresent()) return;

            AccountEntity accountEntity = new AccountEntity();
            accountEntity.setAccount("admin");
            accountEntity.setPassword(passwordEncoder.encode("123456"));
            accountEntity.setRealName("系统管理员");
            Set<RoleEntity> roles = new HashSet<>();
            roles.add(roleEntity);
            roles.add(roleEntity2);
            accountEntity.setAuthorities(roles);
            accountRepository.save(accountEntity);
        };
    }
}
