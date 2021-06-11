package com.gmail.evgenymoshchin.web.exception;

import com.gmail.evgenymoshchin.service.exception.ServiceException;
import lombok.extern.log4j.Log4j2;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import javax.servlet.http.HttpServletRequest;

@Log4j2
@ControllerAdvice(basePackages = "com.gmail.evgenymoshchin.web.controllers.web")
public class ServiceExceptionHandler {

    @ExceptionHandler(ServiceException.class)
    public String handleException(HttpServletRequest request, ServiceException exception) {
        request.setAttribute("exception", exception.getMessage());
        log.error(exception.getMessage(), exception);
        return "controller_error";
    }

    @ExceptionHandler(Exception.class)
    public String handleAllException(HttpServletRequest request, Exception exception) {
        request.setAttribute("exception", exception.getMessage());
        log.error(exception.getMessage(), exception);
        return "controller_error";
    }
}
