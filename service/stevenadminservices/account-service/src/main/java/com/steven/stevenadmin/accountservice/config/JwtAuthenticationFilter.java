package com.steven.stevenadmin.accountservice.config;

import com.steven.stevenadmin.accountservice.model.entity.AccountEntity;
import com.steven.stevenadmin.accountservice.service.AccountService;
import com.steven.stevenadmin.accountservice.utils.JwtUtils;
import com.steven.stevenadmin.accountservice.utils.TokenUtils;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.lang.NonNull;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;
import java.util.Optional;

@Component
@RequiredArgsConstructor
public class JwtAuthenticationFilter extends OncePerRequestFilter {

    private final JwtUtils jwtUtils;
    private final AccountService accountService;

    @Override
    protected void doFilterInternal(
            @NonNull HttpServletRequest request,
            @NonNull HttpServletResponse response,
            @NonNull FilterChain filterChain) throws ServletException, IOException {
        final String authHeader = request.getHeader("Authorization");
        final String jwtToken;
        final String userAccount;
        System.out.println("doFilterInternal called----authHeader->"+authHeader);

        if (authHeader == null || authHeader.startsWith("Bearer ")) {
            filterChain.doFilter(request, response);
            return;
        }

        jwtToken = authHeader.substring(7);
        userAccount = jwtUtils.extractAccountInfo(jwtToken);

        if (userAccount != null && SecurityContextHolder.getContext().getAuthentication() == null) {
            Optional<AccountEntity> foundAccount = accountService.ifAccountExist(userAccount);
            if (foundAccount.isPresent()) {
                AccountEntity accountEntity = foundAccount.get();
                if (jwtUtils.isTokenValid(jwtToken, accountEntity)) {
                    UsernamePasswordAuthenticationToken authenticationToken =
                            new UsernamePasswordAuthenticationToken(accountEntity, null, accountEntity.getAuthorities());

                    authenticationToken.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));

                    SecurityContextHolder.getContext().setAuthentication(authenticationToken);
                }
            }
        }
        filterChain.doFilter(request, response);
    }
}
