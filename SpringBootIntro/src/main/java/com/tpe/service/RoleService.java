package com.tpe.service;


import com.tpe.domain.Role;
import com.tpe.domain.enums.RoleType;
import com.tpe.exception.ResourceNotFoundException;
import com.tpe.repository.RoleRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class RoleService {


    @Autowired
    private RoleRepository roleRepository;

    public Role getRoleByType(RoleType type){
        Role role=roleRepository.findByType(type).orElseThrow(()->
                new ResourceNotFoundException("role is not found"));
        return role;
    }





}
