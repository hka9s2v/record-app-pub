package com.example.springbootdemo.security;

import org.springframework.security.core.GrantedAuthority;
import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import lombok.Getter;

@Getter
@AllArgsConstructor
@EqualsAndHashCode
public class CustomRole implements GrantedAuthority {
    private final String role;

    public static final CustomRole ROLE_USER = new CustomRole("ROLE_USER");
    public static final CustomRole ROLE_ADMIN = new CustomRole("ROLE_ADMIN");

    @Override
    public String getAuthority() {
        return role;
    }
}
