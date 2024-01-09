package com.example.springbootdemo.security;

import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.interfaces.DecodedJWT;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;
import java.io.IOException;
import java.util.ArrayList;

/**
 * 認証が必要なAPIアクセス時に認証処理を行うFilter
 */
@Component
public class AuthorizeFilter extends OncePerRequestFilter {

    private final AntPathRequestMatcher matcher = new AntPathRequestMatcher("/login");

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
        if (!matcher.matches(request)) {
            // headersのkeyを指定して値(トークン)を取得する
            String xAuthToken = request.getHeader("X-AUTH-TOKEN");
            if (xAuthToken == null || !xAuthToken.startsWith("Bearer ")) {
                filterChain.doFilter(request, response);
                return;
            }
            // tokenの検証と認証を行う
            DecodedJWT decodedJWT = JWT.require(Algorithm.HMAC256("secret")).build().verify(xAuthToken.substring(7));
            // usernameの取得
            String username = decodedJWT.getClaim("username").toString();
            // ログイン状態の設定
            SecurityContextHolder.getContext().setAuthentication(new UsernamePasswordAuthenticationToken(username, null, new ArrayList<>()));
        }
        filterChain.doFilter(request, response);
    }
}
