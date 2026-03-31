package com.zyratechnologies.jwtrolebasedproject.service;

import com.zyratechnologies.jwtrolebasedproject.model.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import com.zyratechnologies.jwtrolebasedproject.repository.UserRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class UserDetailsImpl implements UserDetailsService {
    @Autowired
    private  UserRepository userRepository;


    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
       User user = userRepository.findByUsername(username)
        .orElseThrow(()->new UsernameNotFoundException("user with the username is not found"));
        return new org.springframework.security.core.userdetails.User(
                user.getUsername(),
                user.getPassword(),
               List.of(new SimpleGrantedAuthority("ROLE_"+user.getRole().name())
        ));

    }
}
