package com.gmail.evgenymoshchin.service.converters;

import com.gmail.evgenymoshchin.repository.model.Review;
import com.gmail.evgenymoshchin.service.model.ReviewDTO;

public interface ReviewServiceConverter {
    ReviewDTO convertReviewToDTO(Review review);

    Review convertDTOToReview(ReviewDTO reviewDTO, String username);
}
