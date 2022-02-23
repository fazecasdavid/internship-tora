package com.tora.deadlock;

import com.tora.CommonResources;
import lombok.SneakyThrows;

public class Deadlock1 implements Runnable {
    @SneakyThrows
    @Override
    public void run() {
        synchronized (CommonResources.resource1) {
            System.out.println("Deadlock1: Entered synchronized block on resource1");
            Thread.sleep(10);
            synchronized (CommonResources.resource2) {
                System.out.println("Deadlock1: Entered synchronized block on resource2");
            }
        }
    }
}
