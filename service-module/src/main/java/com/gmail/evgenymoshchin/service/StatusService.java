package com.gmail.evgenymoshchin.service;

import com.gmail.evgenymoshchin.service.model.StatusDTO;

import java.util.List;

public interface StatusService {
    List<StatusDTO> findAll();
}
