package ch05;

import util.Debug;

public class MayNotBeTerminatedDemo {
    public static void main(String[] args) throws InterruptedException {
        TaskRunner tr = new TaskRunner();
        tr.init();

        tr.submit(() -> {
            Debug.info("before doing task");
            try {
                Thread.sleep(2000);
            } catch (InterruptedException e) {
                // 什么也不做:这会导致线程中断标记被清除
            }
            Debug.info("after doing task");
        });
        tr.workerThread.interrupt();
    }
}
