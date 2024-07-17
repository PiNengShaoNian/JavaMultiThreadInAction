package ch12;

import java.io.IOException;
import java.io.PrintWriter;
import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.util.Date;

public class LogPrinterV1 {
    final static SimpleDateFormat DATE_FORMAT = new SimpleDateFormat("yyMMddHHmm");
    final static DecimalFormat DECIMAL_FORMAT = new DecimalFormat("00");
    final static int MAX_RECORDS_PER_FILE = 10000;
    private PrintWriter pwr = null;
    private int recordsInFile = MAX_RECORDS_PER_FILE;
    private int fileSeq = 0;

    public void print(String record) {
        PrintWriter writer;
        try {
            synchronized (this) {
                writer = getPrintWriter();
                writer.println(record);
                recordsInFile++;
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public synchronized void shutdown() {
        if (pwr != null) {
            pwr.close();
        }
    }

    private PrintWriter getPrintWriter() throws IOException {
        PrintWriter writer;
        if (recordsInFile == MAX_RECORDS_PER_FILE) {
            String newFileName = retrieveFileName();
            writer = new PrintWriter(newFileName);
            recordsInFile = 0;
            if (pwr != null) {
                pwr.flush();
                pwr.close();
            }
            pwr = writer;
        } else {
            writer = pwr;
        }
        return writer;
    }

    protected String retrieveFileName() {
        String fileName;
        fileName = DATE_FORMAT.format(new Date()) + DECIMAL_FORMAT.format(fileSeq) + ".log";
        if (++fileSeq > 99) {
            fileSeq = 0;
        }
        return fileName;
    }
}
