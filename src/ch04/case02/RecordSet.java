package ch04.case02;

public class RecordSet {
    public final int capacity;
    final String[] records;
    int readIndex = 0;
    int writeIndex = 0;

    public RecordSet(int capacity) {
        this.capacity = capacity;
        records = new String[capacity];
    }

    public String nextRecord() {
        String record = null;
        if (readIndex < writeIndex) {
            record = records[readIndex++];
        }
        return record;
    }

    public boolean putRecord(String line) {
        if (writeIndex == capacity) {
            return true;
        }
        records[writeIndex++] = line;
        return false;
    }

    public void reset() {
        readIndex = 0;
        writeIndex = 0;
        for (int i = 0, len = records.length; i < len; i++) {
            records[i] = null;
        }
    }

    public boolean isEmpty() {
        return writeIndex == 0;
    }
}
