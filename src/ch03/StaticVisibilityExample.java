package ch03;

import util.Debug;
import util.Tools;

import java.util.HashMap;
import java.util.Map;

public class StaticVisibilityExample {
    private static Map<String, String> taskConfig;
    static {
        Debug.info("The class being initialized...");
        taskConfig = new HashMap<String, String>();// 语句①
        taskConfig.put("url", "https://github.com/Viscent");// 语句②
        taskConfig.put("timeout", "1000");// 语句③
    }

    public static void changeConfig(String url, int timeout) {
        taskConfig = new HashMap<String, String>();
        taskConfig.put("url", url);
        taskConfig.put("timeout", String.valueOf(timeout));
    }

    public static void init() {
        Thread t = new Thread(() -> {
            String url = taskConfig.get("url");
            String timeout = taskConfig.get("timeout");
            doTask(url, Integer.parseInt(timeout));
        });
        t.start();
    }

    private static void doTask(String url, int timeout) {
        // 省略其他代码

        // 模拟实际操作的耗时
        Tools.randomPause(500);
    }
}
