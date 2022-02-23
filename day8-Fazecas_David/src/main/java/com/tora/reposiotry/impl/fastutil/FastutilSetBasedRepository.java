package com.tora.reposiotry.impl.fastutil;

import com.tora.reposiotry.InMemoryRepository;
import it.unimi.dsi.fastutil.objects.ObjectOpenHashSet;

import java.util.Set;

public class FastutilSetBasedRepository<T> implements InMemoryRepository<T> {
    private final Set<T> set;

    public FastutilSetBasedRepository() {
        set = new ObjectOpenHashSet<>();
    }

    @Override
    public void add(T element) {
        set.add(element);
    }

    @Override
    public boolean contains(T element) {
        return set.contains(element);
    }

    @Override
    public void remove(T element) {
        set.remove(element);
    }

}
