package com.example.springbootdemo.controller;

import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import com.example.springbootdemo.controller.request.UserRequest;
import com.example.springbootdemo.service.UserService;
import lombok.AllArgsConstructor;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.core.AuthenticationException;
import org.springframework.web.bind.annotation.*;

/**
 * ログイン処理を行う
 */
@RestController
@CrossOrigin
@RequiredArgsConstructor
public class LoginController {

    private final DaoAuthenticationProvider authenticationProvider;

    private final UserService userService;

    @RequestMapping(value = "/login",method = RequestMethod.POST)
    public ResponseEntity<Integer> login(@RequestBody UserRequest request) {
        try {
            // username,passwordでの認証を行う
            authenticationProvider.authenticate(new UsernamePasswordAuthenticationToken(request.getUsername(), request.getPassword()));
            // JWTトークンの生成
            String token = JWT.create().withClaim("username",request.getUsername())
                    .sign(Algorithm.HMAC256("secret"));
            // トークンをクライアントに返す
            HttpHeaders httpHeaders = new HttpHeaders();
            httpHeaders.add("x-auth-token",token);
            var userId = userService.findUserByUsername(request.getUsername()).getId();
            return new ResponseEntity<>(userId, httpHeaders, HttpStatus.OK);
        } catch (AuthenticationException e) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
        }
    }
}
