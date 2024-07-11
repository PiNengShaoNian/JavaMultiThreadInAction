package ch08;

import ch04.case01.BigFileDownloader;
import ch04.case01.DownloadTask;

import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

public class TPBigFileDownloader extends BigFileDownloader {
    final static int N_CPU = Runtime.getRuntime().availableProcessors();
    final ThreadPoolExecutor executor = new ThreadPoolExecutor(2, N_CPU * 2, 4, TimeUnit.SECONDS,
            new ArrayBlockingQueue<>(N_CPU * 8),
            new ThreadPoolExecutor.CallerRunsPolicy());

    public TPBigFileDownloader(String file) throws Exception {
        super(file);
    }

    public static void main(String[] args) throws Exception {
        final int argc = args.length;
        TPBigFileDownloader downloader = new TPBigFileDownloader(args[0]);
        long reportInterval = argc >= 2 ? Integer.parseInt(args[1]) : 10;

        // 平均每个处理器执行8个下载子任务
        final int taskCount = N_CPU * 8;
        downloader.download(taskCount, reportInterval * 1000);
    }

    @Override
    protected void dispatchWork(DownloadTask dt, int workerIndex) {
        executor.submit(() -> {
            try {
                dt.run();
            } catch (Exception e) {
                e.printStackTrace();
                // 任何一个下载子任务出现异常就取消整个下载任务
                cancelDownload();
            }
        });
    }

    @Override
    protected void doCleanup() {
        executor.shutdownNow();
        super.doCleanup();
    }
}
