package ch03;

import util.Debug;

import java.util.Map;

public class SafeObjPublishWhenStartingThread {
    private final Map<String, String> objectState;

    private SafeObjPublishWhenStartingThread(Map<String, String> objectState) {
        this.objectState = objectState;
        // 不在构造器中启动工作者线程，以避免this逸出
    }

    private void init() {
        // 创建并启动工作者线程
        new Thread(() -> {
            // 访问外层类实例的状态变量
            String value = objectState.get("someKey");
            Debug.info(value);
            // 省略其他代码
        }).start();
    }

    // 工厂方法
    public static SafeObjPublishWhenStartingThread newInstance(Map<String, String> objectState) {
        SafeObjPublishWhenStartingThread instance = new SafeObjPublishWhenStartingThread((objectState));
        instance.init();
        return instance;
    }
}
