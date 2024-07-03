package ch04.case02;

import java.io.InputStream;
import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.BlockingQueue;

public class LogReaderThread extends AbstractLogReader {
    // 线程安全的队列
    final BlockingQueue<RecordSet> channel = new ArrayBlockingQueue<>(2);

    public LogReaderThread(InputStream in, int inputBufferSize, int batchSize) {
        super(in, inputBufferSize, batchSize);
    }

    @Override
    protected RecordSet nextBatch() throws InterruptedException {
        RecordSet batch;
        // 从队列中取出一个记录集
        batch = channel.take();
        if (batch.isEmpty()) {
            batch = null;
        }
        return batch;
    }

    @Override
    protected void publish(RecordSet recordBatch) throws InterruptedException {
        // 记录集存入队列
        channel.put(recordBatch);
    }
}
