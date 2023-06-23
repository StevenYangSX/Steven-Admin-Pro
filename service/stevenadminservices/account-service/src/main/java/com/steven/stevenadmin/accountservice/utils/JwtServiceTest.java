package com.steven.stevenadmin.accountservice.utils;

import com.steven.stevenadmin.accountservice.model.entity.AccountEntity;
import com.steven.stevenadmin.accountservice.utils.JwtUtils;

public class JwtServiceTest {


    public static JwtUtils jwtUtils = new JwtUtils();

    public static void main(String[] args) {
        AccountEntity accountEntity = new AccountEntity();
        accountEntity.setAccount("testAccount");

        String token = jwtUtils.generateToken(accountEntity);
        System.out.println("generated token ---->"+token);
        String userInfo = jwtUtils.extractAccountInfo(token);
        System.out.println("Extracted userinfo ---->"+userInfo);
        System.out.println("--------------------");
        System.out.println("is valid ..."+ jwtUtils.isTokenValid(token,accountEntity));
    }
}
