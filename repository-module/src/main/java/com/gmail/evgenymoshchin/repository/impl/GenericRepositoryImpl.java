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
    public void merge(T entity) {
        entityManager.merge(entity);
    }

    @Override
    @SuppressWarnings("unchecked")
    public List<T> findAll() {
        String queryString = "from " + entityClass.getName();
        Query query = entityManager.createQuery(queryString);
        return query.getResultList();
    }

    @Override
    @SuppressWarnings("unchecked")
    public List<T> findWithPagination(int pageNumber, int pageSize) {
        Query query = entityManager.createQuery("select t from " + entityClass.getName() + " t");
        query.setFirstResult(calculateOffset(pageNumber, pageSize));
        query.setMaxResults(pageSize);
        return query.getResultList();
    }

    @Override
    public Long getCount() {
        Query query = entityManager.createQuery("select count (t.id) from " + entityClass.getName() + " t");
        return (Long) query.getSingleResult();
    }

    private int calculateOffset(int pageNumber, int pageSize) {
        return ((pageSize * pageNumber) - pageSize);
    }
}
