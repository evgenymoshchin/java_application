package com.gmail.evgenymoshchin.service.converters.impl;

import com.gmail.evgenymoshchin.repository.UserRepository;
import com.gmail.evgenymoshchin.repository.model.Review;
import com.gmail.evgenymoshchin.repository.model.User;
import com.gmail.evgenymoshchin.service.converters.ReviewServiceConverter;
import com.gmail.evgenymoshchin.service.model.ReviewDTO;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.time.LocalDate;
import java.util.Objects;

@Component
@RequiredArgsConstructor
public class ReviewServiceConverterImpl implements ReviewServiceConverter {

    private final UserRepository userRepository;

    @Override
    public ReviewDTO convertReviewToDTO(Review review) {
        ReviewDTO reviewDTO = new ReviewDTO();
        reviewDTO.setId(review.getId());
        reviewDTO.setReviewBody(review.getReviewBody());
        reviewDTO.setDate(review.getCreatedBy());
        reviewDTO.setIsVisible(review.getIsVisible());
        if (Objects.nonNull(review.getUser())) {
            User user = userRepository.findById(review.getUser().getId());
            reviewDTO.setUserId(user.getId());
            reviewDTO.setFirstName(user.getFirstName());
            reviewDTO.setLastName(user.getLastName());
            reviewDTO.setPatronymic(user.getPatronymic());
        }
        return reviewDTO;
    }

    @Override
    public Review convertDTOToReview(ReviewDTO reviewDTO, String username) {
        Review review = new Review();
        review.setReviewBody(reviewDTO.getReviewBody());
        review.setIsVisible(Boolean.FALSE);
        review.setCreatedBy(LocalDate.now());
        if (Objects.nonNull(userRepository.findByUsername(username))) {
            User user = userRepository.findByUsername(username);
            review.setUser(user);
        }
        return review;
    }
}
