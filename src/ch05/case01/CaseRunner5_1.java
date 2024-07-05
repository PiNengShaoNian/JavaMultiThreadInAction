package ch05.case01;

import util.Tools;

public class CaseRunner5_1 {
    final static AlarmAgent alarmAgent;
    static {
        alarmAgent = AlarmAgent.getInstance();
        alarmAgent.init();
    }

    public static void main(String[] args) throws InterruptedException {
        alarmAgent.sendAlarm("Database offline!");
        Tools.randomPause(12000);
        alarmAgent.sendAlarm("XXX service unreachable!");
    }
}
