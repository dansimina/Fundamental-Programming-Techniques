package business.logic;

import model.Server;
import model.Task;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class Scheduler {
    private final List<Server> servers;
    private final Integer maxNoServers;
    private final Integer maxTasksPerServer;
    private Strategy strategy;
    private final ExecutorService executorService;

    public Scheduler(int maxNoServers, int maxTasksPerServer) {
        this.servers = new ArrayList<>();
        this.maxNoServers = maxNoServers;
        this.maxTasksPerServer = maxTasksPerServer;
        this.strategy = new ShortestQueueStrategy();

        executorService = Executors.newFixedThreadPool(maxNoServers);
        for (int i = 0; i < this.maxNoServers; i++) {
            Server server = new Server(this.maxTasksPerServer);
            servers.add(server);
            executorService.submit(server);
        }
    }

    public void stopServers() {
        for (Server server : servers) {
            server.stop();
        }
        executorService.shutdown();
    }

    public void changeStrategy(SelectionPolicy policy) {
        if (policy == SelectionPolicy.SHORTEST_QUEUE) {
            strategy = new ShortestQueueStrategy();
        }
        if (policy == SelectionPolicy.SHORTEST_TIME) {
            strategy = new TimeStrategy();
        }
    }

    public void dispatchTask(Task task) {
        strategy.addTask(servers, task);
    }

    public List<Server> getServers() {
        return servers;
    }

    public Integer getMaxNoServers() {
        return maxNoServers;
    }

    public Integer getMaxTasksPerServer() {
        return maxTasksPerServer;
    }

    public Strategy getStrategy() {
        return strategy;
    }
}
