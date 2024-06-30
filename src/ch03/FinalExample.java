package ch03;

import util.Debug;
import util.Tools;

import java.util.HashMap;
import java.util.Map;

public class FinalExample {
    private final Map<String, String> taskConfig;
    private final String defaultTimeout = "2000";
    private int retriedTimes = -1;

    private static FinalExample instance;

    public FinalExample(String url) {
        taskConfig = new HashMap<String, String>();
        taskConfig.put("url", url);
        taskConfig.put("timeout", String.valueOf(defaultTimeout));
    }

    public FinalExample(String url, int timeout) {
        taskConfig = new HashMap<String, String>();
        taskConfig.put("url", url);
        taskConfig.put("timeout", String.valueOf(timeout));
    }

    public static void init(String url, int timeout) {
        instance = new FinalExample(url, timeout);
    }

    public static void checkConfig() {
        if (instance != null) {
            Map<String, String> conf = instance.taskConfig;
            Debug.info("url:" + conf.get("url"));
            Debug.info("defaultTimeout:" + instance.defaultTimeout.equals("2000"));
            Debug.info("retriedTimes:" + instance.retriedTimes);
        }
    }

    public static void main(String[] args) {
        Thread subThread = new Thread(() -> {
            Tools.randomPause(50);
            FinalExample.checkConfig();
        });
        subThread.start();
        FinalExample.init("https://github.com/Viscent", 1200);
    }
}
