package com.snuh.smile.mapper;

public interface GenericMapper<V, E> {
    V toVO(E entity);
    E toEntity(V vo);
}
