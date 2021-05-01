package com.gmail.evgenymoshchin.service.impl;

import com.gmail.evgenymoshchin.repository.ReviewRepository;
import com.gmail.evgenymoshchin.repository.UserRepository;
import com.gmail.evgenymoshchin.repository.model.Review;
import com.gmail.evgenymoshchin.repository.model.User;
import com.gmail.evgenymoshchin.service.ReviewService;
import com.gmail.evgenymoshchin.service.model.ReviewDTO;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.ArrayList;
import java.util.List;

@Service
public class ReviewServiceImpl implements ReviewService {

    private final ReviewRepository reviewRepository;
    private final UserRepository userRepository;

    public ReviewServiceImpl(ReviewRepository reviewRepository, UserRepository userRepository) {
        this.reviewRepository = reviewRepository;
        this.userRepository = userRepository;
    }

    @Transactional
    @Override
    public List<ReviewDTO> findAll() {
        List<Review> reviews = reviewRepository.findAll();
        List<ReviewDTO> reviewDTOS = new ArrayList<>();
        for (Review review : reviews) {
            reviewDTOS.add(convertReviewToDTO(review));
        }
        return reviewDTOS;
    }

    @Override
    @Transactional
    public void removeById(Long id) {
        Review review = reviewRepository.findById(id);
        reviewRepository.remove(review);
    }

    private ReviewDTO convertReviewToDTO(Review review) {
        ReviewDTO reviewDTO = new ReviewDTO();
        reviewDTO.setId(review.getId());
        reviewDTO.setReviewBody(review.getReviewBody());
        reviewDTO.setDate(review.getCreatedBy());
        reviewDTO.setShown(review.getShown());
        User user = userRepository.findById(review.getUser().getId());
        reviewDTO.setUserId(user.getId());
        reviewDTO.setFirstName(user.getFirstName());
        reviewDTO.setLastName(user.getLastName());
        reviewDTO.setPatronymic(user.getPatronymic());
        return reviewDTO;
    }
}
