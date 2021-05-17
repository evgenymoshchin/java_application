package com.gmail.evgenymoshchin.service.model;

import lombok.Data;

import java.time.LocalDate;

@Data
public class CommentDTO {
    private Long id;
    private String firstName;
    private String lastName;
    private LocalDate date;
    private String commentBody;
}
