package com.gmail.evgenymoshchin.service;

import com.gmail.evgenymoshchin.service.model.ReviewDTO;
import com.gmail.evgenymoshchin.service.model.ReviewPageDTO;

import java.util.List;

public interface ReviewService {
    ReviewPageDTO findReviewsWithPagination(Integer pageNumber, Integer pageSize);

//    List<ReviewDTO> findAll();

    void removeById(Long id);

    void changeVisibilityById(Long allId);
}
