package com.gmail.evgenymoshchin.service.constants;

public interface UserServiceConstants {
    String MAIL_PASSWORD_CHANGE_NOTIFICATION_VALUE = "Password change notification";
    String MAIL_TEXT_NOTIFICATION_VALUE = "Password for : %s is %s";
    String USER_EXIST_EXCEPTION_MESSAGE_VALUE = "There is an user with that email: ";
    String SUCCESSFUL_MAIL_MESSAGE = "Password for user {} sent successfully";
    String USER_NOT_FOUND_EXCEPTION_MESSAGE = "User with username: %s was not found";
}
