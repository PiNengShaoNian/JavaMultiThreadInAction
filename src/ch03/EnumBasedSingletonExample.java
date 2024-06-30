package ch03;

import util.Debug;

public class EnumBasedSingletonExample {
    public static void main(String[] args) {
        Thread t = new Thread(() -> {
            Debug.info(Singleton.class.getName());
            Singleton.INSTANCE.someService();
        });
        t.start();
    }

    public static enum Singleton {
        INSTANCE;
        // 私有构造器
        Singleton() {
            Debug.info("Singleton inited.");
        }

        public void someService() {
            Debug.info("someService invoked.");
            // 省略其他代码
        }
    }
}
