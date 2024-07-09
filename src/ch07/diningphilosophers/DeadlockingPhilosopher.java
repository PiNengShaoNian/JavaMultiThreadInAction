package ch07.diningphilosophers;

import util.Debug;

public class DeadlockingPhilosopher extends AbstractPhilosopher {
    public DeadlockingPhilosopher(int id, Chopstick left, Chopstick right) {
        super(id, left, right);
    }

    @Override
    public void eat() {
        synchronized (left) {
            Debug.info("%s is picking up %s on his left...%n", this, left);
            left.pickUp();// 拿起左边的筷子
            synchronized (right) {
                Debug.info("%s is picking up %s on his right...%n", this, right);
                right.pickUp();// 拿起右边的筷子
                doEat();// 同时拿起两根筷子的时候才能够吃饭
                right.putDown();
            }
            left.putDown();
        }
    }
}
