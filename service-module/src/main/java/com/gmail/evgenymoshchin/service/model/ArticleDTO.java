package com.gmail.evgenymoshchin.service.model;

import lombok.Data;
import lombok.ToString;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

@Data
public class ArticleDTO {
    private Long id;
    private String articleBody;
    private String name;
    private String summary;
    private LocalDate date;
    private Long userId;
    private String firstName;
    private String lastName;
    @ToString.Exclude
    private List<CommentDTO> comments = new ArrayList<>();
}
