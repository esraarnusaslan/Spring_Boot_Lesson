package com.tpe.controller;


import com.tpe.domain.User;
import com.tpe.dto.UserRequest;
import com.tpe.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@RestController
public class UserController {

    @Autowired
    private UserService userService;

    //localhost:8080/register + POST + JSON body
    @RequestMapping("/register")
    @PostMapping
    public ResponseEntity<String> register(@Valid @RequestBody UserRequest userRequest){
        userService.saveUser(userRequest);
        return new ResponseEntity<>("User is registered successfully", HttpStatus.CREATED);//201
    }





}
