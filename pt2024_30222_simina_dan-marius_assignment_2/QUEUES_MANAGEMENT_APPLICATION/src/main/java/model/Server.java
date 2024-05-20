package model;

import java.util.concurrent.BlockingQueue;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.atomic.AtomicBoolean;
import java.util.concurrent.atomic.AtomicInteger;

public class Server implements Runnable {
    private final BlockingQueue<Task> tasks;
    private final AtomicInteger waitingPeriod;
    private final AtomicBoolean running;
    private final AtomicInteger totalWaitingTime;
    private final AtomicInteger totalClients;
    private final AtomicInteger totalServiceTime;
    private final AtomicInteger totalServedClients;

    public Server(int maxNumberOfTasks) {
        tasks = new LinkedBlockingQueue<>(maxNumberOfTasks);
        waitingPeriod = new AtomicInteger(0);
        running = new AtomicBoolean(true);
        totalWaitingTime = new AtomicInteger(0);
        totalClients = new AtomicInteger(0);
        totalServiceTime = new AtomicInteger(0);
        totalServedClients = new AtomicInteger(0);
    }

    public void addTask(Task newTask) {
        tasks.add(newTask);
        totalWaitingTime.getAndAdd(waitingPeriod.intValue());
        waitingPeriod.addAndGet(newTask.getServiceTime());
        totalClients.getAndIncrement();
    }

    @Override
    public void run() {
        while (running.get()) {
            try {
                Thread.sleep(1000);
            } catch (InterruptedException ignored) {
            }

            if (!tasks.isEmpty()) {
                Task currentTask = tasks.peek();
                int serviceTime = currentTask.getServiceTime();

                while (currentTask.getServiceTime() > 1) {
                    waitingPeriod.getAndDecrement();
                    currentTask.setServiceTime(currentTask.getServiceTime() - 1);
                    try {
                        Thread.sleep(1000);
                    } catch (InterruptedException ignored) {
                    }
                }
                tasks.poll();
                waitingPeriod.getAndDecrement();
                totalServiceTime.getAndAdd(serviceTime);
                totalServedClients.getAndIncrement();

            }
        }
    }

    public int getCapacity() {
        return tasks.remainingCapacity();
    }

    public Task[] getTasks() {
        return tasks.toArray(Task[]::new);
    }

    public int getWaitingPeriod() {
        return waitingPeriod.intValue();
    }

    public void stop() {
        running.set(false);
    }

    public double averageWaitingTime() {
        if(totalClients.intValue() == 0)
            return 0;
        return (double) totalWaitingTime.intValue() / totalClients.intValue();
    }

    public double averageServiceTime() {
        if(totalServedClients.intValue() == 0)
            return 0;
        return (double) totalServiceTime.intValue() / totalServedClients.intValue();
    }
}
