package com.gmail.evgenymoshchin.service;

import com.gmail.evgenymoshchin.service.model.ReviewDTO;
import com.gmail.evgenymoshchin.service.model.ReviewPageDTO;

import java.util.List;

public interface ReviewService {
    ReviewPageDTO findReviewsWithPagination(int pageNumber, int pageSize);

    void removeById(Long id);

    void changeVisibilityByIds(List<Long> selectedIds);

    void addReview(ReviewDTO reviewDTO, String username);
}
