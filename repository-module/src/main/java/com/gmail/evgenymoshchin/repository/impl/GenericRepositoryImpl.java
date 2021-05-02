package com.gmail.evgenymoshchin.repository.impl;

import com.gmail.evgenymoshchin.repository.GenericRepository;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import java.lang.reflect.ParameterizedType;
import java.util.List;

public abstract class GenericRepositoryImpl<I, T> implements GenericRepository<I, T> {

    @PersistenceContext
    protected EntityManager entityManager;

    protected Class<T> entityClass;

    @SuppressWarnings("unchecked")
    public GenericRepositoryImpl() {
        ParameterizedType genericSuperclass = (ParameterizedType) getClass()
                .getGenericSuperclass();
        this.entityClass = (Class<T>) genericSuperclass.getActualTypeArguments()[1];
    }

    @Override
    public void persist(T entity) {
        entityManager.persist(entity);
    }

    @Override
    public void remove(T entity) {
        entityManager.remove(entity);
    }

    @Override
    public T findById(I id) {
        return entityManager.find(entityClass, id);
    }

    @Override
    @SuppressWarnings("unchecked")
    public List<T> findAll() {
        String queryString = "from " + entityClass.getName() + " c";
        Query query = entityManager.createQuery(queryString);
        return query.getResultList();
    }

    @Override
    @SuppressWarnings("unchecked")
    public List<T> findWithPagination(int limitPerPage, int page, String entity) {
        String sql = "SELECT t FROM " + entity + " t";
        Query query = entityManager.createQuery(sql)
                .setFirstResult(calculateOffset(page, limitPerPage))
                .setMaxResults(limitPerPage);
        return query.getResultList();
    }

    private int calculateOffset(int page, int limit) {
        return ((limit * page) - limit);
    }
}
