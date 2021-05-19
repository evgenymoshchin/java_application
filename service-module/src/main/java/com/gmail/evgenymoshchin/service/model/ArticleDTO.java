package com.gmail.evgenymoshchin.service.model;

import lombok.Builder;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
@EqualsAndHashCode
public class ArticleDTO {
    private Long id;
    @NotNull
    @NotEmpty
    @Size(max = 1000)
    private String articleBody;
    @NotNull
    @NotEmpty
    @Size(max = 20)
    private String name;
    private String summary;
    private LocalDate date;
    private Long userId;
    private String firstName;
    private String lastName;
    private List<CommentDTO> comments = new ArrayList<>();
}
