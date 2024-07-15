package ch12.case01;

public interface RequestRegistry {
    void registerRequest(RequestMessage request);

    void unregisterRequest(RequestMessage request);

    ResponseMessage waitForResponse(RequestMessage request, long timeout) throws TimeoutException, InterruptedException;

    void responseReceived(ResponseMessage response);
}
