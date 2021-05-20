package com.gmail.evgenymoshchin.web.exception;

import com.gmail.evgenymoshchin.service.exception.UserAlreadyExistException;
import com.gmail.evgenymoshchin.web.controllers.web.UserController;
import lombok.extern.log4j.Log4j2;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import javax.servlet.http.HttpServletRequest;

@Log4j2
@ControllerAdvice(basePackageClasses = UserController.class)
public class UserControllerExceptionHandler {

    @ExceptionHandler(UserAlreadyExistException.class)
    public String handleException(HttpServletRequest request, UserAlreadyExistException exception) {
        request.setAttribute("exception", exception.getMessage());
        log.error(exception.getMessage(), exception);
        return "user_controller_error";
    }
}
