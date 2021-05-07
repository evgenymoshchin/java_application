package com.gmail.evgenymoshchin.service;

import com.gmail.evgenymoshchin.service.model.ArticleDTO;
import com.gmail.evgenymoshchin.service.model.ArticlePageDTO;

import java.util.List;

public interface ArticleService {

    ArticlePageDTO findArticlesWithPagination(Integer pageNumber, Integer pageSize);

    ArticleDTO findArticleById(Long id);

    List<ArticleDTO> findAll();

    void addArticle(ArticleDTO article, String username);
}
