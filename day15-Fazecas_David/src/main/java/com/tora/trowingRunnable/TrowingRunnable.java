package com.tora.trowingRunnable;

@FunctionalInterface
public interface TrowingRunnable<E extends Throwable> {
    void run() throws E;

    static <E extends Throwable> Runnable unchecked(TrowingRunnable<E> runnable) {
        return () -> {
            try {
                runnable.run();
            } catch (Throwable e) {
                throw new RuntimeException(e);
            }
        };
    }
}
