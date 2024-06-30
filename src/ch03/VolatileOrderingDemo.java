package ch03;

import util.stf.*;

@ConcurrencyTest(iterations = 200000)
public class VolatileOrderingDemo {
    private int dataA = 0;
    private long dataB = 0L;
    private String dataC = null;
    private volatile boolean ready = false;

    @Actor
    public void writer() {
        dataA = 1;
        dataB = 10000L;
        dataC = "Content...";
        // volatile赋值变量指令前会加入内存屏障,也就是说如果其他地方发现ready为true了
        // 那么上面的三个赋值也一定完成了不存在重排序的情况
        ready = true;
    }

    @Observer({
            @Expect(desc = "Normal", expected = 1),
            @Expect(desc = "Impossible", expected = 2),
            @Expect(desc = "ready not true", expected = 3),
    })
    public int reader() {
        int result = 0;
        boolean allIsOK;
        if (ready) {
            allIsOK = (dataA == 1) && (dataB == 10000L) && "Content...".equals(dataC);
            result = allIsOK ? 1 : 2;
        } else {
            result = 3;
        }
        return result;
    }

    public static void main(String[] args) throws InstantiationException, IllegalAccessException {
        // 调用测试工具运行测试代码
        TestRunner.runTest(VolatileOrderingDemo.class);
    }
}
