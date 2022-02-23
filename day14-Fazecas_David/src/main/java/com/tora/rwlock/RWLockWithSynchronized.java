package com.tora.rwlock;

import lombok.SneakyThrows;

import java.util.ArrayList;
import java.util.List;


public class RWLockWithSynchronized {
    private boolean writer = false;
    private long writerId = -1;
    private int numberOfReaders = 0;
    private final List<Long> readersIds = new ArrayList<>();


    @SneakyThrows
    public synchronized void acquireRLock() {
        while (writer) {
            Thread.sleep(10);
        }
        numberOfReaders++;
        readersIds.add(Thread.currentThread().getId());
    }

    public synchronized void releaseRLock() {
        if (numberOfReaders > 0 && readersIds.contains(Thread.currentThread().getId())) {
            numberOfReaders--;
            readersIds.remove(Thread.currentThread().getId());
        }
    }

    @SneakyThrows
    public synchronized void acquireWLock() {
        while (writer || numberOfReaders > 0) {
            Thread.sleep(10);
        }
        writer = true;
        writerId = Thread.currentThread().getId();
    }

    @SneakyThrows
    public synchronized void releaseWLock() {
        if (writerId == Thread.currentThread().getId()) {
            writer = false;
            writerId = -1;
        }
    }
}
