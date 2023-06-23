package com.steven.stevenadmin.accountservice.utils;


import com.steven.stevenadmin.accountservice.model.entity.AccountEntity;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;
import org.springframework.stereotype.Service;

import java.security.Key;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.function.Function;

@Service
public class JwtUtils {

    // jwt signing key  :
    private static final String SECRET_KEY = "A4jgg5BysruKQWmDHrHojIs9qclhz86zgpHxVSiCD4E=";
    private static final Integer numberOfDays = 5;

    /*
        在token中 解析出用户信息
     */
    public String extractAccountInfo(String jwtToken) {
//        return extractClaim(jwtToken, Claims::getSubject);
        Claims body = Jwts.parserBuilder().setSigningKey(getSigningKey()).build().parseClaimsJws(jwtToken).getBody();
        String subject = body.getSubject();
        System.out.println("subject -------------->   " + subject);
        return subject;
    }

    public <T> T extractClaim(String jwtToken, Function<Claims, T> claimsFunction) {
        final Claims claims = extractAllClaims(jwtToken);
        return claimsFunction.apply(claims);
    }

    public Claims extractAllClaims(String jwtToken) {
        return Jwts.parserBuilder().setSigningKey(getSigningKey())
                .build().parseClaimsJws(jwtToken).getBody();
    }


    /*
    Method used to generate a jwt token
     */
    public String generateToken(Map<String, Object> extraClaims, AccountEntity accountEntity) {
        return Jwts.builder().setClaims(extraClaims).setSubject(accountEntity.getUsername())
                .setIssuedAt(new Date(System.currentTimeMillis()))
                .setExpiration(new Date(System.currentTimeMillis() + 1000L * 60 * 60 * 24 * numberOfDays))
                .signWith(getSigningKey(), SignatureAlgorithm.HS256)
                .compact();
    }

    public String generateToken(AccountEntity accountEntity) {
        return generateToken(new HashMap<>(), accountEntity);
    }

    public Boolean isTokenValid(String token, AccountEntity accountEntity) {
        final String userAccount = extractAccountInfo(token);
        return userAccount.equals(accountEntity.getUsername()) && !isTokenExpired(token);
    }

    public Long getExpirationTime(String token) {
        return extractClaim(token, Claims::getExpiration).getTime() / 1000;
    }

    private boolean isTokenExpired(String token) {
        return extractClaim(token, Claims::getExpiration).before(new Date());
    }


    private Key getSigningKey() {
        byte[] keyBytes = Decoders.BASE64.decode(SECRET_KEY);
        return Keys.hmacShaKeyFor(keyBytes);
    }
}
