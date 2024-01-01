package com.saasbank.patremery.services;

import com.saasbank.patremery.dto.AccountInfo;
import com.saasbank.patremery.dto.BankResponse;
import com.saasbank.patremery.dto.EmailDetails;
import com.saasbank.patremery.dto.UserRequest;
import com.saasbank.patremery.entities.User;
import com.saasbank.patremery.repositories.UserRepository;
import com.saasbank.patremery.utils.AccountUtils;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;

@Service
public class UserService implements UserInterface {
    private final UserRepository userRepository;
    private EmailService emailService;

    public UserService(UserRepository userRepository, EmailService emailService) {
        this.userRepository = userRepository;
        this.emailService = emailService;
    }

    @Override
    public BankResponse createUser(UserRequest userRequest) {
        if(userRepository.existsByEmail(userRequest.getEmail())) {
            return BankResponse.builder()
                                .responseCode(AccountUtils.ACCOUNT_EXISTS_CODE)
                                .responseMessage(AccountUtils.ACCOUNT_EXISTS_MESSAGE)
                                .accountInfo(null)
                                .build();
        } else {
            User newUser = User.builder()
                                .firstName(userRequest.getFirstName())
                                .lastName(userRequest.getLastName())
                                .otherName(userRequest.getOtherName())
                                .email(userRequest.getEmail())
                                .phoneNumber(userRequest.getPhoneNumber())
                                .alternativePhoneNumber(userRequest.getAlternativePhoneNumber())
                                .city(userRequest.getCity())
                                .gender(userRequest.getGender())
                                .stateOfOrigin(userRequest.getStateOfOrigin())
                                .address(userRequest.getAddress())
                                .accountNumber(AccountUtils.generateAccountNumber())
                                .accountBalance(BigDecimal.ZERO)
                                .status("ACTIVE")
                                .build();
            //Save New User to database
            User user = userRepository.save(newUser);

            //Send email to user
            EmailDetails emailDetails = EmailDetails.builder()
                                                    .subject("Account created")
                                                    .messageBody("Congratulations " + user.getFirstName() + ", Your account was successfully created. Your account number is: " + user.getAccountNumber())
                                                    .recipient(user.getEmail())
                                                    .build();

            emailService.sendMail(emailDetails);

            return BankResponse.builder()
                    .responseMessage(AccountUtils.ACCOUNT_CREATED_MESSAGE)
                    .responseCode(AccountUtils.ACCOUNT_CREATED_CODE)
                    .accountInfo(AccountInfo.builder()
                            .accountBalance(user.getAccountBalance())
                            .accountNumber(user.getAccountNumber())
                            .accountName(user.getFirstName() + " " + user.getLastName() + " " + user.getOtherName())
                            .build())
                    .build();
        }
    }
}
