package com.gmail.evgenymoshchin.service.model;

import lombok.Data;

import java.time.LocalDate;
import java.util.Date;

@Data
public class ArticleDTO {
    private Long id;
    private String articleBody;
    private String name;
    private String summary;
    private Date date;
    private Long userId;
    private String firstName;
    private String lastName;
}
