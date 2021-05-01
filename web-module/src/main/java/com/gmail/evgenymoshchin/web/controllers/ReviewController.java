package com.gmail.evgenymoshchin.web.controllers;

import com.gmail.evgenymoshchin.service.ReviewService;
import com.gmail.evgenymoshchin.service.model.ReviewDTO;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

import java.lang.invoke.MethodHandles;
import java.util.List;

@Controller
@RequestMapping("/reviews")
public class ReviewController {

    private final ReviewService reviewService;

    private static final Logger logger = LogManager.getLogger(MethodHandles.lookup().lookupClass());

    public ReviewController(ReviewService reviewService) {
        this.reviewService = reviewService;
    }

    @GetMapping("/get")
    public String getAllReviews(Model model) {
        List<ReviewDTO> reviews = reviewService.findAll();
        model.addAttribute("reviews", reviews);
        return "get_all_reviews";
    }

    @GetMapping("/remove/{id}")
    public String removeReview(@PathVariable Long id) {
        reviewService.removeById(id);
        return "redirect:/reviews/get";
    }
}
