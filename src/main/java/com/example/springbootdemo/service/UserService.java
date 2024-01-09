package com.example.springbootdemo.service;

import com.example.springbootdemo.dto.UserDTO;
import com.example.springbootdemo.model.UserModel;
import com.example.springbootdemo.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class UserService{

    private final UserRepository userRepository;
    private final PasswordEncoder bCryptPasswordEncoder;

    public UserModel findUserByUsername(String username){
        return userRepository.findByName(username);
    }

    public void createUser(UserDTO userDTO){
        String encryptedPassword = bCryptPasswordEncoder.encode(userDTO.getPassword());
        userDTO.setEncryptedPassword(encryptedPassword);
        userRepository.insert(userDTO);
    }

    public void deleteUser(Integer userId){
        userRepository.updateValid(userId);
    }
}
