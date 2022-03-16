package com.szymek.socializr.mapper;

public interface BeanMapper<T1, T2> {

    T1 toEntity(T2 dto);
    T2 toDTO(T1 entity);

}

//TODO -name of this class didn't fit