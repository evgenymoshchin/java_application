package com.gmail.evgenymoshchin.service.impl;

import com.gmail.evgenymoshchin.repository.ReviewRepository;
import com.gmail.evgenymoshchin.repository.model.Review;
import com.gmail.evgenymoshchin.service.converters.ReviewServiceConverter;
import com.gmail.evgenymoshchin.service.model.ReviewDTO;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class ReviewServiceImplTest {

    @Mock
    ReviewRepository reviewRepository;
    @Mock
    ReviewServiceConverter converter;
    @InjectMocks
    ReviewServiceImpl reviewService;

    @Test
    void shouldSaveReviewAndReturnCorrectId() {
        Long id = 1L;
        ReviewDTO reviewDTO = new ReviewDTO();
        reviewDTO.setId(1L);
        Review review = new Review();
        when(converter.convertDTOToReview(reviewDTO, "")).thenReturn(review);
        reviewRepository.persist(review);
        when(converter.convertReviewToDTO(review)).thenReturn(reviewDTO);
        ReviewDTO savedReview = reviewService.addReview(reviewDTO, "");
        assertEquals(id, savedReview.getId());
    }
}