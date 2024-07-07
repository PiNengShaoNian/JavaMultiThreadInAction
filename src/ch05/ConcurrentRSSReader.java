package ch05;

import util.Tools;

import java.io.*;
import java.net.HttpURLConnection;
import java.net.URL;
import java.nio.ByteBuffer;
import java.nio.channels.Channels;
import java.nio.channels.ReadableByteChannel;
import java.nio.channels.WritableByteChannel;

public class ConcurrentRSSReader {
    public static void main(String[] args) throws Exception {
        final int argc = args.length;
        String url = argc > 0 ? args[0] : "http://lorem-rss.herokuapp.com/feed";

        // 从网络加载RSS数据
        InputStream in = loadRSS(url);
    }

    private static InputStream loadRSS(final String url) throws IOException {
        final PipedInputStream in = new PipedInputStream();
        // 以in为参数创建PipedOutputStream实例
        final PipedOutputStream pos = new PipedOutputStream(in);

        Thread workerThread = new Thread(() -> {
            try {
                doDownload(url, pos);
            } catch (Exception e) {
                // RSS数据下载过程中出现异常时，关闭相关输出流和输入流。
                // 注意，此处我们不能像平常那样在finally块中关闭相关输出流
                Tools.silentClose(pos, in);
                e.printStackTrace();
            }
        }, "rss-loader");

        workerThread.start();
        return in;
    }

    static BufferedInputStream issueRequest(String url) throws Exception {
        URL requestURL = new URL(url);
        final HttpURLConnection conn = (HttpURLConnection) requestURL.openConnection();
        conn.setConnectTimeout(2000);
        conn.setReadTimeout(2000);
        conn.setRequestMethod("GET");
        conn.setRequestProperty("Connection", "close");
        conn.setDoInput(true);
        conn.connect();
        int statusCode = conn.getResponseCode();
        if (statusCode != HttpURLConnection.HTTP_OK) {
            conn.disconnect();
            throw new Exception("Server exception,status code:" + statusCode);
        }

        return new BufferedInputStream(conn.getInputStream()) {
            @Override
            public void close() throws IOException {
                try {
                    super.close();
                } finally {
                    conn.disconnect();
                }
            }
        };
    }

    static void doDownload(String url, OutputStream os) throws Exception {
        ReadableByteChannel readChannel = null;
        WritableByteChannel writeChannel = null;
        try {
            // 对指定的URL发起HTTP请求
            BufferedInputStream in = issueRequest(url);
            readChannel = Channels.newChannel(in);
            ByteBuffer buf = ByteBuffer.allocate(1024);
            writeChannel = Channels.newChannel(os);
            while (readChannel.read(buf) > 0) {
                buf.flip();
                writeChannel.write(buf);
                buf.clear();
            }
        } finally {
            Tools.silentClose(readChannel, writeChannel);
        }
    }
}
