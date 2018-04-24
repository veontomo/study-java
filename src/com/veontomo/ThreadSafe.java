package com.veontomo;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.BrokenBarrierException;
import java.util.concurrent.CyclicBarrier;

class Counter {
    private int hitCounter = 0;

    void inc() {
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
        final var N = 10000;
        final int[] v = new int[]{0};
        final var c = new Counter();
        final var gate = new CyclicBarrier(N + 1);
        final List<Thread> pool = new ArrayList<Thread>(N);
        for (var i = 0; i < N; i++) {
            var t = new Thread(() -> {
                try {
                    gate.await();
                    c.inc();
                    v[0]++;
                } catch (InterruptedException | BrokenBarrierException e) {
                    e.printStackTrace();
                }
            });
            pool.add(t);
            t.setName("Thread:" + (i + 1));
            t.start();
        }
        try {
            gate.await();
            System.out.println("waiting...");
            for (var t : pool) {
                t.join();
            }
            System.out.println(c.getCounter());
            System.out.println(v[0]);
        } catch (InterruptedException | BrokenBarrierException e) {
            e.printStackTrace();
        }
    }
}
