package gui;

import model.Task;

import javax.swing.*;
import java.awt.*;
import java.util.List;
import java.util.concurrent.atomic.AtomicInteger;

public class AnimationPanel extends JPanel {
    private static final int WINDOW_HEIGHT = 700;
    private static final int WINDOW_WIDTH = 1280;
    private static final int STEP_HEIGHT = 23;
    private static final int STEP_WIDTH = 200;
    private static final int TASK_HEIGHT = 18;
    private static final int TASK_WIDTH = 195;
    private static final int MAX_DISPLAYED_TASKS = 30;
    private static final int SERVER_HEIGHT = 490;
    private static final int SERVER_WIDTH = 230;
    private volatile List<Task> tasks;
    private volatile Task[][] serverTasks;
    private Integer currentTime;

    public AnimationPanel() {
        currentTime = 0;
        setPreferredSize(new Dimension(WINDOW_WIDTH, WINDOW_HEIGHT));

        JFrame animationFrame = new JFrame();
        animationFrame.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        animationFrame.getContentPane().add(this);
        animationFrame.setSize(WINDOW_WIDTH, WINDOW_HEIGHT);
        animationFrame.setVisible(true);
    }

    @Override
    public void paintComponent(Graphics graphics) {
        super.paintComponent(graphics);
        graphics.setColor(Color.CYAN);
        graphics.fillRect(0, 0, WINDOW_WIDTH, WINDOW_HEIGHT);
        graphics.setColor(Color.WHITE);
        graphics.fillRect(WINDOW_WIDTH / 2 - 30, 0, 60, 20);
        graphics.setColor(Color.BLACK);
        graphics.drawString("Time: " + currentTime.toString(), WINDOW_WIDTH / 2 - 25, 15);

        drawTasks(graphics);
        drawServers(graphics);
    }

    private void drawServers(Graphics graphics) {
        graphics.setColor(Color.BLUE);
        graphics.fillRect(10, STEP_HEIGHT * 6, SERVER_WIDTH, SERVER_HEIGHT);
        graphics.setColor(Color.WHITE);
        graphics.drawString("SERVER", SERVER_WIDTH / 2 - 10, STEP_HEIGHT * 7 - 10);

        if (serverTasks != null) {
            int y = STEP_HEIGHT * 7 + 5;
            for (int i = 0; i < serverTasks.length; i++) {
                int x = 40;
                graphics.setColor(Color.WHITE);
                graphics.drawString("Q" + (i + 1), 15, y + 10);
                if (serverTasks[i] == null || serverTasks[i].length == 0) {
                    graphics.setColor(Color.RED);
                    graphics.fillRect(x, y, TASK_WIDTH, TASK_HEIGHT);
                    graphics.setColor(Color.BLACK);
                    graphics.drawString("CLOSED", x, y + 12);
                } else {
                    for (int j = 0; j < serverTasks[i].length && x + STEP_WIDTH <= WINDOW_WIDTH; j++) {
                        graphics.setColor(Color.ORANGE);
                        graphics.fillRect(x, y, TASK_WIDTH, TASK_HEIGHT);
                        graphics.setColor(Color.BLACK);
                        graphics.drawString(serverTasks[i][j].toString(), x, y + 12);
                        x += j == 0 ? (STEP_WIDTH + 20) : STEP_WIDTH;
                    }
                }
                y += STEP_HEIGHT;
            }
        }
    }

    private void drawTasks(Graphics graphics) {
        int x = 5, y = TASK_HEIGHT;
        if (tasks != null) {
            int cnt = 0;
            for (Task task : tasks) {
                graphics.setColor(Color.ORANGE);
                graphics.fillRect(x, y, TASK_WIDTH, TASK_HEIGHT);
                graphics.setColor(Color.BLACK);
                graphics.drawString(task.toString(), x, y + 12);

                x += STEP_WIDTH;

                if (x + STEP_WIDTH >= WINDOW_WIDTH) {
                    x = 5;
                    y += STEP_HEIGHT;
                }

                cnt++;
                if (cnt == MAX_DISPLAYED_TASKS) {
                    break;
                }
            }
        }
    }

    public synchronized void setTasks(List<Task> tasks) {
        this.tasks = tasks;
    }

    public synchronized void setServerTasks(Task[][] serverTasks) {
        this.serverTasks = serverTasks;
    }

    public synchronized void setCurrentTime(int currentTime) {
        this.currentTime = currentTime;
    }
}
