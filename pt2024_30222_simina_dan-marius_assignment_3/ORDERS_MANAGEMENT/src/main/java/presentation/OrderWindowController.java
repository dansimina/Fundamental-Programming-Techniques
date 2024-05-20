package presentation;

import business.logic.ClientBLL;
import business.logic.OrderBLL;
import business.logic.ProductBLL;
import model.Order;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

/**
 * The controller for the orders viewing window.
 */
public class OrderWindowController implements ActionListener {
    MainWindowController mainWindowController;
    OrderWindow orderWindow;
    ClientBLL clientBLL;
    ProductBLL productBLL;
    OrderBLL orderBLL;

    public OrderWindowController(MainWindowController mainWindowController) {
        this.mainWindowController = mainWindowController;
        orderWindow = new OrderWindow(this);
        clientBLL = new ClientBLL();
        productBLL = new ProductBLL();
        orderBLL = new OrderBLL();
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        String command = e.getActionCommand();
        switch (command) {
            case "Back" -> {
                mainWindowController.setVisible(true);
                orderWindow.setVisible(false);
            }
            case "Insert" -> insert();
            case "ViewOrders" -> {
                OrderViewWindowController orderViewWindowController = new OrderViewWindowController();
                orderViewWindowController.updateTable();
            }
        }
    }

    private void insert() {
        try {
            int clientId = Integer.parseInt(orderWindow.getTableClients().getValueAt(orderWindow.getTableClients().getSelectedRow(), 0).toString());
            int productId = Integer.parseInt(orderWindow.getTableProducts().getValueAt(orderWindow.getTableProducts().getSelectedRow(), 0).toString());
            int quantity = Integer.parseInt(orderWindow.getTextFieldQuantity().getText());
            int price = productBLL.findProductById(productId).getPrice() * quantity;
            Order order = new Order(quantity, price, clientId, productId);
            orderBLL.insertOrder(order);
            orderWindow.setLabelPrice(Integer.toString(price));
            updateTables();
        } catch (Exception ignored) {
            message();
        }
    }

    public void updateTables() {
        orderWindow.updateTables(clientBLL.findClientAll(), productBLL.findProductAll());
    }

    private void message() {
        JOptionPane.showMessageDialog(null, "Invalid data!\n");
    }

    public void setVisible(boolean visible) {
        orderWindow.setVisible(visible);
    }
}
