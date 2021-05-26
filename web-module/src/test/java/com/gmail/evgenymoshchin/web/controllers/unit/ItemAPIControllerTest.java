package com.gmail.evgenymoshchin.web.controllers.unit;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.gmail.evgenymoshchin.service.ItemService;
import com.gmail.evgenymoshchin.service.model.ItemDTO;
import com.gmail.evgenymoshchin.web.controllers.api.ItemAPIController;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;

import java.math.BigDecimal;
import java.util.Collections;
import java.util.List;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(controllers = ItemAPIController.class)
@ActiveProfiles("test")
class ItemAPIControllerTest {

    public static final String API_ITEMS_URL = "/api/items";

    @Autowired
    private MockMvc mockMvc;
    @Autowired
    private ObjectMapper objectMapper;
    @MockBean
    ItemService itemService;

    @Test
    void shouldVerifyThatGetRequestCallItemService() throws Exception {
        mockMvc.perform(
                get(API_ITEMS_URL)
                        .contentType(MediaType.APPLICATION_JSON)
        ).andExpect(status().isOk());
        verify(itemService, times(1)).getItems();
    }

    @Test
    void shouldReturnEmptyListWhenDoGetRequestItems() throws Exception {
        MvcResult result = mockMvc.perform(
                get(API_ITEMS_URL)
                        .contentType(MediaType.APPLICATION_JSON)
        ).andReturn();
        String resultString = result.getResponse().getContentAsString();
        assertThat(resultString).isEqualToIgnoringCase(objectMapper.writeValueAsString(Collections.emptyList()));
    }

    @Test
    void shouldReturnListOfItemsWhenDoGetRequestItems() throws Exception {
        ItemDTO itemDTO = new ItemDTO();
        itemDTO.setName("testName");
        List<ItemDTO> items = Collections.singletonList(itemDTO);
        when(itemService.getItems()).thenReturn(items);
        MvcResult result = mockMvc.perform(
                get(API_ITEMS_URL)
                        .contentType(MediaType.APPLICATION_JSON)
        ).andReturn();
        String resultString = result.getResponse().getContentAsString();
        assertThat(resultString).isEqualToIgnoringCase(objectMapper.writeValueAsString(items));
    }

    @Test
    void shouldAddItemWithValidParametersAndReturn201() throws Exception {
        ItemDTO itemDTO = new ItemDTO();
        itemDTO.setName("testName");
        itemDTO.setDescription("description");
        itemDTO.setPrice(new BigDecimal("123.24"));
        mockMvc.perform(
                post(API_ITEMS_URL)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(itemDTO))
        ).andExpect(status().isCreated());
    }

    @Test
    void shouldNotAddItemWithInvalidNameAndReturn400() throws Exception {
        ItemDTO itemDTO = new ItemDTO();
        itemDTO.setName("aaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaa");
        itemDTO.setDescription("description");
        itemDTO.setPrice(new BigDecimal("123.24"));
        mockMvc.perform(
                post(API_ITEMS_URL)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(itemDTO))
        ).andExpect(status().isBadRequest());
    }

    @Test
    void shouldNotAddItemWithEmptyDescriptionAndReturn400() throws Exception {
        ItemDTO itemDTO = new ItemDTO();
        itemDTO.setName("testName");
        itemDTO.setDescription("");
        itemDTO.setPrice(new BigDecimal("123.24"));
        mockMvc.perform(
                post(API_ITEMS_URL)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(itemDTO))
        ).andExpect(status().isBadRequest());
    }

    @Test
    void shouldNotAddItemWithNegativePriceAndReturn400() throws Exception {
        ItemDTO itemDTO = new ItemDTO();
        itemDTO.setName("testName");
        itemDTO.setDescription("description");
        itemDTO.setPrice(new BigDecimal("-123.24"));
        mockMvc.perform(
                post(API_ITEMS_URL)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(itemDTO))
        ).andExpect(status().isBadRequest());
    }

    @Test
    void shouldReturnItemWhenDoGetRequestById() throws Exception {
        ItemDTO itemDTO = new ItemDTO();
        Long validId = 1L;
        itemDTO.setId(validId);
        itemDTO.setName("testName");
        itemDTO.setDescription("description");
        itemDTO.setPrice(new BigDecimal("123.24"));
        when(itemService.findItemById(validId)).thenReturn(itemDTO);
        MvcResult result = mockMvc.perform(
                get(API_ITEMS_URL + "/{id}", validId)
                        .contentType(MediaType.APPLICATION_JSON)
        ).andReturn();
        String resultString = result.getResponse().getContentAsString();
        assertThat(resultString).isEqualToIgnoringCase(objectMapper.writeValueAsString(itemDTO));
    }

    @Test
    public void shouldVerifyThatDeleteRequestCallItemService() throws Exception {
        Long validId = 1L;
        mockMvc.perform(
                delete(API_ITEMS_URL + "/{id}", validId)
                        .contentType(MediaType.APPLICATION_JSON)
        ).andExpect(status().isOk());
        verify(itemService, times(1)).removeItemById(validId);
    }
}