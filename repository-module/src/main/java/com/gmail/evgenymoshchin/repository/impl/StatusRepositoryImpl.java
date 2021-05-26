package com.gmail.evgenymoshchin.repository.impl;

import com.gmail.evgenymoshchin.repository.StatusRepository;
import com.gmail.evgenymoshchin.repository.model.Status;
import com.gmail.evgenymoshchin.repository.model.StatusEnum;
import org.springframework.stereotype.Repository;

import javax.persistence.Query;

import static com.gmail.evgenymoshchin.repository.impl.constant.QueryConstants.FIND_STATUS_BY_NAME_QUERY;
import static com.gmail.evgenymoshchin.repository.impl.constant.QueryConstants.NAME_VALUE;

@Repository
public class StatusRepositoryImpl extends GenericRepositoryImpl<Long, Status> implements StatusRepository {

    @Override
    public Status findStatusByName(StatusEnum statusEnum) {
        Query query = entityManager.createQuery(FIND_STATUS_BY_NAME_QUERY);
        query.setParameter(NAME_VALUE, statusEnum);
        return (Status) query.getSingleResult();
    }
}
