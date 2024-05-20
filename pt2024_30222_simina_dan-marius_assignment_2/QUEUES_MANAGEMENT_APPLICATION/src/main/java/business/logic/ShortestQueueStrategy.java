package business.logic;

import model.Server;
import model.Task;

import java.util.List;

public class ShortestQueueStrategy implements Strategy {
    @Override
    public void addTask(List<Server> servers, Task task) {
        Server server = null;
        int shortestQueueCapacity = 0;
        for (Server currentServer : servers) {
            if (currentServer.getCapacity() > 0 && shortestQueueCapacity < currentServer.getCapacity()) {
                shortestQueueCapacity = currentServer.getCapacity();
                server = currentServer;
            }
        }
        if (server != null) {
            server.addTask(task);
        } else {
            System.out.println("Could not add task: " + task);
        }
    }
}
