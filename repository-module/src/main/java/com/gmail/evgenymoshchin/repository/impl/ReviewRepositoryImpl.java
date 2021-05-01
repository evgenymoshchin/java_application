package com.gmail.evgenymoshchin.repository.impl;

import com.gmail.evgenymoshchin.repository.ReviewRepository;
import com.gmail.evgenymoshchin.repository.model.Review;
import org.springframework.stereotype.Repository;

@Repository
public class ReviewRepositoryImpl extends GenericRepositoryImpl<Long, Review> implements ReviewRepository {
}
