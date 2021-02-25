package com.epam.esm.repository;


import java.util.List;

/**
 * interface for common create, read, delete operations in repository module
 * @param <T> - generic param which describes entity type
 * @param <I> - generic param which describes id
 */

public interface CrdOperations <T, I>{
    public List<T> findAll(int offset, int limit);
    public T findById(I id);
    public T create(T entity);
    public Long delete(I id);


}

