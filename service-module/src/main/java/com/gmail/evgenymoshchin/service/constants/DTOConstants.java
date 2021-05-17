package com.gmail.evgenymoshchin.service.constants;

public interface DTOConstants {
    String REGEX_FOR_LATIN_CHARS = "^[A-Za-z]*$";
    String MESSAGE_WHEN_NOT_LATIN_CHARS = "Should be only latin characters";
    String EMAIL_NOT_VALID_MESSAGE = "Email should be valid";
    String PHONE_NUMBER_REGEX = "^\\+375\\d{9}";
    String PHONE_NUMBER_NOT_VALID_MESSAGE = "Phone number should be valid";
}
