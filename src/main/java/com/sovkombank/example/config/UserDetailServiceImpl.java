package com.sovkombank.example.config;

import com.sovkombank.example.entity.Users;
import com.sovkombank.example.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Component;

@Component
public class UserDetailServiceImpl implements UserDetailsService {

    @Autowired
    private UserService userService;

    @Override
    public UserDetails loadUserByUsername(String login) throws UsernameNotFoundException {
        Users user = userService.getUserByLogin(login);
        User.UserBuilder builder;
        builder = User.withUsername(login);
        builder.disabled(false);
        builder.password(user.getPassword());
        builder.authorities(user.getRole());
        return builder.build();
    }
}
