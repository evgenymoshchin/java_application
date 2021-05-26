package com.gmail.evgenymoshchin.web.controllers.unit;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.gmail.evgenymoshchin.service.ArticleService;
import com.gmail.evgenymoshchin.service.model.ArticleDTO;
import com.gmail.evgenymoshchin.web.controllers.api.ArticleAPIController;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;

import java.security.Principal;
import java.util.Collections;
import java.util.List;

import static org.assertj.core.api.AssertionsForInterfaceTypes.assertThat;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(controllers = ArticleAPIController.class)
@ActiveProfiles("test")
class ArticleAPIControllerTest {

    public static final String API_ARTICLES_URL = "/api/articles";

    @Autowired
    private MockMvc mockMvc;
    @Autowired
    private ObjectMapper objectMapper;
    @MockBean
    private ArticleService articleService;

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
        articleDTO.setArticleBody("testArticleBody");
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
    void shouldAddArticleWithValidParametersAndReturn201() throws Exception {
        ArticleDTO articleDTO = new ArticleDTO();
        articleDTO.setName("testArticleName");
        articleDTO.setArticleBody("testArticleBody");
        Principal mockPrincipal = mock(Principal.class);
        when(mockPrincipal.getName()).thenReturn("username");
        mockMvc.perform(
                post(API_ARTICLES_URL)
                        .principal(mockPrincipal)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(articleDTO))
        ).andExpect(status().isCreated());
    }

    @Test
    void shouldNotAddArticleWithNullNameAndReturn400() throws Exception {
        ArticleDTO articleDTO = new ArticleDTO();
        articleDTO.setName(null);
        articleDTO.setArticleBody("testArticleBody");
        mockMvc.perform(
                post(API_ARTICLES_URL)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(articleDTO))
        ).andExpect(status().isBadRequest());
    }

    @Test
    void shouldNotAddArticleWithEmptyArticleBodyAndReturn400() throws Exception {
        ArticleDTO articleDTO = new ArticleDTO();
        articleDTO.setName("testArticleName");
        articleDTO.setArticleBody("");
        mockMvc.perform(
                post(API_ARTICLES_URL)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(articleDTO))
        ).andExpect(status().isBadRequest());
    }

    @Test
    void shouldReturnArticleWhenDoGetRequestById() throws Exception {
        ArticleDTO articleDTO = new ArticleDTO();
        Long validId = 1L;
        articleDTO.setId(validId);
        articleDTO.setName("testArticleName");
        articleDTO.setArticleBody("testArticleBody");
        when(articleService.findArticleById(validId)).thenReturn(articleDTO);
        MvcResult result = mockMvc.perform(
                get(API_ARTICLES_URL + "/{id}", validId)
                        .contentType(MediaType.APPLICATION_JSON)
        ).andReturn();
        String resultString = result.getResponse().getContentAsString();
        assertThat(resultString).isEqualToIgnoringCase(objectMapper.writeValueAsString(articleDTO));
    }

    @Test
    public void shouldVerifyThatDeleteRequestCallArticleService() throws Exception {
        Long validId = 1L;
        mockMvc.perform(
                delete(API_ARTICLES_URL + "/{id}", validId)
                        .contentType(MediaType.APPLICATION_JSON)
        ).andExpect(status().isOk());
        verify(articleService, times(1)).deleteArticleById(validId);
    }
}