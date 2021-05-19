package com.gmail.evgenymoshchin.service.impl;

import com.gmail.evgenymoshchin.repository.ArticleRepository;
import com.gmail.evgenymoshchin.repository.model.Article;
import com.gmail.evgenymoshchin.service.impl.ArticleServiceImpl;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class ArticleServiceImplTest {

    @Mock
    private ArticleRepository articleRepository;

    @InjectMocks
    ArticleServiceImpl articleService;

    @Test
    void shouldFindArticleById() {
        Long id = 1L;
        Article article = new Article();
        article.setId(id);
        when(articleRepository.findById(id)).thenReturn(article);
        assertEquals(article, articleRepository.findById(id));
    }
}