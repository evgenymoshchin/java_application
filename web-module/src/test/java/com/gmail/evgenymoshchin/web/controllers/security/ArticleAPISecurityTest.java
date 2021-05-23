package com.gmail.evgenymoshchin.web.controllers.security;

import com.gmail.evgenymoshchin.service.ArticleService;
import com.gmail.evgenymoshchin.web.controllers.api.ArticleAPIController;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(controllers = ArticleAPIController.class)
public class ArticleAPISecurityTest {

    public static final String API_ARTICLES_URL = "/api/articles";

    @Autowired
    private MockMvc mockMvc;
    @MockBean
    private ArticleService articleService;

    @WithMockUser(roles = "SECURE_API_USER")
    @Test
    void shouldDoGetRequestForArticlesWithValidUserRoleAndReturn200() throws Exception {
        mockMvc.perform(
                get(API_ARTICLES_URL)
                        .contentType(MediaType.APPLICATION_JSON)
        ).andExpect(status().isOk());
    }

    @WithMockUser(roles = {"SALE_USER", "CUSTOMER_USER", "ADMINISTRATOR"})
    @Test
    void shouldDoGetRequestForArticlesWithInvalidUserRoleAndReturn403() throws Exception {
        mockMvc.perform(
                get(API_ARTICLES_URL)
                        .contentType(MediaType.APPLICATION_JSON)
        ).andExpect(status().isForbidden());
    }
}
