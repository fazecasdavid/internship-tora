package com.tora.reposiotry.impl;

import com.tora.reposiotry.InMemoryRepository;

import java.util.ArrayList;
import java.util.List;

public class ArrayListBasedRepository<T> implements InMemoryRepository<T> {
    private final List<T> list;

    public ArrayListBasedRepository() {
        list = new ArrayList<>();
    }

    @Override
    public void add(T entity) {
        list.add(entity);
    }

    @Override
    public boolean contains(T entity) {
        return list.contains(entity);
    }

    @Override
    public void remove(T entity) {
        list.remove(entity);
    }
}
