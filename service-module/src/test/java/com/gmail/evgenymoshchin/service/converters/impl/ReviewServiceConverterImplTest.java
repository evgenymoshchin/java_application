package com.gmail.evgenymoshchin.service.converters.impl;

import com.gmail.evgenymoshchin.repository.UserRepository;
import com.gmail.evgenymoshchin.repository.model.Review;
import com.gmail.evgenymoshchin.repository.model.User;
import com.gmail.evgenymoshchin.service.model.ReviewDTO;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.LocalDate;

import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class ReviewServiceConverterImplTest {

    @Mock
    private UserRepository userRepository;

    @InjectMocks
    private ReviewServiceConverterImpl reviewServiceConverter;

    @Test
    void shouldConvertReviewToDTOAndReturnCorrectId() {
        Review review = new Review();
        Long testId = 1L;
        review.setId(testId);
        ReviewDTO reviewDTO = reviewServiceConverter.convertReviewToDTO(review);
        Assertions.assertEquals(testId, reviewDTO.getId());
    }

    @Test
    void shouldConvertReviewToDTOAndReturnCorrectReviewBody() {
        Review review = new Review();
        String reviewBody = "body";
        review.setReviewBody(reviewBody);
        ReviewDTO reviewDTO = reviewServiceConverter.convertReviewToDTO(review);
        Assertions.assertEquals(reviewBody, reviewDTO.getReviewBody());
    }

    @Test
    void shouldConvertReviewToDTOAndReturnCorrectDate() {
        Review review = new Review();
        LocalDate date = LocalDate.now();
        review.setCreatedBy(date);
        ReviewDTO reviewDTO = reviewServiceConverter.convertReviewToDTO(review);
        Assertions.assertEquals(date, reviewDTO.getDate());
    }

    @Test
    void shouldConvertReviewToDTOAndReturnCorrectBooleanVisible() {
        Review review = new Review();
        review.setIsVisible(true);
        ReviewDTO reviewDTO = reviewServiceConverter.convertReviewToDTO(review);
        Assertions.assertTrue(reviewDTO.getIsVisible());
    }

    @Test
    void shouldConvertReviewToDTOAndReturnCorrectUserId() {
        User user = new User();
        Long id = 1L;
        user.setId(id);
        when(userRepository.findById(id)).thenReturn(user);
        Review review = new Review();
        review.setUser(user);
        ReviewDTO reviewDTO = reviewServiceConverter.convertReviewToDTO(review);
        Assertions.assertEquals(id, reviewDTO.getUserId());
    }

    @Test
    void shouldConvertReviewToDTOAndReturnCorrectFirstName() {
        User user = new User();
        String firstName = "name";
        Long id = 1L;
        user.setId(id);
        user.setFirstName(firstName);
        when(userRepository.findById(id)).thenReturn(user);
        Review review = new Review();
        review.setUser(user);
        ReviewDTO reviewDTO = reviewServiceConverter.convertReviewToDTO(review);
        Assertions.assertEquals(firstName, reviewDTO.getFirstName());
    }

    @Test
    void shouldConvertReviewToDTOAndReturnCorrectLastName() {
        User user = new User();
        String lastName = "name";
        Long id = 1L;
        user.setId(id);
        user.setLastName(lastName);
        when(userRepository.findById(id)).thenReturn(user);
        Review review = new Review();
        review.setUser(user);
        ReviewDTO reviewDTO = reviewServiceConverter.convertReviewToDTO(review);
        Assertions.assertEquals(lastName, reviewDTO.getLastName());
    }

    @Test
    void shouldConvertReviewToDTOAndReturnCorrectPatronymic() {
        User user = new User();
        String patronymic = "patronymic";
        Long id = 1L;
        user.setId(id);
        user.setPatronymic(patronymic);
        when(userRepository.findById(id)).thenReturn(user);
        Review review = new Review();
        review.setUser(user);
        ReviewDTO reviewDTO = reviewServiceConverter.convertReviewToDTO(review);
        Assertions.assertEquals(patronymic, reviewDTO.getPatronymic());
    }
}