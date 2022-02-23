package com.tora;


import com.tora.rwlock.RWLockWithSynchronized;

public class CommonResources {
    public final static Object resource1 = new Object();
    public final static Object resource2 = new Object();

    public final static RWLockWithSynchronized rWLock = new RWLockWithSynchronized();
    public static int resource = 0;
}
