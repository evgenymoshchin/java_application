package com.gmail.evgenymoshchin.service.impl;

import com.gmail.evgenymoshchin.repository.ReviewRepository;
import com.gmail.evgenymoshchin.repository.model.Review;
import com.gmail.evgenymoshchin.service.ReviewService;
import com.gmail.evgenymoshchin.service.converters.ReviewServiceConverter;
import com.gmail.evgenymoshchin.service.model.ReviewDTO;
import com.gmail.evgenymoshchin.service.model.ReviewPageDTO;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

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
        reviewRepository.remove(review);
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
                log.info("This review id {}, wasn't contain in visibleIdsFromDB list, visibility will change", id);
                findReviewByIdUpdateVisible(id);
            }
        }
        for (Long id : selectedIds) {
            if (!visibleIdsFromDB.contains(id)) {
                log.info("This review id {}, wasn't contain in selectedIds list, visibility will change", id);
                findReviewByIdUpdateVisible(id);
            }
        }
    }

    private void findReviewByIdUpdateVisible(Long id) {
        Review review = reviewRepository.findById(id);
        review.setIsVisible(!review.getIsVisible());
    }
}
