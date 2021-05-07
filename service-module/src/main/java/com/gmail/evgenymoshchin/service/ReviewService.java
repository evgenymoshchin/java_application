package com.gmail.evgenymoshchin.service;

import com.gmail.evgenymoshchin.service.model.ReviewPageDTO;

public interface ReviewService {
    ReviewPageDTO findReviewsWithPagination(int pageNumber, int pageSize);

    void removeById(Long id);

    void changeVisibilityById(Long ids);
}
