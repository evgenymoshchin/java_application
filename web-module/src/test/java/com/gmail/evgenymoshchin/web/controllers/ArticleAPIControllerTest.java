package com.gmail.evgenymoshchin.web.controllers;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.gmail.evgenymoshchin.service.ArticleService;
import com.gmail.evgenymoshchin.service.model.ArticleDTO;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;

import java.util.Collections;
import java.util.List;

import static org.assertj.core.api.AssertionsForInterfaceTypes.assertThat;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(controllers = ArticleAPIController.class)
class ArticleAPIControllerTest {

    public static final String API_ARTICLES_URL = "/api/articles";
    @Autowired
    private MockMvc mockMvc;
    @Autowired
    private ObjectMapper objectMapper;
    @MockBean
    private ArticleService articleService;

    @Test
    void shouldDoGetRequestForArticles() throws Exception {
        mockMvc.perform(
                get(API_ARTICLES_URL)
                        .contentType(MediaType.APPLICATION_JSON)
        ).andExpect(status().isOk());
    }

    @Test
    void shouldVerifyThatGetRequestCallArticleService() throws Exception {
        mockMvc.perform(
                get(API_ARTICLES_URL)
                        .contentType(MediaType.APPLICATION_JSON)
        ).andExpect(status().isOk());
        verify(articleService, times(1)).findAll();
    }

    @Test
    void shouldReturnEmptyListWhenDoGetRequestArticles() throws Exception {
        MvcResult result = mockMvc.perform(
                get(API_ARTICLES_URL)
                        .contentType(MediaType.APPLICATION_JSON)
        ).andReturn();
        String resultString = result.getResponse().getContentAsString();
        assertThat(resultString).isEqualToIgnoringCase(objectMapper.writeValueAsString(Collections.emptyList()));
    }

    @Test
    void shouldReturnListOfArticlesWhenDoGetRequestArticles() throws Exception {
        ArticleDTO articleDTO = new ArticleDTO();
        articleDTO.setId(1L);
        articleDTO.setName("testArticleName");
        List<ArticleDTO> articles = Collections.singletonList(articleDTO);
        when(articleService.findAll()).thenReturn(articles);
        MvcResult result = mockMvc.perform(
                get(API_ARTICLES_URL)
                        .contentType(MediaType.APPLICATION_JSON)
        ).andReturn();
        String resultString = result.getResponse().getContentAsString();
        assertThat(resultString).isEqualToIgnoringCase(objectMapper.writeValueAsString(articles));
    }

    @Test
    void shouldAddArticle() throws Exception {
        ArticleDTO articleDTO = new ArticleDTO();
        articleDTO.setName("testArticleName");
        articleDTO.setArticleBody("testArticleBody");
        articleDTO.setSummary("testSummary");
        mockMvc.perform(
                post(API_ARTICLES_URL)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(articleDTO))
        ).andExpect(status().isCreated());
    }

    @Test
    void shouldNotAddArticleWithInvalidParameters() throws Exception {
        ArticleDTO articleDTO = new ArticleDTO();
        mockMvc.perform(
                post(API_ARTICLES_URL)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(articleDTO))
        ).andExpect(status().isBadRequest());
    }
}