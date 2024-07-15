package ch12.case01;

import java.util.HashMap;
import java.util.Map;

public class FineRequestRegistry implements RequestRegistry {
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
    public ResponseMessage waitForResponse(RequestMessage request, long timeout) throws TimeoutException, InterruptedException {
        ResponseMessage res = null;
        long start = System.currentTimeMillis();
        long waitTime;
        long now;
        boolean isTimeout = false;
        synchronized (request) {
            while ((res = request.getResponse()) == null) {
                now = System.currentTimeMillis();
                // 计算剩余等待时间
                waitTime = timeout - (now - start);
                if (waitTime <= 0) {
                    // 等待超时退出
                    isTimeout = true;
                    break;
                }
                request.wait(waitTime);
            }
        }
        if (isTimeout) {
            unregisterRequest(request);
            throw new TimeoutException(timeout, request.toString());
        }
        return res;
    }

    @Override
    public void responseReceived(ResponseMessage response) {
        String requestID = response.getRequestID();
        RequestMessage request = null;
        synchronized (this) {
            request = (RequestMessage) requests.get(requestID);
            if (request == null) {
                return;
            }
            requests.remove(requestID);
        }
        synchronized (request) {
            request.setResponse(response);
            request.notify();
        }
    }
}
