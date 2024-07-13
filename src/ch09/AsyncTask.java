package ch09;

import java.util.concurrent.Callable;
import java.util.concurrent.Executor;
import java.util.logging.Level;
import java.util.logging.Logger;

public abstract class AsyncTask<V> implements Runnable, Callable<V> {
    final static Logger LOGGER = Logger.getAnonymousLogger();
    protected final Executor executor;

    public AsyncTask(Executor executor) {
        this.executor = executor;
    }

    public AsyncTask() {
        this((Runnable::run));
    }

    @Override
    public void run() {
        Exception exp = null;
        V r = null;
        try {
            r = call();
        } catch (Exception e) {
            exp = e;
        }

        final V result = r;
        if (exp == null) {
            executor.execute(() -> {
                onResult(result);
            });
        } else {
            final Exception exceptionCaught = exp;
            executor.execute(() -> {
                onError(exceptionCaught);
            });
        }
    }

    /**
     * 留给子类实现任务执行结果的处理逻辑。
     *
     * @param result 任务执行结果
     */
    protected abstract void onResult(V result);

    /**
     * 子类可覆盖该方法来对任务执行过程中抛出的异常进行处理。
     *
     * @param e 任务执行过程中抛出的异常
     */
    protected void onError(Exception e) {
        LOGGER.log(Level.SEVERE, "AsyncTask[" + this + "] failed.", e);
    }
}
