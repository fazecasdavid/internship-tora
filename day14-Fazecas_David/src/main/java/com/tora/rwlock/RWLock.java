package com.tora.rwlock;

import lombok.SneakyThrows;

import java.util.concurrent.locks.ReentrantLock;

public class RWLock {
    private final ReentrantLock lock = new ReentrantLock();
    private int numberOfReaders = 0;

    @SneakyThrows
    public synchronized void acquireRLock() {
        while (lock.isLocked()) {
            Thread.sleep(10);
        }
        numberOfReaders++;
    }

    public synchronized void releaseRLock() {
        if (numberOfReaders > 0) {
            numberOfReaders--;
        }
    }

    @SneakyThrows
    public synchronized void acquireWLock() {
        while (lock.isLocked() || numberOfReaders > 0) {
            Thread.sleep(10);
        }
        lock.lock();
    }

    @SneakyThrows
    public synchronized void releaseWLock() {
        lock.unlock();
    }

}
