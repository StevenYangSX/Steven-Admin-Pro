package com.steven.stevenadmin.accountservice.service.impl;

import com.steven.stevenadmin.accountservice.utils.JwtUtils;
import com.steven.stevenadmin.accountservice.model.dto.AccountDto;
import com.steven.stevenadmin.accountservice.model.entity.AccountEntity;
import com.steven.stevenadmin.accountservice.model.repository.AccountRepository;
import com.steven.stevenadmin.accountservice.model.repository.RoleRepository;
import com.steven.stevenadmin.accountservice.model.vo.LoginVo;
import com.steven.stevenadmin.accountservice.service.AccountService;
import com.steven.stevenadmin.accountservice.utils.RedisService;
import com.steven.stevenadmin.accountservice.utils.TokenUtils;
import com.steven.stevenadmin.common.exceptionHandler.CustomExceptionHandler;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;


@Service
@Transactional
public class AccountServiceImpl implements AccountService {

    @Autowired
    private AccountRepository accountRepository;

    @Autowired
    private RoleRepository roleRepository;

    @Autowired
    private JwtUtils jwtUtils;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Autowired
    private AuthenticationManager authenticationManager;

    @Autowired
    private TokenUtils tokenUtils;

    @Autowired
    private RedisService redisService;


    @Override
    public AccountEntity registerAccount(AccountEntity accountEntity) {
        String encodedPassword = passwordEncoder.encode(accountEntity.getPassword());
        accountEntity.setPassword(encodedPassword);

        // TODO: Add Account Role, leave it for now. findRoleById(); Add To Set

        return accountRepository.save(accountEntity);
    }

    @Override
    public LoginVo loginAccount(AccountDto accountDto) {

//        try {
//            LoginVo loginVo = new LoginVo();
//            AccountEntity accountEntity = loadUserByUsername(accountDto.getAccount());
//            if (passwordEncoder.matches(accountDto.getPassword(), accountEntity.getPassword())) {
//                String newToken = jwtUtils.generateToken(accountEntity);
//                loginVo.setToken(newToken);
//                loginVo.setId(accountEntity.getId());
//                loginVo.setAccount(accountEntity.getAccount());
//                loginVo.setExpirationTime(jwtUtils.getExpirationTime(newToken));
//                return loginVo;
//            }
//            throw new CustomExceptionHandler(40001, "Bad Credentials");
//        } catch (AuthenticationException e) {
//            throw new CustomExceptionHandler(40002, e.getMessage());
//        }


        try {

            Authentication authentication = new UsernamePasswordAuthenticationToken(accountDto.getAccount(), accountDto.getPassword());
            Authentication auth = authenticationManager.authenticate(authentication);

            String token = tokenUtils.generateToken(auth);
            AccountEntity accountEntity = loadUserByUsername(accountDto.getAccount());
            LoginVo loginVo = new LoginVo();
            loginVo.setAccount(accountEntity.getAccount());
            loginVo.setToken(token);
            loginVo.setId(accountEntity.getId());
            loginVo.setExpirationTime(tokenUtils.getExpiredTime(token).toEpochMilli() / 1000);

            redisService.setValueWithExpiration(accountEntity.getAccount(), accountEntity.toString(), 100);
            return loginVo;
        } catch (AuthenticationException e) {
            throw new CustomExceptionHandler(40002, e.getMessage());
        }

    }

    @Override
    public Optional<AccountEntity> ifAccountExist(String email) {
        return accountRepository.findByAccount(email);
    }


    @Override
    public AccountEntity loadUserByUsername(String account) throws UsernameNotFoundException {
        // better code   :  use of orElseThroe(()-> new ...)
        return accountRepository.findByAccount(account)
                .orElseThrow(() -> new UsernameNotFoundException("Account: " + account + " Not Found"));
    }
}

