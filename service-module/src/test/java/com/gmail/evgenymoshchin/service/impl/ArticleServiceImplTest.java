package com.gmail.evgenymoshchin.service.impl;

import com.gmail.evgenymoshchin.repository.ArticleRepository;
import com.gmail.evgenymoshchin.repository.model.Article;
import com.gmail.evgenymoshchin.service.converters.ArticleServiceConverter;
import com.gmail.evgenymoshchin.service.model.ArticleDTO;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Collections;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class ArticleServiceImplTest {

    @Mock
    private ArticleRepository articleRepository;
    @Mock
    private ArticleServiceConverter converter;
    @InjectMocks
    ArticleServiceImpl articleService;

    @Test
    void shouldReturnEmptyList() {
        List<ArticleDTO> articles = articleService.findAll();

        assertTrue(articles.isEmpty());
    }

    @Test
    void shouldReturnArticlesList() {
        ArticleDTO articleDTO = new ArticleDTO();
        articleDTO.setId(1L);
        Article article = new Article();

        when(articleRepository.findAll()).thenReturn(Collections.singletonList(article));
        when(converter.convertArticleToDTO(article)).thenReturn(articleDTO);

        List<ArticleDTO> articles = articleService.findAll();

        assertEquals(articles.get(0).getId(), articleDTO.getId());
    }
}