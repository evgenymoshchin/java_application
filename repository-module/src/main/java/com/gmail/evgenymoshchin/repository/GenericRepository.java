package com.gmail.evgenymoshchin.repository;

import java.util.List;

public interface GenericRepository<I, T> {
    void persist(T entity);

    void remove(T entity);

    T findById(I id);

    List<T> findAll();

    List<T> findWithPagination(int limitPerPage, int page, String entity);
}
