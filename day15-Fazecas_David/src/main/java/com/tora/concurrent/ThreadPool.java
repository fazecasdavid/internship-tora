package com.tora.concurrent;

import com.tora.adt.CustomBlockingPriorityQueue;
import io.github.rkamradt.possibly.PossiblyConsumer;
import lombok.SneakyThrows;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.atomic.AtomicBoolean;
import java.util.stream.IntStream;

import static com.tora.concurrent.Task.EMPTY_TASK;

/**
 * Implement a thread pool allowing:
 * - execution over a fixed number of threads
 * - stopping
 * - adding (enqueuing) tasks
 * - removing unexecuted tasks
 * - task priority - execute tasks only when
 * - exclusion context - don't execute a task while another task with the same exclusion context is being executed
 */
public class ThreadPool {
    private final int numberOfThreads;
    private final CustomBlockingPriorityQueue<Task> taskQueue;
    private final List<CustomThreads> threads = new ArrayList<>();
    private final AtomicBoolean running = new AtomicBoolean(false);

    private final Set<String> exclusionContexts = Collections.newSetFromMap(new ConcurrentHashMap<>());

    public ThreadPool(int numberOfThreads) {
        this.numberOfThreads = numberOfThreads;
        this.taskQueue = new CustomBlockingPriorityQueue<>();
    }

    public void start() {
        running.set(true);
        IntStream.range(0, numberOfThreads).forEach(i -> {
            CustomThreads thread = new CustomThreads();
            thread.start();
            threads.add(thread);
        });
    }

    @SneakyThrows
    public void stop() {
        running.set(false);
        while (!taskQueue.isEmpty()) {
            taskQueue.waitOnResource();
        }
        threads.forEach(th -> taskQueue.addCustom(EMPTY_TASK()));
        threads.forEach(PossiblyConsumer.of(Thread::join));
        threads.clear();
    }

    public void addTask(Task task) {
        synchronized (taskQueue) {
            taskQueue.addCustom(task);
        }
    }

    private class CustomThreads extends Thread {
        @SneakyThrows
        public void run() {
            Task task;
            while (running.get()) {
                task = taskQueue.popCustom(t -> !exclusionContexts.contains(t.getExclusionContext()));

                try {
                    exclusionContexts.add(task.getExclusionContext());
                    task.run();
                    synchronized (taskQueue) {
                        exclusionContexts.remove(task.getExclusionContext());
                        taskQueue.notify();
                    }
                } catch (RuntimeException e) {
                    System.out.println("Exception during execution: " + e.getMessage());
                }
            }
        }
    }
}
