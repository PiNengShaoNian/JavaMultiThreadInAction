package ch12.case01;

public class TimeoutException extends Exception {
    public TimeoutException(long timeout, String extraMessage) {
        super("timeout:" + timeout + ",additional message:\n" + extraMessage);
    }
}
