package com.gmail.evgenymoshchin.service.exception;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

class UserAlreadyExistExceptionTest {

    @Test
    void shouldThrowException() {
        Throwable exception = assertThrows(UserAlreadyExistException.class, () ->
        {
            throw new UserAlreadyExistException("a message");
        });
        assertEquals("a message", exception.getMessage());
    }

}