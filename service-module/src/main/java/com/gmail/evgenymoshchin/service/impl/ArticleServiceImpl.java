package com.gmail.evgenymoshchin.service.impl;

import com.gmail.evgenymoshchin.repository.ArticleRepository;
import com.gmail.evgenymoshchin.repository.CommentRepository;
import com.gmail.evgenymoshchin.repository.model.Article;
import com.gmail.evgenymoshchin.repository.model.Comment;
import com.gmail.evgenymoshchin.service.ArticleService;
import com.gmail.evgenymoshchin.service.ArticleSummaryMaker;
import com.gmail.evgenymoshchin.service.converters.ArticleServiceConverter;
import com.gmail.evgenymoshchin.service.exception.ServiceException;
import com.gmail.evgenymoshchin.service.model.ArticleDTO;
import com.gmail.evgenymoshchin.service.model.ArticlePageDTO;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.Objects;

import static com.gmail.evgenymoshchin.service.constants.ExceptionConstants.ARTICLE_WAS_NOT_FOUND_MESSAGE;
import static com.gmail.evgenymoshchin.service.constants.ExceptionConstants.COMMENT_WAS_NOT_FOUND_MESSAGE;
import static com.gmail.evgenymoshchin.service.util.ServiceUtil.getNumbersOfPages;

@Service
@RequiredArgsConstructor
public class ArticleServiceImpl implements ArticleService {

    private final ArticleRepository articleRepository;
    private final ArticleServiceConverter converter;
    private final ArticleSummaryMaker summaryMaker;
    private final CommentRepository commentRepository;

    @Override
    @Transactional
    public ArticlePageDTO findArticlesWithPagination(int pageNumber, int pageSize) {
        ArticlePageDTO articlePage = new ArticlePageDTO();
        List<Article> articles = articleRepository.findWithPagination(pageNumber, pageSize);
        List<ArticleDTO> articleDTOS = new ArrayList<>();
        for (Article article : articles) {
            articleDTOS.add(converter.convertArticleToDTO(article));
        }
        articleDTOS.sort(Comparator.comparing(ArticleDTO::getDate).reversed());
        articlePage.getArticles().addAll(articleDTOS);
        Long countOfArticles = articleRepository.getCount();
        articlePage.setPagesCount(countOfArticles);
        List<Integer> numbersOfPages = getNumbersOfPages(pageSize, countOfArticles);
        articlePage.getNumbersOfPages().addAll(numbersOfPages);
        return articlePage;
    }

    @Override
    @Transactional
    public ArticleDTO findArticleById(Long id) {
        Article article = articleRepository.findById(id);
        if (Objects.nonNull(article)) {
            return converter.convertArticleToDTO(article);
        } else {
            throw new ServiceException(String.format(ARTICLE_WAS_NOT_FOUND_MESSAGE, id));
        }
    }

    @Override
    @Transactional
    public List<ArticleDTO> findAll() {
        List<Article> articles = articleRepository.findAll();
        List<ArticleDTO> articleDTOS = new ArrayList<>();
        for (Article article : articles) {
            articleDTOS.add(converter.convertArticleToDTO(article));
        }
        return articleDTOS;
    }

    @Override
    @Transactional
    public void addArticle(ArticleDTO articleDTO, String username) {
        articleRepository.persist(converter.convertDTOtoArticle(articleDTO, username));
    }

    @Override
    @Transactional
    public void deleteArticleById(Long id) {
        Article article = articleRepository.findById(id);
        if (Objects.nonNull(article)) {
            articleRepository.remove(article);
        } else {
            throw new ServiceException(String.format(ARTICLE_WAS_NOT_FOUND_MESSAGE, id));
        }
    }

    @Override
    @Transactional
    public void updateArticle(ArticleDTO articleDTO) {
        Article article = articleRepository.findById(articleDTO.getId());
        if (Objects.nonNull(article)) {
            article.setName(articleDTO.getName());
            article.setCreatedBy(LocalDate.now());
            String articleBody = articleDTO.getArticleBody();
            if (Objects.nonNull(articleBody)) {
                article.setArticleBody(articleBody);
                String summary = summaryMaker.getSummaryOfArticle(articleBody);
                article.setSummary(summary);
            }
        } else {
            throw new ServiceException(String.format(ARTICLE_WAS_NOT_FOUND_MESSAGE, articleDTO.getId()));
        }
    }

    @Override
    @Transactional
    public void deleteArticleCommentById(Long commentId) {
        Comment comment = commentRepository.findById(commentId);
        if (Objects.nonNull(comment)) {
            commentRepository.remove(comment);
        } else {
            throw new ServiceException(String.format(COMMENT_WAS_NOT_FOUND_MESSAGE, commentId));
        }
    }
}
