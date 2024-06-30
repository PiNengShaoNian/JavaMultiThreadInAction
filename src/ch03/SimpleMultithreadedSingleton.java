package ch03;

public class SimpleMultithreadedSingleton {
    // 保存该类的唯一实例
    private static SimpleMultithreadedSingleton instance = null;

    /*
     * 私有构造器使其他类无法直接通过new创建该类的实例
     */
    private SimpleMultithreadedSingleton() {
        // 什么也不做
    }

    /**
     * 创建并返回该类的唯一实例 <BR>
     * 即只有该方法被调用时该类的唯一实例才会被创建
     *
     * @return
     */
    public static SimpleMultithreadedSingleton getInstance() {
        // 并发度不高，每个调用getInstance的线程都需要先获取锁
        synchronized (SimpleMultithreadedSingleton.class) {
            if (instance == null) {
                instance = new SimpleMultithreadedSingleton();
            }
        }
        return instance;
    }

    public void someService() {
    }
}
