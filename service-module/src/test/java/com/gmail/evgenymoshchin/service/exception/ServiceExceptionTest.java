package com.gmail.evgenymoshchin.service.exception;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class ServiceExceptionTest {

    @Test
    void shouldThrowException() {
        Throwable exception = assertThrows(ServiceException.class, () ->
        {
            throw new ServiceException("a message");
        });
        assertEquals("a message", exception.getMessage());
    }

}