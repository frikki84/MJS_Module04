package com.epam.esm.repository;


import java.util.List;

/**
 * interface for common create, read, delete operations in repository module
 * @param <T> - generic param which describes entity type
 */

public interface CrdOperations <T>{
    /**
     *
     * @param offset - number of search results per page
     * @param limit - number of pages
     * @return
     */
    public List<T> findAll(int offset, int limit);
    public T findById(long id);
    public T create(T entity);
    public long delete(long id);
    public Long findNumberOfEntities();


}

