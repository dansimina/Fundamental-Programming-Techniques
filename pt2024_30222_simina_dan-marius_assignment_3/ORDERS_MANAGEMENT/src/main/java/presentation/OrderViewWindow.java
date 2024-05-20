package presentation;

import model.Order;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionListener;
import java.util.List;

/**
 * Implement the order viewing window.
 */
public class OrderViewWindow extends JFrame {
    private JPanel contentPane;
    private JButton buttonClose;
    private JTable tableOrders;
    private JButton buttonRefresh;

    ActionListener actionListener;

    public OrderViewWindow(ActionListener actionListener) {
        this.actionListener = actionListener;

        tableOrders.setDefaultEditor(Object.class, null);
        this.add(contentPane);
        this.setSize(900, 500);
        Dimension dim = Toolkit.getDefaultToolkit().getScreenSize();
        this.setLocation(dim.width / 2 - this.getSize().width / 2, dim.height / 2 - this.getSize().height / 2);
        this.setVisible(true);

        initializeButtons();
    }

    private void initializeButtons() {
        buttonClose.setActionCommand("Close");
        buttonClose.addActionListener(actionListener);
        buttonRefresh.setActionCommand("Refresh");
        buttonRefresh.addActionListener(actionListener);
    }

    public void updateTable(List<Order> orderList) {
        PopulateTable.populateTable(orderList, tableOrders);
    }
}
