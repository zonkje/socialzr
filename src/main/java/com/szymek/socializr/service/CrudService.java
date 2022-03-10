package com.szymek.socializr.service;

import com.szymek.socializr.common.ApplicationResponse;

import java.util.Collection;

public interface CrudService<T, ID> {

    Collection<T> findAll(Integer page, Integer size);
    T findById(ID id);
    T create(T object);
    ApplicationResponse deleteById(ID id);
    T update(T object, ID id);

}
