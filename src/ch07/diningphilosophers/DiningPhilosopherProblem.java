package ch07.diningphilosophers;

import util.Debug;

import java.lang.reflect.Constructor;

public class DiningPhilosopherProblem {
    public static void main(String[] args) throws Exception {
        int numOfPhilosophers;
        numOfPhilosophers = args.length > 0 ? Integer.parseInt(args[0]) : 2;
        // 创建筷子
        Chopstick[] chopsticks = new Chopstick[numOfPhilosophers];
        for (int i = 0; i < numOfPhilosophers; i++) {
            chopsticks[i] = new Chopstick(i);
        }

        String philosopherImplClassName = System.getProperty("x.philo.impl");
        if (null == philosopherImplClassName) {
            philosopherImplClassName = "FixedPhilosopher";
        }
        Debug.info("Using %s as implementation.", philosopherImplClassName);
        for (int i = 0; i < numOfPhilosophers; i++) {
            // 创建哲学家
            createPhilosopher(philosopherImplClassName, i, chopsticks);
        }
    }

    private static void createPhilosopher(String philosopherImplClassName, int id, Chopstick[] chopsticks)
            throws Exception {
        int numOfPhilosophers = chopsticks.length;
        @SuppressWarnings("unchecked")
        Class<Philosopher> philosopherClass = (Class<Philosopher>) Class
                .forName(DiningPhilosopherProblem.class.getPackage().getName() + "."
                        + philosopherImplClassName);
        Constructor<Philosopher> constructor = philosopherClass.getConstructor(int.class, Chopstick.class, Chopstick.class);
        Philosopher philosopher = constructor.newInstance(id, chopsticks[id],
                chopsticks[(id + 1)
                        % numOfPhilosophers]);
        philosopher.start();
    }
}
