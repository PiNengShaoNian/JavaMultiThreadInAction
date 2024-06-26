package ch02;

import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.util.Date;

public class RequestIDGenerator implements CircularSeqGenerator {
    /**
     * 保存改类的唯一实例
     */
    private final static RequestIDGenerator INSTANCE = new RequestIDGenerator();
    private final static short SEQ_UPPER_LIMIT = 999;
    private short sequence = -1;

    // 私有构造器
    private RequestIDGenerator() {
        // 什么也不做
    }

    @Override
    public short nextSequence() {
        if (sequence >= SEQ_UPPER_LIMIT) {
            sequence = 0;
        } else {
            sequence++;
        }
        return sequence;
    }

    public String nextID() {
        SimpleDateFormat sdf = new SimpleDateFormat("yyMMddHHmmss");
        String timestamp = sdf.format(new Date());
        DecimalFormat df = new DecimalFormat("000");

        // 生成请求序号
        short sequenceNo = nextSequence();

        return "0049" + timestamp + df.format(sequenceNo);
    }

    public static RequestIDGenerator getInstance() {
        return INSTANCE;
    }
}
