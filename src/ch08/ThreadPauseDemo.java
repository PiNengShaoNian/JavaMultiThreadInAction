package ch08;

import util.Debug;
import util.Tools;

import java.util.Scanner;

public class ThreadPauseDemo {
    final static PauseControl pc = new PauseControl();

    public static void main(String[] args) {
        final Runnable action = () -> {
            Debug.info("Master,I'm working...");
            Tools.randomPause(300);
        };
        Thread slave = new Thread(() -> {
            try {
                for (; ; ) {
                    pc.pauseIfNecessary(action);
                }
            } catch (InterruptedException e) {
                // 什么也不做
            }
        });

        slave.setDaemon(true);
        slave.start();
        askOnBehaveOfSlave();
    }

    static void askOnBehaveOfSlave() {
        String answer;
        int minPause = 2000;
        try (Scanner sc = new Scanner(System.in)) {
            for (; ; ) {
                Tools.randomPause(8000, minPause);
                Debug.info("Master,may I take a rest now?%n");
                Debug.info("%n(1) OK,you may take a rest%n" + "(2) No, Keep working!%nPress any other key to quit:%n");
                answer = sc.next();
                if (answer.equals("1")) {
                    pc.requestPause();
                    Debug.info("Thank you,my master!");
                    minPause = 8000;
                } else if ("2".equals(answer)) {
                    Debug.info("Yes,my master!");
                    pc.proceed();
                    minPause = 2000;

                } else {
                    break;
                }
            }
        }
        Debug.info("Game over!");
    }
}