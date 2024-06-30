package ch03;

public class Counter {
    // 使用volatile保证该变量的可见性
    private volatile long count;

    public long value() {
        return count;
    }

    public void increment() {
        synchronized (this) {
            // 使用同步块保证该变量累加时的原子性
            count++;
        }
    }
}
