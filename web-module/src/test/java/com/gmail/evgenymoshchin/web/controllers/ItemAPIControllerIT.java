package com.gmail.evgenymoshchin.web.controllers;

import com.gmail.evgenymoshchin.config.BaseIT;
import com.gmail.evgenymoshchin.service.model.ItemDTO;
import org.junit.jupiter.api.Test;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.jdbc.Sql;

import java.util.List;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.junit.jupiter.api.Assertions.*;

class ItemAPIControllerIT extends BaseIT {

    // TODO дописать остальные параметры как v init script,если не отработает с одним name
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
        assertEquals("name", response.getBody().get(0).getName());
    }
}