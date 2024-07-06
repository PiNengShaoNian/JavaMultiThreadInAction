package ch05.case03;

import ch04.case02.AbstractLogReader;
import ch04.case02.MultithreadedStatTask;

import java.io.InputStream;

public class StatTask extends MultithreadedStatTask {
    public StatTask(InputStream in, int sampleInterval, int traceIdDiff,
                    String expectedOperationName, String expectedExternalDeviceList) {
        super(in, sampleInterval, traceIdDiff, expectedOperationName,
                expectedExternalDeviceList);
    }

    @Override
    protected AbstractLogReader createLogReader() {
        return new ExchangerBasedLogReaderThread(in, inputBufferSize, batchSize);
    }
}
