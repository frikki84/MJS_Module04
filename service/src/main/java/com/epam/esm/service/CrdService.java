package com.epam.esm.service;

import java.util.List;

public interface CrdService<T, I, E> {

    public List<T> findAll();

    public T findById(I id);

    public T create(E entity);

    public Long delete(I id);

}
