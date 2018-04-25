package com.veontomo;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.BrokenBarrierException;
import java.util.concurrent.CyclicBarrier;

class Counter {
    private int hitCounter = 0;

    void inc() {
        ++hitCounter;
        --hitCounter;
        hitCounter++;

    }

    int getCounter() {
        return hitCounter;
    }
}

/**
 * Launch N many threads and increment by one unit a variable from each of the threads.
 * Try to see whether the final value of the variable is different from the number of threads.
 */
public class ThreadSafe {
    public static void main(String... args) {
        final var N = 6;
        final var container = new int[]{0};
        final var c = new Counter();
        final var gate = new CyclicBarrier(N + 1);
        final List<Thread> pool = new ArrayList<Thread>(N);
        for (var i = 0; i < N; i++) {
            pool.add(new Thread(() -> {
                try {
                    System.out.println("Thread " + Thread.currentThread().getName() + " is waiting...");
                    gate.await();
                    System.out.println("Thread " + Thread.currentThread().getName() + " is proceeding...");
                    c.inc();
                    container[0]++;
                } catch (InterruptedException | BrokenBarrierException e) {
                    e.printStackTrace();
                }
            }, "My-thread-" + (i + 1)));
            pool.get(i).start();
        }
        try {
            System.out.println("Last part is added");
            gate.await();
            System.out.println("waiting for all threads to join...");
            for (var t : pool) {
                t.join();
            }
            final var value1 = c.getCounter();
            final var value2 = container[0];
            System.out.println("N = " + N + ", value1 = " + value1 + ", value2 = " + value2);
            if (value1 != N || value2 != N) {
                System.out.println("Race condition has occurred!!!");
            } else {
                System.out.println("No race condition...");
            }

        } catch (InterruptedException | BrokenBarrierException e) {
            e.printStackTrace();
        }
    }
}
