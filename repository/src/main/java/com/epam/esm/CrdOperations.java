package com.epam.esm;

import java.util.List;

public interface CrdOperations <T, K>{
    public List<T> findAll();
    public T findById(K id);
    public Integer createNew(T entity);
    public Integer updateCertificate(GiftCertificate certificate, long id);
    public Integer deleteCertificate(long id);


}

