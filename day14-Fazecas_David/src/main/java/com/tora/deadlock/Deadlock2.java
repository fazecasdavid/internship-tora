package com.tora.deadlock;

import com.tora.CommonResources;
import lombok.SneakyThrows;

public class Deadlock2 implements Runnable {
    @SneakyThrows
    @Override
    public void run() {
        synchronized (CommonResources.resource2) {
            System.out.println("Deadlock2: Entered synchronized block on resource2");
            Thread.sleep(10);
            synchronized (CommonResources.resource1) {
                System.out.println("Deadlock2: Entered synchronized block on resource1");
            }
        }
    }
}
