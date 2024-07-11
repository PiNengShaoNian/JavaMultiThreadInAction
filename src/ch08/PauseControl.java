package ch08;

import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.ReentrantLock;

public class PauseControl extends ReentrantLock {
    private volatile boolean suspended = false;
    private final Condition condSuspended = newCondition();

    /**
     * 暂停线程
     */
    public void requestPause() {
        suspended = true;
    }

    /**
     * 恢复线程
     */
    public void proceed() {
        lock();
        try {
            suspended = false;
            condSuspended.signalAll();
        } finally {
            unlock();
        }
    }

    /**
     * 当前线程仅在线程暂挂标记不为true的情况下才执行指定的目标动作。
     *
     * @param targetAction 目标动作
     */
    public void pauseIfNecessary(Runnable targetAction) throws InterruptedException {
        lock();
        try {
            while (suspended) {
                condSuspended.await();
            }
            targetAction.run();
        } finally {
            unlock();
        }
    }
}
