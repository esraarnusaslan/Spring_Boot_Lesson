package com.tpe.service;


import com.tpe.domain.Role;
import com.tpe.domain.User;
import com.tpe.domain.enums.RoleType;
import com.tpe.dto.UserRequest;
import com.tpe.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.HashSet;
import java.util.Set;

@Service
public class UserService {


    @Autowired
    private UserRepository userRepository;

    @Autowired
    private RoleService roleService;

    @Autowired
    private PasswordEncoder passwordEncoder;


    public void saveUser(UserRequest userRequest) {
        User user=new User();
        user.setFirstName(userRequest.getFirstName());
        user.setLastName(userRequest.getLastName());
        user.setUserName(userRequest.getUserName());
        //passwordu sifreleyerek db ye gonderecegiz
        String password=userRequest.getPassword();
        String encodedPassword=passwordEncoder.encode(password);//request teki passsword karmasik bir ifade aldi
        user.setPassword(encodedPassword);

        //userin role u setlenecek
        Set<Role>roles=new HashSet<>();
        Role role=roleService.getRoleByType(RoleType.ROLE_ADMIN);
        roles.add(role);

        user.setRoles(roles);//defaultta user a admin rolunu verdik

        userRepository.save(user);


    }
}
