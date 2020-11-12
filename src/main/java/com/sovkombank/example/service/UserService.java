package com.sovkombank.example.service;

import com.sovkombank.example.entity.Users;

public interface UserService {
    Users getUserByLogin(String login);
}
