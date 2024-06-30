package ch03;

public class IncorrectDCLSingleton {
    // 保存该类的唯一实例
    private static IncorrectDCLSingleton instance = null;

    /*
     * 私有构造器使其他类无法直接通过new创建该类的实例
     */
    private IncorrectDCLSingleton() {
        // 什么也不做
    }

    /**
     * 创建并返回该类的唯一实例 <BR>
     * 即只有该方法被调用时该类的唯一实例才会被创建
     *
     * @return
     */
    public static IncorrectDCLSingleton getInstance() {
        // 错误的写法，读取instance和设置instance没有互斥，且没有限制执行重排序
        // 导致此时可能正在执行临界区中设置instance的
        // 代码而读取到一个分配好空间但是初始化不完全instance
        if (instance == null) {
            synchronized (IncorrectDCLSingleton.class) {
                if (instance == null) {
                    instance = new IncorrectDCLSingleton();
                }
            }
        }
        return instance;
    }

    public void someService() {
        // 省略其他代码
    }
}
