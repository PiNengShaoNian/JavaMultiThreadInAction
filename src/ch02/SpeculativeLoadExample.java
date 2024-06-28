package ch02;

public class SpeculativeLoadExample {
    private boolean ready = false;
    private int[] data = new int[]{1, 2, 3, 4, 5, 6, 7, 8};

    public void writer() {
        int[] newData = new int[]{1, 2, 3, 4, 5, 6, 7, 8};
        for (int i = 0; i < newData.length; i++) {
            // 此处包含读内存的操作
            newData[i] = newData[i] - i;
        }
        data = newData;
        // 此处包含写内存的操作
        ready = true;
    }

    public int reader() {
        int sum = 0;
        int[] snapshot;
        if (ready) {
            snapshot = data;
            // 由于分支预测可能会存在先执行if块中的逻辑而在去判断ready是否为真的情况
            for (int i = 0; i < snapshot.length; i++) {
                sum += snapshot[i];
            }
        }
        return sum;
    }
}
