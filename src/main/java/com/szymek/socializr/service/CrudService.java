package com.szymek.socializr.service;

import java.util.Collection;
import java.util.Optional;

public interface CrudService<T, ID> {

    Collection<T> findAll();
    Optional<T> findById(ID id);
    T create(T object);
    void delete(T object);
    void deleteById(ID id);

}
