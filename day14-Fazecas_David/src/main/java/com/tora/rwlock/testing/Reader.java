package com.tora.rwlock.testing;

import static com.tora.CommonResources.rWLock;
import static com.tora.CommonResources.resource;

public class Reader implements Runnable {

    @Override
    public void run() {
        rWLock.acquireRLock();
        System.out.printf("Reader/ThreadID: %d, resource: %d\n", Thread.currentThread().getId(), resource);
        rWLock.releaseRLock();
    }
}
