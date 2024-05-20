package business.logic;

import gui.AnimationPanel;
import gui.SimulationFrameController;
import model.Server;
import model.Task;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.util.*;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class SimulationManager implements Runnable {
    private int timeLimit;
    private int numberOfClients;
    private int numberOfQueues;
    private int maxArrivalTime;
    private int minArrivalTime;
    private int maxServiceTime;
    private int minServiceTime;
    private int currentTime;
    private SelectionPolicy selectionPolicy;
    private final SimulationFrameController simulationFrameController;
    private Scheduler scheduler;
    private List<Task> tasks;
    private BufferedWriter out;
    private final AnimationPanel animationPanel;
    private Task[][] serverTasks;
    private int pickHourCapacity;
    private int pickHour;
    private double averageWaitingTime;
    private double averageServiceTime;

    public SimulationManager() {
        currentTime = -1;
        scheduler = null;
        simulationFrameController = new SimulationFrameController(this);
        animationPanel = new AnimationPanel();
    }

    public void startSimulation(int numberOfClients, int numberOfQueues, int timeLimit, int minArrivalTime, int maxArrivalTime, int minServiceTime, int maxServiceTime, SelectionPolicy selectionPolicy) {
        this.numberOfClients = numberOfClients;
        this.numberOfQueues = numberOfQueues;
        this.timeLimit = timeLimit;
        this.minArrivalTime = minArrivalTime;
        this.maxArrivalTime = maxArrivalTime;
        this.minServiceTime = minServiceTime;
        this.maxServiceTime = maxServiceTime;
        this.selectionPolicy = selectionPolicy;

        averageWaitingTime = 0;
        averageServiceTime = 0;
        pickHour = 0;
        pickHourCapacity = Integer.MAX_VALUE;

        scheduler = new Scheduler(this.numberOfQueues, this.numberOfClients);
        scheduler.changeStrategy(selectionPolicy);
        tasks = new ArrayList<>();
        serverTasks = new Task[this.numberOfQueues][];
        currentTime = 0;

        generateRandomTasks();
        newFile();

        this.run();
    }

    private void generateRandomTasks() {
        Random randomArrivalTime = new Random();
        Random randomProcessingTime = new Random();
        tasks.clear();

        for (int i = 0; i < numberOfClients; i++) {
            int arrivalTime = randomArrivalTime.nextInt(maxArrivalTime - minArrivalTime) + minArrivalTime;
            int processingTime = randomProcessingTime.nextInt(maxServiceTime - minServiceTime) + minServiceTime;
            tasks.add(new Task(i, arrivalTime, processingTime));
        }

        Collections.sort(tasks);
    }

    private void newFile() {
        String file = "output\\" + System.currentTimeMillis() + selectionPolicy + ".txt";
        try {
            out = new BufferedWriter(new FileWriter(file));
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    private void print() {
        try {
            out.write("Time " + currentTime + "\n");
            out.write("Tasks " + tasks + "\n");
            for (int i = 0; serverTasks != null && i < serverTasks.length; i++) {
                out.write("Server" + Arrays.toString(serverTasks[i]) + "\n");
            }
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    private void extractServerInfo() {
        int i = 0;
        int totalCapacity = 0;
        List<Server> servers = scheduler.getServers();
        for (Server server : servers) {
            serverTasks[i++] = server.getTasks();
            totalCapacity += server.getCapacity();
        }

        if(totalCapacity < pickHourCapacity) {
            pickHourCapacity = totalCapacity;
            pickHour = currentTime;
        }
    }

    @Override
    public void run() {
        while (currentTime >= 0 && currentTime <= timeLimit && (!tasks.isEmpty() || !isEmptyServerTasks())) {
            try {
                Thread.sleep(1000);
            } catch (InterruptedException ignored) {
            }
            addTasks();
            extractServerInfo();
            print();

            animationPanel.setTasks(tasks);
            animationPanel.setCurrentTime(currentTime);
            animationPanel.setServerTasks(serverTasks);
            animationPanel.paintImmediately(0, 0, 1280, 700);

            currentTime++;
        }

        if(currentTime > 0) {
            averageWaitingTime = averageWaitingTime();
            averageServiceTime = averageServiceTime();
            printStatistics();
            closeFile();
        }

        if (scheduler != null) {
            scheduler.stopServers();
        }

        if (currentTime >= 0) {
            simulationFrameController.setMessage("FINISHED");
        }
    }

    private boolean isEmptyServerTasks() {
        for(Task[] task: serverTasks) {
            if(task.length > 0)
                return false;
        }
        return true;
    }

    private void addTasks() {
        List<Task> remainingTasks = new ArrayList<>();
        for (Task task : tasks) {
            if (task.getArrivalTime() == currentTime) {
                scheduler.dispatchTask(task);
            } else {
                remainingTasks.add(task);
            }
        }
        tasks = remainingTasks;
    }

    private double averageWaitingTime() {
        double totalAverageTime = 0;
        List<Server> servers = scheduler.getServers();
        for(Server server : servers) {
            totalAverageTime += server.averageWaitingTime();
        }

        return totalAverageTime / numberOfQueues;
    }

    private double averageServiceTime() {
        double totalServiceTime = 0;
        List<Server> servers = scheduler.getServers();
        for(Server server : servers) {
            totalServiceTime += server.averageServiceTime();
        }

        return totalServiceTime / numberOfQueues;
    }

    private void printStatistics() {
        try {
            out.write("\nAverage waiting time: " + averageWaitingTime + "\n");
            out.write("Pick hour: " + pickHour + "\n");
            out.write("Average service time: " + averageServiceTime + "\n");

            simulationFrameController.setMessage("Average waiting time: " + averageWaitingTime);
            simulationFrameController.setMessage("Pick hour: " + pickHour);
            simulationFrameController.setMessage("Average service time: " + averageServiceTime);

        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    private void closeFile() {
        if (out != null) {
            try {
                out.close();
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        }
    }

    public static void main(String[] args) {
        SimulationManager gen = new SimulationManager();
        ExecutorService executor = Executors.newFixedThreadPool(1);
        executor.submit(gen);
    }
}