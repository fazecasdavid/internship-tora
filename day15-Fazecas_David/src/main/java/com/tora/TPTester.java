package com.tora;

import com.tora.concurrent.Task;
import com.tora.concurrent.ThreadPool;
import com.tora.trowingRunnable.TrowingRunnable;
import lombok.SneakyThrows;

class TPTester {
    @SneakyThrows
    public static void main(String[] args) {
        int threads = 5;
        ThreadPool pool = new ThreadPool(threads);
        pool.start();

        pool.addTask(new Task(Task.Priority.CRITICAL, "context 1", TrowingRunnable.unchecked(() -> {
            Thread.sleep(1000);
            System.out.println("slept 1 sec context 1");
        })));
        pool.addTask(new Task(Task.Priority.CRITICAL, "context 3", TrowingRunnable.unchecked(() -> {
            Thread.sleep(1000);
            System.out.println("slept 1 sec context 2");
        })));
        pool.addTask(new Task(Task.Priority.CRITICAL, "context 3", TrowingRunnable.unchecked(() -> {
            Thread.sleep(1000);
            System.out.println("slept 1 sec context 3");
        })));

        pool.stop();
    }
}
