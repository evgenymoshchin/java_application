package com.gmail.evgenymoshchin.web.controllers;

import com.gmail.evgenymoshchin.service.ReviewService;
import com.gmail.evgenymoshchin.service.model.ReviewDTO;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.List;

import static com.gmail.evgenymoshchin.web.constants.ReviewControllerConstants.GET_ALL_REVIEWS_MAPPING_VALUE;
import static com.gmail.evgenymoshchin.web.constants.ReviewControllerConstants.GET_ALL_REVIEWS_VIEW_NAME_VALUE;
import static com.gmail.evgenymoshchin.web.constants.ReviewControllerConstants.REDIRECTION_REVIEWS_GET_PATH_VALUE;
import static com.gmail.evgenymoshchin.web.constants.ReviewControllerConstants.REMOVE_REVIEW_MAPPING_VALUE;
import static com.gmail.evgenymoshchin.web.constants.ReviewControllerConstants.REVIEWS_ATTRIBUTE_VALUE;
import static com.gmail.evgenymoshchin.web.constants.ReviewControllerConstants.REVIEW_CONTROLLER_MAPPING_VALUE;

@Controller
@RequestMapping(REVIEW_CONTROLLER_MAPPING_VALUE)
public class ReviewController {

    private final ReviewService reviewService;

    public ReviewController(ReviewService reviewService) {
        this.reviewService = reviewService;
    }

    @GetMapping(GET_ALL_REVIEWS_MAPPING_VALUE)
    public String getAllReviews(Model model) {
        List<ReviewDTO> reviews = reviewService.findAll();
        model.addAttribute(REVIEWS_ATTRIBUTE_VALUE, reviews);
        return GET_ALL_REVIEWS_VIEW_NAME_VALUE;
    }

    @GetMapping(REMOVE_REVIEW_MAPPING_VALUE)
    public String removeReview(@PathVariable Long id) {
        reviewService.removeById(id);
        return REDIRECTION_REVIEWS_GET_PATH_VALUE;
    }
}
