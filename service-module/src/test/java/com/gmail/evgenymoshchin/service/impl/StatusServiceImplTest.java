package com.gmail.evgenymoshchin.service.impl;

import com.gmail.evgenymoshchin.repository.StatusRepository;
import com.gmail.evgenymoshchin.repository.model.Status;
import com.gmail.evgenymoshchin.repository.model.StatusEnum;
import com.gmail.evgenymoshchin.service.model.StatusDTO;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Collections;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class StatusServiceImplTest {

    @Mock
    private StatusRepository statusRepository;
    @InjectMocks
    StatusServiceImpl statusService;

    @Test
    void shouldReturnEmptyList() {
        List<StatusDTO> statuses = statusService.findAll();

        assertTrue(statuses.isEmpty());
    }

    @Test
    void shouldReturnStatusesList() {
        StatusDTO statusDTO = new StatusDTO();
        statusDTO.setName(StatusEnum.IN_PROGRESS);
        Status status = new Status();
        status.setName(StatusEnum.IN_PROGRESS);

        when(statusRepository.findAll()).thenReturn(Collections.singletonList(status));

        List<StatusDTO> statuses = statusService.findAll();

        assertEquals(statuses.get(0).getName(), statusDTO.getName());
    }
}