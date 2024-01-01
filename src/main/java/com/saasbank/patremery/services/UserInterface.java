package com.saasbank.patremery.services;

import com.saasbank.patremery.dto.BankResponse;
import com.saasbank.patremery.dto.UserRequest;

public interface UserInterface {
    BankResponse createUser(UserRequest userRequest);
}
