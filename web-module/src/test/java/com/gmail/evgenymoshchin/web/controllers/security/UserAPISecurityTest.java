package com.gmail.evgenymoshchin.web.controllers.security;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.gmail.evgenymoshchin.service.UserService;
import com.gmail.evgenymoshchin.service.model.UserDTO;
import com.gmail.evgenymoshchin.web.controllers.api.UserAPIController;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(controllers = UserAPIController.class)
public class UserAPISecurityTest {

    public static final String API_USERS_URL = "/api/users";

    @Autowired
    private MockMvc mockMvc;
    @Autowired
    private ObjectMapper objectMapper;
    @MockBean
    private UserService userService;

    @WithMockUser(roles = {"SALE_USER", "CUSTOMER_USER", "ADMINISTRATOR"})
    @Test
    void shouldDoGetRequestForUsersWithInvalidUserRoleAndReturn403() throws Exception {
        mockMvc.perform(
                get(API_USERS_URL)
                        .contentType(MediaType.APPLICATION_JSON)
        ).andExpect(status().isForbidden());
    }

    @WithMockUser(roles = "SECURE_API_USER")
    @Test
    void shouldUserWithAllowedRoleAddUserWithValidParametersAndReturn201() throws Exception {
        UserDTO user = new UserDTO();
        user.setFirstName("ivan");
        user.setLastName("ivanov");
        user.setPatronymic("ivanych");
        user.setUsername("ivan@gmail.com");
        mockMvc.perform(
                post(API_USERS_URL)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(user))
        ).andExpect(status().isCreated());
    }
}
