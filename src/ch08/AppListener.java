package ch08;

import java.util.logging.Level;
import java.util.logging.Logger;

public class AppListener {
    final static Logger LOGGER = Logger.getAnonymousLogger();

    public void contextInitialized() {
        // 设置默认UncaughtExceptionHandler
        Thread.UncaughtExceptionHandler ueh = new LoggingUncaughtExceptionHandler();
        Thread.setDefaultUncaughtExceptionHandler(ueh);

        // 启动若干工作者线程
        startServices();
    }

    static class LoggingUncaughtExceptionHandler implements Thread.UncaughtExceptionHandler {
        @Override
        public void uncaughtException(Thread t, Throwable e) {
            String threadInfo = "Thread[" + t.getName() + "," + t.getId() + ","
                    + t.getThreadGroup().getName() + ",@" + t.hashCode() + "]";

            // 将线程异常终止的相关信息记录到日志中
            LOGGER.log(Level.SEVERE, threadInfo + " terminated:", e);
        }
    }

    protected void startServices() {
        // 省略其他代码
    }

    protected void stopServices() {
        // 省略其他代码
    }

    public void contextDestroyed() {
        Thread.setDefaultUncaughtExceptionHandler(null);
        stopServices();
    }
}
