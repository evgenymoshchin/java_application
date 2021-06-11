package com.gmail.evgenymoshchin.service.impl;

import com.gmail.evgenymoshchin.repository.StatusRepository;
import com.gmail.evgenymoshchin.repository.model.Status;
import com.gmail.evgenymoshchin.service.StatusService;
import com.gmail.evgenymoshchin.service.model.StatusDTO;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
public class StatusServiceImpl implements StatusService {

    private final StatusRepository statusRepository;

    @Override
    public List<StatusDTO> findAll() {
        List<Status> statuses = statusRepository.findAll();
        List<StatusDTO> statusDTOS = new ArrayList<>();
        for (Status status : statuses) {
            statusDTOS.add(convertStatusToDTO(status));
        }
        return statusDTOS;
    }

    private StatusDTO convertStatusToDTO(Status status) {
        StatusDTO statusDTO = new StatusDTO();
        statusDTO.setName(status.getName());
        return statusDTO;
    }
}
