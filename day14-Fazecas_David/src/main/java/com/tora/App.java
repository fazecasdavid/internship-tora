package com.tora;

import com.tora.deadlock.Deadlock1;
import com.tora.deadlock.Deadlock2;
import com.tora.rwlock.testing.Reader;
import com.tora.rwlock.testing.Writer;


public class App {
    public static void main(String[] args) {
        deadlock();
//        rwLock();
    }

    public static void deadlock() {
        new Thread(new Deadlock1()).start();
        new Thread(new Deadlock2()).start();
    }
    public static void rwLock() {
        new Thread(new Writer()).start();

        new Thread(new Reader()).start();
        new Thread(new Reader()).start();
        new Thread(new Reader()).start();
        new Thread(new Reader()).start();

        new Thread(new Writer()).start();
    }
}
