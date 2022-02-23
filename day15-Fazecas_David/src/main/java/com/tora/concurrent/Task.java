package com.tora.concurrent;

import lombok.Data;

@Data
public class Task implements Runnable, Comparable<Task> {
    private final Priority priority;
    private final String exclusionContext;
    private final Runnable runnable;

    @Override
    public void run() {
        runnable.run();
    }

    @Override
    public int compareTo(Task other) {
        return Integer.compare(priority.value, other.priority.value);
    }


    public enum Priority {
        CRITICAL(0),
        HIGH(1),
        MEDIUM(2),
        LOW(3);

        private final int value;

        Priority(int value) {
            this.value = value;
        }
    }

    public static Task EMPTY_TASK() {
        return new Task(Priority.LOW, "exiting", () -> {
        });
    }
}
