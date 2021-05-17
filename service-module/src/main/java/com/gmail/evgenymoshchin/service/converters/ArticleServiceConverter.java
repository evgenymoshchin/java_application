package com.gmail.evgenymoshchin.service.converters;

import com.gmail.evgenymoshchin.repository.model.Article;
import com.gmail.evgenymoshchin.service.model.ArticleDTO;

public interface ArticleServiceConverter {
    ArticleDTO convertArticleToDTO(Article article);

    Article convertDTOtoArticle(ArticleDTO articleDTO, String username);
}
