package ch08;

import util.Debug;

import java.util.Random;
import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.ThreadFactory;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

public class XThreadFactoryUsage {
    final static ThreadPoolExecutor executor = new ThreadPoolExecutor(4, 4, 4,
            TimeUnit.SECONDS,
            new ArrayBlockingQueue<>(8),
            new ThreadPoolExecutor.CallerRunsPolicy());


    public static void main(String[] args) {
        final ThreadFactory tf = new XThreadFactory("worker");
        executor.setThreadFactory(tf);

        final Random rnd = new Random();

        for (int i = 0; i < 10; i++) {
            executor.execute(() -> {
                Debug.info("running...");
                // 模拟随机性运行时异常抛出
                new TaskWithException(rnd).run();
            });
        }
    }
}
