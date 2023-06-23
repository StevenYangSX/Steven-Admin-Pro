package com.steven.stevenadmin.accountservice.controller;

import com.steven.stevenadmin.accountservice.model.dto.AccountDto;
import com.steven.stevenadmin.accountservice.model.entity.AccountEntity;
import com.steven.stevenadmin.accountservice.model.vo.LoginVo;
import com.steven.stevenadmin.accountservice.service.AccountService;
import com.steven.stevenadmin.common.exceptionHandler.CustomExceptionHandler;
import com.steven.stevenadmin.common.exceptionHandler.GlobalExceptionHandler;
import com.steven.stevenadmin.common.utils.ApiResponse;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import java.util.Optional;

@RestController
@RequestMapping("/api/account/")
public class AccountController {

    @Autowired
    private AccountService accountService;

    @RequestMapping("/health")
    public String healthCheck() {
        return "API STATUS: OK";
    }


    @RequestMapping(value = "/register", method = RequestMethod.POST)
    public ApiResponse<Object> accountRegister(@RequestBody AccountDto accountDto) {
        if (accountService.ifAccountExist(accountDto.getAccount()).isPresent()) {
            return ApiResponse.error().message("Account Already Exists.");
        }

        AccountEntity accountEntity = new AccountEntity();
        accountEntity.setAccount(accountDto.getAccount());
        accountEntity.setPassword(accountDto.getPassword());
        accountEntity.setHeadPic(accountDto.getHeadPic());
        accountEntity.setRealName(accountDto.getRealName());

        AccountEntity savedEntity = accountService.registerAccount(accountEntity);

        return ApiResponse.ok().message("Register Success").data(savedEntity);

    }

    @RequestMapping(value = "/login", method = RequestMethod.POST)
    public ApiResponse<Object> accountLogin(@RequestBody AccountDto accountDto) {
        try {
            LoginVo accountLoginVo = accountService.loginAccount(accountDto);
            return ApiResponse.ok().message("Login Success").data(accountLoginVo);
        } catch (CustomExceptionHandler e) {
            return GlobalExceptionHandler.customError(new CustomExceptionHandler(e.getCode(), e.getMessage()));
        }

    }
}
