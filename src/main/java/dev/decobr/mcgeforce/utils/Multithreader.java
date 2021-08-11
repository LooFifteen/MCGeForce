package dev.decobr.mcgeforce.utils;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.atomic.AtomicInteger;

public class Multithreader {
    private static final AtomicInteger threadCounter = new AtomicInteger(0);
    private static final ExecutorService SERVICE = Executors.newCachedThreadPool(task -> new Thread(task, "MCGeForceMod Thread " + threadCounter.getAndIncrement()));
    
    public static void run(Runnable task) {
        SERVICE.execute(task);
    }
}
