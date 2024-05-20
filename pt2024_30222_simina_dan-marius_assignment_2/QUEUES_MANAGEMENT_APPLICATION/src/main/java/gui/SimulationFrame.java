package gui;

import business.logic.SelectionPolicy;

import javax.swing.*;
import java.awt.event.ActionListener;

public class SimulationFrame extends JFrame {
    private static final int WINDOW_HEIGHT = 500;
    private static final int WINDOW_WIDTH = 450;
    private JTextField textFieldNumberOfQueues;
    private JTextField textFieldNumberOfClients;
    private JTextField textFieldMaximumArrivalTime;
    private JTextField textFieldMinimumArrivalTime;
    private JTextField textFieldMaximumServiceTime;
    private JTextField textFieldMinimumServiceTime;
    private JTextField textFieldSimulationInterval;
    private JComboBox comboBoxStrategy;
    private JButton buttonStart;
    private JPanel contentPane;
    private JLabel labelNumberOfClients;
    private JLabel labelNumberOfQueues;
    private JLabel labelSimulationInterval;
    private JLabel labelMinimumServiceTime;
    private JLabel labelMaximumServiceTime;
    private JLabel labelMinimumArrivalTime;
    private JLabel labelMaximumArrivalTime;
    private JTextArea textAreaMessages;
    private final ActionListener actionListener;

    public SimulationFrame(ActionListener actionListener) {
        this.actionListener = actionListener;
        this.setSize(WINDOW_WIDTH, WINDOW_HEIGHT);
        textAreaMessages.setEditable(false);
        prepareLabels();
        prepareButtons();
        this.add(contentPane);
        this.setVisible(true);
        this.setDefaultCloseOperation(EXIT_ON_CLOSE);
    }

    private void prepareLabels() {
        labelNumberOfClients.setText("Number of Clients");
        labelNumberOfQueues.setText("Number of Queues");
        labelSimulationInterval.setText("Simulation interval");
        labelMinimumArrivalTime.setText("Minimum arrival time");
        labelMaximumArrivalTime.setText("Maximum arrival time");
        labelMinimumServiceTime.setText("Minimum service time");
        labelMaximumServiceTime.setText("Maximum service time");
    }

    private void prepareButtons() {
        buttonStart.setActionCommand("SIMULATE");
        buttonStart.addActionListener(actionListener);
    }

    public int getNumberOfClients() {
        return Integer.parseInt(textFieldNumberOfClients.getText());
    }

    public int getNumberOfQueues() {
        return Integer.parseInt(textFieldNumberOfQueues.getText());
    }

    public int getMinimumArrivalTime() {
        return Integer.parseInt(textFieldMinimumArrivalTime.getText());
    }

    public int getMaximumArrivalTime() {
        return Integer.parseInt(textFieldMaximumArrivalTime.getText());
    }

    public int getMinimumServiceTime() {
        return Integer.parseInt(textFieldMinimumServiceTime.getText());
    }

    public int getMaximumServiceTime() {
        return Integer.parseInt(textFieldMaximumServiceTime.getText());
    }

    public int getSimulationInterval() {
        return Integer.parseInt(textFieldSimulationInterval.getText());
    }

    public SelectionPolicy getStrategy() {
        if (comboBoxStrategy.getSelectedIndex() == 1) {
            return SelectionPolicy.SHORTEST_TIME;
        }
        return SelectionPolicy.SHORTEST_QUEUE;
    }

    public void setTextAreaMessages(String message) {
        this.textAreaMessages.setText(this.textAreaMessages.getText() + "\n" + message);
    }
}
