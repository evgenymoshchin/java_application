package com.gmail.evgenymoshchin.service.impl;

import com.gmail.evgenymoshchin.repository.ReviewRepository;
import com.gmail.evgenymoshchin.repository.model.Review;
import com.gmail.evgenymoshchin.service.ReviewService;
import com.gmail.evgenymoshchin.service.converters.ReviewServiceConverter;
import com.gmail.evgenymoshchin.service.exception.ServiceException;
import com.gmail.evgenymoshchin.service.model.ReviewDTO;
import com.gmail.evgenymoshchin.service.model.ReviewPageDTO;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.Objects;

import static com.gmail.evgenymoshchin.service.constants.ExceptionConstants.REVIEW_WAS_NOT_FOUND_MESSAGE;
import static com.gmail.evgenymoshchin.service.constants.ExceptionConstants.VISIBILITY_CHANGE_MESSAGE;
import static com.gmail.evgenymoshchin.service.constants.ExceptionConstants.VISIBILITY_WILL_CHANGE;
import static com.gmail.evgenymoshchin.service.util.ServiceUtil.getNumbersOfPages;

@Log4j2
@Service
@RequiredArgsConstructor
public class ReviewServiceImpl implements ReviewService {

    private final ReviewRepository reviewRepository;
    private final ReviewServiceConverter reviewServiceConverter;

    @Transactional
    @Override
    public ReviewPageDTO findReviewsWithPagination(int pageNumber, int pageSize) {
        ReviewPageDTO reviewPage = new ReviewPageDTO();
        List<Review> reviews = reviewRepository.findWithPagination(pageNumber, pageSize);
        List<ReviewDTO> reviewDTOS = new ArrayList<>();
        for (Review review : reviews) {
            reviewDTOS.add(reviewServiceConverter.convertReviewToDTO(review));
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
        if (Objects.nonNull(review)) {
            reviewRepository.remove(review);
        } else {
            throw new ServiceException(String.format(REVIEW_WAS_NOT_FOUND_MESSAGE, id));
        }
    }

    @Override
    @Transactional
    public void changeVisibilityByIds(List<Long> selectedIds) {
        List<Review> reviews = reviewRepository.findAll();
        List<Long> visibleIdsFromDB = new ArrayList<>();
        for (Review review : reviews) {
            if (review.getIsVisible()) {
                visibleIdsFromDB.add(review.getId());
            }
        }
        for (Long id : visibleIdsFromDB) {
            if (!selectedIds.contains(id)) {
                log.info(VISIBILITY_CHANGE_MESSAGE, id);
                findReviewByIdUpdateVisible(id);
            }
        }
        for (Long id : selectedIds) {
            if (!visibleIdsFromDB.contains(id)) {
                log.info(VISIBILITY_WILL_CHANGE, id);
                findReviewByIdUpdateVisible(id);
            }
        }
    }

    @Override
    @Transactional
    public ReviewDTO addReview(ReviewDTO reviewDTO, String username) {
        Review review = reviewServiceConverter.convertDTOToReview(reviewDTO, username);
        reviewRepository.persist(review);
        return reviewServiceConverter.convertReviewToDTO(review);
    }

    private void findReviewByIdUpdateVisible(Long id) {
        Review review = reviewRepository.findById(id);
        if (Objects.nonNull(review)) {
            review.setIsVisible(!review.getIsVisible());
        } else {
            throw new ServiceException(String.format(REVIEW_WAS_NOT_FOUND_MESSAGE, id));
        }
    }
}
