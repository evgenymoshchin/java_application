package com.gmail.evgenymoshchin.web.controllers.security;

import com.gmail.evgenymoshchin.service.OrderService;
import com.gmail.evgenymoshchin.web.controllers.api.OrderAPIController;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(controllers = OrderAPIController.class)
public class OrderAPISecurityTest {

    public static final String API_ORDERS_URL = "/api/orders";

    @Autowired
    private MockMvc mockMvc;
    @MockBean
    OrderService orderService;

    @WithMockUser(roles = "SECURE_API_USER")
    @Test
    void shouldDoGetRequestForOrdersWithValidUserRoleAndReturn200() throws Exception {
        mockMvc.perform(
                get(API_ORDERS_URL)
                        .contentType(MediaType.APPLICATION_JSON)
        ).andExpect(status().isOk());
    }

    @WithMockUser(roles = {"SALE_USER", "CUSTOMER_USER", "ADMINISTRATOR"})
    @Test
    void shouldDoGetRequestForOrdersWithInvalidUserRoleAndReturn403() throws Exception {
        mockMvc.perform(
                get(API_ORDERS_URL)
                        .contentType(MediaType.APPLICATION_JSON)
        ).andExpect(status().isForbidden());
    }
}
