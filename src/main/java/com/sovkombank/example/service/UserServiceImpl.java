package com.sovkombank.example.service;


import com.sovkombank.example.entity.Users;
import com.sovkombank.example.repository.UsersRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;

@Service
@Transactional
public class UserServiceImpl implements UserService {

    @Autowired
    UsersRepository usersRepository;
    @Override
    public Users getUserByLogin(String login) {
        return usersRepository.findUsersByLogin(login);
    }
}
