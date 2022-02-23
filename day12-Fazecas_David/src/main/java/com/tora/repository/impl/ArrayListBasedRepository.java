package com.tora.repository.impl;

import com.tora.domain.BaseEntity;
import com.tora.repository.Repository;
import org.springframework.context.annotation.Primary;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@org.springframework.stereotype.Repository
@Primary
public class ArrayListBasedRepository<T extends BaseEntity<ID>, ID extends Serializable> implements Repository<T, ID> {
    private final List<T> list;

    public ArrayListBasedRepository() {
        list = new ArrayList<>();
    }

    @Override
    public Optional<T> findById(ID id) {
        return list.stream()
            .filter(entity -> entity.getId().equals(id))
            .findAny();
    }

    @Override
    public long count() {
        return list.size();
    }

    @Override
    public void delete(T entity) {
        list.remove(entity);
    }

    @Override
    public void deleteById(ID id) {
        list.removeIf(entity -> entity.getId().equals(id));
    }

    @Override
    public boolean existsById(ID id) {
        return list.stream()
            .anyMatch(entity -> entity.getId().equals(id));
    }

    @Override
    public Iterable<T> findAll() {
        return list;
    }

    @Override
    public <S extends T> S save(S entity) {
        if (!existsById(entity.getId()))
            list.add(entity);
        return entity;
    }

    @Override
    public <S extends T> Iterable<S> saveAll(Iterable<S> entities) {
        entities.forEach(this::save);
        return entities;
    }
}
