package ch08;

import util.Debug;
import util.Tools;

import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.BlockingQueue;
import java.util.logging.Level;
import java.util.logging.Logger;

public class ThreadMonitorDemo {
    volatile boolean inited = false;
    static int threadIndex = 0;
    final static Logger LOGGER = Logger.getAnonymousLogger();
    final BlockingQueue<String> channel = new ArrayBlockingQueue<>(100);

    public static void main(String[] args) throws InterruptedException {
        ThreadMonitorDemo demo = new ThreadMonitorDemo();
        demo.init();
        for (int i = 0; i < 100; i++) {
            demo.service("test-" + i);
        }

        Thread.sleep(2000);
        System.exit(0);
    }

    public synchronized void init() {
        if (inited) {
            return;
        }
        Debug.info("init...");
        WorkerThread t = new WorkerThread();
        t.setName("Worker0-" + threadIndex++);
        // 为线程t关联一个UncaughtExceptionHandler
        t.setUncaughtExceptionHandler(new ThreadMonitor());
        t.start();
        inited = true;
    }

    public void service(String message) throws InterruptedException {
        channel.put(message);
    }

    private class ThreadMonitor implements Thread.UncaughtExceptionHandler {
        @Override
        public void uncaughtException(Thread t, Throwable e) {
            Debug.info("Current thread is `t`:%s, it is still alive:%s",
                    Thread.currentThread() == t, t.isAlive());

            // 将线程异常终止的相关信息记录到日志中
            String threadInfo = t.getName();
            LOGGER.log(Level.SEVERE, threadInfo + " terminated:", e);

            // 创建并启动替代线程
            LOGGER.info("About to restart " + threadInfo);
            // 重置线程启动标记
            inited = false;
            init();
        }
    }

    private class WorkerThread extends Thread {
        @Override
        public void run() {
            Debug.info("Do something important...");
            String msg;
            try {
                for (; ; ) {
                    msg = channel.take();
                    process(msg);
                }
            } catch (InterruptedException ignored) {
            }
        }

        private void process(String message) {
            Debug.info(message);
            // 模拟随机性异常
            int i = (int) (Math.random() * 100);
            if (i < 2) {
                throw new RuntimeException("test");
            }
            Tools.randomPause(100);
        }
    }
}
