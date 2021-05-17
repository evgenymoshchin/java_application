package com.gmail.evgenymoshchin.web.controllers;

import com.gmail.evgenymoshchin.service.ReviewService;
import com.gmail.evgenymoshchin.service.model.ReviewPageDTO;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;

import static com.gmail.evgenymoshchin.web.constants.ControllersConstants.DEFAULT_PAGE_SIZE_VALUE;
import static com.gmail.evgenymoshchin.web.constants.ControllersConstants.DEFAULT_PAGE_VALUE;
import static com.gmail.evgenymoshchin.web.constants.ControllersConstants.PAGE;
import static com.gmail.evgenymoshchin.web.constants.ControllersConstants.PAGE_SIZE;
import static com.gmail.evgenymoshchin.web.constants.ControllersConstants.REVIEWS_GET_REDIRECTION_URL;

@Log4j2
@Controller
@RequiredArgsConstructor
@RequestMapping("/reviews")
public class ReviewController {

    private final ReviewService reviewService;

    @GetMapping("/get")
    public String getReviews(Model model,
                             @RequestParam(value = PAGE_SIZE, defaultValue = DEFAULT_PAGE_SIZE_VALUE) int pageSize,
                             @RequestParam(value = PAGE, defaultValue = DEFAULT_PAGE_VALUE) int pageNumber
    ) {
        ReviewPageDTO reviewPage = reviewService.findReviewsWithPagination(pageNumber, pageSize);
        model.addAttribute("reviewPage", reviewPage);
        return "get_all_reviews";
    }

    @GetMapping("/remove/{id}")
    public String removeReview(@PathVariable Long id) {
        reviewService.removeById(id);
        return REVIEWS_GET_REDIRECTION_URL;
    }

    @PostMapping("/change")
    public String changeVisibilityById(@RequestParam(value = "selectedReviews") List<Long> selectedIds,
                                       @RequestParam(value = "allIds") List<Long> reviewsIds) {
        if (selectedIds != null) {
            reviewsIds.removeAll(selectedIds);
            for (Long allId : reviewsIds) {
                reviewService.changeVisibilityById(allId);
            }
        }
        return REVIEWS_GET_REDIRECTION_URL;
    }
}
