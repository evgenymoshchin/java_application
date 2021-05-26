package com.gmail.evgenymoshchin.service.model;

import lombok.Data;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.time.LocalDate;

@Data
public class ReviewDTO {
    private Long id;
    @NotNull
    @NotEmpty
    @Size(max = 500)
    private String reviewBody;
    private LocalDate date;
    private Boolean isVisible;
    private Long userId;
    private String firstName;
    private String lastName;
    private String patronymic;
}
