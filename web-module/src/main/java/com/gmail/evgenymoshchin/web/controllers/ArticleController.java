package com.gmail.evgenymoshchin.web.controllers;

import com.gmail.evgenymoshchin.service.ArticleService;
import com.gmail.evgenymoshchin.service.model.ArticleDTO;
import com.gmail.evgenymoshchin.service.model.ArticlePageDTO;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.lang.invoke.MethodHandles;

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
                             @RequestParam(value = "pageSize", required = false, defaultValue = "10") int pageSize,
                             @RequestParam(value = "page", required = false, defaultValue = "1") int pageNumber
    ) {
        ArticlePageDTO articlePage = articleService.findArticlesWithPagination(pageNumber, pageSize);
        model.addAttribute("articlePage", articlePage);
        return "get_all_articles";
    }

    @GetMapping("/show-article-by-id")
    public String getArticleById(@RequestParam("id") Long id, Model model) {
        ArticleDTO article = articleService.findArticleById(id);
        model.addAttribute("comments", article.getComments());
        model.addAttribute("article", article);
        return "get_article_by_id";
    }
}
