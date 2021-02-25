package com.epam.esm.service;

import java.util.List;

public interface CrdService<T, I> {

    public List<T> findAll(int offset, int limit);

    public T findById(I id);

    public T create(T entity);

    public Long delete(I id);

}
