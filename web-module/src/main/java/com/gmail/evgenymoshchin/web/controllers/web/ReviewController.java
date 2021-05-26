package com.gmail.evgenymoshchin.web.controllers.web;

import com.gmail.evgenymoshchin.service.ReviewService;
import com.gmail.evgenymoshchin.service.model.ReviewDTO;
import com.gmail.evgenymoshchin.service.model.ReviewPageDTO;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import javax.validation.Valid;
import java.security.Principal;
import java.time.LocalDate;
import java.util.Collections;
import java.util.List;
import java.util.Objects;

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
    public String changeVisibilityById(@RequestParam(value = "selectedIds", required = false) List<Long> selectedIds) {
        reviewService.changeVisibilityByIds(Objects.requireNonNullElse(selectedIds, Collections.emptyList()));
        return REVIEWS_GET_REDIRECTION_URL;
    }

    @GetMapping("/add")
    public String addReviewPage(ReviewDTO reviewDTO, Model model) {
        model.addAttribute("localDate", LocalDate.now());
        return "add_review";
    }

    @PostMapping("/add")
    public String addReview(@Valid ReviewDTO reviewDTO,
                            BindingResult bindingResult,
                            Principal principal) {
        if (bindingResult.hasErrors()) {
            return "add_review";
        } else {
            if (principal == null) {
                String name = "Unauthorized";
                reviewService.addReview(reviewDTO, name);
            } else {
                reviewService.addReview(reviewDTO, principal.getName());
            }
            return "redirect:/login";
        }
    }

}
