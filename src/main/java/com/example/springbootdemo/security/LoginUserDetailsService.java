package com.example.springbootdemo.security;

import com.example.springbootdemo.model.UserModel;
import com.example.springbootdemo.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@RequiredArgsConstructor
public class LoginUserDetailsService implements UserDetailsService {

    private final UserRepository repository;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        Optional<UserModel> maybeUser = Optional.of(repository.findByName(username));
        return maybeUser.map(LoginUserDetails::new)
                .orElseThrow(() -> new UsernameNotFoundException("username not found."));
    }
}
