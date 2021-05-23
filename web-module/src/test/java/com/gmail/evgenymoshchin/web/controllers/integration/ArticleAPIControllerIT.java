package com.gmail.evgenymoshchin.web.controllers.integration;

import com.gmail.evgenymoshchin.config.BaseIT;
import com.gmail.evgenymoshchin.service.model.ArticleDTO;
import org.junit.jupiter.api.Test;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.jdbc.Sql;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

@ActiveProfiles("test")
class ArticleAPIControllerIT extends BaseIT {

    public static final String API_ARTICLES_URL = "/api/articles";

    @Test
    @Sql({"/scripts/clean_article.sql", "/scripts/init_article.sql"})
    void shouldGetAllArticles() {
        HttpEntity<String> request = new HttpEntity<>(null, new HttpHeaders());
        ResponseEntity<List<ArticleDTO>> response = restTemplate.exchange(
                API_ARTICLES_URL,
                HttpMethod.GET,
                request,
                new ParameterizedTypeReference<>() {
                }
        );
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(1, response.getBody().size());
        assertEquals(1L, response.getBody().get(0).getId());
        assertEquals("name", response.getBody().get(0).getName());
        assertEquals("body", response.getBody().get(0).getArticleBody());
        assertEquals("summary", response.getBody().get(0).getSummary());
        assertNotNull(response.getBody().get(0).getDate());
        assertEquals(2L, response.getBody().get(0).getUserId());
        assertEquals("a", response.getBody().get(0).getFirstName());
        assertEquals("b", response.getBody().get(0).getLastName());
    }
}