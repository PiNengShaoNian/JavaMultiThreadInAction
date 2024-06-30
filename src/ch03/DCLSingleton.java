package ch03;

public class DCLSingleton {
    // 保存该类的唯一实例
    private static volatile DCLSingleton instance = null;

    /*
     * 私有构造器使其他类无法直接通过new创建该类的实例
     */
    private DCLSingleton() {
        // 什么也不做
    }

    /**
     * 创建并返回该类的唯一实例 <BR>
     * 即只有该方法被调用时该类的唯一实例才会被创建
     *
     * @return
     */
    public static DCLSingleton getInstance() {
        // 在这里instance被设置为了volatile变量如果此时他不为空则它的初始化操作一定已经完成了
        // 不会出现获取到一个初始化不完全实例的情况
        if (instance == null) {
            synchronized (IncorrectDCLSingleton.class) {
                if (instance == null) {
                    instance = new DCLSingleton();
                }
            }
        }
        return instance;
    }

    public void someService() {
        // 省略其他代码
    }
}
