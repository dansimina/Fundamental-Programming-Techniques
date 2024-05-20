package model;

import java.util.Objects;

public class Task implements Comparable<Task> {
    private final Integer id;
    private final Integer arrivalTime;
    private Integer serviceTime;

    public Task(Integer id, Integer arrivalTime, Integer serviceTime) {
        this.id = id;
        this.arrivalTime = arrivalTime;
        this.serviceTime = serviceTime;
    }

    public Integer getId() {
        return id;
    }

    public Integer getArrivalTime() {
        return arrivalTime;
    }

    public Integer getServiceTime() {
        return serviceTime;
    }

    public void setServiceTime(Integer serviceTime) {
        this.serviceTime = serviceTime;
    }

    @Override
    public String toString() {
        return "Task(" +
                "id=" + id +
                ", arrival=" + arrivalTime +
                ", service=" + serviceTime +
                ')';
    }

    @Override
    public int compareTo(Task o) {
        if (!Objects.equals(this.arrivalTime, o.arrivalTime)) {
            return this.arrivalTime - o.getArrivalTime();
        }

        if (!Objects.equals(this.serviceTime, o.serviceTime)) {
            return this.serviceTime - o.getServiceTime();
        }

        return this.id - o.getId();
    }
}
