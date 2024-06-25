package ch1;

public class WelcomeApp {
    public static void main(String[] args) {
        // 创建线程
        Thread welcomeThread = new WelcomeThread();

        welcomeThread.start();

        // 输出“当前线程”的线程名称
        System.out.printf("1.Welcome! I'm %s.%n", Thread.currentThread().getName());
    }
}

class WelcomeThread extends Thread {
    @Override
    public void run() {
        System.out.printf("2.Welcome! I'm %s.%n", Thread.currentThread().getName());
    }
}
