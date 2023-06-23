package com.steven.stevenadmin.accountservice.model.vo;

import lombok.Data;

import java.util.Date;

@Data
public class LoginVo {
    private String account;
    private Integer id;
    private String token;
    private Long expirationTime;
}
