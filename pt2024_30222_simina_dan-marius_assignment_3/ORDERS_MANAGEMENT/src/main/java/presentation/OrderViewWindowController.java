package presentation;

import business.logic.OrderBLL;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

/**
 * The controller for the orders viewing window.
 */
public class OrderViewWindowController implements ActionListener {
    OrderViewWindow orderViewWindow;
    OrderBLL orderBLL;

    public OrderViewWindowController() {
        orderViewWindow = new OrderViewWindow(this);
        orderBLL = new OrderBLL();
    }

    public void updateTable() {
        orderViewWindow.updateTable(orderBLL.findOrderAll());
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        String command = e.getActionCommand();
        switch (command) {
            case "Close" -> orderViewWindow.dispose();
            case "Refresh" -> updateTable();
        }
    }
}
