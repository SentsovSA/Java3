package Lesson5;

import java.util.concurrent.CyclicBarrier;

public class Car implements Runnable {
    private static int CARS_COUNT;

    static {
        CARS_COUNT = 0;
    }


    private Race race;
    private int speed;
    private String name;
    private static boolean winner;
    private CyclicBarrier cb;


    public String getName() {
        return name;
    }

    public int getSpeed() {
        return speed;
    }

    public Car(Race race, int speed, CyclicBarrier cb) {
        this.race = race;
        this.speed = speed;
        CARS_COUNT++;
        this.name = "Участник #" + CARS_COUNT;
        this.cb = cb;
    }

    @Override
    public void run() {
        try {
            System.out.println(this.name + " готовится");
            Thread.sleep(500 + (int) (Math.random() * 800));
            System.out.println(this.name + " готов");
            cb.await();
            cb.await();
            for (int i = 0; i < race.getStages().size(); i++) {
                race.getStages().get(i).go(this);
            }
            checkWin(this);
            cb.await();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private static synchronized void checkWin(Car car) {
        if (!winner) {
            System.out.println(car.name + " - WIN!!!!");
            winner = true;
        }
    }
}
