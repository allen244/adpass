package com.avs.adpass.services;

import java.util.List;

public interface AvsService<T>   {

    List<?> listAll();

    T getById(Long id);

    T saveOrUpdate(T domainObject);

    void delete(Long id);
}
