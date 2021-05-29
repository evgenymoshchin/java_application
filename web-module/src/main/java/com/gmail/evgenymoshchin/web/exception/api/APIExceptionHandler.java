package com.gmail.evgenymoshchin.web.exception.api;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.gmail.evgenymoshchin.service.exception.ServiceException;
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
    public String handleUserAlreadyExistException(HttpServletRequest request, UserAlreadyExistException exception) throws JsonProcessingException {
        request.setAttribute("exception", exception.getMessage());
        log.error(exception.getMessage(), exception);
        return objectMapper.writeValueAsString(exception);
    }

    @ExceptionHandler(ServiceException.class)
    public String handleServiceException(HttpServletRequest request, ServiceException exception) throws JsonProcessingException {
        request.setAttribute("exception", exception.getMessage());
        log.error(exception.getMessage(), exception);
        return objectMapper.writeValueAsString(exception);
    }

    @ExceptionHandler(Exception.class)
    public String handleAllException(HttpServletRequest request, Exception exception) throws JsonProcessingException {
        request.setAttribute("exception", exception.getMessage());
        log.error(exception.getMessage(), exception);
        return objectMapper.writeValueAsString(exception);
    }
}
