package com.steven.stevenadmin.accountservice.service;

import com.steven.stevenadmin.accountservice.model.dto.AccountDto;
import com.steven.stevenadmin.accountservice.model.entity.AccountEntity;
import com.steven.stevenadmin.accountservice.model.vo.LoginVo;
import org.springframework.security.core.userdetails.UserDetailsService;

import java.util.Optional;

public interface AccountService extends UserDetailsService {

    // Save operation
    AccountEntity registerAccount(AccountEntity accountEntity);

    Optional<AccountEntity> ifAccountExist(String email);

    LoginVo loginAccount(AccountDto accountDto);
}
