package com.szymek.socializr.service;

import java.util.Collection;
import java.util.Optional;

public interface CrudService<T, ID> {

    Collection<T> findAll();
    T findById(ID id);
    T create(T object);
    void deleteById(ID id);
    T update(T object, ID id);

}
