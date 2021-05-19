package com.gmail.evgenymoshchin.service.converters.impl;

import com.gmail.evgenymoshchin.repository.UserRepository;
import com.gmail.evgenymoshchin.repository.model.Article;
import com.gmail.evgenymoshchin.repository.model.User;
import com.gmail.evgenymoshchin.service.model.ArticleDTO;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.LocalDate;

import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class ArticleServiceConverterImplTest {

    @Mock
    private UserRepository userRepository;

    @InjectMocks
    private ArticleServiceConverterImpl articleServiceConverter;

    @Test
    void shouldConvertArticleToDTOAndReturnNotNullObject() {
        Article article = new Article();
        ArticleDTO articleDTO = articleServiceConverter.convertArticleToDTO(article);
        Assertions.assertNotNull(articleDTO);
    }

    @Test
    void shouldConvertArticleToDTOAndReturnCorrectId() {
        Article article = new Article();
        Long testId = 1L;
        article.setId(testId);
        ArticleDTO articleDTO = articleServiceConverter.convertArticleToDTO(article);
        Assertions.assertEquals(testId, articleDTO.getId());
    }

    @Test
    void shouldConvertArticleToDTOAndReturnCorrectArticleBody() {
        Article article = new Article();
        String articleBody = "body";
        article.setArticleBody(articleBody);
        ArticleDTO articleDTO = articleServiceConverter.convertArticleToDTO(article);
        Assertions.assertEquals(articleBody, articleDTO.getArticleBody());
    }

    @Test
    void shouldConvertArticleToDTOAndReturnCorrectName() {
        Article article = new Article();
        String testName = "name";
        article.setName(testName);

        ArticleDTO articleDTO = articleServiceConverter.convertArticleToDTO(article);

        Assertions.assertEquals(testName, articleDTO.getName());
    }

    @Test
    void shouldConvertArticleToDTOAndReturnCorrectSummary() {
        Article article = new Article();
        String summary = "summary";
        article.setSummary(summary);
        ArticleDTO articleDTO = articleServiceConverter.convertArticleToDTO(article);
        Assertions.assertEquals(summary, articleDTO.getSummary());
    }

    @Test
    void shouldConvertArticleToDTOAndReturnCorrectLocalDate() {
        Article article = new Article();
        LocalDate date = LocalDate.now();
        article.setCreatedBy(date);
        ArticleDTO articleDTO = articleServiceConverter.convertArticleToDTO(article);
        Assertions.assertEquals(date, articleDTO.getDate());
    }

    @Test
    void shouldConvertArticleToDTOAndReturnCorrectUserId() {
        User user = new User();
        Long id = 1L;
        user.setId(id);
        when(userRepository.findById(id)).thenReturn(user);
        Article article = new Article();
        article.setUser(user);
        ArticleDTO articleDTO = articleServiceConverter.convertArticleToDTO(article);
        Assertions.assertEquals(id, articleDTO.getUserId());
    }

    @Test
    void shouldConvertDTOToArticleAndReturnNotNullObject() {
        ArticleDTO articleDTO = new ArticleDTO();
        Article article = articleServiceConverter.convertDTOtoArticle(articleDTO, "");
        Assertions.assertNotNull(article);
    }

    @Test
    void shouldConvertDTOToArticleAndReturnCorrectArticleBody() {
        ArticleDTO articleDTO = new ArticleDTO();
        String articleBody = "body";
        articleDTO.setArticleBody(articleBody);
        Article article = articleServiceConverter.convertDTOtoArticle(articleDTO, "");
        Assertions.assertEquals(articleBody, article.getArticleBody());
    }

    @Test
    void shouldConvertDTOToArticleAndReturnCorrectName() {
        ArticleDTO articleDTO = new ArticleDTO();
        String testName = "name";
        articleDTO.setName(testName);
        Article article = articleServiceConverter.convertDTOtoArticle(articleDTO, "");
        Assertions.assertEquals(testName, article.getName());
    }

    @Test
    void shouldConvertDTOToArticleAndReturnCorrectSummary() {
        ArticleDTO articleDTO = new ArticleDTO();
        String summary = "summary";
        articleDTO.setSummary(summary);
        Article article = articleServiceConverter.convertDTOtoArticle(articleDTO, "");
        Assertions.assertEquals(summary, article.getSummary());
    }

    @Test
    void shouldConvertDTOToArticleAndReturnCorrectDate() {
        ArticleDTO articleDTO = new ArticleDTO();
        LocalDate date = LocalDate.now();
        articleDTO.setDate(date);
        Article article = articleServiceConverter.convertDTOtoArticle(articleDTO, "");
        Assertions.assertEquals(date, article.getCreatedBy());
    }

    @Test
    void shouldConvertDTOToArticleAndReturnCorrectUserId() {
        User user = new User();
        String userName = "test";
        Long id = 1L;
        user.setUsername(userName);
        user.setId(id);
        when(userRepository.findByUsername(userName)).thenReturn(user);
        ArticleDTO articleDTO = new ArticleDTO();
        articleDTO.setUserId(user.getId());
        Article article = articleServiceConverter.convertDTOtoArticle(articleDTO, userName);
        Assertions.assertEquals(id, article.getUser().getId());
    }
}