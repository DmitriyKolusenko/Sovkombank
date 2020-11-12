package com.sovkombank.example.service;

import com.sovkombank.example.entity.InvitedNumbers;
import com.sovkombank.example.entity.Users;

import java.util.List;

public interface InvitedService {
    public void invite(List<InvitedNumbers> numbers, Users user);
}
