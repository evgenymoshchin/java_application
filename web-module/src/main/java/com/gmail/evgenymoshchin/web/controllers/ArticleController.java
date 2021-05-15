package com.gmail.evgenymoshchin.web.controllers;

import com.gmail.evgenymoshchin.service.ArticleService;
import com.gmail.evgenymoshchin.service.model.ArticleDTO;
import com.gmail.evgenymoshchin.service.model.ArticlePageDTO;
import com.gmail.evgenymoshchin.service.model.UserProfileDTO;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import javax.validation.Valid;
import java.lang.invoke.MethodHandles;
import java.security.Principal;
import java.time.LocalDate;

@Controller
@RequestMapping("/articles")
public class ArticleController {

    private static final Logger logger = LogManager.getLogger(MethodHandles.lookup().lookupClass());

    private final ArticleService articleService;

    public ArticleController(ArticleService articleService) {
        this.articleService = articleService;
    }

    @GetMapping("/get")
    public String getReviews(Model model,
                             Principal principal,
                             @RequestParam(value = "pageSize", required = false, defaultValue = "10") int pageSize,
                             @RequestParam(value = "page", required = false, defaultValue = "1") int pageNumber
    ) {
        ArticlePageDTO articlePage = articleService.findArticlesWithPagination(pageNumber, pageSize);
        model.addAttribute("articlePage", articlePage);
        model.addAttribute("email", principal.getName());
        logger.info(principal.getName());
        return "get_all_articles";
    }

    @GetMapping("/show-article-by-id")
    public String getArticleById(@RequestParam("id") Long id, Model model, Principal principal) {
        ArticleDTO article = articleService.findArticleById(id);
        model.addAttribute("email", principal.getName());
        model.addAttribute("comments", article.getComments());
        model.addAttribute("article", article);
        return "get_article_by_id";
    }

    @GetMapping("/delete-article-by-id")
    public String removeArticleById(@RequestParam("id") Long id) {
        if (id != null) {
            articleService.deleteArticleById(id);
        }
        return "redirect:/articles/get";
    }

    @GetMapping("/add")
    public String addArticlePage(ArticleDTO article, Model model) {
        model.addAttribute("article", article);
        model.addAttribute("localDate", LocalDate.now());
        return "add_article";
    }

    @PostMapping("/add")
    public String addArticle(@Valid ArticleDTO article,
                             BindingResult bindingResult,
                             Principal principal) {
        logger.info(article);
        logger.info(principal);
        logger.info(bindingResult.getFieldError());
        if (!bindingResult.hasErrors()) {
            articleService.addArticle(article, principal.getName());
            return "redirect:/articles/get";
        } else {
            return "add_article";
        }
    }

    @GetMapping("/update/{id}")
    public String getUserProfilePage(@PathVariable Long id, Model model) {
        model.addAttribute("article", articleService.findArticleById(id));
        return "update_article";
    }

    @PostMapping("/update")
    public String updateUserProfile(@Valid ArticleDTO article, BindingResult bindingResult, Model model) {
        model.addAttribute("article", article);
        if (!bindingResult.hasErrors()) {
            articleService.updateArticle(article);
            return "redirect:/articles/get";
        }
        return "update_article";
    }

    @GetMapping("/delete-comment")
    public String removeCommentById(@RequestParam Long id) {
        logger.info(id);
        if (id != null) {
            articleService.deleteArticleCommentById(id);
        }
        return "redirect:/articles/get";
    }

}
