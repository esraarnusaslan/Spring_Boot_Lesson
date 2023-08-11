package com.tpe.security.service;


import com.tpe.domain.Role;
import com.tpe.domain.User;
import com.tpe.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;


@Service
public class UserDetailsServiceImpl implements UserDetailsService {

    //amac: UserDetails-->User    GrandtedAuthorities-->Role
    //kendi yapilarimizi security yapilarina taniticaz


    @Autowired
    private UserRepository userRepository;


    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {

        User user=userRepository.findByUserName(username).orElseThrow(()->
                new UsernameNotFoundException("User not found by username: "+username));

        return new org.springframework.security.core.userdetails.
                User(user.getUserName(),user.getPassword(),buildGrantedAuthorities(user.getRoles()));
        //ust satirdaki islem security service impl ederek user ve rollerimizi security yapisina cevirdik
    }


    private List<SimpleGrantedAuthority> buildGrantedAuthorities(Set<Role> roles){

        List<SimpleGrantedAuthority> authorities=new ArrayList<>();
        for (Role role: roles){//rollerin isimlerini parametre olarak SimpleGrantedAuthority nin  const. veridgimizde
                                //yeni SimpleGrantedAuthority olusturuyoeuz. ve bu SimpleGrantedAuthority listeye ekleriz
            authorities.add(new SimpleGrantedAuthority(role.getType().name()));
        }
        return authorities;
    }









}
