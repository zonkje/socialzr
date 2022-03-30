package com.szymek.socializr.service;

import com.szymek.socializr.common.ApplicationResponse;

import java.util.Collection;

public interface CrudService<T, ID> {

    Collection<T> findAll(Integer page, Integer size);
    T findById(ID id);
    T create(T object, String authorName);
    ApplicationResponse deleteById(ID id, String loggedUserName);
    T update(T object, String loggedUserName);

}
