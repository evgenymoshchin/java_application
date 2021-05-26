package com.gmail.evgenymoshchin.web.controllers.integration;

import com.gmail.evgenymoshchin.config.BaseIT;
import com.gmail.evgenymoshchin.service.model.ItemDTO;
import org.junit.jupiter.api.Test;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.jdbc.Sql;

import java.math.BigDecimal;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

@ActiveProfiles("test")
class ItemAPIControllerIT extends BaseIT {

    @Test
    @Sql({"/scripts/clean_item.sql", "/scripts/init_item.sql"})
    void shouldGetAllItems() {
        HttpEntity<String> request = new HttpEntity<>(null, new HttpHeaders());
        ResponseEntity<List<ItemDTO>> response = restTemplate.exchange(
                "/api/items",
                HttpMethod.GET,
                request,
                new ParameterizedTypeReference<>() {
                }
        );
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(1, response.getBody().size());
        assertEquals(1L, response.getBody().get(0).getId());
        assertEquals("name", response.getBody().get(0).getName());
        assertNotNull(response.getBody().get(0).getUuid());
        assertEquals(new BigDecimal("154.254"), response.getBody().get(0).getPrice());
        assertEquals("description of item", response.getBody().get(0).getDescription());
    }
}