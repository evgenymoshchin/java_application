package com.gmail.evgenymoshchin.service;

import com.gmail.evgenymoshchin.service.model.ReviewDTO;

import java.util.List;

public interface ReviewService {
    List<ReviewDTO> findAll();

    void removeById(Long id);
}
