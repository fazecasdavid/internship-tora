package com.tora.adt;

import lombok.SneakyThrows;

import java.util.ArrayList;
import java.util.function.Predicate;

public class CustomBlockingPriorityQueue<E> extends ArrayList<E> {
    private final Object commonResource = new Object();

    public synchronized void addCustom(E entity) {
        add(entity);
        this.notify();
    }

    public synchronized E popCustom(Predicate<E> predicate) {
        while (stream().noneMatch(predicate)) {
            try {
                this.wait();
            } catch (InterruptedException e) {
                System.out.println("Exception during execution: " + e.getMessage());
            }
        }
        E wanted = stream()
            .filter(predicate)
            .sorted()
            .findFirst()
            .orElseThrow();
        remove(wanted);
        synchronized (commonResource) {
            commonResource.notify();
        }
        return wanted;
    }

    @Override
    public synchronized boolean isEmpty() {
        return super.isEmpty();
    }

    @SneakyThrows
    public void waitOnResource() {
        synchronized (commonResource) {
            commonResource.wait();
        }
    }
}
