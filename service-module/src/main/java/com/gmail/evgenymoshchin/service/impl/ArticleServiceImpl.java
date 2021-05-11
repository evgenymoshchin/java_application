package com.gmail.evgenymoshchin.service.impl;

import com.gmail.evgenymoshchin.repository.ArticleRepository;
import com.gmail.evgenymoshchin.repository.model.Article;
import com.gmail.evgenymoshchin.service.ArticleService;
import com.gmail.evgenymoshchin.service.converters.ArticleServiceConverter;
import com.gmail.evgenymoshchin.service.model.ArticleDTO;
import com.gmail.evgenymoshchin.service.model.ArticlePageDTO;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

import static com.gmail.evgenymoshchin.service.util.ServiceUtil.getNumbersOfPages;

@Service
public class ArticleServiceImpl implements ArticleService {

    private final ArticleRepository articleRepository;
    private final ArticleServiceConverter converter;

    public ArticleServiceImpl(ArticleRepository articleRepository, ArticleServiceConverter converter) {
        this.articleRepository = articleRepository;
        this.converter = converter;
    }

    @Override
    @Transactional
    public ArticlePageDTO findArticlesWithPagination(int pageNumber, int pageSize) {
        ArticlePageDTO articlePage = new ArticlePageDTO();
        List<Article> articles = articleRepository.findWithPagination(pageNumber, pageSize);
        List<ArticleDTO> articleDTOS = new ArrayList<>();
        for (Article article : articles) {
            articleDTOS.add(converter.convertArticleToDTO(article));
        }
        articleDTOS.sort(Comparator.comparing(ArticleDTO::getDate));
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
        return converter.convertArticleToDTO(articleRepository.findById(id));
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
        articleRepository.remove(article);
    }
}
