package com.tora.repository;


import java.io.Serializable;
import java.util.Optional;

public interface Repository<T , ID extends Serializable> {

    Optional<T> findById(final ID id);

    long count();

    void delete(T entity);

    void deleteById(ID id);

    boolean existsById(ID id);

    Iterable<T> findAll();

    <S extends T> S save(S entity);

    <S extends T> Iterable<S> saveAll(Iterable<S> entities);
}
