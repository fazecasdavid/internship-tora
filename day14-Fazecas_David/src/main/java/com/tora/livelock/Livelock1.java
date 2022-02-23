package com.tora.livelock;

import com.tora.CommonResources;

public class Livelock1 implements Runnable {
    @Override
    public void run() {
        while(true) {
            synchronized (CommonResources.resource1){

            }
        }
    }
}
