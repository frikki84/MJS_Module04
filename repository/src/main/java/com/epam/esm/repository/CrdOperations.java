package com.epam.esm.repository;


import java.util.List;

/**
 * interface for common create, read, delete operations in repository module
 * @param <T> - generic param which describes entity type
 * @param <I> - generic param which describes id
 */

public interface CrdOperations <T, I>{
    public List<T> findAll();
    public T findById(I id);
    public Integer create(T entity);
    public Integer delete(I id);


}

