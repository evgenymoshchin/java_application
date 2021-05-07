package com.gmail.evgenymoshchin.service.impl;

import com.gmail.evgenymoshchin.repository.ReviewRepository;
import com.gmail.evgenymoshchin.repository.UserRepository;
import com.gmail.evgenymoshchin.repository.model.Review;
import com.gmail.evgenymoshchin.repository.model.User;
import com.gmail.evgenymoshchin.service.ReviewService;
import com.gmail.evgenymoshchin.service.model.ReviewDTO;
import com.gmail.evgenymoshchin.service.model.ReviewPageDTO;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.Objects;

import static com.gmail.evgenymoshchin.service.util.ServiceUtil.getNumbersOfPages;

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
    public ReviewPageDTO findReviewsWithPagination(int pageNumber, int pageSize) {
        ReviewPageDTO reviewPage = new ReviewPageDTO();
        List<Review> reviews = reviewRepository.findWithPagination(pageNumber, pageSize);
        List<ReviewDTO> reviewDTOS = new ArrayList<>();
        for (Review review : reviews) {
            reviewDTOS.add(convertReviewToDTO(review));
        }
        reviewDTOS.sort(Comparator.comparing(ReviewDTO::getDate));
        reviewPage.getReviews().addAll(reviewDTOS);
        Long countOfReviews = reviewRepository.getCount();
        reviewPage.setPagesCount(countOfReviews);
        List<Integer> numbersOfPages = getNumbersOfPages(pageSize, countOfReviews);
        reviewPage.getNumbersOfPages().addAll(numbersOfPages);
        return reviewPage;
    }

    @Override
    @Transactional
    public void removeById(Long id) {
        Review review = reviewRepository.findById(id);
        reviewRepository.remove(review);
    }

    @Override
    @Transactional
    public void changeVisibilityById(Long ids) {
        Review review = reviewRepository.findById(ids);
        review.setIsVisible(!review.getIsVisible());
    }

    private ReviewDTO convertReviewToDTO(Review review) {
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
}
