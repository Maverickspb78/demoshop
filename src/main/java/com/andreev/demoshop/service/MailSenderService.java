package com.andreev.demoshop.service;

import com.andreev.demoshop.domain.User;

public interface MailSenderService {
    void sendActivateCode(User user);
}