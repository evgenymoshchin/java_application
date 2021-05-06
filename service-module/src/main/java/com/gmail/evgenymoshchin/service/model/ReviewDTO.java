package com.gmail.evgenymoshchin.service.model;

import lombok.Data;

import java.time.LocalDate;

@Data
public class ReviewDTO {
    private Long id;
    private String reviewBody;
    private LocalDate date;
    private Boolean isVisible;
    private Long userId;
    private String firstName;
    private String lastName;
    private String patronymic;
}
