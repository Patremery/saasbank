package com.saasbank.patremery.controllers;

import com.saasbank.patremery.dto.BankResponse;
import com.saasbank.patremery.dto.UserRequest;
import com.saasbank.patremery.services.UserInterface;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/user")
public class UserController {
    UserInterface userInterface;

    public UserController(UserInterface userInterface) {
        this.userInterface = userInterface;
    }

    @PostMapping
    public BankResponse createAccount(@RequestBody UserRequest userRequest) {
        return userInterface.createUser(userRequest);
    }
}
