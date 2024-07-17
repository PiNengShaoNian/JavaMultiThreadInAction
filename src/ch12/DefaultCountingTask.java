package ch12;

public class DefaultCountingTask implements CountingTask {
    private final long iterations;
    private volatile long value;

    DefaultCountingTask() {
        this(1000000);
    }

    DefaultCountingTask(long iterations) {
        this.iterations = iterations;
    }

    @Override
    public void setValue(long value) {
        this.value = value;
    }

    @Override
    public long getValue() {
        return value;
    }

    @Override
    public long getIterations() {
        return iterations;
    }
}
