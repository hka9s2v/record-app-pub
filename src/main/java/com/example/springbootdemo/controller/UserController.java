package com.example.springbootdemo.controller;

import com.example.springbootdemo.controller.request.UserRequest;
import com.example.springbootdemo.dto.UserDTO;
import com.example.springbootdemo.service.UserService;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotNull;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

@Controller
@CrossOrigin
@RequiredArgsConstructor
public class UserController {

    private final UserService userService;

    /**
     * ユーザーを新規作成する
     */
    @PostMapping("/users")
    ResponseEntity<Void> createUser(@RequestBody UserRequest userRequest){
        userService.createUser(new UserDTO(userRequest.getUsername(), userRequest.getPassword(), ""));
        return new ResponseEntity<>(HttpStatus.OK);
    }

    /**
     * ユーザーを削除する
     */
    @PatchMapping("/users/{userId}")
    ResponseEntity<Void> createUser(@NotNull @Valid @PathVariable Integer userId){
        userService.deleteUser(userId);
        return new ResponseEntity<>(HttpStatus.OK);
    }

}
