package ch12.case01;

import java.util.HashMap;
import java.util.Map;

public class NaiveRequestRegistry implements RequestRegistry {
    private final Map<String, RequestMessage> requests = new HashMap<>();

    @Override
    public synchronized void registerRequest(RequestMessage request) {
        String requestID = request.getID();
        requests.put(requestID, request);
    }

    @Override
    public synchronized void unregisterRequest(RequestMessage request) {
        String requestID = request.getID();
        requests.remove(requestID);
    }

    @Override
    public synchronized ResponseMessage waitForResponse(RequestMessage request, long timeout) throws TimeoutException, InterruptedException {
        ResponseMessage res = null;
        long start = System.currentTimeMillis();
        long waitTime;
        long now;
        boolean isTimeout = false;
        while ((res = request.getResponse()) == null) {
            now = System.currentTimeMillis();
            // 计算剩余等待时间
            waitTime = timeout - (now - start);
            if (waitTime <= 0) {
                // 等待超时退出
                isTimeout = true;
                break;
            }
            wait(waitTime);
        }

        if (isTimeout) {
            unregisterRequest(request);
            throw new TimeoutException(timeout, request.toString());
        }
        return res;
    }

    // 响应消息接收线程接收到消息后会调用该方法

    @Override
    public synchronized void responseReceived(ResponseMessage response) {
        String requestID = response.getRequestID();
        RequestMessage request = (RequestMessage) requests.get(requestID);
        // request为null说明响应没有在规定的时间内达到当前系统
        if (request != null) {
            requests.remove(requestID);
            request.setResponse(response);
            notifyAll();
        }
    }
}
