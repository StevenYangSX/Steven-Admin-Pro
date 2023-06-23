package com.steven.stevenadmin.accountservice.model.dto;

import lombok.Data;

@Data
public class AccountDto {
    private String account;
    private String password;
    private String realName;
    private String headPic;
}
