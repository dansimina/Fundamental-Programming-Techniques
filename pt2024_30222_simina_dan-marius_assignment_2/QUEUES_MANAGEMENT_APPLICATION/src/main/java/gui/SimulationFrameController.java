package gui;

import business.logic.SimulationManager;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class SimulationFrameController implements ActionListener {
    private final SimulationFrame simulationFrame;
    private final SimulationManager simulationManager;

    public SimulationFrameController(SimulationManager simulationManager) {
        this.simulationManager = simulationManager;
        simulationFrame = new SimulationFrame(this);
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        switch (e.getActionCommand()) {
            case "SIMULATE" -> {
                simulationFrame.setTextAreaMessages("START");
                newSimulation();
            }

        }
    }

    private void newSimulation() {
        try {
            if (simulationFrame.getNumberOfQueues() > 20) {
                simulationFrame.setTextAreaMessages("NUMBER OF QUEUES MUST BE UNDER 20");
                return;
            }
            simulationManager.startSimulation(simulationFrame.getNumberOfClients(), simulationFrame.getNumberOfQueues(), simulationFrame.getSimulationInterval(), simulationFrame.getMinimumArrivalTime(), simulationFrame.getMaximumArrivalTime(), simulationFrame.getMinimumServiceTime(), simulationFrame.getMaximumServiceTime(), simulationFrame.getStrategy());

        } catch (Exception e) {
            setMessage("INVALID INPUT");
        }
    }

    public void setMessage(String message) {
        simulationFrame.setTextAreaMessages(message);
    }
}
