package com.gmail.evgenymoshchin.web.controllers.unit;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.gmail.evgenymoshchin.service.OrderService;
import com.gmail.evgenymoshchin.service.model.OrderShowDTO;
import com.gmail.evgenymoshchin.web.controllers.api.OrderAPIController;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;

import java.util.Collections;
import java.util.List;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(controllers = OrderAPIController.class)
@ActiveProfiles("test")
class OrderAPIControllerTest {

    public static final String API_ORDERS_URL = "/api/orders";

    @Autowired
    private MockMvc mockMvc;
    @Autowired
    private ObjectMapper objectMapper;
    @MockBean
    OrderService orderService;

    @Test
    void shouldVerifyThatGetRequestCallOrderService() throws Exception {
        mockMvc.perform(
                get(API_ORDERS_URL)
                        .contentType(MediaType.APPLICATION_JSON)
        ).andExpect(status().isOk());
        verify(orderService, times(1)).getOrders();
    }

    @Test
    void shouldReturnEmptyListWhenDoGetRequestOrders() throws Exception {
        MvcResult result = mockMvc.perform(
                get(API_ORDERS_URL)
                        .contentType(MediaType.APPLICATION_JSON)
        ).andReturn();
        String resultString = result.getResponse().getContentAsString();
        assertThat(resultString).isEqualToIgnoringCase(objectMapper.writeValueAsString(Collections.emptyList()));
    }

    @Test
    void shouldReturnListOfOrdersWhenDoGetRequestOrders() throws Exception {
        OrderShowDTO orderShowDTO = new OrderShowDTO();
        orderShowDTO.setLastName("testName");
        List<OrderShowDTO> orders = Collections.singletonList(orderShowDTO);
        when(orderService.getOrders()).thenReturn(orders);
        MvcResult result = mockMvc.perform(
                get(API_ORDERS_URL)
                        .contentType(MediaType.APPLICATION_JSON)
        ).andReturn();
        String resultString = result.getResponse().getContentAsString();
        assertThat(resultString).isEqualToIgnoringCase(objectMapper.writeValueAsString(orders));
    }

    @Test
    void shouldReturnOrderWhenDoGetRequestById() throws Exception {
        OrderShowDTO orderShowDTO = new OrderShowDTO();
        Long validId = 1L;
        orderShowDTO.setId(validId);
        when(orderService.findOrderWithItemsByOrderId(validId)).thenReturn(orderShowDTO);
        MvcResult result = mockMvc.perform(
                get(API_ORDERS_URL + "/{id}", validId)
                        .contentType(MediaType.APPLICATION_JSON)
        ).andReturn();
        String resultString = result.getResponse().getContentAsString();
        assertThat(resultString).isEqualToIgnoringCase(objectMapper.writeValueAsString(orderShowDTO));
    }
}
