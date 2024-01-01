package com.saasbank.patremery.services;

import com.saasbank.patremery.dto.EmailDetails;

public interface EmailInterface {
    void sendMail(EmailDetails emailDetails);
}
