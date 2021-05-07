package com.gmail.evgenymoshchin.service.impl;

import com.gmail.evgenymoshchin.service.PasswordService;
import org.springframework.stereotype.Component;

import java.util.Random;

import static com.gmail.evgenymoshchin.service.constants.PasswordServiceConstant.PASSWORD_GENERATOR_STRING_VALUE;
import static com.gmail.evgenymoshchin.service.constants.PasswordServiceConstant.PASSWORD_RANDOM_BOUND_VALUE;

@Component
public class PasswordServiceImpl implements PasswordService {

    private static final Random RANDOM = new Random();

    @Override
    public String generateRandomPassword() {
        return PASSWORD_GENERATOR_STRING_VALUE + RANDOM.nextInt(PASSWORD_RANDOM_BOUND_VALUE);
    }
}

