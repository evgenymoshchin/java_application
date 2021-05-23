package com.gmail.evgenymoshchin.web.controllers.unit;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.gmail.evgenymoshchin.repository.model.RoleEnum;
import com.gmail.evgenymoshchin.service.UserService;
import com.gmail.evgenymoshchin.service.model.UserDTO;
import com.gmail.evgenymoshchin.web.controllers.api.UserAPIController;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.web.servlet.MockMvc;

import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(controllers = UserAPIController.class)
@ActiveProfiles("test")
class UserAPIControllerTest {

    public static final String API_USERS_URL = "/api/users";

    @Autowired
    private MockMvc mockMvc;
    @Autowired
    private ObjectMapper objectMapper;
    @MockBean
    private UserService userService;

    @Test
    void shouldVerifyThatPostRequestCallUserService() throws Exception {
        UserDTO user = new UserDTO();
        user.setFirstName("ivan");
        user.setLastName("ivanov");
        user.setPatronymic("ivanych");
        user.setUsername("ivan@gmail.com");
        user.setRole(RoleEnum.ROLE_SECURE_API_USER);
        mockMvc.perform(
                post(API_USERS_URL)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(user))
        ).andExpect(status().isCreated());
        verify(userService, times(1)).addUser(user);
    }

    @Test
    void shouldAddUserWithValidParametersAndReturn201() throws Exception {
        UserDTO user = new UserDTO();
        user.setFirstName("ivan");
        user.setLastName("ivanov");
        user.setPatronymic("ivanych");
        user.setUsername("ivan@gmail.com");
        user.setRole(RoleEnum.ROLE_SECURE_API_USER);
        mockMvc.perform(
                post(API_USERS_URL)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(user))
        ).andExpect(status().isCreated());
    }

    @Test
    void shouldNotAddUserWithInvalidEmailAndReturn400() throws Exception {
        UserDTO user = new UserDTO();
        user.setFirstName("ivan");
        user.setLastName("ivanov");
        user.setPatronymic("ivanych");
        user.setUsername("1111");
        mockMvc.perform(
                post(API_USERS_URL)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(user))
        ).andExpect(status().isBadRequest());
    }

    @Test
    void shouldNotAddUserWithEmptyFirstNameAndReturn400() throws Exception {
        UserDTO user = new UserDTO();
        user.setFirstName("");
        user.setLastName("ivanov");
        user.setPatronymic("ivanych");
        user.setUsername("ivan@gmail.com");
        mockMvc.perform(
                post(API_USERS_URL)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(user))
        ).andExpect(status().isBadRequest());
    }

    @Test
    void shouldNotAddUserWithNullLastNameAndReturn400() throws Exception {
        UserDTO user = new UserDTO();
        user.setFirstName("ivan");
        user.setLastName(null);
        user.setPatronymic("ivanych");
        user.setUsername("ivan@gmail.com");
        mockMvc.perform(
                post(API_USERS_URL)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(user))
        ).andExpect(status().isBadRequest());
    }

    @Test
    void shouldNotAddUserWithNotLatinPatronymicAndReturn400() throws Exception {
        UserDTO user = new UserDTO();
        user.setFirstName("ivan");
        user.setLastName("ivanov");
        user.setPatronymic("иванович");
        user.setUsername("ivan@gmail.com");
        mockMvc.perform(
                post(API_USERS_URL)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(user))
        ).andExpect(status().isBadRequest());
    }
}