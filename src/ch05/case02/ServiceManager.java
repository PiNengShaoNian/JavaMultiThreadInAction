package ch05.case02;

import java.util.HashSet;
import java.util.Set;
import java.util.concurrent.CountDownLatch;

public class ServiceManager {
    static volatile CountDownLatch latch;
    static Set<Service> services;

    public static void startServices() {
        services = getServices();
        for (Service service : services) {
            service.start();
        }
    }

    public static boolean checkServiceStatus() {
        boolean allIsOk = true;
        // 等待服务启动结束
        try {
            latch.await();
        } catch (InterruptedException e) {
            return false;
        }

        for (Service service : services) {
            if (!service.isStarted()) {
                allIsOk = false;
                break;
            }
        }

        return allIsOk;
    }

    static Set<Service> getServices() {
        // 模拟实际代码
        latch = new CountDownLatch(3);
        HashSet<Service> services = new HashSet<>();
        services.add(new SampleServiceC(latch));
        services.add(new SampleServiceA(latch));
        services.add(new SampleServiceB(latch));
        return services;
    }
}
