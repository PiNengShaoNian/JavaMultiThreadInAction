package ch05;

import util.Debug;
import util.Tools;

import java.util.Date;
import java.util.Random;
import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

public class TimeoutWaitWithCondition {
    private static final Lock lock = new ReentrantLock();
    private static final Condition condition = lock.newCondition();
    private static boolean ready = false;
    protected static final Random random = new Random();

    public static void main(String[] args) throws InterruptedException {
        Thread t = new Thread(() -> {
            for (; ; ) {
                lock.lock();
                try {
                    ready = random.nextInt(100) < 5;
                    if (ready) {
                        condition.signal();
                    }
                } finally {
                    lock.unlock();
                }

                // 使当前线程暂停一段（随机）时间
                Tools.randomPause(500);
            }
        });
        t.setDaemon(true);
        t.start();
        waiter(1000);
    }

    public static void waiter(final long timeout) throws InterruptedException {
        if (timeout < 0) {
            throw new IllegalArgumentException();
        }

        // 计算等待的最后期限
        final Date deadline = new Date(System.currentTimeMillis() + timeout);
        // 是否继续等待
        boolean continueToWait = true;
        lock.lock();
        try {
            while (!ready) {
                Debug.info("still not ready,continue to wait:%s", continueToWait);
                // 等待未超时，继续等待
                if (!continueToWait) {
                    // 等待超时退出
                    Debug.error("Wait timed out,unable to execution target action!");
                    return;
                }
                continueToWait = condition.awaitUntil(deadline);
            }

            // 执行目标动作
            guarededAction();
        } finally {
            lock.unlock();
        }
    }

    private static void guarededAction() {
        Debug.info("Take some action.");
        // ...
    }
}
