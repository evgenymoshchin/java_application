package com.gmail.evgenymoshchin.repository;

import com.gmail.evgenymoshchin.repository.model.Status;
import com.gmail.evgenymoshchin.repository.model.StatusEnum;

public interface StatusRepository extends GenericRepository<Long, Status> {
    Status findStatusByName(StatusEnum statusEnum);
}
