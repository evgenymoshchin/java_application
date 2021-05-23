package com.gmail.evgenymoshchin.web.controllers.security;

import com.gmail.evgenymoshchin.service.ItemService;
import com.gmail.evgenymoshchin.web.controllers.api.ItemAPIController;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(controllers = ItemAPIController.class)
public class ItemAPISecurityTest {

    public static final String API_ITEMS_URL = "/api/items";

    @Autowired
    private MockMvc mockMvc;
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

    @WithMockUser(roles = {"SALE_USER", "CUSTOMER_USER", "ADMINISTRATOR"})
    @Test
    void shouldDoGetRequestForItemsWithInvalidUserRoleAndReturn403() throws Exception {
        mockMvc.perform(
                get(API_ITEMS_URL)
                        .contentType(MediaType.APPLICATION_JSON)
        ).andExpect(status().isForbidden());
    }
}
