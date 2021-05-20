package com.gmail.evgenymoshchin.web.exception.api;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.gmail.evgenymoshchin.service.exception.UserAlreadyExistException;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import javax.servlet.http.HttpServletRequest;

@Log4j2
@RequiredArgsConstructor
@ControllerAdvice(basePackages = "com.gmail.evgenymoshchin.web.controllers.api")
public class APIExceptionHandler {

    private final ObjectMapper objectMapper;

    @ExceptionHandler(UserAlreadyExistException.class)
    public String handleException(HttpServletRequest request, UserAlreadyExistException exception) throws JsonProcessingException {
        request.setAttribute("exception", exception.getMessage());
        log.error(exception.getMessage(), exception);
        return objectMapper.writeValueAsString(exception);
    }
}
