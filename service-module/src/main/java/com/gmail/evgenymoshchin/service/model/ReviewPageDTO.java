package com.gmail.evgenymoshchin.service.model;

import lombok.Data;

import java.util.ArrayList;
import java.util.List;

@Data
public class ReviewPageDTO {
    private List<ReviewDTO> reviews = new ArrayList<>();
    private Long pagesCount;
    private List<Integer> numbersOfPages = new ArrayList<>();
}
