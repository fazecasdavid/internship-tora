package com.tora.rwlock.testing;

import lombok.SneakyThrows;

import static com.tora.CommonResources.rWLock;
import static com.tora.CommonResources.resource;

public class Writer implements Runnable{

    @SneakyThrows
    @Override
    public void run() {
        rWLock.acquireWLock();
        Thread.sleep(10);
        System.out.printf("Writer/ThreadID: %d, resource: %d\n", Thread.currentThread().getId(), ++resource);
        rWLock.releaseWLock();
        System.out.println("Released W lock");
    }
}
