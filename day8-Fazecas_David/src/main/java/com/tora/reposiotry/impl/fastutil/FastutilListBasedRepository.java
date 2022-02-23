package com.tora.reposiotry.impl.fastutil;


import com.tora.reposiotry.InMemoryRepository;
import it.unimi.dsi.fastutil.objects.ObjectArrayList;

import java.util.List;

public class FastutilListBasedRepository<T> implements InMemoryRepository<T> {
    private final List<T> list;

    public FastutilListBasedRepository() {
        list = new ObjectArrayList<>();
    }

    @Override
    public void add(T element) {
        list.add(element);
    }

    @Override
    public boolean contains(T element) {
        return list.contains(element);
    }

    @Override
    public void remove(T element) {
        list.remove(element);
    }
}
