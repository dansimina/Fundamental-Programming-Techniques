package presentation;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

/**
 * Implement the controller for the main menu window.
 */
public class MainWindowController implements ActionListener {
    MainWindow mainWindow;

    ClientWindowController clientWindowController;
    ProductWindowController productWindowController;
    OrderWindowController orderWindowController;
    BillWindowController billWindowController;

    public MainWindowController() {
        mainWindow = new MainWindow(this);
        clientWindowController = new ClientWindowController(this);
        productWindowController = new ProductWindowController(this);
        orderWindowController = new OrderWindowController(this);
        billWindowController = new BillWindowController(this);
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        String command = e.getActionCommand();
        switch (command) {
            case "Client" -> {
                clientWindowController.updateTable();
                clientWindowController.setVisible(true);
                mainWindow.setVisible(false);
            }
            case "Product" -> {
                productWindowController.updateTable();
                productWindowController.setVisible(true);
                mainWindow.setVisible(false);
            }
            case "Order" -> {
                orderWindowController.updateTables();
                orderWindowController.setVisible(true);
                mainWindow.setVisible(false);
            }
            case "Bill" -> {
                billWindowController.updateTable();
                billWindowController.setVisible(true);
                mainWindow.setVisible(false);
            }
        }
    }

    public void setVisible(boolean visible) {
        mainWindow.setVisible(visible);
    }
}
