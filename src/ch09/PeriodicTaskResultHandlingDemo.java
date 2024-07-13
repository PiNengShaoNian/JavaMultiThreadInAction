package ch09;

import util.Debug;
import util.Tools;

import java.util.Random;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

public class PeriodicTaskResultHandlingDemo {
    final static ScheduledExecutorService ses = Executors.newScheduledThreadPool(2);

    public static void main(String[] args) throws InterruptedException {
        final String host = args.length > 0 ? args[0] : "localhost";
        final AsyncTask<Integer> asyncTask = new AsyncTask<Integer>() {
            final Random rnd = new Random();
            final String targetHost = host;

            @Override
            public Integer call() throws Exception {
                return pingHost();
            }

            private Integer pingHost() throws Exception {
                // 模拟实际操作耗时
                Tools.randomPause(2000);
                // 模拟的探测结果码
                return Integer.valueOf(rnd.nextInt(4));
            }

            @Override
            protected void onResult(Integer result) {
                // 将结果保存到数据库
                saveToDatabase(result);
            }

            private void saveToDatabase(Integer result) {
                Debug.info(targetHost + " status:" + result);
                // 省略其他代码
            }

            @Override
            public String toString() {
                return "Ping " + targetHost + "," + super.toString();
            }
        };

        ses.scheduleAtFixedRate(asyncTask, 0, 3, TimeUnit.SECONDS);
        Tools.delayedAction("The ScheduledExecutorService will be shutdown", ses::shutdown, 60);
    }
}
