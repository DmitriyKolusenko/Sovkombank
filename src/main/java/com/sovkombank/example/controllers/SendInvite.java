package com.sovkombank.example.controllers;

import com.sovkombank.example.entity.InvitedNumbers;
import com.sovkombank.example.entity.Users;
import com.sovkombank.example.repository.UsersRepository;
import com.sovkombank.example.service.InvitedService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.security.Principal;
import java.util.Collections;
import java.util.List;
import java.util.Map;

@Controller
@RequestMapping("/sendInvite")
public class SendInvite {

    @Autowired
    UsersRepository usersRepository;

    @Autowired
    InvitedService invitedService;

    @RequestMapping(method = RequestMethod.POST)
    @ResponseBody
    public Map<String, String> sendInvite(Principal principal, @RequestBody List<InvitedNumbers> numbersList) {
        Users user = usersRepository.findUsersByLogin(principal.getName());
        invitedService.invite(numbersList, user);
        return Collections.singletonMap("message", "The users was successfully invited.");
    }
}
