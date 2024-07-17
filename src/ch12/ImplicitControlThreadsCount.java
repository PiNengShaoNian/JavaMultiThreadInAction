package ch12;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Semaphore;

public class ImplicitControlThreadsCount {
    final ExecutorService executorService = Executors.newCachedThreadPool();
    final Semaphore semaphore = new Semaphore(Runtime.getRuntime().availableProcessors() * 2);

    public void doSomething(final String data) throws InterruptedException {
        semaphore.acquire();

        Runnable task = new Runnable() {
            @Override
            public void run() {
                try {
                    process(data);
                } finally {
                    semaphore.release();
                }
            }
        };

        executorService.submit(task);
    }

    private void process(String data) {
    }
}
