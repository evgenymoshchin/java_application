package com.gmail.evgenymoshchin.web.controllers;

import com.gmail.evgenymoshchin.service.ArticleService;
import com.gmail.evgenymoshchin.service.model.ArticleDTO;
import com.gmail.evgenymoshchin.service.model.ArticlePageDTO;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import javax.validation.Valid;
import java.security.Principal;
import java.time.LocalDate;
import java.util.Objects;

import static com.gmail.evgenymoshchin.web.constants.ControllersConstants.ARTICLES_GET_REDIRECTION_URL;
import static com.gmail.evgenymoshchin.web.constants.ControllersConstants.ARTICLE_ATTRIBUTE_VALUE;
import static com.gmail.evgenymoshchin.web.constants.ControllersConstants.DEFAULT_PAGE_SIZE_VALUE;
import static com.gmail.evgenymoshchin.web.constants.ControllersConstants.DEFAULT_PAGE_VALUE;
import static com.gmail.evgenymoshchin.web.constants.ControllersConstants.PAGE;
import static com.gmail.evgenymoshchin.web.constants.ControllersConstants.PAGE_SIZE;

@Log4j2
@Controller
@RequiredArgsConstructor
@RequestMapping("/articles")
public class ArticleController {

    private final ArticleService articleService;

    @GetMapping("/get")
    public String getArticles(Model model,
                              @RequestParam(value = PAGE_SIZE, defaultValue = DEFAULT_PAGE_SIZE_VALUE) int pageSize,
                              @RequestParam(value = PAGE, defaultValue = DEFAULT_PAGE_VALUE) int pageNumber
    ) {
        ArticlePageDTO articlePage = articleService.findArticlesWithPagination(pageNumber, pageSize);
        model.addAttribute("articlePage", articlePage);
        return "get_all_articles";
    }

    @GetMapping("/show-article-by-id")
    public String getArticleById(@RequestParam("id") Long id, Model model) {
        ArticleDTO article = articleService.findArticleById(id);
        model.addAttribute("comments", article.getComments());
        model.addAttribute(ARTICLE_ATTRIBUTE_VALUE, article);
        return "get_article_by_id";
    }

    @GetMapping("/delete-article-by-id")
    public String removeArticleById(@RequestParam("id") Long id) {
        if (Objects.nonNull(id)) {
            articleService.deleteArticleById(id);
        }
        return ARTICLES_GET_REDIRECTION_URL;
    }

    @GetMapping("/add")
    public String addArticlePage(ArticleDTO articleDTO, Model model) {
        model.addAttribute("localDate", LocalDate.now());
        return "add_article";
    }

    @PostMapping("/add")
    public String addArticle(@Valid ArticleDTO articleDTO,
                             BindingResult bindingResult,
                             Principal principal) {
        if (bindingResult.hasErrors()) {
            return "add_article";
        } else {
            articleService.addArticle(articleDTO, principal.getName());
            return ARTICLES_GET_REDIRECTION_URL;
        }
    }

    @GetMapping("/update")
    public String getUpdateArticlePage(ArticleDTO articleDTO, Model model) {
        ArticleDTO article = articleService.findArticleById(articleDTO.getId());
        articleDTO.setArticleBody(article.getArticleBody());
        articleDTO.setName(article.getName());
        model.addAttribute("articleDTO", articleDTO);
        return "update_article";
    }

    @PostMapping("/update")
    public String updateArticle(@Valid ArticleDTO articleDTO, BindingResult bindingResult) {
        if (!bindingResult.hasErrors()) {
            articleService.updateArticle(articleDTO);
            return ARTICLES_GET_REDIRECTION_URL;
        }
        return "update_article";
    }

    @GetMapping("/delete-comment")
    public String removeCommentById(@RequestParam Long id) {
        if (Objects.nonNull(id)) {
            articleService.deleteArticleCommentById(id);
        }
        return ARTICLES_GET_REDIRECTION_URL;
    }
}
