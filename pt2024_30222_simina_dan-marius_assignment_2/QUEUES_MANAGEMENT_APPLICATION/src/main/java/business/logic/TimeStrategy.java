package business.logic;

import model.Server;
import model.Task;

import java.util.List;

public class TimeStrategy implements Strategy {
    @Override
    public void addTask(List<Server> servers, Task task) {
        Server server = null;
        int shortestWaitingTime = Integer.MAX_VALUE;

        for (Server currentServer : servers) {
            if (currentServer.getCapacity() > 0 && shortestWaitingTime > currentServer.getWaitingPeriod()) {
                shortestWaitingTime = currentServer.getWaitingPeriod();
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
