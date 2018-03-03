package util;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.ThreadFactory;
import java.util.concurrent.ThreadPoolExecutor;

public class Threads {

    private static ExecutorService executor = Executors.newFixedThreadPool(8, r -> {
        final Thread thread = new Thread(r);

        thread.setUncaughtExceptionHandler((t, e) -> e.printStackTrace());
        return thread;
    });

    public static void submit(Runnable runnable) {

        executor.execute(runnable);
    }

}
