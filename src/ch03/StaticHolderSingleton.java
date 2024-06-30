package ch03;

import util.Debug;

public class StaticHolderSingleton {
    // 私有构造器
    private StaticHolderSingleton() {
        Debug.info("StaticHolderSingleton inited.");
    }

    static class InstanceHolder {
        // 保存外部类的唯一实例
        static {
            Debug.info("InstanceHolder inited.");
        }
        final static StaticHolderSingleton INSTANCE = new StaticHolderSingleton();
    }

    public static StaticHolderSingleton getInstance() {
        Debug.info("getInstance invoked.");
        return InstanceHolder.INSTANCE;
    }

    public void someService() {
        Debug.info("someService invoked.");
        // 省略其他代码
    }

    public static void main(String[] args) {
        Thread t = new Thread(() -> {
            Debug.info(StaticHolderSingleton.InstanceHolder.class.getName());
            StaticHolderSingleton.InstanceHolder.INSTANCE.someService();
        });
        t.start();
    }
}
