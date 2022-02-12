package com.szymek.socializr.service;

import java.util.Collection;

public interface CrudService<T, ID> {

    Collection<T> findAll();
    T findById(ID id);
    T create(T object);
    void delete(T object);
    void deleteById(ID id);

}
