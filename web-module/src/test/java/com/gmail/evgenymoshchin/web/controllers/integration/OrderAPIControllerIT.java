package com.gmail.evgenymoshchin.web.controllers.integration;

import com.gmail.evgenymoshchin.config.BaseIT;
import com.gmail.evgenymoshchin.repository.model.StatusEnum;
import com.gmail.evgenymoshchin.service.model.ArticleDTO;
import com.gmail.evgenymoshchin.service.model.OrderShowDTO;
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
class OrderAPIControllerIT extends BaseIT {

    public static final String API_ORDERS_URL = "/api/orders";

    @Test
    @Sql({"/scripts/clean_order.sql", "/scripts/init_order.sql"})
    void shouldGetAllOrders() {
        HttpEntity<String> request = new HttpEntity<>(null, new HttpHeaders());
        ResponseEntity<List<OrderShowDTO>> response = restTemplate.exchange(
                API_ORDERS_URL,
                HttpMethod.GET,
                request,
                new ParameterizedTypeReference<>() {
                }
        );
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(1, response.getBody().size());
        assertEquals(1L, response.getBody().get(0).getId());
        assertEquals(StatusEnum.NEW, response.getBody().get(0).getStatus());
        assertEquals("ivan", response.getBody().get(0).getFirstName());
        assertEquals("ivanov", response.getBody().get(0).getLastName());
    }
}