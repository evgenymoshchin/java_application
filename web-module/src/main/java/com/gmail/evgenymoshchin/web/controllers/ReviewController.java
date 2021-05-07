package com.gmail.evgenymoshchin.web.controllers;

import com.gmail.evgenymoshchin.service.ReviewService;
import com.gmail.evgenymoshchin.service.model.ReviewPageDTO;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.lang.invoke.MethodHandles;
import java.util.List;

@Controller
@RequestMapping("/reviews")
public class ReviewController {

    private static final Logger logger = LogManager.getLogger(MethodHandles.lookup().lookupClass());

    private final ReviewService reviewService;

    public ReviewController(ReviewService reviewService) {
        this.reviewService = reviewService;
    }

    @GetMapping("/get")
    public String getReviews(Model model,
                             @RequestParam(value = "pageSize", required = false, defaultValue = "10") Integer pageSize,
                             @RequestParam(value = "page", required = false, defaultValue = "1") Integer pageNumber
    ) {
        ReviewPageDTO reviewPage = reviewService.findReviewsWithPagination(pageNumber, pageSize);
        model.addAttribute("reviewPage", reviewPage);
        return "get_all_reviews";
    }

    @GetMapping("/remove/{id}")
    public String removeReview(@PathVariable Long id) {
        reviewService.removeById(id);
        return "redirect:/reviews/get";
    }

    @PostMapping("/change")
    public String changeVisibilityById(@RequestParam(value = "selectedReviews") List<Long> ids,
                                       @RequestParam(value = "allIds") List<Long> allIds) {
        if (ids != null) {
            allIds.removeAll(ids);
            logger.info(allIds.toString());
            for (Long allId : allIds) {
                reviewService.changeVisibilityById(allId);
            }
        }
        return "redirect:/reviews/get";
    }
}
