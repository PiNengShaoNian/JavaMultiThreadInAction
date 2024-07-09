package ch06.so;

import ch03.case01.Endpoint;

import java.util.Comparator;

public class DefaultEndpointComparator implements Comparator<Endpoint> {
    @Override
    public int compare(Endpoint server1, Endpoint server2) {
        int result = 0;
        boolean isOnline1 = server1.isOnline();
        boolean isOnline2 = server2.isOnline();
        // 优先按照服务器是否在线排序
        if (isOnline1 == isOnline2) {
            // 被比较的两台服务器都在线（或不在线）的情况下进一步比较服务器权重
            result = compareWeight(server1.weight, server2.weight);
        } else {
            // 在线的服务器排序靠前
            if (isOnline1) {
                result = -1;
            }
        }
        return result;
    }

    private int compareWeight(int weight1, int weight2) {
        // 按权重降序排列
        return Integer.compare(weight2, weight1);
    }
}
