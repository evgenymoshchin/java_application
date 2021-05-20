package com.gmail.evgenymoshchin.web.controllers.unit;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.gmail.evgenymoshchin.service.ItemService;
import com.gmail.evgenymoshchin.web.controllers.api.ItemAPIController;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;

import java.util.Collections;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(controllers = ItemAPIController.class)
class ItemAPIControllerTest {

    public static final String API_ITEMS_URL = "/api/items";
    @Autowired
    private MockMvc mockMvc;
    @Autowired
    private ObjectMapper objectMapper;
    @MockBean
    ItemService itemService;

    @WithMockUser(roles = "SECURE_API_USER")
    @Test
    void shouldDoGetRequestForItemsWithValidUserRoleAndReturn200() throws Exception {
        mockMvc.perform(
                get(API_ITEMS_URL)
                        .contentType(MediaType.APPLICATION_JSON)
        ).andExpect(status().isOk());
    }

    @WithMockUser(roles = {"SALE_USER","CUSTOMER_USER","ADMINISTRATOR"})
    @Test
    void shouldDoGetRequestForItemsWithInvalidUserRoleAndReturn403() throws Exception {
        mockMvc.perform(
                get(API_ITEMS_URL)
                        .contentType(MediaType.APPLICATION_JSON)
        ).andExpect(status().isForbidden());
    }

    @WithMockUser(roles = "SECURE_API_USER")
    @Test
    void shouldVerifyThatGetRequestCallItemService() throws Exception {
        mockMvc.perform(
                get(API_ITEMS_URL)
                        .contentType(MediaType.APPLICATION_JSON)
        ).andExpect(status().isOk());
        verify(itemService, times(1)).getItems();
    }

    @WithMockUser(roles = "SECURE_API_USER")
    @Test
    void shouldReturnEmptyListWhenDoGetRequestItems() throws Exception {
        MvcResult result = mockMvc.perform(
                get(API_ITEMS_URL)
                        .contentType(MediaType.APPLICATION_JSON)
        ).andReturn();
        String resultString = result.getResponse().getContentAsString();
        assertThat(resultString).isEqualToIgnoringCase(objectMapper.writeValueAsString(Collections.emptyList()));
    }

//    @Test
//    void shouldReturnListOfArticlesWhenDoGetRequestItems() throws Exception {
//        ArticleDTO articleDTO = new ArticleDTO();
//        articleDTO.setId(1L);
//        articleDTO.setName("testArticleName");
//        List<ArticleDTO> articles = Collections.singletonList(articleDTO);
//        when(itemService.getItems()).thenReturn(articles);
//        MvcResult result = mockMvc.perform(
//                get(API_ITEMS_URL)
//                        .contentType(MediaType.APPLICATION_JSON)
//        ).andReturn();
//        String resultString = result.getResponse().getContentAsString();
//        assertThat(resultString).isEqualToIgnoringCase(objectMapper.writeValueAsString(articles));
//    }
//
//    @Test
//    void shouldAddItem() throws Exception {
//        ArticleDTO articleDTO = new ArticleDTO();
//        articleDTO.setName("testArticleName");
//        articleDTO.setArticleBody("testArticleBody");
//        articleDTO.setSummary("testSummary");
//        mockMvc.perform(
//                post(API_ITEMS_URL)
//                        .contentType(MediaType.APPLICATION_JSON)
//                        .content(objectMapper.writeValueAsString(articleDTO))
//        ).andExpect(status().isCreated());
//    }
//
//    @Test
//    void shouldNotAddItemWithInvalidParameters() throws Exception {
//        ArticleDTO articleDTO = new ArticleDTO();
//        mockMvc.perform(
//                post(API_ITEMS_URL)
//                        .contentType(MediaType.APPLICATION_JSON)
//                        .content(objectMapper.writeValueAsString(articleDTO))
//        ).andExpect(status().isBadRequest());
//    }
}